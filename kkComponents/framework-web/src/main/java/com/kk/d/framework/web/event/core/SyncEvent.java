package com.kk.d.framework.web.event.core;

import org.springframework.context.ApplicationEvent;

/**
 * 同步事件抽象类
 *
 * @author yangqh
 * @date 2019/12/26
 **/
public abstract class SyncEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SyncEvent(Object source) {
        super(source);
    }
}
