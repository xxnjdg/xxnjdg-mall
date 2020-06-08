package io.xxnjdg.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/4 8:49
 */
@SpringBootApplication
@MapperScan(basePackages = {"io.xxnjdg.mall.product.dao"})
@EnableDiscoveryClient
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class);
    }
}
