package com.kk.d.third.party.weixin.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WeiXinUserInfo {
    //普通用户的标识，对当前开发者帐号唯一
    private String openId;
    //普通用户昵称
    private String nickname;
    //应用id
    private String appId;
    //性别(0未知，1男，2女 )
    private int sex;
    //省
    private String province;
    //市
    private String city;
    //国家
    private String country;
    //图像
    private String headimgurl;
    //用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
    private List privilege;
    //用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的
    private String unionId;

}