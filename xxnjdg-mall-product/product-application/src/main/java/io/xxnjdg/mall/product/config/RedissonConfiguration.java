package io.xxnjdg.mall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/19 14:57
 */
@Configuration
public class RedissonConfiguration {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {
        // 默认连接地址 127.0.0.1:6379
        // RedissonClient redisson = Redisson.create();

        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.100.26:6379");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}
