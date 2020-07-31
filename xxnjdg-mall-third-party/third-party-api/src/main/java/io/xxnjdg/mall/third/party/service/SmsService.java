package io.xxnjdg.mall.third.party.service;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/26 10:56
 */
public interface SmsService {
    void sendCode(String mobile,String code);
}
