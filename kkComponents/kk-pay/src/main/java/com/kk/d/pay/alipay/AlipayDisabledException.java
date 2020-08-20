package com.kk.d.pay.alipay;

/**
 * 支付宝禁用异常
 */
public class AlipayDisabledException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AlipayDisabledException(String message) {
        super(message);
    }
}
