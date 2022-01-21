package com.kk.d.base.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author kk
 * @since 2019-12-27
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class KkUserBO {


    private String id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码盐")
    private String salt;

    @ApiModelProperty(value = "用户编号")
    private String userNum;

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
    private LocalDateTime createDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

    @ApiModelProperty(value = "删除标记")
    private String delFlag;


}
