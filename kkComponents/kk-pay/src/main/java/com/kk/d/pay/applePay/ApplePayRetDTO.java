package com.kk.d.pay.applePay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplePayRetDTO {

    /**
     * 状态
     * {@link StatusConstant}
     **/
    private String status;

    /**
     * 苹果后台配置的商品ID
     **/
    private String productId;

    /**
     * 二次验证返回参数
     **/
    private String resultStr;

    private String transactionId;
}
