package com.kk.d.framework.web.event.core;

/**
 * 同步事件监听器
 *
 * @author kk
 * @date 2019/12/26
 **/
public class SyncEventApplicationListener extends AbstractEventApplicationListener<SyncEventHandle, SyncEvent> {

    @Override
    public void onApplicationEvent(SyncEvent event) {
        execute(event);
    }

    @Override
    protected Class getEventHandleClass() {
        return SyncEventHandle.class;
    }
}
