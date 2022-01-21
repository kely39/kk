package com.kk.d.pay.alipay;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: 支付宝相关配置
 * @Author kk
 * @Date 17:24 2018/12/25
 **/
@ConfigurationProperties(prefix = "kk.alipay")
public class AlipayProperties {

    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    /**开发者的应用ID **/
    private String appId;

    /**商户自己生成的私钥，需绝对保密，不允许存放客户端 {@see {https://docs.open.alipay.com/291/105971/}}**/
    private String privateKey;

    /**支付生成的公钥（不是商户公钥）用于回调验签  {@see {https://docs.open.alipay.com/291/105971/}}**/
    private String alipayPublicKey;

    /**同步回调地址**/
    private String returnUrl;

    /**异步回调地址**/
    private String notifyUrl;

    private String charset = "UTF-8";

    private String signType = "RSA2";

    private String format = "json";

    /**
     * 【该配置非必填，默认：2m】
     * 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
     * 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
     **/
    private String timeoutExpress = "2m";

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }
}
