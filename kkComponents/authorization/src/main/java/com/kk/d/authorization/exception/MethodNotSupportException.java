package com.kk.d.authorization.exception;

/**
 * 方法不支持当前环境的异常，主要跟一些配置参数有关
 *
 * @author kk
 * @date 2019/12/26
 **/
public class MethodNotSupportException extends RuntimeException {
    public MethodNotSupportException(String message) {
        super(message);
    }
}
