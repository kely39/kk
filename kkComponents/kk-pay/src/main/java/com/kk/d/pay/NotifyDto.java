package com.kk.d.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description: 回调业务参数
 * @Author yangqh
 * @Date 11:23 2019/1/23
 **/
@Data
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class NotifyDto {

    /**
     * 商家数据包：回参参数，一般设置为业务表主键
     **/
    private String attach;

    /**
     * 第三方订单号
     **/
    private String transactionId;

    /**
     * 回调订单总金额，单位：元
     **/
    private BigDecimal totalFee;

    /**
     * 商户订单号，与请求一致。
     **/
    private String outTradeNo;

    /**
     * 交易状态
     **/
    private String tradeStatus;

    /**
     * 支付类型  {@link PayContants}
     */
    private Integer payType;

    //--------------------------------------------->

    /**
     * 订单表订单金额，单位：元，幂等性判断时赋值（用于判断回调金额与订单表金额是否一致）
     **/
    private BigDecimal orderFee;
}
