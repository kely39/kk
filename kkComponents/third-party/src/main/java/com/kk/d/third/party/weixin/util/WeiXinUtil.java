package com.kk.d.third.party.weixin.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeiXinUtil {
    private static final Logger log = LoggerFactory.getLogger(WeiXinUtil.class);
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public static final String ACCESS_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    public static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    public WeiXinUtil() {
    }

    public static WeiXinAccessToken getAccessTokenForApp(String code, String appid, String appsecret) {
        WeiXinAccessToken weiXinAccessToken = null;
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code".replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);

        try {
            log.info("获取微信token:" + requestUrl);
            String result = HttpUtil.get(requestUrl);
            log.info("微信端返回获取token结果:" + result);
            if (StrUtil.isEmpty(result)) {
                return weiXinAccessToken;
            } else {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (ObjectUtil.isNotNull(jsonObject.getString("access_token"))) {
                    weiXinAccessToken = new WeiXinAccessToken();
                    weiXinAccessToken.setAccessToken(jsonObject.getString("access_token"));
                    weiXinAccessToken.setRefreshToken(jsonObject.getString("refresh_token"));
                    weiXinAccessToken.setExpiresIn(jsonObject.getString("expires_in"));
                    weiXinAccessToken.setScope(jsonObject.getString("scope"));
                    weiXinAccessToken.setOpenId(jsonObject.getString("openid"));
                    weiXinAccessToken.setAppId(appid);
                }

                return weiXinAccessToken;
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            return weiXinAccessToken;
        }
    }

    public static WeiXinUserInfo getWeiXinUserInfo(String code, String appid, String appsecret) {
        WeiXinUserInfo weiXinUserInfo = null;

        try {
            WeiXinAccessToken weiXinAccessToken = getAccessTokenForApp(code, appid, appsecret);
            if (ObjectUtil.isNull(weiXinAccessToken)) {
                return weiXinUserInfo;
            } else {
                String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID".replace("ACCESS_TOKEN", weiXinAccessToken.getAccessToken()).replace("OPENID", weiXinAccessToken.getOpenId());
                log.info("获取微信用户信息url:" + requestUrl);
                String result = HttpUtil.get(requestUrl);
                log.info("获取微信端返回获取用户信息结果:" + result);
                log.info("微信端返回获取token结果:" + result);
                if (StrUtil.isEmpty(result)) {
                    return weiXinUserInfo;
                } else {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    weiXinUserInfo = new WeiXinUserInfo();
                    weiXinUserInfo.setOpenId(jsonObject.getString("openid"));
                    weiXinUserInfo.setNickname(jsonObject.getString("nickname"));
                    weiXinUserInfo.setProvince(jsonObject.getString("province"));
                    weiXinUserInfo.setCity(jsonObject.getString("city"));
                    weiXinUserInfo.setCountry(jsonObject.getString("country"));
                    weiXinUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
                    weiXinUserInfo.setPrivilege(Convert.toList(jsonObject.getString("privilege")));
                    weiXinUserInfo.setSex(jsonObject.getInteger("sex"));
                    weiXinUserInfo.setUnionId(jsonObject.getString("unionid"));
                    return weiXinUserInfo;
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            return weiXinUserInfo;
        }
    }
}