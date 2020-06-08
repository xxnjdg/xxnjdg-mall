package io.xxnjdg.mall.order.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/6 2:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TestDTO implements Serializable {
    private static final long serialVersionUID = 8208571530829456110L;

    /**
     * 讲师用户编号
     */
    @NotNull(message = "sss")
    private Integer lecturerUserNo;
}
