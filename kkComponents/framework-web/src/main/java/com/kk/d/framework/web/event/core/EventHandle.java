package com.kk.d.framework.web.event.core;

import org.springframework.context.ApplicationEvent;

/**
 * @author kk
 * @date 2019/12/26
 **/
public interface EventHandle<T extends ApplicationEvent> {

    void execute(T event);
}
