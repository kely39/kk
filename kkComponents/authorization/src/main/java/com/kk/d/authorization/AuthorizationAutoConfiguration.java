package com.kk.d.authorization;

import com.kk.d.authorization.interceptor.AuthorizationInterceptor;
import com.kk.d.authorization.manager.TokenCacheService;
import com.kk.d.authorization.manager.impl.CacheTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Authorization自动配置类
 *
 * @author yangqh
 * @date 2019/12/26
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(name = {"kk.authorization.enabled"}, havingValue = "true", matchIfMissing = false)
@ConditionalOnBean(TokenCacheService.class)
public class AuthorizationAutoConfiguration extends WebMvcConfigurerAdapter {

    @ConfigurationProperties(prefix = "kk.authorization.token-manager")
    @Bean
    public CacheTokenManager tokenManager() {
        log.info("CacheTokenManager init...");
        return new CacheTokenManager();
    }

    @ConfigurationProperties(prefix = "kk.authorization.interceptor")
    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        log.info("AuthorizationInterceptor init...");
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("addInterceptors init...");
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
