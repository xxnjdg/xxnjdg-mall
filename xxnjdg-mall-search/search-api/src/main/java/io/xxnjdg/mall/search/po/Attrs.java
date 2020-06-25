package io.xxnjdg.mall.search.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/17 0:20
 */
@Data
public class Attrs implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long attrId;

    private String attrName;

    private String attrValue;

}
