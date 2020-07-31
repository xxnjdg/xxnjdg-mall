package io.xxnjdg.mall.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.xxnjdg.common.utils.ServiceExceptionUtil;
import io.xxnjdg.mall.auth.constant.AuthErrorCodeEnum;
import io.xxnjdg.mall.auth.po.AccessTokenResult;
import io.xxnjdg.mall.auth.service.LoginService;
import io.xxnjdg.mall.member.constant.MemberErrorCodeEnum;
import io.xxnjdg.mall.member.entity.MemberEntity;
import io.xxnjdg.mall.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/7/5 16:39
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Reference(version = "1.0.0", check = true)
    private MemberService memberService;

    @Override
    public String oauthLogin(String code) {
        String url = "https://api.weibo.com/oauth2/access_token";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.put("client_id", Collections.singletonList("3593412374"));
        paramMap.put("client_secret", Collections.singletonList("da952d98fe67af21850601d63898180b"));
        paramMap.put("grant_type", Collections.singletonList("authorization_code"));
        paramMap.put("redirect_uri", Collections.singletonList("http://auth.gulimall.com/oauth2.0/weibo/success"));
        paramMap.put("code", Collections.singletonList(code));
        log.info(paramMap.toString());

        //请求 accessToken
//        AccessTokenResult accessTokenResult = restTemplate.postForObject(url, paramMap, AccessTokenResult.class);
        ResponseEntity<AccessTokenResult> entity = restTemplate.postForEntity(url, paramMap, AccessTokenResult.class);
        if (entity.getStatusCodeValue() != 200){
            return null;
        }
        AccessTokenResult accessTokenResult = entity.getBody();
        if (accessTokenResult == null) {
//            throw ServiceExceptionUtil.exception(AuthErrorCodeEnum.WEIBO_LOGIN_ERROR.getCode());
            return null;
        }

        log.info(accessTokenResult.toString());

        MemberEntity memberEntity = memberService.getMemberByUid(accessTokenResult.getUid());
        if (memberEntity != null) {
            //注册过
            boolean update = memberService.updateById(memberEntity
                    .setAccessToken(accessTokenResult.getAccess_token())
                    .setExpiresIn(accessTokenResult.getExpires_in()));

            if (!update) {
//                throw ServiceExceptionUtil.exception(MemberErrorCodeEnum.MEMBER_SAVE_ERROR.getCode());
                return null;
            }

        } else {
            //未注册过
            url = "https://api.weibo.com/2/users/show.json?access_token={access_token}&uid={uid}";
            Map<String, Object> map = new HashMap<>();
            map.put("uid", accessTokenResult.getUid());
            map.put("access_token", accessTokenResult.getAccess_token());
            JSONObject result = restTemplate.getForObject(url, JSONObject.class, map);
            if (result == null){
                return null;
            }
            String name = result.getString("name");
            memberEntity = new MemberEntity()
                    .setNickname(name)
                    .setUid(accessTokenResult.getUid())
                    .setAccessToken(accessTokenResult.getAccess_token())
                    .setExpiresIn(accessTokenResult.getExpires_in());
            boolean save = memberService.save(memberEntity);

            if (!save) {
//                throw ServiceExceptionUtil.exception(MemberErrorCodeEnum.MEMBER_SAVE_ERROR.getCode());
                return null;
            }

        }

        return memberEntity.getNickname();
    }
}
