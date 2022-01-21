package com.kk.d.pay.wx;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.kk.d.pay.AbstractPayService;
import com.kk.d.pay.NotifyDto;
import com.kk.d.pay.PayContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * @Description: 微信回调接口
 * @Author kk
 * @Date 10:30 2019/1/23
 **/
@Slf4j
public class AbstractWxPayNotifyService {

    @Autowired(required = false)
    private WxPayService wxPayService;

    /**
     * @Description: 微信回调接口（封装通知校验，验签，参数解析，业务校验）
     * @Author kk
     * @Date 11:31 2019/1/23
     * @Param [request]
     * @Return void
     **/
    public void executeNotify(HttpServletRequest request, HttpServletResponse response, AbstractPayService abstractPayService) throws WxPayException {

        WxPayOrderNotifyResult wxPayOrderNotifyResult = verify(request);
        if (!WxPayConstants.ResultCode.SUCCESS.equals(wxPayOrderNotifyResult.getResultCode())) {
            log.error("微信回调失败，参数：{}", JSON.toJSONString(wxPayOrderNotifyResult));
            throw new WxPayException("回调失败，" + wxPayOrderNotifyResult.getErrCode() + "," + wxPayOrderNotifyResult.getErrCodeDes());
        }
        NotifyDto notifyDto = NotifyDto.newBuilder().attach(wxPayOrderNotifyResult.getAttach())
                .outTradeNo(wxPayOrderNotifyResult.getOutTradeNo())
                .totalFee(BigDecimal.valueOf(wxPayOrderNotifyResult.getTotalFee()).divide(BigDecimal.valueOf(100))).payType(PayContants.WECHAT_PAY)
                .transactionId(wxPayOrderNotifyResult.getTransactionId()).tradeStatus(wxPayOrderNotifyResult.getResultCode()).build();

        if (abstractPayService.notifyIdempotentJudge(notifyDto)) {
            log.info("微信支付重复回调，{}", JSON.toJSONString(notifyDto));
            return;
        }

        if (!abstractPayService.notifyIDataVerify(notifyDto)) {
            log.error("微信回调金额错误，{}", JSON.toJSONString(notifyDto));
            return;
        }

        abstractPayService.executeBusiness(notifyDto);

        try {
            response.setContentType("text/xml");
            OutputStream out = response.getOutputStream();
            out.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>".getBytes());
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("微信回调返回异常:{}", e.getMessage());
        }

//        PrintWriter writer;
//        try {
//            response.setContentType("text/xml");
//            writer = response.getWriter();
//            writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            log.error("微信回调返回异常:{}", e.getMessage());
//        }
    }

    /**
     * @Description: 校验回调数据
     * @Author kk
     * @Date 11:09 2019/1/23
     * @Param [request]
     * @Return com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult
     **/
    protected WxPayOrderNotifyResult verify(HttpServletRequest request) throws WxPayException {
        InputStream inputStream;
        ByteArrayOutputStream outSteam;
        String resultXml;
        try {
            inputStream = request.getInputStream();
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, length);
            }
            outSteam.close();
            inputStream.close();
            resultXml = new String(outSteam.toByteArray(), "utf-8");
        } catch (IOException e) {
            log.error("微信回调异常", e);
            throw new WxPayException("微信回调异常");
        }
        return wxPayService.parseOrderNotifyResult(resultXml);
    }

}
