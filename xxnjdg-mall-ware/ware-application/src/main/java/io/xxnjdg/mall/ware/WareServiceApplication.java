package io.xxnjdg.mall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/4 11:59
 */
@SpringBootApplication
@MapperScan(basePackages = {"io.xxnjdg.mall.ware.dao"})
@EnableDiscoveryClient
public class WareServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WareServiceApplication.class);
    }
}