package com.kk.d.qny;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;

/**
 * @author yangqh
 * @date 2020/1/14
 **/
@ConfigurationProperties(prefix = "kk.qny",ignoreInvalidFields = true)
@RefreshScope
public class QnyProperties {

    /**
     * 七牛云ak
     **/
    private String ak;

    /**
     * 七牛云sk
     **/
    private String sk;

    /**
     * 文件夹-前缀 集合
     **/
    private Map<String,Map<String,String>> prefixMap;

    /**
     * 七牛云回调地址
     **/
    private String notifyUrlHost;

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public Map<String, Map<String, String>> getPrefixMap() {
        return prefixMap;
    }

    public void setPrefixMap(Map<String, Map<String, String>> prefixMap) {
        this.prefixMap = prefixMap;
    }

    public String getNotifyUrlHost() {
        return notifyUrlHost;
    }

    public void setNotifyUrlHost(String notifyUrlHost) {
        this.notifyUrlHost = notifyUrlHost;
    }
}
