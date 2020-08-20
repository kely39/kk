package com.kk.d.base.service;

import com.kk.d.base.entity.KkSmsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 短信表 服务类
 * </p>
 *
 * @author yangqh
 * @since 2019-12-27
 */
public interface KkSmsService extends IService<KkSmsEntity> {

    /**
     * 判断验证码是否用过[id是否正确，短信是否使用过，验证码是否正确，参数手机号码和验证码手机号是否一致] 成功返回1000
     *
     * @author yangqh
     * @date 2019/12/27
     **/
    int checkSmsCode(String verifyCodeId, String smsCode, String account);

    /**
     * 使用短信
     *
     * @author yangqh
     * @date 2019/12/27
     **/
    void useSmsCode(String verifyCodeId);

    /**
     * 发送短信
     *
     * @author yangqh
     * @date 2019/12/28
     **/
    String sendMsg(String mobile);
}
