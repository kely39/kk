package com.kk.d.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信表
 * </p>
 *
 * @author yangqh
 * @since 2019-12-27
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("kk_sms")
@ApiModel(value="KkSmsEntity对象", description="短信表")
public class KkSmsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "短信内容")
    private String content;

    @ApiModelProperty(value = "是否发送 0否    1是")
    private Boolean status;

    @ApiModelProperty(value = "是否使用")
    private Boolean useFlag;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "使用时间")
    private LocalDateTime useTime;

    @ApiModelProperty(value = "失败原因")
    private String failReason;
}
