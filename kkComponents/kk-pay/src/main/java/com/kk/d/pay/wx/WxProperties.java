package com.kk.d.pay.wx;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: 微信相关配置
 * @Author yangqh
 * @Date 17:24 2018/12/25
 **/
@ConfigurationProperties(prefix = "kk.weixin")
public class WxProperties {

    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥，设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置（@see {https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3}）
     */
    private String mchKey;

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;

    /**
     * 签名类型
     */
    private String signType = "MD5";

    /**
     * 异步回调地址
     */
    private String notifyUrl;

    /**
     * 连接超时时间，单位毫秒
     */
    private int httpConnectionTimeout = 8000;

    /**
     * 读数据超时时间，单位毫秒
     */
    private int httpTimeout = 10000;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public int getHttpConnectionTimeout() {
        return httpConnectionTimeout;
    }

    public void setHttpConnectionTimeout(int httpConnectionTimeout) {
        this.httpConnectionTimeout = httpConnectionTimeout;
    }

    public int getHttpTimeout() {
        return httpTimeout;
    }

    public void setHttpTimeout(int httpTimeout) {
        this.httpTimeout = httpTimeout;
    }
}
