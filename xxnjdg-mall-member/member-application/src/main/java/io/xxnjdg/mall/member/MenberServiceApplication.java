package io.xxnjdg.mall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/4 11:49
 */
@SpringBootApplication
@MapperScan(basePackages = {"io.xxnjdg.mall.member.dao"})
@EnableDiscoveryClient
public class MenberServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MenberServiceApplication.class);
    }
}