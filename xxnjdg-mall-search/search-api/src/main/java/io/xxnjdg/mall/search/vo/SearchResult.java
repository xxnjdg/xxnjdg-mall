package io.xxnjdg.mall.search.vo;

import io.xxnjdg.mall.search.po.SkuEsModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/22 11:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SearchResult implements Serializable {

    private List<SkuEsModel> products;
    private List<Attr> attrs;
    private List<Brand> brands;
    private List<Category> catalogs;
    private String keyword;

    private Integer topPrice;
    private Integer lowPrice;

    private Boolean saleCount;

    private Boolean hasStock;

    private Boolean hotScore;

    private Boolean skuPrice;

    /**
     * 分页信息
     */
    private Integer pageNum;

    private Long totalRecords;

    private Integer totalPages;

    private List<Integer> pageNavs;
}
