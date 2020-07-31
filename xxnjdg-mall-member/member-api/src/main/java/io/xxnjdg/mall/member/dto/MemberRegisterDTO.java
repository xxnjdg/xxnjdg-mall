package io.xxnjdg.mall.member.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/30 14:34
 */
@Data
@Accessors(chain = true)
public class MemberRegisterDTO implements Serializable {
    private static final long serialVersionUID=1L;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @Pattern(regexp = "^1[3|4|5|8|7][0-9]\\d{4,8}$",message = "参数错误")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @Pattern(regexp = "^[A-Za-z\\d$@!%*?&.]{6,20}$",message = "参数错误")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Pattern(regexp = "^[A-Za-z\\d$@!%*?&.]{6,20}$",message = "参数错误")
    @NotBlank(message = "密码2不能为空")
    private String repeatPassword;
}
