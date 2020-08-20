package com.kk.d.pay;

public interface AbstractPayService {

    //-------------------------------------------------------->支付前
    /**
     * @Description: 判断订单是否重复支付
     * @Author yangqh
     * @Date 9:55 2018/12/28
     * @Param [body - 回传参数，业务表主键]
     * @Return java.lang.Boolean
     **/
    Boolean prePayIdempotentJudge(String body);

    //-------------------------------------------------------->回调
    /**
     * @Description: 数据业务校验（幂等性）重复回调返回TRUE
     * @Author yangqh
     * @Date 11:35 2019/1/23
     * @Param [notifyDto]
     * @Return boolean
     **/
    boolean notifyIdempotentJudge(NotifyDto notifyDto);

    /**
     * @Description: 校验回调金额与订单金额是否一致，totalFee与orderFee不一致时返回FALSE
     * @Author yangqh
     * @Date 13:57 2019/1/23
     * @Param [notifyDto]
     * @Return boolean
     **/
    boolean notifyIDataVerify(NotifyDto notifyDto);

    /**
     * @Description: 执行业务逻辑
     * @Author yangqh
     * @Date 13:46 2019/1/23
     * @Param [notifyDto]
     * @Return void
     **/
    void executeBusiness(NotifyDto notifyDto);
}
