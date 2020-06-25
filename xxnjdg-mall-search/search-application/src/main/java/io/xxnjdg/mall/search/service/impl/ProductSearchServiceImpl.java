package io.xxnjdg.mall.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.xxnjdg.mall.search.constant.ESConstant;
import io.xxnjdg.mall.search.dto.SearchParam;
import io.xxnjdg.mall.search.po.SkuEsModel;
import io.xxnjdg.mall.search.service.ProductSearchService;
import io.xxnjdg.mall.search.vo.Attr;
import io.xxnjdg.mall.search.vo.Brand;
import io.xxnjdg.mall.search.vo.Category;
import io.xxnjdg.mall.search.vo.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/22 15:16
 */
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public SearchResult searchProduct(SearchParam searchParam) {

        SearchRequest searchRequest = new SearchRequest(ESConstant.PRODUCT_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotBlank(searchParam.getKeyword())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle", searchParam.getKeyword()));
        }

        if (searchParam.getCatalog3Id() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("catalogId", searchParam.getCatalog3Id()));
        }

        if (!CollectionUtils.isEmpty(searchParam.getBrandId())) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", searchParam.getBrandId()));
        }

        if (!CollectionUtils.isEmpty(searchParam.getAttrs()) && searchParam.getAttrs().size() > 0) {
            for (String attr : searchParam.getAttrs()) {
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                // attrs=1_5寸:6寸&attrs=2_16GB:8GB
                String[] result = attr.split("_");
                String attrId = result[0];
                String[] attrValues = result[1].split(":");
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));

                // 每一个必须都得生成一个 nested 查询
                NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
                boolQueryBuilder.filter(nestedQueryBuilder);
            }
        }

        if (searchParam.getHasStock() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() > 0));
        }
        String priceRange = searchParam.getSkuPrice();
        if (StringUtils.isNotBlank(priceRange)) {
            String[] s = priceRange.split("_");
            RangeQueryBuilder skuPrice = QueryBuilders.rangeQuery("skuPrice");
            if (s.length == 1) {
                if (priceRange.startsWith("_")) {
                    skuPrice.lte(s[0]);
                }
                if (priceRange.endsWith("_")) {
                    skuPrice.gte(s[0]);
                }
                boolQueryBuilder.filter(skuPrice);
            } else if (s.length == 2) {
                skuPrice.gte(s[0]).lte(s[1]);
                boolQueryBuilder.filter(skuPrice);
            }
        }

        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(ESConstant.PRODUCT_PAGE_SIZE);
        Integer page = searchParam.getPageNum();
        if (page != null && page > 0) {
            searchSourceBuilder.from((page - 1) * ESConstant.PRODUCT_PAGE_SIZE);
        }

        //搜索
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("skuTitle");
        highlightBuilder.field(highlightUser);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //排序
