package com.kk.d.pay.alipay;

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
public class AlipayParam implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * 主题
     **/
    private String subject;

    /**
     * 支付金额，单位：元，两位小数
     **/
    private String totalAmount;

    /**
     * 商户订单号
     **/
    private String outTradeNo;

    /**
     * 回传参数
     **/
    private String body;

    /**
     * 回调地址
     **/
    private String notifyUrl;
}
