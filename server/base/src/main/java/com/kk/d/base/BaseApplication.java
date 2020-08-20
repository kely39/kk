package com.kk.d.base;

import com.kk.d.base.constants.ResultCode;
import com.kk.d.mq.annotation.EnableRocketMQ;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yangqh
 * @date 2019/12/26
 **/
@Slf4j
//@EnableRocketMQ
@MapperScan("com.kk.d.base.mapper")
@ComponentScan(basePackages = {"com.kk.d"})
@EnableDiscoveryClient
@SpringBootApplication
@RefreshScope
@Import({BaseApplication.FrameworkWebImplAutoConfiguration.class})
public class BaseApplication {

    public static void main(String[] args) {
        log.info("kk-base is starting");
        SpringApplication.run(BaseApplication.class, args);
        log.info("kk-base is started");
    }

    @Configuration
    public class FrameworkWebImplAutoConfiguration implements InitializingBean {
        @Override
        public void afterPropertiesSet() {
            // 初始化resultCode
            new ResultCode();
        }
    }
}
