package io.xxnjdg.mall.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 16:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Bounds {
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
