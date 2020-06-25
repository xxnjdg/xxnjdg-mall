package io.xxnjdg.mall.search.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/23 20:46
 */
@Data
@Accessors(chain = true)
public class Brand implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long brandId;

    private String  brandName;

    private String brandImg;
}
