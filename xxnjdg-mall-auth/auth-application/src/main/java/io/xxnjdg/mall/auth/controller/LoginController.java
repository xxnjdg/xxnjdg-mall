package io.xxnjdg.mall.auth.controller;

import com.alibaba.fastjson.JSONObject;
import io.xxnjdg.mall.auth.po.AccessTokenResult;
import io.xxnjdg.mall.auth.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/7/5 9:06
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/oauth2.0/weibo/success")
    public String oauthCallBack(String code, HttpSession httpSession){
        String name = loginService.oauthLogin(code);
        if (name == null) {
            return "redirect:http://auth.gulimall.com/login.html";
        }

        httpSession.setAttribute("userData",name);
        return "redirect:http://gulimall.com/";
    }

}
