package io.xxnjdg.mall.product.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/5 22:48
 */
@Getter
@AllArgsConstructor
public enum ProductErrorCodeEnum {

    // ========== Banner 模块 ==========
    TEST_EXISTS(1006000000, "测试移仓"),
    ;

    private final int code;
    private final String message;
}
