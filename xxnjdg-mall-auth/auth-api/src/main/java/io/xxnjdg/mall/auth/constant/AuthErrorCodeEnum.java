package io.xxnjdg.mall.auth.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/26 13:01
 */
@Getter
@AllArgsConstructor
public enum AuthErrorCodeEnum {

    // ========== Banner 模块 ==========
    SMS_IS_SENT_TOO_FREQUENTLY(1009000000, "短信发送太频繁"),
    WEIBO_LOGIN_ERROR(1009000001, "微博第三方认证登陆失败"),
    ;

    private final int code;
    private final String message;
}