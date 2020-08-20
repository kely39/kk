package com.kk.d.base.service.sys;

/**
 * @author yangqh
 * @date 2019/12/27
 **/
public interface RedisUtilService {

    /**
     * key - value
     *
     * @author yangqh
     * @date 2019/12/27
     **/
    void setString(String key, String code, Integer expireSeconds);

    /**
     * key - value
     *
     * @author yangqh
     * @date 2019/12/28
     **/
    String getString(String key);
}
