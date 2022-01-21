package com.kk.d.pay.applePay;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

@Slf4j

public class ApplePayService {

    public static final String SANDBOX = "https://sandbox.itunes.apple.com/verifyReceipt";
    public static final String PRO = "https://buy.itunes.apple.com/verifyReceipt";

    /**
     * @param receiptData app调用apple pay返回receipt-data
     * @param isSandbox   是否是沙箱环境
     * @author kk
     * @date 2019/8/20
     **/
    public ApplePayRetDTO verifyReceipt(String receiptData, Boolean isSandbox) {
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(isSandbox.booleanValue() ? SANDBOX : PRO);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(receiptData);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            log.error("苹果支付二次验证参数：{}", receiptData);
            log.error("苹果支付二次验证响应：{}", sb.toString());
            log.error("苹果二次验证异常", e);
            return ApplePayRetDTO.builder().status(StatusConstant.FAIL).resultStr(sb.toString()).productId(getProductId(sb.toString())).build();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        log.info("苹果支付二次验证结果：{}", sb.toString());
        return ApplePayRetDTO.builder().status(getStatus(sb.toString())).resultStr(sb.toString()).productId(getProductId(sb.toString())).transactionId(getTransactionId(sb.toString())).build();
    }

//    public static void main(String[] args) {
//        getProductId("{\"environment\":\"Sandbox\",\"receipt\":{\"in_app\":[{\"transaction_id\":\"1000000559224151\",\"original_purchase_date\":\"2019-08-20 07:22:12 Etc/GMT\",\"quantity\":\"1\",\"original_transaction_id\":\"1000000559224151\",\"purchase_date_pst\":\"2019-08-20 00:22:12 America/Los_Angeles\",\"original_purchase_date_ms\":\"1566285732000\",\"purchase_date_ms\":\"1566285732000\",\"product_id\":\"vip20190816\",\"original_purchase_date_pst\":\"2019-08-20 00:22:12 America/Los_Angeles\",\"is_trial_period\":\"false\",\"purchase_date\":\"2019-08-20 07:22:12 Etc/GMT\"}],\"adam_id\":0,\"receipt_creation_date\":\"2019-08-20 07:22:12 Etc/GMT\",\"original_application_version\":\"1.0\",\"app_item_id\":0,\"original_purchase_date_ms\":\"1375340400000\",\"request_date_ms\":\"1566286008905\",\"original_purchase_date_pst\":\"2013-08-01 00:00:00 America/Los_Angeles\",\"original_purchase_date\":\"2013-08-01 07:00:00 Etc/GMT\",\"receipt_creation_date_pst\":\"2019-08-20 00:22:12 America/Los_Angeles\",\"receipt_type\":\"ProductionSandbox\",\"bundle_id\":\"com.ctl.student\",\"receipt_creation_date_ms\":\"1566285732000\",\"request_date\":\"2019-08-20 07:26:48 Etc/GMT\",\"version_external_identifier\":0,\"request_date_pst\":\"2019-08-20 00:26:48 America/Los_Angeles\",\"download_id\":0,\"application_version\":\"10\"},\"status\":0}");
//    }

    private String getTransactionId(String toString) {
        if (toString == null || "".equals(toString)) {
            return null;
        }
        ApplePayResponse response = JSON.parseObject(toString, ApplePayResponse.class);
        if (response == null || response.getReceipt() == null || response.getReceipt().getIn_app() == null || response.getReceipt().getIn_app().isEmpty()) {
            return null;
        }
        return response.getReceipt().getIn_app().get(0).getTransaction_id();
    }

    private static String getProductId(String toString) {
        if (toString == null || "".equals(toString)) {
            return null;
        }
        ApplePayResponse response = JSON.parseObject(toString, ApplePayResponse.class);
        if (response == null || response.getReceipt() == null || response.getReceipt().getIn_app() == null || response.getReceipt().getIn_app().isEmpty()) {
            return null;
        }
        return response.getReceipt().getIn_app().get(0).getProduct_id();
    }

    private static String getStatus(String toString) {
        if (toString == null || "".equals(toString)) {
            return StatusConstant.FAIL;
        }
        ApplePayResponse response = JSON.parseObject(toString, ApplePayResponse.class);
        if (response == null || response.getStatus() == null) {
            return StatusConstant.FAIL;
        }
        if (!StatusConstant.SUCCESS.equals(response.getStatus().toString())) {
            return response.getStatus().toString();
        }
        return StatusConstant.SUCCESS;
    }
}