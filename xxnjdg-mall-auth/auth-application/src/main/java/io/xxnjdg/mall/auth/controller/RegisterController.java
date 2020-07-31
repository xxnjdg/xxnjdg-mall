package io.xxnjdg.mall.auth.controller;

import io.xxnjdg.common.utils.R;
import io.xxnjdg.common.utils.ServiceExceptionUtil;
import io.xxnjdg.mall.auth.constant.AuthErrorCodeEnum;
import io.xxnjdg.mall.auth.constant.AuthServiceConstant;
import io.xxnjdg.mall.auth.dto.RegisterDTO;
import io.xxnjdg.mall.auth.service.RegisterService;
import io.xxnjdg.mall.member.dto.MemberRegisterDTO;
import io.xxnjdg.mall.member.service.MemberService;
import io.xxnjdg.mall.third.party.service.SmsService;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/26 11:24
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Reference(version = "1.0.0",validation = "true")
    private MemberService memberService;


    @GetMapping("/sms/send/code")
    @ResponseBody
    public R sendCode(@RequestParam("phone") String phone) {
        registerService.sendCode(phone);
        return R.ok();
    }

    @PostMapping("/register")
    public String register(@Validated RegisterDTO registerDTO, BindingResult result, RedirectAttributes redirectAttributes){
        HashMap<String, String> resultMap = new HashMap<>();
        if (result.hasErrors()){
            Map<String, List<FieldError>> collect = result.getFieldErrors().stream().collect(Collectors.groupingBy(FieldError::getField));
            for (Map.Entry<String, List<FieldError>> stringListEntry : collect.entrySet()) {
                List<FieldError> value = stringListEntry.getValue();
                if (value.size() == 1){
                    resultMap.put(stringListEntry.getKey(),value.get(0).getDefaultMessage());
                } else if (value.size() > 1){
                    value.forEach(fieldError -> {
                        if (StringUtils.contains("NotBlank",fieldError.getCode())){
                            resultMap.put(stringListEntry.getKey(),fieldError.getDefaultMessage());
                        }
                        if (StringUtils.contains("NotNull",fieldError.getCode())){
                            resultMap.put(stringListEntry.getKey(),fieldError.getDefaultMessage());
                        }
                    });
                    String s = resultMap.get(stringListEntry.getKey());
                    if (StringUtils.isBlank(s)){
                        resultMap.put(stringListEntry.getKey(),value.get(0).getDefaultMessage());
                    }
                }
            }

            redirectAttributes.addFlashAttribute("errors", resultMap);
            return "redirect:http://auth.gulimall.com/register.html";
        }


        String key = AuthServiceConstant.SMS_CODE_CACHE_PREFIX + registerDTO.getMobile();
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(value)) {
            String code = value.split("_")[0];
            if (Objects.equals(code,registerDTO.getCode())){
                stringRedisTemplate.delete(key);
                try {
                    memberService.memberRegister(new MemberRegisterDTO()
                            .setMobile(registerDTO.getMobile())
                            .setPassword(registerDTO.getPassword())
                            .setRepeatPassword(registerDTO.getRepeatPassword())
                            .setUsername(registerDTO.getUsername()));
                } catch (Exception e) {
                    resultMap.put("msg","注册失败");
                    redirectAttributes.addFlashAttribute("errors", resultMap);
                    return "redirect:http://auth.gulimall.com/register.html";
                }
            }else{
                resultMap.put("code","验证码错误");
                redirectAttributes.addFlashAttribute("errors", resultMap);
                return "redirect:http://auth.gulimall.com/register.html";
            }
        }else{
            resultMap.put("code","验证码错误");
            redirectAttributes.addFlashAttribute("errors", resultMap);
            return "redirect:http://auth.gulimall.com/register.html";
        }

        return "redirect:http://auth.gulimall.com/login.html";

    }

}
