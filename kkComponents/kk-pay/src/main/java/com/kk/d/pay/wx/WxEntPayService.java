package com.kk.d.pay.wx;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.entpay.EntPayQueryResult;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.EntPayService;
import com.kk.d.pay.wx.param.EntPayParam;
import com.kk.d.pay.wx.ret.EntPayQueryResultDTO;
import com.kk.d.pay.wx.ret.EntPayResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 企业付款接口
 *
 * @author kk
 * @date 2019/8/26
 **/
@Slf4j
public class WxEntPayService {

    @Autowired
    private WxProperties wxProperties;

    @Autowired(required = false)
    private EntPayService entPayService;

    private static final String SUCCESS = "SUCCESS";

    /**
     * 企业付款到零钱（是否需要证书：是）
     *
     * @author kk
     * @date 2019/8/26
     * @see {https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2}
     **/
    public EntPayResultDTO entPay(EntPayParam entPayParam) throws WxPayException {
        if (entPayService == null) {
            throw new WxPayException("微信提现已禁用");
        }
        checkEntPay(entPayParam);
        EntPayRequest request = EntPayRequest.newBuilder()
                .mchAppid(wxProperties.getAppId())
                .mchId(wxProperties.getMchId())
                .partnerTradeNo(entPayParam.getPartnerTradeNo())
                .openid(entPayParam.getOpenid())
                .checkName(entPayParam.getCheckName() == null || "".equals(entPayParam.getCheckName()) ? "FORCE_CHECK" : entPayParam.getCheckName())
                .reUserName(entPayParam.getReUserName())
                .amount(new BigDecimal(entPayParam.getAmount()).multiply(BigDecimal.valueOf(100)).intValue())
                .description(entPayParam.getDesc())
                .spbillCreateIp("127.0.0.1").build();
        EntPayResult entPayResult;
        try {
            entPayResult = entPayService.entPay(request);
            if (SUCCESS.equals(entPayResult.getReturnCode()) && SUCCESS.equals(entPayResult.getResultCode())) {
                log.info("企业付款到零钱成功：参数：{}", JSON.toJSONString(entPayParam));
                EntPayResultDTO dto = EntPayResultDTO.builder().status("0").partnerTradeNo(entPayParam.getPartnerTradeNo()).paymentNo(entPayResult.getPaymentNo()).xmlSting(entPayResult.getXmlString()).reqXml(request.toXML()).build();
                log.info("企业付款最终返回参数：{}", JSON.toJSONString(dto));
                return dto;
            }
        } catch (WxPayException e) {
            log.error("企业付款到零钱失败，异常信息：{}，原因：return_msg：{}，err_code_des：{}，参数：{}", e.getMessage(), e.getReturnMsg(), e.getErrCodeDes(), JSON.toJSONString(entPayParam));
            EntPayResultDTO dto = EntPayResultDTO.builder().partnerTradeNo(entPayParam.getPartnerTradeNo()).xmlSting(e.getXmlString()).reqXml(request.toXML()).errCode(e.getErrCode()).errMsg(e.getErrCodeDes() == null || "".equals(e.getErrCodeDes()) ? e.getMessage() : e.getErrCodeDes()).showFlag(false).build();
            if (!EntPayErrCodeEnum.isExist(e.getErrCode()) || !EntPayErrCodeEnum.isChangeFlag(e.getErrCode())) {
                dto.setStatus("2");
            } else {
                dto.setStatus("1");
            }
            if (EntPayErrCodeEnum.isShowFlag(e.getErrCode())) {
                dto.setShowFlag(true);
                dto.setErrMsg(EntPayErrCodeEnum.getEnum(e.getErrCode()).getErrMsg());
            } else if (!EntPayErrCodeEnum.isExist(e.getErrCode())) {
                dto.setErrCode(EntPayErrCodeEnum.UNDEUND_ERROR.name());
            }
            log.info("企业付款最终返回参数：{}", JSON.toJSONString(dto));
            return dto;
        }
        EntPayResultDTO dto = EntPayResultDTO.builder().status("1").partnerTradeNo(entPayParam.getPartnerTradeNo()).errMsg(EntPayErrCodeEnum.UNDEUND_ERROR.getErrMsg()).showFlag(false).errCode(EntPayErrCodeEnum.UNDEUND_ERROR.name()).xmlSting(entPayResult.getXmlString()).reqXml(request.toXML()).build();
        log.info("企业付款最终返回参数：{}", JSON.toJSONString(dto));
        return dto;
    }

