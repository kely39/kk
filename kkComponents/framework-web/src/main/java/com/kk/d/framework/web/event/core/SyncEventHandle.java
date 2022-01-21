package com.kk.d.framework.web.event.core;

/**
 * 同步事件处理器
 *
 * @author kk
 * @date 2019/12/26
 **/
public interface SyncEventHandle<T extends SyncEvent> extends EventHandle<T> {

    void execute(T event);
}
