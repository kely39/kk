package com.kk.d.mq;

import com.kk.d.mq.core.producer.RocketMQTemplate;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author yangqh
 * @date 2020/1/6
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class ProducerTest {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Ignore
    @Test
    public void sendMsg() {
        rocketMQTemplate.send("test-topic-1", "test-tag-1", "Hello World");
    }

}
