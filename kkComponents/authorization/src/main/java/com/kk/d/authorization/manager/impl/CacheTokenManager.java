package com.kk.d.authorization.manager.impl;

import com.kk.d.authorization.constants.TokenClearType;
import com.kk.d.authorization.manager.TokenCacheService;

import javax.annotation.Resource;

/**
 * 使用缓存存储Token
 *
 * @author kk
 * @date 2019/12/26
 **/
public class CacheTokenManager extends AbstractTokenManager {

    @Resource
    private TokenCacheService tokenCacheService;

    @Override
    protected void delSingleRelationshipByKey(String account, TokenClearType tokenClearType) {
        tokenCacheService.delRelationshipByAccount(account, tokenExpireSeconds, tokenClearType);
    }

    @Override
    public void delRelationshipByToken(String token) {
        tokenCacheService.delRelationshipByToken(token, singleTokenWithUser, tokenExpireSeconds);
    }

    @Override
    public TokenClearType getTokenClearType(String token) {
        return tokenCacheService.getTokenClearType(token);
    }

    @Override
    protected void createSingleRelationship(String account, String token) {
        tokenCacheService.setTokenOneToOne(account, token, tokenExpireSeconds);
    }

    @Override
    protected void createMultipleRelationship(String account, String token) {

        tokenCacheService.setTokenOneToMany(account, token, tokenExpireSeconds);
    }

    @Override
    protected String getAccountByToken(String token, boolean flushExpireAfterOperation) {
        return tokenCacheService.getAccount(token, flushExpireAfterOperation, tokenExpireSeconds, singleTokenWithUser);
    }

}
