package com.kk.d.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.kk.d.pay.service.PayServiceImpl;
import com.kk.d.pay.wx.AbstractWxPayService;
import com.kk.d.pay.wx.WxEntPayService;
import com.kk.d.pay.wx.param.EntPayParam;
import com.kk.d.pay.wx.param.RefundOrderParam;
import com.kk.d.pay.wx.param.WxCreateOrderParam;
import com.kk.d.pay.wx.ret.EntPayQueryResultDTO;
import com.kk.d.pay.wx.ret.EntPayResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Description: 微信支付部分接口单元测试
 * @Author yangqh
 * @Date 14:00 2018/12/26
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@Slf4j
public class CtlWxAbstractPayServiceTest {

    @Resource
    private AbstractWxPayService abstractWxPayService;

    @Resource
    private WxEntPayService wxEntPayService;

    /**
     * @Description: 查询订单
     * @Author yangqh
     * @Date 14:57 2018/12/26
     * @Param []
     * @Return void
     * @see {官方接口地址：https://api.mch.weixin.qq.com/pay/orderquery}
     **/
    @Ignore
    @Test
    public void queryOrderTest() {
        try {
            WxPayOrderQueryResult result = abstractWxPayService.queryOrder("", "20190428001");
        } catch (WxPayException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: APP支付
     * @Author yangqh
     * @Date 14:57 2018/12/26
     * @Param []
     * @Return void
     * @see {官方接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder}
     **/
    @Ignore
    @Test
    public void createOrderTest() {
        WxCreateOrderParam wxCreateOrderParam = WxCreateOrderParam.newBuilder()
                .body("测试")
                .attach("回传参数")
                .outTradeNo("20190428001")
                .totalFee("1")
                .notifyUrl("回调地址")
                .tradeType(WxPayConstants.TradeType.APP)
                .build();
        try {
            WxPayAppOrderResult result = abstractWxPayService.createAppOrder(wxCreateOrderParam, new PayServiceImpl());
            log.info("create order result:{}", JSONObject.toJSONString(result));
        } catch (WxPayException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 原生扫码支付
     * @Author yangqh
     * @Date 14:57 2018/12/26
     * @Param []
     * @Return void
     * @see {官方接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder}
     **/
    @Ignore
    @Test
    public void createOrderPcTest() {
        WxCreateOrderParam wxCreateOrderParam = WxCreateOrderParam.newBuilder()
                .body("测试")
                .attach("回传参数")
                .outTradeNo("201904280021")
                .totalFee("0.01")
                .notifyUrl("回调地址")
                .tradeType(WxPayConstants.TradeType.NATIVE)
                .build();
        try {
            WxPayNativeOrderResult result = abstractWxPayService.createPcOrder(wxCreateOrderParam, new PayServiceImpl());
        } catch (WxPayException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 申请退款
     * @Author yangqh
     * @Date 15:11 2018/12/26
     * @Param []
     * @Return void
     * @see {官方接口地址：https://api.mch.weixin.qq.com/secapi/pay/refund}
     **/
    @Ignore
    @Test
    public void refundTest() {
        RefundOrderParam refundOrderParam = RefundOrderParam.newBuilder()
                .outTradeNo("20190428002")
                .outRefundNo(UUID.randomUUID().toString())
                .totalFee("0.01")
                .refundFee("0.01")
                .build();
        try {
            WxPayRefundResult result = abstractWxPayService.refund(refundOrderParam);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
    }

    /**
     * 企业付款
     *
     * @author yangqh
     * @date 2019/8/27
     * @see {https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2}
     **/
    @Ignore
    @Test
    public void entPay() {
        try {
            EntPayResultDTO result = wxEntPayService.entPay(EntPayParam.builder()
                    .openid("oW0z74pFqzweH5mf5cmb97Qdt2t4")
                    .amount("2")
                    .checkName("FORCE_CHECK")
                    .reUserName("姓名")
                    .partnerTradeNo("A0000003")
                    .desc("测试提现").build());
            System.out.println("=================>" + JSON.toJSONString(result));
        } catch (WxPayException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询企业付款
     *
     * @author yangqh
     * @date 2019/8/28
     * @see {https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3}
     **/
    @Ignore
    @Test
    public void queryEntPay() {
        try {
            EntPayQueryResultDTO result = wxEntPayService.queryEntPay("A0000001");
            System.out.println("=================>" + JSON.toJSONString(result));
        } catch (WxPayException e) {
            e.printStackTrace();
        }
    }
}
