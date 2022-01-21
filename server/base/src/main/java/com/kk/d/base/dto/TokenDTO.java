package com.kk.d.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kk
 * @date 2019/12/27
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TokenDTO {

    @ApiModelProperty(value = "用户编号", required = true)
    private String userNum;

    @ApiModelProperty(value = "用户token，登录后每次请求必须带上该标志", required = true)
    private String token;


}
