package com.kk.d.mq.core.consumer;

/**
 * RocketMQ Consumer Lifecycle Listener
 */
public interface RocketMQConsumerLifecycleListener<T> {
    void prepareStart(final T consumer);
}
