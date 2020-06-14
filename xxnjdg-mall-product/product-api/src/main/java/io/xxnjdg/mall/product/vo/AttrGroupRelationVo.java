package io.xxnjdg.mall.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 15:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AttrGroupRelationVo {

    //"attrId":1,"attrGroupId":2
    private Long attrId;
    private Long attrGroupId;
}
