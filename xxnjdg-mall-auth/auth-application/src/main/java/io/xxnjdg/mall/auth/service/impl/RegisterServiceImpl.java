package io.xxnjdg.mall.auth.service.impl;

import io.xxnjdg.common.utils.ServiceExceptionUtil;
import io.xxnjdg.mall.auth.constant.AuthErrorCodeEnum;
import io.xxnjdg.mall.auth.constant.AuthServiceConstant;
import io.xxnjdg.mall.auth.service.RegisterService;
import io.xxnjdg.mall.third.party.service.SmsService;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/30 15:13
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Reference(version = "1.0.0", validation = "true")
    private SmsService smsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendCode(String phone) {
        String value = stringRedisTemplate.opsForValue().get(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (StringUtils.isNotBlank(value)) {
            long startTime = Long.parseLong(value.split("_")[1]);
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - startTime < 2 * 60 * 1000) {
                throw ServiceExceptionUtil.exception(AuthErrorCodeEnum.SMS_IS_SENT_TOO_FREQUENTLY.getCode());
            }
        }
        long currentTimeMillis = System.currentTimeMillis();
        String code = UUID.randomUUID().toString().substring(0, 5);
        stringRedisTemplate.opsForValue().set(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + phone, code + "_" + currentTimeMillis, 10, TimeUnit.MINUTES);
        smsService.sendCode(phone, code);
    }
}
