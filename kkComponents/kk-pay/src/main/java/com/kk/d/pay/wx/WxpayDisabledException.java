package com.kk.d.pay.wx;

/**
 * 微信支付禁用异常
 */
public class WxpayDisabledException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WxpayDisabledException(String message) {
        super(message);
    }
}
