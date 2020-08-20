package com.kk.d.pay.wx;

import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.kk.d.pay.AbstractPayService;
import com.kk.d.pay.wx.param.RefundOrderParam;
import com.kk.d.pay.wx.param.WxCreateOrderParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @Description: 微信支付相关接口
 * @Author yangqh
 * @Date 11:21 2018/12/28
 **/
public class AbstractWxPayService {

    @Autowired
    private WxProperties wxProperties;

    @Autowired(required = false)
    private WxPayService wxPayService;

    /**
     * @Description: 查询订单
     * @Author yangqh
     * @Date 11:26 2018/12/28
     * @Param [transactionId - 微信订单号, outTradeNo - 商户订单号]
     * @Return com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult
     * @see {官方接口地址：https://api.mch.weixin.qq.com/pay/orderquery}
     **/
    public WxPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxPayException {
        if (wxPayService == null) {
            throw new WxpayDisabledException("微信支付已禁用");
        }
        if (StringUtils.isEmpty(transactionId) && StringUtils.isEmpty(outTradeNo)) {
            throw new WxPayException("参数transactionId和outTradeNo不能同时为空");
        }
        return wxPayService.queryOrder(transactionId, outTradeNo);
    }

    /**
     * @Description: 支持 APP支付
     * @Author yangqh
     * @Date 11:30 2018/12/28
     * @Param [wxCreateOrderParam, abstractPayService]
     * @Return com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult
     * @see {官方接口地址：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1}
     **/
    public WxPayAppOrderResult createAppOrder(WxCreateOrderParam wxCreateOrderParam, AbstractPayService abstractPayService) throws WxPayException {
        return wxPayService.createOrder(createOrderPrepare(wxCreateOrderParam, abstractPayService));
    }

    /**
     * @Description: 支持原生扫码支付
     * @Author yangqh
     * @Date 11:30 2018/12/28
     * @Param [wxCreateOrderParam, abstractPayService]
     * @Return com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult
     * @see {官方接口地址：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1}
     **/
    public WxPayNativeOrderResult createPcOrder(WxCreateOrderParam wxCreateOrderParam, AbstractPayService abstractPayService) throws WxPayException {
        return wxPayService.createOrder(createOrderPrepare(wxCreateOrderParam, abstractPayService));
    }

    public WxPayUnifiedOrderRequest createOrderPrepare(WxCreateOrderParam wxCreateOrderParam, AbstractPayService abstractPayService) throws WxPayException {
        if (wxPayService == null) {
            throw new WxpayDisabledException("微信支付已禁用");
        }
        this.checkParam(wxCreateOrderParam);
        Boolean isRepeat = abstractPayService.prePayIdempotentJudge(wxCreateOrderParam.getAttach());
        if (isRepeat) {
            throw new WxPayException("订单已支付");
        }

        WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder()
                .body(wxCreateOrderParam.getBody())
                .attach(wxCreateOrderParam.getAttach())
                .outTradeNo(wxCreateOrderParam.getOutTradeNo())
                .totalFee(new BigDecimal(wxCreateOrderParam.getTotalFee()).multiply(new BigDecimal(100)).intValue())
                .spbillCreateIp("127.0.0.1")
                .notifyUrl(wxCreateOrderParam.getNotifyUrl())
                .tradeType(wxCreateOrderParam.getTradeType())
                //trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
                .productId(UUID.randomUUID().toString())
                .build();
        return request;
    }

    /**
     * @Description: 申请退款 <p/> 要求：将api证书存放resourses的一级目录下（测试使用证书参见ctl-starter\ctl-pay\test\resourses\apiclient_cert.p12）
     * @Author yangqh
     * @Date 12:22 2018/12/28
     * @Param [refundOrderParam]
     * @Return com.github.binarywang.wxpay.bean.result.WxPayRefundResult
     * @see {官方接口地址：https://api.mch.weixin.qq.com/secapi/pay/refund}
     **/
    public WxPayRefundResult refund(RefundOrderParam refundOrderParam) throws WxPayException {

        if (wxPayService == null) {
            throw new WxpayDisabledException("微信支付已禁用");
        }
        this.checkRefundOrderParam(refundOrderParam);
        WxPayRefundRequest request = WxPayRefundRequest.newBuilder()
                .outTradeNo(refundOrderParam.getOutTradeNo())
                .outRefundNo(refundOrderParam.getOutRefundNo())
                .totalFee(new BigDecimal(refundOrderParam.getTotalFee()).multiply(new BigDecimal(100)).intValue())
                .refundFee(new BigDecimal(refundOrderParam.getRefundFee()).multiply(new BigDecimal(100)).intValue())
                .build();
        return wxPayService.refund(request);
    }

    private void checkRefundOrderParam(RefundOrderParam refundOrderParam) throws WxPayException {

        if (StringUtils.isEmpty(refundOrderParam.getOutTradeNo())) {
            throw new WxPayException("参数outTradeNo不能为空");
        }

        if (StringUtils.isEmpty(refundOrderParam.getOutRefundNo())) {
            throw new WxPayException("参数outRefundNo不能为空");
        }

        if (StringUtils.isEmpty(refundOrderParam.getRefundFee())) {
            throw new WxPayException("参数totalFee不能为空");
        } else if (!Pattern.matches("^\\d{0,8}\\.{0,1}(\\d{1,2})?$", refundOrderParam.getRefundFee())) {
            throw new WxPayException("参数totalFee格式错误，要求：数字或小数位不超过2位");
        }

        if (StringUtils.isEmpty(refundOrderParam.getTotalFee())) {
            throw new WxPayException("参数refundFee不能为空");
        } else if (!Pattern.matches("^\\d{0,8}\\.{0,1}(\\d{1,2})?$", refundOrderParam.getTotalFee())) {
            throw new WxPayException("参数refundFee格式错误，要求：数字或小数位不超过2位");
        }

        int totalFee = new BigDecimal(refundOrderParam.getRefundFee()).multiply(new BigDecimal(100)).intValue();
        int refundFee = new BigDecimal(refundOrderParam.getRefundFee()).multiply(new BigDecimal(100)).intValue();
        if (refundFee > totalFee) {
            throw new WxPayException("参数refundFee不能大于totalFee");
        }
    }

    public void checkParam(WxCreateOrderParam wxCreateOrderParam) throws WxPayException {

        if (StringUtils.isEmpty(wxCreateOrderParam.getBody())) {
            throw new WxPayException("参数body不能为空");
        }

        if (StringUtils.isEmpty(wxCreateOrderParam.getAttach())) {
            throw new WxPayException("参数attach不能为空");
        }

        if (StringUtils.isEmpty(wxCreateOrderParam.getOutTradeNo())) {
            throw new WxPayException("参数outTradeNo不能为空");
        }

        if (StringUtils.isEmpty(wxCreateOrderParam.getTotalFee())) {
            throw new WxPayException("参数totalFee不能为空");
        } else if (!Pattern.matches("^\\d{0,8}\\.{0,1}(\\d{1,2})?$", wxCreateOrderParam.getTotalFee())) {
            throw new WxPayException("参数totalFee格式错误，要求：数字或小数位不超过2位");
        }

        if (StringUtils.isEmpty(wxCreateOrderParam.getNotifyUrl())) {
            wxCreateOrderParam.setNotifyUrl(wxProperties.getNotifyUrl());
        }

        if (wxCreateOrderParam.getTradeType() == null) {
            throw new WxPayException("参数tradeType不能为空");
        }
    }
}
