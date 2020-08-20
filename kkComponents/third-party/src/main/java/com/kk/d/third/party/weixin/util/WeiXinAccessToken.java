package com.kk.d.third.party.weixin.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WeiXinAccessToken {
    //接口调用凭证
    private String accessToken;
    //access_token接口调用凭证超时时间，单位（秒）
    private String expiresIn;
    //用户刷新access_token
    private String refreshToken;
    //授权用户唯一标识
    private String openId;
    //用户授权的作用域，使用逗号（,）分隔
    private String scope;
    //同一开放平台下用户唯一标识
    private String unionId;
    //应用id
    private String appId;
}