package com.kk.d.pay.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.kk.d.pay.AbstractPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @Description: 支付宝相关接口
 * @Author yangqh
 * @Date 17:03 2018/12/26
 **/
@Slf4j
public class AlipayServiceHandle {

    @Autowired(required = false)
    private AlipayClient alipayClient;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired(required = false)
    private AlipayTradeService alipayTradeService;

    /**
     * @Description: PC创建预支付订单
     * @Author yangqh
     * @Date 17:55 2018/12/26
     * @Param [alipayParam]
     * @Return java.lang.String 返回支付二维码
     * @see {https://docs.open.alipay.com/api_1/alipay.trade.precreate}
     **/
    public String createPcPreOrder(AlipayParam alipayParam, AbstractPayService abstractPayService) throws AlipayApiException {

        if (alipayTradeService == null) {
            throw new AlipayDisabledException("支付宝已禁用");
        }
        this.checkParam(alipayParam);
        Boolean isRepeat = abstractPayService.prePayIdempotentJudge(alipayParam.getBody());
        if (isRepeat) {
            throw new AlipayApiException("订单已支付");
        }

        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(alipayParam.getSubject())
                .setTotalAmount(alipayParam.getTotalAmount())
                .setOutTradeNo(alipayParam.getOutTradeNo())
                .setTimeoutExpress(alipayProperties.getTimeoutExpress())
                //文档为可选，SDK必填
                .setStoreId("ctl_alipay_01")
                .setBody(alipayParam.getBody())
                .setNotifyUrl(alipayParam.getNotifyUrl());
        AlipayF2FPrecreateResult result = alipayTradeService.tradePrecreate(builder);
        AlipayTradePrecreateResponse response = null;
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝二维码预下单成功！");
                response = result.getResponse();
                return response.getQrCode();
            default:    //FAILED | UNKNOWN

                throw new AlipayApiException("调用支付宝(tradePrecreate)返回错误，tradeStatus=" + result.getTradeStatus() + "|code=" + response.getCode() + "|msg=" + response.getMsg() + "|subCode=" + response.getSubCode() + "|subMsg=" + response.getSubMsg());
        }
    }

    /**
     * @Description: APP创建预支付订单
     * @Author yangqh
     * @Date 18:33 2018/12/26
     * @Param [alipayParam]
     * @Return java.lang.String
     * @see {https://docs.open.alipay.com/204/105465/}
     **/
    public String createAppPreOrder(AlipayParam alipayParam, AbstractPayService abstractPayService) throws AlipayApiException {

        if (alipayClient == null) {
            throw new AlipayDisabledException("支付宝已禁用");
        }
        this.checkParam(alipayParam);
        Boolean isRepeat = abstractPayService.prePayIdempotentJudge(alipayParam.getBody());
        if (isRepeat) {
            throw new AlipayApiException("订单已支付");
        }
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(alipayParam.getSubject());
        model.setOutTradeNo(alipayParam.getOutTradeNo());
        model.setTotalAmount(alipayParam.getTotalAmount());
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setPassbackParams(alipayParam.getBody());
        request.setBizModel(model);
        request.setNotifyUrl(alipayParam.getNotifyUrl());
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        if (response.isSuccess()) {
            return response.getBody();
        }
        throw new AlipayApiException("调用支付宝(AlipayTradeAppPayRequest)返回错误，code=" + response.getCode() + "|msg=" + response.getMsg() + "|subCode=" + response.getSubCode() + "|subMsg=" + response.getSubMsg());
    }

    /**
     * @Description: 订单查询
     * @Author yangqh
     * @Date 18:38 2018/12/26
     * @Param [outTradeNo - 商户订单号]
     * @Return java.lang.Object
     **/
    public AlipayTradeQueryResponse orderQuery(String outTradeNo) throws AlipayApiException {

        if (alipayClient == null) {
            throw new AlipayDisabledException("支付宝已禁用");
        }
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", outTradeNo);
        String biz = bizContent.toString().replaceAll("\"", "'");
        request.setBizContent(biz);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            return response;
        }
        throw new AlipayApiException("调用支付宝(AlipayTradeQueryRequest)返回错误，code=" + response.getCode() + "|msg=" + response.getMsg() + "|subCode=" + response.getSubCode() + "|subMsg=" + response.getSubMsg());
    }

    /**
     * @Description: alipay.trade.refund(统一收单交易退款接口)
     * @Author yangqh
     * @Date 18:48 2018/12/26
     * @Param [outTradeNo - 商户订单号, refundAmount - 退款金额（单位：元/两位小数）]
     * @Return java.lang.Boolean
     * @see {https://docs.open.alipay.com/api_1/alipay.trade.refund/}
     **/
    public Boolean tradeRefund(String outTradeNo, String refundAmount) throws AlipayApiException {

        if (alipayClient == null) {
            throw new AlipayDisabledException("支付宝已禁用");
        }
        this.checkRefundParam(outTradeNo, refundAmount);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", outTradeNo);
        bizContent.put("refund_amount", refundAmount);
        String biz = bizContent.toString().replaceAll("\"", "'");
        request.setBizContent(biz);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess() && AliConstants.SUCCESS.equals(response.getCode())) {
            return true;
        }
        throw new AlipayApiException("调用支付宝(AlipayTradeRefundRequest)返回错误，code=" + response.getCode() + "|msg=" + response.getMsg() + "|sub_code=" + response.getSubCode() + "|sub_msg=" + response.getSubMsg());
    }

    private void checkRefundParam(String outTradeNo, String refundAmount) throws AlipayApiException {
        if (StringUtils.isEmpty(outTradeNo)) {
            throw new AlipayApiException("参数outTradeNo不能为空");
        }
        if (StringUtils.isEmpty(refundAmount)) {
            throw new AlipayApiException("参数refundAmount不能为空");
        } else if (!Pattern.matches("^\\d{0,8}\\.{0,1}(\\d{1,2})?$", refundAmount)) {
            throw new AlipayApiException("参数refundAmount格式错误，要求：数字或小数位不超过2位");
        }
    }

    private void checkParam(AlipayParam alipayParam) throws AlipayApiException {

        if (StringUtils.isEmpty(alipayParam.getSubject())) {
            throw new AlipayApiException("参数subject不能为空");
        }

        if (StringUtils.isEmpty(alipayParam.getTotalAmount())) {
            throw new AlipayApiException("参数totalAmount不能为空");
        } else if (!Pattern.matches("^\\d{0,8}\\.{0,1}(\\d{1,2})?$", alipayParam.getTotalAmount())) {
            throw new AlipayApiException("参数totalAmount格式错误，要求：数字或小数位不超过2位");
        }

        if (StringUtils.isEmpty(alipayParam.getOutTradeNo())) {
            throw new AlipayApiException("参数outTradeNo不能为空");
        }

        if (StringUtils.isEmpty(alipayParam.getBody())) {
            throw new AlipayApiException("参数body不能为空");
        }

        if (StringUtils.isEmpty(alipayParam.getNotifyUrl())) {
            alipayParam.setNotifyUrl(alipayProperties.getNotifyUrl());
        }

    }
}
