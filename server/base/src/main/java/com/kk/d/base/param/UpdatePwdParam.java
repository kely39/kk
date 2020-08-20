package com.kk.d.base.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdatePwdParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发短信返回ID")
    @NotBlank(message = "verifyCodeId不能为空！")
    private String verifyCodeId;

    @ApiModelProperty(value = "手机验证码", required = true)
    @NotBlank(message = "smsCode不能为空！")
    private String smsCode;

    @ApiModelProperty(value = "手机号", example = "18876788787")
    @NotBlank(message = "手机号不能为空！")
    @Size(min = 11, max = 11, message = "手机号格式错误！")
    private String phone;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "password不能为空！")
    private String password;

}