    /**
     * 查询企业付款
     *
     * @author kk
     * @date 2019/8/27
     * @see {https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3}
     **/
    public EntPayQueryResultDTO queryEntPay(String partnerTradeNo) throws WxPayException {
        if (entPayService == null) {
            throw new WxPayException("微信提现已禁用");
        }
        if (partnerTradeNo == null || "".equals(partnerTradeNo)) {
            throw new WxPayException("商户订单号不能为空");
        }
        try {
            EntPayQueryResult entPayQueryResult = entPayService.queryEntPay(partnerTradeNo);
            if (SUCCESS.equals(entPayQueryResult.getReturnCode()) && SUCCESS.equals(entPayQueryResult.getResultCode())) {
                if (EntPayResultEnum.SUCCESS.name().equals(entPayQueryResult.getStatus())) {
                    return EntPayQueryResultDTO.builder().resultEnum(EntPayResultEnum.SUCCESS).build();
                } else if (EntPayResultEnum.PROCESSING.name().equals(entPayQueryResult.getStatus())) {
                    return EntPayQueryResultDTO.builder().resultEnum(EntPayResultEnum.PROCESSING).errMsg(entPayQueryResult.getErrCodeDes()).build();
                } else {
                    return EntPayQueryResultDTO.builder().resultEnum(EntPayResultEnum.FAILED).errMsg(entPayQueryResult.getErrCodeDes()).build();
                }
            }
        } catch (WxPayException e) {
            return EntPayQueryResultDTO.builder().resultEnum(EntPayResultEnum.FAILED).errMsg((e.getErrCodeDes() != null && !"".equals(e.getErrCodeDes())) ? e.getErrCodeDes() : e.getReturnMsg()).build();
        }
        return EntPayQueryResultDTO.builder().resultEnum(EntPayResultEnum.FAILED).errMsg(EntPayErrCodeEnum.UNDEUND_ERROR.getErrMsg()).build();
    }


    private void checkEntPay(EntPayParam entPayParam) throws WxPayException {
        if (entPayParam == null) {
            throw new WxPayException("参数不能为空");
        } else {
            if (entPayParam.getPartnerTradeNo() == null || "".equals(entPayParam.getPartnerTradeNo())) {
                throw new WxPayException("商户订单号不能为空");
            }
            if (entPayParam.getOpenid() == null || "".equals(entPayParam.getOpenid())) {
                throw new WxPayException("用户openid不能为空");
            }
            if (entPayParam.getCheckName() == null || "".equals(entPayParam.getCheckName())) {
                throw new WxPayException("校验用户姓名选项不能为空");
            } else if ("FORCE_CHECK".equals(entPayParam.getCheckName()) &&
                    (entPayParam.getReUserName() == null || "".equals(entPayParam.getReUserName()))) {
                throw new WxPayException("校验用户姓名选项为FORCE_CHECK时收款用户姓名不能为空");
            }
            if (entPayParam.getAmount() == null || "".equals(entPayParam.getAmount())) {
                throw new WxPayException("支付金额不能为空");
            } else if (!isMoney(entPayParam.getAmount())) {
                throw new WxPayException("支付金额必须为数值且保留两位小数");
            } else if (new BigDecimal(entPayParam.getAmount()).compareTo(BigDecimal.ZERO) <= 0) {
                throw new WxPayException("支付金额必须大于0");
            }
            if (entPayParam.getDesc() == null || "".equals(entPayParam.getDesc())) {
                throw new WxPayException("企业付款备注不能为空");
            }
        }
    }

    /**
     * 判断小数点后2位的数字的正则表达式
     *
     * @author kk
     * @date 2019/8/27
     **/
    private static boolean isMoney(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }
}
