package com.kk.d.pay.wx;

/**
 * @author kk
 * @date 2019/8/27
 * @see {https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2}
 **/
public enum EntPayErrCodeEnum {

    NOTENOUGH(true, "余额不足", true),
    SYSTEMERROR(true, "", false),
    NAME_MISMATCH(true, "姓名校验出错", true),
    SIGN_ERROR(true, "", false),
    FREQ_LIMIT(true, "", false),
    MONEY_LIMIT(true, "已经达到今日付款总额上限", true),
    CA_ERROR(true, "", false),
    V2_ACCOUNT_SIMPLE_BAN(true, "无法给非实名用户付款", true),
    PARAM_IS_NOT_UTF8(true, "", false),
    SENDNUM_LIMIT(true, "该用户今日付款次数超过限制", true),

    AMOUNT_LIMIT(false, "金额超限", true),
    NO_AUTH(false, "", false),
    PARAM_ERROR(false, "", false),
    OPENID_ERROR(false, "", false),
    SEND_FAILED(false, "", false),
    XML_ERROR(false, "", false),
    FATAL_ERROR(false, "", false),
    RECV_ACCOUNT_NOT_ALLOWED(false, "", false),
    PAY_CHANNEL_NOT_ALLOWED(false, "", false),
    UNDEUND_ERROR(false, "未知错误", false);//系统定义，不是微信商户平台定义

    private boolean changeFlag;//是否原商户订单号重试
    private String errMsg;
    private boolean showFlag;//是否显示问题提示

    EntPayErrCodeEnum(boolean changeFlag, String errMsg, boolean showFlag) {
        this.changeFlag = changeFlag;
        this.errMsg = errMsg;
        this.showFlag = showFlag;
    }

    public static boolean isExist(String name) {
        EntPayErrCodeEnum[] entPayErrCodeEnums = EntPayErrCodeEnum.values();
        for (EntPayErrCodeEnum codeEnum : entPayErrCodeEnums) {
            if (codeEnum.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChangeFlag(String name) {
        EntPayErrCodeEnum[] entPayErrCodeEnums = EntPayErrCodeEnum.values();
        for (EntPayErrCodeEnum codeEnum : entPayErrCodeEnums) {
            if (codeEnum.name().equals(name)) {
                return codeEnum.isChangeFlag();
            }
        }
        return false;
    }

    public static boolean isShowFlag(String name) {
        EntPayErrCodeEnum[] entPayErrCodeEnums = EntPayErrCodeEnum.values();
        for (EntPayErrCodeEnum codeEnum : entPayErrCodeEnums) {
            if (codeEnum.name().equals(name)) {
                return codeEnum.isShowFlag();
            }
        }
        return false;
    }

    public static EntPayErrCodeEnum getEnum(String name) {
        EntPayErrCodeEnum[] entPayErrCodeEnums = EntPayErrCodeEnum.values();
        for (EntPayErrCodeEnum codeEnum : entPayErrCodeEnums) {
            if (codeEnum.name().equals(name)) {
                return codeEnum;
            }
        }
        return null;
    }

    public boolean isChangeFlag() {
        return changeFlag;
    }

    public boolean isShowFlag() {
        return showFlag;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
