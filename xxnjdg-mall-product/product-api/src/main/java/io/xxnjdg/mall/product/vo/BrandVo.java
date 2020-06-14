package io.xxnjdg.mall.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 19:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BrandVo {
    /**
     * "brandId": 0,
     * "brandName": "string",
     */
    private Long brandId;
    private String  brandName;
}
