package com.kk.d.pay.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.kk.d.pay.AbstractPayService;
import com.kk.d.pay.NotifyDto;
import com.kk.d.pay.PayContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description: 支付宝回调接口
 * @Author kk
 * @Date 14:10 2019/1/23
 * @see {https://docs.open.alipay.com/194/103296/}
 **/
@Slf4j
public class AbstractAlipayNotifyService {

    @Autowired
    private AlipayProperties alipayProperties;

    /**
     * @Description: 支付宝回调接口（封装通知校验，验签，参数解析，业务校验）
     * @Author kk
     * @Date 11:31 2019/1/23
     * @Param [request]
     * @Return void
     **/
    public void executeNotify(HttpServletRequest request, HttpServletResponse response, AbstractPayService abstractPayService) throws AlipayApiException {

        NotifyDto notifyDto = verify(request);
        if (!AliConstants.TRADE_SUCCESS.equals(notifyDto.getTradeStatus())) {
            log.error("支付宝回调失败，tradeStatus={}，参数：{}", notifyDto.getTradeStatus(), JSON.toJSONString(notifyDto));
            return;
        }

        if (abstractPayService.notifyIdempotentJudge(notifyDto)) {
            log.info("支付宝重复回调，{}", JSON.toJSONString(notifyDto));
            return;
        }

        if (!abstractPayService.notifyIDataVerify(notifyDto)) {
            log.error("支付宝回调金额错误，{}", JSON.toJSONString(notifyDto));
            return;
        }
        notifyDto.setPayType(PayContants.AILI_PAY);
        abstractPayService.executeBusiness(notifyDto);

        try {
            OutputStream out = response.getOutputStream();
            out.write("success".getBytes(alipayProperties.getCharset()));
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("支付宝回调返回异常:{}", e.getMessage());
        }

//        PrintWriter writer;
//        try {
//            writer = response.getWriter();
//            writer.write("success");
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
//            log.error("支付宝回调返回异常:{}", e.getMessage());
//        }
    }

    /**
     * @Description: 校验回调数据
     * @Author kk
     * @Date 15:00 2019/1/23
     * @Param [request]
     * @Return com.ctl.starter.pay.NotifyDto
     **/
    private NotifyDto verify(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> responseMap = new TreeMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            responseMap.put(name, valueStr);
        }
        boolean verifyResult = false;
        try {
            //rsaCheckV2 和rsaCheckV1的区别
            //目前支付宝的SDK验签方法主要有两种一种是rsaCheckV1一种是rsaCheckV2 两种验签方法用于不同的接口的返回参数验签
            //    1、rsaCheckV1验签方法
            //       rsaCheckV1验签方法主要用于支付接口的返回参数的验签比如：当面付，APP支付，手机网站支付，电脑网站支付 这些接口都是使用rsaCheckV1方法验签的
            //    2、rsaCheckV2验签方法
            //       rsaCheckV2验签方法主要是用于物业缴费接口以及生活号相关的事件消息和口碑服务市场订购信息等发送到应用网关地址的异步信息的验签
            //(@see {https://opensupport.alipay.com/support/knowledge/20060/201602315102?ant_source=zsearch})

            verifyResult = AlipaySignature.rsaCheckV1(responseMap, alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(), alipayProperties.getSignType());

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (!verifyResult) {
            log.error("支付宝回调参数：{}", JSON.toJSONString(responseMap));
            throw new AlipayApiException("伪造的通知！");
        }

        String outTradeNo = request.getParameter("out_trade_no");
        String tradeNo = request.getParameter("trade_no");
        String totalAmount = request.getParameter("total_amount");
        //APP支付
        String passbackParams = request.getParameter("passback_params");
        if (passbackParams == null) {
            //pc扫码
            passbackParams = request.getParameter("body");
        }
        try {
            passbackParams = java.net.URLDecoder.decode(passbackParams, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("解析参数passbackParams错误", e);
            throw new AlipayApiException("支付宝回调异常");
        }
        String tradeStatus = request.getParameter("trade_status");
        return NotifyDto.newBuilder().attach(passbackParams)
                .outTradeNo(outTradeNo)
                .totalFee(new BigDecimal(totalAmount))
                .transactionId(tradeNo)
                .tradeStatus(tradeStatus).build();
    }

}
