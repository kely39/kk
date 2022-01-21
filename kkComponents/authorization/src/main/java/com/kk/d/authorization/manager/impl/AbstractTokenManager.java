package com.kk.d.authorization.manager.impl;

import com.kk.d.authorization.constants.TokenClearType;
import com.kk.d.authorization.exception.MethodNotSupportException;
import com.kk.d.authorization.manager.TokenManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;

/**
 * Token管理的基础类
 *
 * @author kk
 * @date 2019/12/26
 **/
public abstract class AbstractTokenManager implements TokenManager {

    protected int tokenExpireSeconds = 7 * 24 * 3600;

    protected boolean singleTokenWithUser = true;

    protected boolean flushExpireAfterOperation = true;

    public static final String TOKEN_KEY = "sdfhsfhgyvcw%#$^%783624wGFGEJH";

    public void setTokenExpireSeconds(int tokenExpireSeconds) {
        this.tokenExpireSeconds = tokenExpireSeconds;
    }

    public void setSingleTokenWithUser(boolean singleTokenWithUser) {
        this.singleTokenWithUser = singleTokenWithUser;
    }

    public void setFlushExpireAfterOperation(boolean flushExpireAfterOperation) {
        this.flushExpireAfterOperation = flushExpireAfterOperation;
    }

    public int getTokenExpireSeconds() {
        return tokenExpireSeconds;
    }

    public boolean getSingleTokenWithUser() {
        return singleTokenWithUser;
    }

    public boolean getFlushExpireAfterOperation() {
        return flushExpireAfterOperation;
    }

    @Override
    public void delRelationship(String account, TokenClearType tokenClearType) {
        //如果是多个Token关联同一个Key，不允许直接通过Key删除所有Token，防止误操作
        if (!singleTokenWithUser) {
            throw new MethodNotSupportException("非单客户端登录时无法调用该方法");
        }
        delSingleRelationshipByKey(account, tokenClearType);
    }

    /**
     * 一个用户只能绑定一个Token时通过Key删除关联关系
     */
    protected abstract void delSingleRelationshipByKey(String account, TokenClearType tokenClearType);

    @Override
    public String createToken(String type, String userNo, String userId, String account) {
        String id = userId;
        // JWT
        String token = Jwts
                .builder()
                .setIssuer(type)
                .setId(userNo)
                .setSubject(account)
                .setAudience(id)
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(SignatureAlgorithm.HS512, AbstractTokenManager.TOKEN_KEY)
                .compact();

        // 根据设置的每个用户是否只允许绑定一个Token，调用不同的方法
        if (singleTokenWithUser) {
            //delSingleRelationshipByKey(account);
            createSingleRelationship(account, token);
        } else {
            createMultipleRelationship(account, token);
        }
        return token;
    }

    /**
     * 一个用户可以绑定多个Token时创建关联关系
     */
    protected abstract void createMultipleRelationship(String account, String token);

    /**
     * 一个用户只能绑定一个Token时创建关联关系
     */
    protected abstract void createSingleRelationship(String account, String token);

    @Override
    public String getAccount(String token) {
        String account = getAccountByToken(token, flushExpireAfterOperation);
        return account;
    }

    /**
     * 通过Token获得Key
     */
    protected abstract String getAccountByToken(String token, boolean flushExpireAfterOperation);

}
