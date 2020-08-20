package com.kk.d.base.constants;

import com.kk.d.framework.web.BaseResultCode;

public class ResultCode extends BaseResultCode {

    public static final int USER_NOT_EXIST = 1100;
    public static final int USER_EXIST = 1101;

    public static final int SMS_CODEID_ERROR = 1102;
    public static final int SMS_CODEID_USED = 1103;
    public static final int SMS_CODE_ERROR = 1104;
    public static final int SMS_MOBILE_NOTMATCH = 1105;
    public static final int SMS_CODE_OVERTIME = 1106;

    public static final int IMGCODE_ERROR = 1107;
    public static final int IMGCODE_OVERTIME = 1108;

    public static final int WEI_XIN_LOGIN_IS_NOT_BIND = 1109;

    public static final int SMS_CODE_MOBILE_ACCOUNT_NOMATCH = 1110;

    // 初始化状态码与文字说明
    static {

        messageMap.put(USER_NOT_EXIST, "用户未注册");
        messageMap.put(USER_EXIST, "用户已存在");

        messageMap.put(SMS_CODEID_ERROR, "手机验证码ID错误");
        messageMap.put(SMS_CODEID_USED, "验证码已使用");
        messageMap.put(SMS_CODE_ERROR, "验证码错误");
        messageMap.put(SMS_MOBILE_NOTMATCH, "手机号与验证码不匹配");
        messageMap.put(SMS_CODE_OVERTIME, "短信验证码已失效");

        messageMap.put(IMGCODE_ERROR, "验证码错误");
        messageMap.put(IMGCODE_OVERTIME, "验证码已失效或不存在");

        messageMap.put(WEI_XIN_LOGIN_IS_NOT_BIND, "当前没有绑定微信登录，请授权绑定");

        messageMap.put(SMS_CODE_MOBILE_ACCOUNT_NOMATCH, "接收短信手机号码与当前账号不匹配");
    }
}
