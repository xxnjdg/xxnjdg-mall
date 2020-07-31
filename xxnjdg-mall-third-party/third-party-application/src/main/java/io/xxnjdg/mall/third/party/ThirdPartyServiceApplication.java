package io.xxnjdg.mall.third.party;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/8 1:17
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ThirdPartyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyServiceApplication.class);
    }

}
