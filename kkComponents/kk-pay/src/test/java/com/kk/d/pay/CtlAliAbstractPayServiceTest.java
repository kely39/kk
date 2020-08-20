package com.kk.d.pay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.kk.d.pay.alipay.AlipayParam;
import com.kk.d.pay.alipay.AlipayServiceHandle;
import com.kk.d.pay.service.PayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Description: 支付宝单元测试类
 * @Author yangqh
 * @Date 17:45 2018/12/26
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@Slf4j
public class CtlAliAbstractPayServiceTest {

    @Resource
    private AlipayServiceHandle alipayServiceHandle;


    @Ignore
    @Test
    public void createPcPreOrderTest() {
        AlipayParam alipayParam = AlipayParam.newBuilder()
                .subject("测试")
                .totalAmount("0.01")
                .outTradeNo("20191226001")
                .body("10086")
                .build();
        try {
            String result = alipayServiceHandle.createPcPreOrder(alipayParam, new PayServiceImpl());
            log.info("result={}", result);
        } catch (AlipayApiException e) {
            log.error(e.getErrMsg());
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void createAppPreOrderTest() {
        AlipayParam alipayParam = AlipayParam.newBuilder()
                .subject("测试")
                .totalAmount("0.01")
                .outTradeNo("20171226003")
                .body("10086")
                .build();
        try {
            String result = alipayServiceHandle.createAppPreOrder(alipayParam, new PayServiceImpl());
            log.info("result={}", result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void orderQueryTest() {
        try {
            AlipayTradeQueryResponse response = alipayServiceHandle.orderQuery("20181226001");
            log.info("response:{}", JSONObject.toJSONString(response));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void tradeRefundTest() {
        try {
            Boolean result = alipayServiceHandle.tradeRefund("20181226001", "0.01");
            log.info("result:{}", result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
