package com.kk.d.pay.wx.ret;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntPayResultDTO {

    /**
     * 状态：0-成功 1-失败（原商户订单号重试） 2-失败（更新商户订单号重试）
     **/
    private String status;

    /**
     * 错误信息
     **/
    private String errMsg;

    /**
     * 错误码
     **/
    private String errCode;

    /**
     * 控制显示errMsg标记（true推送用户errMsg，false推送用户errCode）
     * {EntPayErrCodeEnum}
     **/
    private boolean showFlag;

    /**
     * 商户订单号
     **/
    private String partnerTradeNo;

    /**
     * 商户付款单号
     **/
    private String paymentNo;

    /**
     * 接口返回元数据
     **/
    private String xmlSting;

    /**
     * 接口请求元数据
     **/
    private String reqXml;
}
