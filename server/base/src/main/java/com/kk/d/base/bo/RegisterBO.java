package com.kk.d.base.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterBO {
    @ApiModelProperty(value = "注册账号", required = true)
    private String account;

    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
