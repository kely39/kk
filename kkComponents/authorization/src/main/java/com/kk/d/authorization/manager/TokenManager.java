package com.kk.d.authorization.manager;

import com.kk.d.authorization.constants.TokenClearType;

/**
 * 对Token进行管理的接口
 * 引入JWT 2017/03/15
 *
 * @author kk
 * @date 2019/12/26
 **/
public interface TokenManager {

    /**
     * 通过key删除关联关系
     *
     * @param account
     * @param tokenClearType
     */
    void delRelationship(String account, TokenClearType tokenClearType);

    /**
     * 通过token删除关联关系
     *
     * @param token
     */
    void delRelationshipByToken(String token);

    /**
     * 创建token
     *
     * @param type
     * @param userNo
     * @param userId
     * @param account
     */
    String createToken(String type, String userNo, String userId, String account);

    /**
     * 通过token获得对应的key
     *
     * @param token
     * @return
     */
    String getAccount(String token);

    /**
     * 如token存在，但鉴权失败时通过该接口获取失败原因
     *
     * @param token
     * @return
     */
    TokenClearType getTokenClearType(String token);
}
