package io.xxnjdg.mall.search.service;

import io.xxnjdg.mall.search.po.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/17 10:15
 */
public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> esSkuModels) ;
}
