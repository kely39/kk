package com.kk.d.base.service.sys.impl;

import com.kk.d.base.service.sys.RedisUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author kk
 * @date 2019/12/27
 **/
@Service
public class RedisUtilServiceImpl implements RedisUtilService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void setString(String key, String code, Integer expireSeconds) {
        stringRedisTemplate.opsForValue().set(key, code, expireSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
