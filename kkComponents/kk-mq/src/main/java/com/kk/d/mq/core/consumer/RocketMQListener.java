
package com.kk.d.mq.core.consumer;

/**
 * RocketMQ监听消息，实现业务逻辑接口
 */
public interface RocketMQListener<T> {
    void onMessage(T message);
}
