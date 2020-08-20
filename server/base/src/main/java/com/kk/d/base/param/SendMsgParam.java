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
public class SendMsgParam {

    @ApiModelProperty(value = "图形验证码返回UUID", required = true)
    @NotBlank(message = "uuid不能为空")
    private String uuid;

    @ApiModelProperty(value = "图形验证码", required = true)
    @NotBlank(message = "imgCode不能为空")
    private String imgCode;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "mobile不能为空")
    private String mobile;
}
