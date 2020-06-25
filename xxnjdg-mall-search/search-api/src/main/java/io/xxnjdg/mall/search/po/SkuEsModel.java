package io.xxnjdg.mall.search.po;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/15 17:56
 */
@Data
@ToString
public class SkuEsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long skuId;

    private Long spuId;

    private String skuTitle;

    private BigDecimal skuPrice;

    private String skuImg;

    private Long saleCount;

    private Boolean hasStock;

    private Long hotScore;

    private Long brandId;

    private String  brandName;

    private String brandImg;

    private Long catalogId;

    private String catalogName;

    private List<Attrs> attrs;

}
