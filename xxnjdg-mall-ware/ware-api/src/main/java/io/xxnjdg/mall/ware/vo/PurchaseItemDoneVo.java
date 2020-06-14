package io.xxnjdg.mall.ware.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 20:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchaseItemDoneVo {
    //{itemId:1,status:4,reason:""}
    private Long itemId;
    private Integer status;
    private String reason;
}
