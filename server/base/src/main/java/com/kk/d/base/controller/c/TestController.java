package com.kk.d.base.controller.c;

import com.kk.d.framework.web.BaseController;
import com.kk.d.framework.web.Result;
import com.kk.d.mq.core.producer.RocketMQTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangqh
 * @date 2020/1/14
 **/
@Api(tags = "测试")
@RequestMapping("/c/test/")
@RestController
@Slf4j
@RefreshScope
public class TestController extends BaseController {

    @Autowired(required = false)
    private RocketMQTemplate rocketMQTemplate;

    @Value("${kk.framework-web.swagger.name}")
    private String swaggerName;

    @GetMapping("sendMqMsg")
    @ApiOperation(value = "测试mq消息", httpMethod = "GET")
    public Result sendMqMsg() {
        rocketMQTemplate.send("test-topic-1", "test-tag-1", "Hello World");
        return successGet();
    }

    @GetMapping("getSwaggerName")
    @ApiOperation(value = "getSwaggerName", httpMethod = "GET")
    public Result getSwaggerName() {
        return successGet(swaggerName);
    }
}

