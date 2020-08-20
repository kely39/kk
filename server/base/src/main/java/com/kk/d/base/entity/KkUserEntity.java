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
 * 用户表
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
@TableName("kk_user")
@ApiModel(value="KkUserEntity对象", description="用户表")
public class KkUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户编号")
    private String userNum;

    @ApiModelProperty(value = "密码盐")
    private String salt;

    @ApiModelProperty(value = "头像")
    private String headimg;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "性别 1-男|2-女")
    private Integer sex;

    @ApiModelProperty(value = "是否冻结")
    private Boolean freezeFlag;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime lastUpdatedTime;

    @ApiModelProperty(value = "逻辑删除标识（0.未删除 1.已删除）")
    private Boolean deleteFlag;


}
