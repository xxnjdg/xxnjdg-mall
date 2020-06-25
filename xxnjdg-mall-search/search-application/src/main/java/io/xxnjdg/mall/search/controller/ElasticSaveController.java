package io.xxnjdg.mall.search.controller;

import io.xxnjdg.common.utils.R;
import io.xxnjdg.mall.search.po.SkuEsModel;
import io.xxnjdg.mall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/17 10:08
 */
@RequestMapping("/search/save")
@RestController
@Slf4j
public class ElasticSaveController {

    @Autowired
    private ProductSaveService productSaveService;

    /**
     * 列表
     */
    @PostMapping("/product")
    public R list(@RequestBody List<SkuEsModel> skuEsModelList) {
        boolean result;

        result = productSaveService.productStatusUp(skuEsModelList);


        if (result) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}
