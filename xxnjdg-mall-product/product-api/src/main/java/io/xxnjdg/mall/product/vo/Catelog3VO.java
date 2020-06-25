package io.xxnjdg.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/18 19:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Catelog3VO implements Serializable {

    private String catalog2Id;

    private String id;

    private String name;
}
