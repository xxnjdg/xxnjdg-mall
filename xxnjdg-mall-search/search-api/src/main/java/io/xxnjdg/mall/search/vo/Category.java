package io.xxnjdg.mall.search.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/23 21:16
 */
@Data
@Accessors(chain = true)
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long catalogId;

    private String catalogName;
}
