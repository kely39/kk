package com.kk.d.pay.wx.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangqh
 * @date 2019/8/26
 * @link {https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2}
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntPayParam {

    /**
     * 商户订单号-必填
     **/
    private String partnerTradeNo;

    /**
     * 用户openid-必填
     **/
    private String openid;

    /**
     * 校验用户姓名选项（非必填，不填时默认NO_CHECK）
     * NO_CHECK：不校验真实姓名
     * FORCE_CHECK：强校验真实姓名
     **/
    private String checkName;

    /**
     * 收款用户姓名-checkName=FORCE_CHECK时必填
     **/
    private String reUserName;

    /**
     * 支付金额，单位：元，两位小数-必填
     **/
    private String amount;

    /**
     * 企业付款备注-必填
     *  企业付款备注。注意：备注中的敏感词会被转成字符*
     **/
    private String desc;
}
