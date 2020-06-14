package io.xxnjdg.mall.ware.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 20:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchaseDoneVo {
    @NotNull
    private Long id;//采购单id

    private List<PurchaseItemDoneVo> items;
}
