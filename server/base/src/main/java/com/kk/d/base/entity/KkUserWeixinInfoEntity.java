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
 * 用户微信信息
 * </p>
 *
 * @author kk
 * @since 2019-12-27
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("kk_user_weixin_info")
@ApiModel(value="KkUserWeixinInfoEntity对象", description="用户微信信息")
public class KkUserWeixinInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "统一开发平台的id")
    private String unionId;

    @ApiModelProperty(value = "应用id")
    private String appId;

    @ApiModelProperty(value = "微信对应第三方应用的唯一标识")
    private String openId;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "微信用户信息")
    private String userInfo;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime lastUpdatedTime;

    @ApiModelProperty(value = "逻辑删除标识（0.未删除 1.已删除）")
    private Boolean deleteFlag;


}
