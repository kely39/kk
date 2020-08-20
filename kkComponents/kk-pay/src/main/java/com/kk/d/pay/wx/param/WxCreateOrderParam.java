package com.kk.d.pay.wx.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 参数对象
 * @Author yangqh
 * @Date 17:17 2018/12/26
 **/
@Data
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class WxCreateOrderParam implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * 商品描述
     **/
    private String body;

    /**
     * 附加数据，用作回传参数（一般为业务表主键），回调使用
     **/
    private String attach;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。
     **/
    private String outTradeNo;

    /**
     * 支付金额，单位：元，两位小数
     **/
    private String totalFee;

    /**
     * 回调地址
     **/
    private String notifyUrl;

    /**
     * 终端类型，取值：WxPayConstants.TradeType.[APP/NATIVE]
     **/
    private String tradeType;
}
