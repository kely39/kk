package com.kk.d.qny;

import com.kk.d.qny.impl.QnyServiceImpl;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * @author kk
 * @date 2020/1/14
 **/
@Configuration
@Import({QnyAutoConfigration.AuthAutoConfiguration.class})
@EnableConfigurationProperties({QnyProperties.class})
@Slf4j
public class QnyAutoConfigration {

    @Configuration
    @EnableConfigurationProperties({QnyProperties.class})
    @ConditionalOnProperty(name = {"kk.qny.enabled"}, havingValue = "true", matchIfMissing = false)
    public class AuthAutoConfiguration {

        @Bean
        public Auth auth(QnyProperties properties) {
            Auth auth = Auth.create(properties.getAk(), properties.getSk());
            log.info("qny Auth init");
            return auth;
        }
    }

    @Primary
    @Bean
    public QnyService qnyService() {
        return new QnyServiceImpl() {
        };
    }
}