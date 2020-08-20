package com.kk.d.base.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.d.base.constants.ResultCode;
import com.kk.d.base.entity.KkSmsEntity;
import com.kk.d.base.mapper.KkSmsMapper;
import com.kk.d.base.service.KkSmsService;
import com.kk.d.util.KkUUID;
import com.kk.d.util.sms.aliyun.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <p>
 * 短信表 服务实现类
 * </p>
 *
 * @author yangqh
 * @since 2019-12-27
 */
@Slf4j
@RefreshScope
@Service
public class KkSmsServiceImpl extends ServiceImpl<KkSmsMapper, KkSmsEntity> implements KkSmsService {

    @Value("${kk.smsCodeValidMins}")
    private int smsCodeValidMins;

    @Resource
    private KkSmsMapper kkSmsMapper;

    @Resource
    private SmsService smsService;

    @Override
    public int checkSmsCode(String verifyCodeId, String smsCode, String account) {
        KkSmsEntity entity = kkSmsMapper.selectById(verifyCodeId);
        if (entity == null) {
            return ResultCode.SMS_CODEID_ERROR;
        }
        if (entity.getUseFlag().booleanValue()) {
            return ResultCode.SMS_CODEID_USED;
        }
        if (!StrUtil.equals(smsCode, entity.getSmsCode())) {
            return ResultCode.SMS_CODE_ERROR;
        }
        if (!StrUtil.equals(account, entity.getMobile())) {
            return ResultCode.SMS_MOBILE_NOTMATCH;
        }
        if (smsIsOverdue(entity.getSendTime(), smsCodeValidMins)) {
            return ResultCode.SMS_CODE_OVERTIME;
        }
        return ResultCode.SUCCESS;
    }

    @Override
    public void useSmsCode(String verifyCodeId) {
        KkSmsEntity entity = KkSmsEntity.builder().id(verifyCodeId).useFlag(Boolean.TRUE).useTime(LocalDateTime.now()).build();
        kkSmsMapper.updateById(entity);
    }

    @Override
    public String sendMsg(String mobile) {
        String smsCode = RandomUtil.randomNumbers(6);
        String sendResult = smsService.sendSms(mobile, smsCode, "templateCode");//TODO 根据不同的运营商模板CODE不同
        log.info("发送短信返回：{}", sendResult);
        JSONObject jsonObject = JSONObject.parseObject(sendResult);
        SendSmsResponse response = JSONObject.toJavaObject(jsonObject, SendSmsResponse.class);
        boolean sendFlag = false;
        if (response != null && StrUtil.isNotEmpty(response.getCode()) && "OK".equals(response.getCode())) {
            //发送成功
            sendFlag = true;
        }
        KkSmsEntity smsEntity = KkSmsEntity.builder().id(KkUUID.generatePrimaryKeyId())
                .mobile(mobile)
                .smsCode(smsCode)
                .templateId("templateCode")
                .content(matchSmsContent("templateCode")) // TODO 需根据不同的模板替换短信内容
                .status(sendFlag)
                .sendTime(LocalDateTime.now())
                .failReason(response == null ? "返回reponse=null" : response.getMessage()).build();
        kkSmsMapper.insert(smsEntity);
        return smsEntity.getId();
    }

    private String matchSmsContent(String templateCode) {
        return "未知" + templateCode;
    }

    private Boolean smsIsOverdue(LocalDateTime beginDate, int minutes) {
        if (null == beginDate) {
            return false;
        }
        try {
            Duration duration = Duration.between(beginDate, LocalDateTime.now());
            return duration.toMinutes() > minutes;
        } catch (Exception e) {
            log.error("短信是否超时异常：{}", e);
            return false;
        }
    }
}
