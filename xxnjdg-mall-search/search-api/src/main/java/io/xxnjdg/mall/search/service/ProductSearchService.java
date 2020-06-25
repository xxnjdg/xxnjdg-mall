package io.xxnjdg.mall.search.service;

import io.xxnjdg.mall.search.dto.SearchParam;
import io.xxnjdg.mall.search.vo.SearchResult;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/22 15:16
 */
public interface ProductSearchService {
    SearchResult searchProduct(SearchParam searchParam);
}
