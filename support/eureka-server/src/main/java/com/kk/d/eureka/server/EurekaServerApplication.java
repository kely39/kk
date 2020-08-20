package com.kk.d.eureka.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author yangqh
 * @date 2019/12/26
 **/
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    private static Logger logger = LoggerFactory.getLogger(EurekaServerApplication.class);

    public static void main(String[] args) {

        logger.info("eureka-server is starting");
        SpringApplication.run(EurekaServerApplication.class, args);
        logger.info("eureka-server is started");


    }
}
