package com.kk.d.base.constants;

/**
 * 缓存key常量
 *
 * @author yangqh
 * @date 2019/12/27
 **/
public class KeyConstant {

    /**
     * 会员账号-token
     */
    @Deprecated
    public static final String REDIS_KEY_MEMBER_PREFIX = "AUTHORIZATION_KEY_MEMBER_";

    /**
     * 会员token-账号
     */
    @Deprecated
    public static final String REDIS_TOKEN_MEMBER_PREFIX = "AUTHORIZATION_TOKEN_MEMBER_";

    /**
     * 商家账号-token
     */
    @Deprecated
    public static final String REDIS_KEY_MERCHANT_PREFIX = "AUTHORIZATION_KEY_MERCHANT_";

    /**
     * 商家token-账号
     */
    @Deprecated
    public static final String REDIS_TOKEN_MERCHANT_PREFIX = "AUTHORIZATION_TOKEN_MERCHANT_";

    /**
     * 账号-token
     */
    public static final String REDIS_ACCOUNT_PREFIX = "AUTH_ACCOUNT_";

    /**
     * token-账号
     */
    public static final String REDIS_TOKEN_PREFIX = "AUTH_TOKEN_";

    /**
     * token-删除原因
     */
    public static final String REDIS_TOKEN_CLEAR_PREFIX = "AUTH_TOKEN_DEL_";

    /**
     *  图形验证码
     */
    public static final String IMG_CODE = "IMG_CODE";
}
