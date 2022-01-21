package com.kk.d.pay.applePay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kk
 * @date 2019/8/20
 **/
@Configuration
@Slf4j
public class ApplePayAutoConfiguration {

    @Bean
    public ApplePayService applePayService() {
        return new ApplePayService();
    }

}
