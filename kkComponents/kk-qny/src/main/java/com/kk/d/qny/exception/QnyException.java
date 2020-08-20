package com.kk.d.qny.exception;

/**
 * 七牛云异常
 */
public class QnyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QnyException(String message) {
        super(message);
    }
}
