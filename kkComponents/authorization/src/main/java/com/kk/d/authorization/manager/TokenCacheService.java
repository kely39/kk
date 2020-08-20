package com.kk.d.authorization.manager;


import com.kk.d.authorization.constants.TokenClearType;

/**
 * @author yangqh
 * @date 2019/12/26
 **/
public interface TokenCacheService {

    void setTokenOneToOne(String account, String token, Integer expireSeconds);

    void setTokenOneToMany(String account, String token, Integer expireSeconds);

    String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser);

    void delRelationshipByAccount(String account, Integer expireSeconds, TokenClearType tokenClearType);

    void delRelationshipByToken(String token, Boolean singleTokenWithUser, Integer expireSeconds);

    TokenClearType getTokenClearType(String token);

}
