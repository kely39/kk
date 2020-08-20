package com.kk.d.base.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterParam {

    @ApiModelProperty(value = "注册账号", required = true)
    @NotBlank(message = "account不能为空！")
    private String account;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "password不能为空！")
    private String password;

    @ApiModelProperty(value = "手机验证码", required = true)
    @NotBlank(message = "smsCode不能为空！")
    private String smsCode;
}
