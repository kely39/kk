package com.kk.d.base.service.sys.impl;

import com.kk.d.authorization.constants.TokenClearType;
import com.kk.d.authorization.manager.TokenCacheService;
import com.kk.d.base.constants.UserLoginType;
import com.kk.d.base.service.sys.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author kk
 * @date 2019/12/27
 **/
@RefreshScope
@Service
public class RedisTokenCacheServiceImpl implements TokenCacheService {

    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 用户登录类型
     */
    @Value("${kk.authorization.token-cache.user-login-type}")
    private UserLoginType userLoginType;

    /**
     * 缓存超时时间
     */
    @Value("${kk.authorization.token-cache.tokenExpireSeconds}")
    private Integer expireSeconds;

    @Override
    public void setTokenOneToOne(String account, String token, Integer expireSeconds) {
        loginTokenService.setTokenOneToOne(userLoginType.getVal(), account, token, expireSeconds == null ? this.expireSeconds : expireSeconds, TokenClearType.LOGOUT_PASSIVE.getVal());
    }

    @Override
    public void setTokenOneToMany(String account, String token, Integer expireSeconds) {

        loginTokenService.setTokenOneToMany(userLoginType.getVal(), account, token, expireSeconds == null ? this.expireSeconds : expireSeconds);
    }

    @Override
    public String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        return loginTokenService.getAccount(userLoginType.getVal(), token, flushExpireAfterOperation, expireSeconds == null ? this.expireSeconds : expireSeconds, singleTokenWithUser);
    }

    @Override
    public void delRelationshipByAccount(String account, Integer expireSeconds, TokenClearType tokenClearType) {
        loginTokenService.delRelationshipByAccount(userLoginType.getVal(), account, expireSeconds == null ? this.expireSeconds : expireSeconds, tokenClearType == null ? null : tokenClearType.getVal());
    }

    @Override
    public void delRelationshipByToken(String token, Boolean singleTokenWithUser, Integer expireSeconds) {
        loginTokenService.delRelationshipByToken(userLoginType.getVal(), token, singleTokenWithUser, expireSeconds == null ? this.expireSeconds : expireSeconds, null);
    }

    @Override
    public TokenClearType getTokenClearType(String token) {
        Integer tokenClearType = loginTokenService.getTokenClearType(userLoginType.getVal(), token);
        return tokenClearType == null ? null : TokenClearType.getEnum(tokenClearType);
    }
}
