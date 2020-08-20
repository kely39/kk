
package com.kk.d.mq.core.consumer;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

/**
 * RocketMQ PushConsumer Lifecycle Listener
 */
public interface RocketMQPushConsumerLifecycleListener extends RocketMQConsumerLifecycleListener<DefaultMQPushConsumer> {
}