//        if (searchParam.getCount() != null) {
//            searchSourceBuilder.sort("saleCount", SortOrder.DESC);
//        }

        String sort = searchParam.getSort();
        if (StringUtils.isNotBlank(sort)) {
            String[] s = sort.split("_");
            if (s.length == 2 &&
                    ("saleCount".equals(s[0]) || "skuPrice".equals(s[0]) || "hotScore".equals(s[0])) &&
                    ("desc".equals(s[1]) || "asc".equals(s[1]))) {
                searchSourceBuilder.sort(s[0], "desc".equals(s[1]) ? SortOrder.DESC : SortOrder.ASC);
            }
        }

        //聚合
        //分类
        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("group_category").field("catalogId");
        categoryAgg.subAggregation(AggregationBuilders.terms("category_name_agg").field("catalogName").size(1));
        searchSourceBuilder.aggregation(categoryAgg);

        //品牌
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("group_brand").field("brandId");
        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        searchSourceBuilder.aggregation(brandAgg);
        //属性
        NestedAggregationBuilder attrListAgg = AggregationBuilders.nested("group_attr", "attrs");
        TermsAggregationBuilder attrIdListAgg = AggregationBuilders.terms("group_attr_id").field("attrs.attrId");
        attrIdListAgg.subAggregation(AggregationBuilders.terms("group_attr_name").field("attrs.attrName").size(1));
        attrIdListAgg.subAggregation(AggregationBuilders.terms("group_attr_value").field("attrs.attrValue").size(1));
        attrListAgg.subAggregation(attrIdListAgg);
        searchSourceBuilder.aggregation(attrListAgg);

        searchSourceBuilder.query(boolQueryBuilder);
        System.out.println(searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        SearchResult searchResult = new SearchResult();
        SearchHits hits = search.getHits();

        SearchHit[] searchHits = hits.getHits();
        searchResult.setPageNum(1);
        searchResult.setTotalPages(1);
        searchResult.setPageNavs(Collections.singletonList(1));
        if (StringUtils.isNotBlank(searchParam.getKeyword())) {
            searchResult.setKeyword(searchParam.getKeyword());
        }
        if (searchHits == null || searchHits.length <= 0) {
            return searchResult;
        }

        // 设置分页信息
        if (searchParam.getPageNum() != null && searchParam.getPageNum() > 0) {
            searchResult.setPageNum(searchParam.getPageNum());
        }

        long totalRecords = hits.getTotalHits().value;
        searchResult.setTotalRecords(totalRecords);
        int totalPages = totalRecords % ESConstant.PRODUCT_PAGE_SIZE == 0 ? (int) totalRecords / ESConstant.PRODUCT_PAGE_SIZE : (int) totalRecords / ESConstant.PRODUCT_PAGE_SIZE + 1;
        searchResult.setTotalPages(totalPages);

        List<Integer> pageNavs = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNavs.add(i);
        }
        searchResult.setPageNavs(pageNavs);

        List<SkuEsModel> skuEsModelList = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            SkuEsModel skuEsModel = JSONObject.parseObject(searchHit.getSourceAsString(), SkuEsModel.class);
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            HighlightField skuTitle = highlightFields.get("skuTitle");
            if (skuTitle != null) {
                Text[] fragments = skuTitle.fragments();
                skuEsModel.setSkuTitle(fragments[0].toString());
            }
            skuEsModelList.add(skuEsModel);
        }
        searchResult.setProducts(skuEsModelList);


        //分类
        Aggregations aggregations = search.getAggregations();
        ParsedLongTerms groupCategory = aggregations.get("group_category");
        if (groupCategory != null) {
            List<? extends Terms.Bucket> buckets = groupCategory.getBuckets();
            if (!CollectionUtils.isEmpty(buckets)) {
                List<Category> categoryList = new ArrayList<>();
                for (Terms.Bucket bucket : buckets) {
                    List<String> categoryNameAgg = getProperties(bucket, "category_name_agg");
                    if (categoryNameAgg == null) {
                        continue;
                    }

                    Category category = new Category()
                            .setCatalogId((Long) bucket.getKeyAsNumber())
                            .setCatalogName(categoryNameAgg.get(0));

                    categoryList.add(category);

                }
                searchResult.setCatalogs(categoryList);
            }
        }

        ParsedLongTerms groupBrand = aggregations.get("group_brand");
        if (groupBrand != null) {
            List<? extends Terms.Bucket> buckets = groupBrand.getBuckets();
            if (!CollectionUtils.isEmpty(buckets)) {
                List<Brand> brandList = new ArrayList<>();
                for (Terms.Bucket bucket : buckets) {
                    List<String> brandNameAgg = getProperties(bucket, "brand_name_agg");
                    if (brandNameAgg == null) {
                        continue;
                    }

                    List<String> brandImgAgg = getProperties(bucket, "brand_img_agg");
                    if (brandImgAgg == null) {
                        continue;
                    }

                    Brand brand = new Brand()
                            .setBrandId((Long) bucket.getKeyAsNumber())
                            .setBrandImg(brandImgAgg.get(0))
                            .setBrandName(brandNameAgg.get(0));

                    brandList.add(brand);
                }
                searchResult.setBrands(brandList);
            }
        }

        ParsedNested groupAttr = aggregations.get("group_attr");
        if (groupAttr != null) {
            ParsedLongTerms groupAttrId = groupAttr.getAggregations().get("group_attr_id");
            if (groupAttrId != null) {
                List<? extends Terms.Bucket> buckets = groupAttrId.getBuckets();
                if (!CollectionUtils.isEmpty(buckets)) {
                    List<Attr> attrList = new ArrayList<>();
                    for (Terms.Bucket bucket : buckets) {
                        List<String> groupAttrName = getProperties(bucket, "group_attr_name");
                        if (groupAttrName == null) {
                            continue;
                        }

                        List<String> groupAttrValue = getProperties(bucket, "group_attr_value");
                        if (groupAttrValue == null) {
                            continue;
                        }

                        Attr attr = new Attr()
                                .setAttrId((Long) bucket.getKeyAsNumber())
                                .setAttrName(groupAttrName.get(0))
                                .setAttrValue(groupAttrValue);

                        attrList.add(attr);
                    }
                    searchResult.setAttrs(attrList);
                }
            }
        }

        return searchResult;
    }

    private List<String> getProperties(Terms.Bucket bucket, String properties) {
        ParsedStringTerms agg = bucket.getAggregations().get(properties);
        if (agg == null) {
            return null;
        }
        List<? extends Terms.Bucket> buckets = agg.getBuckets();
        if (CollectionUtils.isEmpty(buckets)) {
            return null;
        }
        ArrayList<String> value = new ArrayList<>();
        for (Terms.Bucket bucket1 : buckets) {
            value.add(bucket1.getKeyAsString());
        }
        return value;
    }
}
