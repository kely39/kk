package com.kk.d.test;

import com.kk.d.mq.annotation.EnableRocketMQ;
import com.kk.d.test.constants.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yangqh
 * @date 2019/12/26
 **/
@Slf4j
@EnableRocketMQ
@ComponentScan(basePackages = {"com.kk.d"})
@SpringBootApplication
@Import({TestApplication.FrameworkWebImplAutoConfiguration.class})
public class TestApplication {

    public static void main(String[] args) {
        log.info("test is starting");
        SpringApplication.run(TestApplication.class, args);
        log.info("test is started");
    }

    @GetMapping("/get")
    public String get() {
        return "1111111";
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
