package io.xxnjdg.mall.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 16:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Attr {
    private Long attrId;
    private String attrName;
    private String attrValue;
}
