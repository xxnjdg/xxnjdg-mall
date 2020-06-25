package io.xxnjdg.mall.search.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/24 5:51
 */
@Data
@Accessors(chain = true)
public class Attr implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long attrId;

    private String attrName;

    private List<String> attrValue;

}

