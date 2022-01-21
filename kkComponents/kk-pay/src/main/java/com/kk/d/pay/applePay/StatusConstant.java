package com.kk.d.pay.applePay;

/**
 * @author kk
 * @date 2019/8/20
 **/
public class StatusConstant {

    /**
     * 21000 App Store不能读取你提供的JSON对象
     * 21002 receipt-data域的数据有问题
     * 21003 receipt无法通过验证
     * 21004 提供的shared secret不匹配你账号中的shared secret
     * 21005 receipt服务器当前不可用
     * 21006 receipt合法，但是订阅已过期。服务器接收到这个状态码时，receipt数据仍然会解码并一起发送
     * 21007 receipt是Sandbox receipt，但却发送至生产系统的验证服务
     * 21008 receipt是生产receipt，但却发送至Sandbox环境的验证服务
     **/
    public static final String SUCCESS = "0";
    public static final String FAIL = "-1";
}
