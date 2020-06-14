package io.xxnjdg.mall.ware.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 20:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MergeVo {
    private Long purchaseId; //整单id
    private List<Long> items;//[1,2,3,4] //合并项集合
}
