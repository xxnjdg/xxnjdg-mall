package io.xxnjdg.mall.search.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/22 11:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SearchParam {

    private String keyword;
    private List<Long> brandId;
    private Long catalog3Id;

    private List<String> attrs;
    private String skuPrice;
    private Boolean count;
    private String comprehensive;
    private Integer hasStock;
    private Integer pageNum;

    /**
     * 排序条件
     *
     * 以下均又分为升序/降序
     * saleCount
     * skuPrice
     * hotScore
     * // 排序条件
     */
    private String sort;
}
