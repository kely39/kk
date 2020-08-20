
package com.kk.d.mq.enums;

/**
 * 消费模式
 */
public enum ConsumeMode {
    /**
     * 并发接收消息
     */
    CONCURRENTLY,

    /**
     * 顺序接收消息
     */
    ORDERLY
}
