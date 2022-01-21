package com.kk.d.framework.web.event.core;

import org.springframework.scheduling.annotation.Async;

/**
 * 异步事件监听器
 *
 * @author kk
 * @date 2019/12/26
 **/
public class AsyncEventApplicationListener extends AbstractEventApplicationListener<AsyncEventHandle, AsyncEvent> {

    @Async
    @Override
    public void onApplicationEvent(AsyncEvent event) {
        execute(event);
    }

    @Override
    protected Class getEventHandleClass() {
        return AsyncEventHandle.class;
    }
}
