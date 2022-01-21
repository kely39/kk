package com.kk.d.third.party.weixin;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpUserServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author kk
 * @date 2019/12/31
 **/
@Slf4j
@Configuration
@Import({WeixinAutoConfiguration.WxMpAutoConfiguration.class})
public class WeixinAutoConfiguration {

    /**
     * @author kk
     * @date 2019/12/31
     **/
    @ConditionalOnProperty(name = {"kk.wx.mp.enabled"}, havingValue = "true", matchIfMissing = false)
    @ConfigurationProperties(prefix = "kk.wx.mp")
    @Configuration
    public static class WxMpAutoConfiguration {

        private String appid;
        private String appsecret;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public void setAppsecret(String appsecret) {
            this.appsecret = appsecret;
        }

        public String getAppsecret() {
            return appsecret;
        }

        @Bean
        public WxMpService wxMpServiceImpl() {
            log.info("WxMpServiceImpl init...");
            WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
            config.setAppId(getAppid());
            config.setSecret(getAppsecret());
            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(config);
            return wxMpService;
        }

        @Bean
        public WxMpUserService wxMpUserServiceImpl() {
            log.info("WxMpUserServiceImpl init...");
            WxMpUserService wxMpUserService = new WxMpUserServiceImpl(wxMpServiceImpl());
            return wxMpUserService;
        }
    }

}
