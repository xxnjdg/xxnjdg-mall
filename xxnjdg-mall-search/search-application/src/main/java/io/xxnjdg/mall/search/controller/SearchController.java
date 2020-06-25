package io.xxnjdg.mall.search.controller;

import io.xxnjdg.mall.search.constant.ESConstant;
import io.xxnjdg.mall.search.dto.SearchParam;
import io.xxnjdg.mall.search.service.ProductSearchService;
import io.xxnjdg.mall.search.vo.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/19 21:12
 */
@Controller
public class SearchController {

    @Autowired
    private ProductSearchService productSearchService;

    @Autowired
    private RestHighLevelClient client;

    @GetMapping("/list.html")
    public String list(SearchParam searchParam, Model model) {
        SearchResult searchResult = productSearchService.searchProduct(searchParam);
        model.addAttribute("result",searchResult);
        return "list";
    }

    @GetMapping("/test")
    @ResponseBody
    public SearchResult list1(SearchParam searchParam) {
        return productSearchService.searchProduct(searchParam);
    }

}
