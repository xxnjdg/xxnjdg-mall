package io.xxnjdg.mall.member.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/7/5 19:04
 */
@Getter
@AllArgsConstructor
public enum MemberErrorCodeEnum {

    // ========== Banner 模块 ==========
    MEMBER_SAVE_ERROR(1002000000, "member插入失败"),
    ;

    private final int code;
    private final String message;
}
