package com.kk.d.pay.wx.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 参数对象
 * @Author kk
 * @Date 17:17 2018/12/26
 **/
@Data
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class RefundOrderParam implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * 商户订单号
     **/
    private String outTradeNo;

    /**
     * 退款订单号
     **/
    private String outRefundNo;

    /**
     * 订单金额，单位：元，两位小数
     **/
    private String totalFee;

    /**
     * 退款金额，单位：元，两位小数
     **/
    private String refundFee;

}
