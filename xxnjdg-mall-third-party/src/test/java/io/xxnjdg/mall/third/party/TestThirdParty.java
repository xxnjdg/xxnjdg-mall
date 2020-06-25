package io.xxnjdg.mall.third.party;

import com.aliyun.oss.OSS;
import io.xxnjdg.mall.third.party.component.SmsComponent;
import io.xxnjdg.mall.third.party.util.HttpUtils;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/8 1:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestThirdParty {
    @Autowired
    private OSS ossClient;

    @Autowired
    private SmsComponent smsComponent;

    @Test
    public void testOss() throws FileNotFoundException {
        ossClient.putObject("xxnjdgoss", "fileName.jpg",
                new FileInputStream("D:\\app\\guli\\分布式基础篇\\docs\\pics\\0d40c24b264aa511.jpg"));
        log.info("upload success");
    }

    @Test
    public void sendSms1(){
        smsComponent.sendCode("","999999");
    }

    @Test
    public void sendSms(){
        String host = "https://edisim.market.alicloudapi.com";
        String path = "/comms/sms/sendmsg";
        String method = "POST";
        String appcode = "";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("callbackUrl", "http://test.dev.esandcloud.com");
        bodys.put("channel", "1");
        bodys.put("mobile", "");
        bodys.put("templateID", "0000000");
        bodys.put("templateParamSet", "['123456', '1']");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
