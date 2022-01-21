package com.kk.d.framework.web.event.core;

import org.springframework.context.ApplicationEvent;

/**
 * 异步事件抽象类
 *
 * @author kk
 * @date 2019/12/26
 **/
public abstract class AsyncEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public AsyncEvent(Object source) {
        super(source);
    }
}
