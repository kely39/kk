package com.kk.d.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangqh
 * @date 2019/12/27
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WxLoginDTO {


    @ApiModelProperty(value = "token，登录成功时返回")
    private String token;

    @ApiModelProperty(value = "用户编号，登录成功时返回")
    private String userNum;

    @ApiModelProperty(value = "openid，登录失败[1109]时返回用于账号绑定")
    private String openid;

    @ApiModelProperty(value = "userId", hidden = true)
    private String id;

    @ApiModelProperty(value = "账号", hidden = true)
    private String account;

}
