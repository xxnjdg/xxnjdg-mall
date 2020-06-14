package io.xxnjdg.mall.product.vo;

import io.xxnjdg.mall.product.entity.AttrEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 15:14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AttrGroupWithAttrsVo {

    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}

