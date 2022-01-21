package com.kk.d.pay.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Description: 支付宝配置类
 * @Author kk
 * @Date 17:00 2018/12/26
 * @Param
 * @Return
 **/
@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
@Import({AlipayConfiguration.AlipayAutoConfiguration.class})
@Slf4j
public class AlipayConfiguration {

    private AlipayProperties alipayProperties;

    @Autowired
    public AlipayConfiguration(AlipayProperties properties) {
        this.alipayProperties = properties;
    }

    @Configuration
    @ConditionalOnProperty(name = {"kk.alipay.enabled"}, havingValue = "true", matchIfMissing = false)
    public class AlipayAutoConfiguration {

        @Bean
        public AlipayClient alipayClient() {

            AlipayClient alipayClient = new DefaultAlipayClient(alipayProperties.getGatewayUrl(),
                    alipayProperties.getAppId(),
                    alipayProperties.getPrivateKey(),
                    alipayProperties.getFormat(),
                    alipayProperties.getCharset(),
                    alipayProperties.getAlipayPublicKey(),
                    alipayProperties.getSignType());
            log.info("AlipayClient init");
            return alipayClient;
        }

        @Bean
        public AlipayTradeService alipayTradeService() {
            log.info("AlipayTradeService init");
            return new AlipayTradeServiceImpl.ClientBuilder().setGatewayUrl(alipayProperties.getGatewayUrl())
                    .setAppid(alipayProperties.getAppId())
                    .setPrivateKey(alipayProperties.getPrivateKey())
                    .setFormat(alipayProperties.getFormat())
                    .setCharset(alipayProperties.getCharset())
                    .setAlipayPublicKey(alipayProperties.getAlipayPublicKey())
                    .setSignType(alipayProperties.getSignType()).build();
        }
    }

    @Bean
    public AlipayServiceHandle alipayServiceHandle() {
        return new AlipayServiceHandle();
    }

    @Bean
    public AbstractAlipayNotifyService abstractAlipayNotifyService() {
        return new AbstractAlipayNotifyService();
    }
}
