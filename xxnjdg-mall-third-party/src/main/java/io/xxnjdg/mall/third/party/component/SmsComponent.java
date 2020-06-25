package io.xxnjdg.mall.third.party.component;

import io.xxnjdg.mall.third.party.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/25 23:31
 */
@Component
public class SmsComponent {

    @Value("${spring.cloud.alicloud.sms.appcode}")
    private String appcode;
    @Value("${spring.cloud.alicloud.sms.callbackUrl}")
    private String callbackUrl;
    @Value("${spring.cloud.alicloud.sms.channel}")
    private String channel;
    @Value("${spring.cloud.alicloud.sms.time}")
    private String time;

    public void sendCode(String mobile,String code){
        String host = "https://edisim.market.alicloudapi.com";
        String path = "/comms/sms/sendmsg";
        String method = "POST";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();
        if (StringUtils.isNotBlank(callbackUrl)){
            bodys.put("callbackUrl", callbackUrl);
        }
        bodys.put("channel", channel);
        bodys.put("mobile", mobile);
        bodys.put("templateID", "0000000");
        bodys.put("templateParamSet", "['"+code+"', '"+time+"']");

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
