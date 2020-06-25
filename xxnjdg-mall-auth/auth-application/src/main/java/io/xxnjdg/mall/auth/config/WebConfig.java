package io.xxnjdg.mall.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/25 21:10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register.html").setViewName("register");
        registry.addViewController("/login.html").setViewName("login");
    }
}
