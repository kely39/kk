package com.kk.d.pay.wx;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.EntPayService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.EntPayServiceImpl;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Description: 微信配置类
 * @Author kk
 * @Date 10:15 2018/12/26
 **/
@Configuration
@EnableConfigurationProperties(WxProperties.class)
@Import({WxConfiguration.AlipayAutoConfiguration.class})
@Slf4j
public class WxConfiguration {

    private WxProperties properties;

    @Autowired
    public WxConfiguration(WxProperties properties) {
        this.properties = properties;
    }

    @Configuration
    @EnableConfigurationProperties(WxProperties.class)
    @ConditionalOnProperty(name = {"kk.weixin.enabled"}, havingValue = "true", matchIfMissing = false)
    public class AlipayAutoConfiguration {

        @Bean
        public WxPayService wxService() {

            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig());
            log.info("WxPayService init...");
            return wxPayService;
        }

        @Bean
        public EntPayService entPayService() {
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig());
            EntPayService entPayService = new EntPayServiceImpl(wxPayService);
            log.info("EntPayService init...");
            return entPayService;
        }

        private WxPayConfig wxPayConfig() {
            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId(StringUtils.trimToNull(properties.getAppId()));
            payConfig.setMchId(StringUtils.trimToNull(properties.getMchId()));
            payConfig.setMchKey(StringUtils.trimToNull(properties.getMchKey()));
            payConfig.setKeyPath(StringUtils.trimToNull(properties.getKeyPath()));
            payConfig.setSignType(StringUtils.trimToNull(properties.getSignType()));
            payConfig.setNotifyUrl(StringUtils.trimToNull(properties.getNotifyUrl()));
            payConfig.setHttpConnectionTimeout(properties.getHttpConnectionTimeout());
            payConfig.setHttpTimeout(properties.getHttpTimeout());
            //可以指定是否使用沙箱环境
            payConfig.setUseSandboxEnv(false);
            return payConfig;
        }
    }

    @Bean
    public AbstractWxPayService abstractWxPayService() {
        log.info("AbstractWxPayService init...");
        return new AbstractWxPayService();
    }

    @Bean
    public AbstractWxPayNotifyService abstractWxPayNotifyService() {
        log.info("AbstractWxPayNotifyService init...");
        return new AbstractWxPayNotifyService();
    }

    @Bean
    public WxEntPayService wxEntPayService() {
        log.info("WxEntPayService init...");
        return new WxEntPayService();
    }
}
