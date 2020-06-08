package io.xxnjdg.mall.order.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/6 2:40
 */
@Getter
@AllArgsConstructor
public enum OrderErrorCodeEnum {

    // ========== Banner 模块 ==========
    TEST_EXISTS(1006000000, "测试移仓1"),
    ;

    private final int code;
    private final String message;
}