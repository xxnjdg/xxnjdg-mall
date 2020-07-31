package io.xxnjdg.mall.auth.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/7/5 16:17
 */
@Data
@Accessors(chain = true)
public class AccessTokenResult {
    private String    access_token;
    private String    remind_in;
    private String    expires_in;
    private String    uid;
    private String    isRealName;
}
