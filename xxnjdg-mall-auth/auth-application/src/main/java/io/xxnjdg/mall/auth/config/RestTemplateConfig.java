package io.xxnjdg.mall.auth.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/7/5 10:22
 */
@Configuration
public class RestTemplateConfig {
    /**
     * @param builder 自动注入 RestTemplateBuilder
     * @return 返回 RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
