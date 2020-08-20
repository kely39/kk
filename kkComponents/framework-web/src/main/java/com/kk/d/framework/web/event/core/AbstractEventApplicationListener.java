package com.kk.d.framework.web.event.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 事件监听器抽象类
 *
 * @author yangqh
 * @date 2019/12/26
 **/
public abstract class AbstractEventApplicationListener<K extends EventHandle, V extends ApplicationEvent> implements ApplicationListener<V>, InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 已注册的处理器
     */
    private Map<Class, K> registerEventHandles = new HashMap<>();

    /**
     * 处理器类别
     *
     * @return
     */
    protected abstract Class getEventHandleClass();

    /**
     * 事件分发执行
     *
     * @param event
     */
    protected void execute(V event) {

        K eventHandle = registerEventHandles.get(event.getClass());
        if (eventHandle != null) {
            eventHandle.execute(event);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * 获取所有具体的EventHandle的实例进行注册
         */

        Class handleClass = this.getEventHandleClass();
        Map<String, K> eventHandleBeans = applicationContext.getBeansOfType(handleClass);

        Iterator<Map.Entry<String, K>> handleIterator = eventHandleBeans.entrySet().iterator();
        while (handleIterator.hasNext()) {
            Map.Entry<String, K> handle = handleIterator.next();
            K eventHandle = handle.getValue();

            Class eventClass = getActualType(eventHandle.getClass());

            if (eventClass != null) {

                registerEventHandles.put(eventClass, eventHandle);
            }
        }
    }

    /**
     * 获取泛型的具体实现类型
     *
     * @param clazz
     * @return
     */
    private Class getActualType(Class clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();

        if (genericInterfaces != null && genericInterfaces.length > 0) {
            return (Class) ((ParameterizedType) genericInterfaces[0]).getActualTypeArguments()[0];
        }

        return null;
    }
}
