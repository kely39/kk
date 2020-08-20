package com.kk.d.test;

import com.kk.d.mq.annotation.RocketMQMessageListener;
import com.kk.d.mq.core.consumer.RocketMQListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "test-consumer-group-1")
public class MyConsumer1 implements RocketMQListener<String> {
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}