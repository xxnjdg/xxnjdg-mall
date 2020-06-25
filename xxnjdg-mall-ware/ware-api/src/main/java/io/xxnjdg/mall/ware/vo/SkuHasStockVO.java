package io.xxnjdg.mall.ware.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/17 9:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SkuHasStockVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long skuId;
    private Boolean hasStock;
}
