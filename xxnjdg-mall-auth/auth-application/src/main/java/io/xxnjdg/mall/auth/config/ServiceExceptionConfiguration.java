package io.xxnjdg.mall.auth.config;

import io.xxnjdg.common.utils.ServiceExceptionUtil;
import io.xxnjdg.mall.auth.constant.AuthErrorCodeEnum;
import io.xxnjdg.mall.member.constant.MemberErrorCodeEnum;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/26 20:28
 */
@Configuration
public class ServiceExceptionConfiguration {

    /**
     * 初始化异常
     */
    @EventListener(ApplicationReadyEvent.class) // 可参考 https://www.cnblogs.com/ssslinppp/p/7607509.html
    public void initMessages() {
        for (AuthErrorCodeEnum item : AuthErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(item.getCode(), item.getMessage());
        }

        for (MemberErrorCodeEnum item : MemberErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(item.getCode(), item.getMessage());
        }

    }

}
