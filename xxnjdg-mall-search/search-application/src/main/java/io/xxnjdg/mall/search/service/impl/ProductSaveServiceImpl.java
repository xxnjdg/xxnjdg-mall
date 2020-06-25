package io.xxnjdg.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import io.xxnjdg.mall.search.config.RestHighLevelClientConfiguration;
import io.xxnjdg.mall.search.constant.ESConstant;
import io.xxnjdg.mall.search.po.SkuEsModel;
import io.xxnjdg.mall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/17 10:18
 */
@Slf4j
@Service
@org.apache.dubbo.config.annotation.Service(protocol = "dubbo", version = "1.0.0",validation = "true")
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean productStatusUp(List<SkuEsModel> esSkuModels) {
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel esSkuModel : esSkuModels) {
            IndexRequest indexRequest = new IndexRequest(ESConstant.PRODUCT_INDEX);
            indexRequest.id(esSkuModel.getSkuId().toString());
            String s = JSON.toJSONString(esSkuModel);
            indexRequest.source(s, XContentType.JSON);

            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(bulkRequest, RestHighLevelClientConfiguration.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO 如果批量保存出现错误
        boolean result = bulkResponse.hasFailures();
        List<String> collect = Arrays.stream(bulkResponse.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        log.error("商品上架完成, {}", collect);
        return result;
    }
}
