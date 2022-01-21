package com.kk.d.pay.service;

import com.kk.d.pay.AbstractPayService;
import com.kk.d.pay.NotifyDto;

/**
 * @Description: test
 * @Author kk
 * @Date 10:26 2018/12/28
 **/
public class PayServiceImpl implements AbstractPayService {

    @Override
    public Boolean prePayIdempotentJudge(String body) {
        return false;
    }

    @Override
    public boolean notifyIdempotentJudge(NotifyDto notifyDto) {
        return false;
    }

    @Override
    public boolean notifyIDataVerify(NotifyDto notifyDto) {
        return false;
    }

    @Override
    public void executeBusiness(NotifyDto notifyDto) {

    }
}
