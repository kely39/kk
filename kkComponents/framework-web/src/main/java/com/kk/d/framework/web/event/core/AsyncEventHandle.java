package com.kk.d.framework.web.event.core;

/**
 * 异步事件处理器
 *
 * @author yangqh
 * @date 2019/12/26
 **/
public interface AsyncEventHandle<T extends AsyncEvent> extends EventHandle<T> {

    void execute(T event);
}
