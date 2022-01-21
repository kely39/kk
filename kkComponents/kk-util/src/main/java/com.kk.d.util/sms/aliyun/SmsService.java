package com.kk.d.util.sms.aliyun;

/**
 * @author kk
 * @date 2019/12/26
 **/
public interface SmsService {

    /**
     * 发送短信
     *
     * @param mobile       手机号
     * @param code         验证码
     * @param templateCode 短信模板
     * @return 发送成功/失败响应
     */
    String sendSms(String mobile, String code, String templateCode);
}
