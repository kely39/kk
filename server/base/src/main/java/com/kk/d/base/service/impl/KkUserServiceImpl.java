package com.kk.d.base.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.d.base.bo.KkUserBO;
import com.kk.d.base.bo.RegisterBO;
import com.kk.d.base.bo.WxBoundBO;
import com.kk.d.base.constants.ResultCode;
import com.kk.d.base.dto.WxLoginDTO;
import com.kk.d.base.entity.KkUserEntity;
import com.kk.d.base.entity.KkUserWeixinInfoEntity;
import com.kk.d.base.mapper.KkUserMapper;
import com.kk.d.base.service.KkSmsService;
import com.kk.d.base.service.KkUserService;
import com.kk.d.base.service.KkUserWeixinInfoService;
import com.kk.d.framework.web.exception.BusinessException;
import com.kk.d.util.KkUUID;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpUserServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kk
 * @since 2019-12-27
 */
@Slf4j
@RefreshScope
@Service
public class KkUserServiceImpl extends ServiceImpl<KkUserMapper, KkUserEntity> implements KkUserService {

    @Value("${kk.wx.mp.appid}")
    private String appid;

    @Resource
    private KkUserMapper kkUserMapper;
    @Resource
    private KkSmsService kkSmsService;
    @Resource
    private KkUserWeixinInfoService kkUserWeixinInfoService;
    @Resource
    private WxMpService wxMpService;
    @Resource
    private WxMpUserService wxMpUserService;

    @Override
    public KkUserBO findByAccount(String account) {
        List<KkUserEntity> list = kkUserMapper.selectList(new QueryWrapper<KkUserEntity>().setEntity(KkUserEntity.builder().account(account).deleteFlag(Boolean.FALSE).build()));
        if (list == null || list.isEmpty()) {
            return null;
        }
        KkUserBO bo = new KkUserBO();
        BeanUtils.copyProperties(list.get(0), bo);
        checkUser(bo);
        return bo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterBO registerBO, String verifyCodeId) {
        String salt = BCrypt.gensalt();//盐
        KkUserEntity entity = KkUserEntity.builder().id(KkUUID.generatePrimaryKeyId())
                .account(registerBO.getAccount())
                .password(BCrypt.hashpw(registerBO.getPassword(), salt))
                .salt(salt)
                .userNum(KkUUID.generatePrimaryKeyId().substring(0, 16))
                .mobile(registerBO.getAccount())
                .createdTime(LocalDateTime.now()).build();
        kkUserMapper.insert(entity);

        kkSmsService.useSmsCode(verifyCodeId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int wxLogin(String code, WxLoginDTO wxLoginDTO) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            //换取openid
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (Exception e) {
            log.error("通过code换取网页授权access_token和openid异常", e);
            throw new BusinessException("授权失败，请重新尝试！");
        }
        //获取微信用户信息
        WxMpUser wxMpUser;
        try {
            wxMpUser = wxMpUserService.userInfo(wxMpOAuth2AccessToken.getOpenId());
        } catch (Exception e) {
            log.error("获取微信用户信息异常", e);
            throw new BusinessException("授权失败，请重新尝试！");
        }
        KkUserWeixinInfoEntity weixinInfoEntity = kkUserWeixinInfoService.selectByOpenid(wxMpOAuth2AccessToken.getOpenId());
        if (weixinInfoEntity == null || StrUtil.isBlank(weixinInfoEntity.getUserId())) {
            if (weixinInfoEntity == null) {
                //初始化用户数据
                kkUserWeixinInfoService.initWeixinUser(wxMpUser, appid);
            } else {
                weixinInfoEntity.setUserInfo(JSONObject.toJSONString(wxMpUser));
                weixinInfoEntity.setLastUpdatedTime(LocalDateTime.now());
                kkUserWeixinInfoService.updateById(weixinInfoEntity);
            }
            wxLoginDTO.setOpenid(wxMpUser.getOpenId());
            //初始化完成后无账号，需绑定账号
            return ResultCode.WEI_XIN_LOGIN_IS_NOT_BIND;

        } else {
            //如果存在用户，则判断昵称和头像是否变化，变化时修改
            KkUserEntity userEntity = kkUserMapper.selectById(weixinInfoEntity.getUserId());
            if (userEntity == null) {
                log.error("微信用户表userId在用户表中无数据");
                throw new BusinessException("系统错误");
            }
            boolean isChange = false;
            if (!StrUtil.equals(wxMpUser.getNickname(), userEntity.getNickname())) {
                userEntity.setNickname(wxMpUser.getNickname());
                isChange = true;
            }
            if (!StrUtil.equals(wxMpUser.getHeadImgUrl(), userEntity.getHeadimg())) {
                userEntity.setHeadimg(wxMpUser.getHeadImgUrl());
                isChange = true;
            }
            if (wxMpUser.getSex().intValue() != userEntity.getSex().intValue()) {
                userEntity.setSex(wxMpUser.getSex());
                isChange = true;
            }
            if (isChange) {
                userEntity.setLastUpdatedTime(LocalDateTime.now());
                kkUserMapper.updateById(userEntity);
            }
            wxLoginDTO.setId(userEntity.getId());
            wxLoginDTO.setUserNum(userEntity.getUserNum());
            wxLoginDTO.setAccount(userEntity.getAccount());
            return ResultCode.SUCCESS;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WxBoundBO boundWeiXinUser(String phone, String openId, KkUserWeixinInfoEntity weixinInfoEntity) {
        KkUserBO kkUserBO = findByAccount(phone);
        if (kkUserBO == null) {
            kkUserBO = initUser(phone, weixinInfoEntity.getUserInfo());
        } else {
            checkUser(kkUserBO);
        }
        weixinInfoEntity.setUserId(kkUserBO.getId());
        weixinInfoEntity.setLastUpdatedTime(LocalDateTime.now());
        kkUserWeixinInfoService.updateById(weixinInfoEntity);
        return WxBoundBO.builder().userId(kkUserBO.getId()).userNum(kkUserBO.getUserNum()).build();
    }

    @Override
    public void updatePwd(String currentUserId, String password) {
        String salt = BCrypt.gensalt();//盐
        KkUserEntity entity = KkUserEntity.builder().id(currentUserId)
                .password(BCrypt.hashpw(password, salt))
                .salt(salt)
                .lastUpdatedTime(LocalDateTime.now()).build();
        kkUserMapper.updateById(entity);
    }

    private KkUserBO initUser(String phone, String userInfo) {
        JSONObject userInfoJsonObject = JSONObject.parseObject(userInfo);
        if (userInfoJsonObject == null) {
            throw new BusinessException("用户微信基本信息无数据");
        }
        KkUserEntity userEntity = KkUserEntity.builder().id(KkUUID.generatePrimaryKeyId())
                .account(phone)
                .headimg(userInfoJsonObject.getString("headImgUrl"))
                .nickname(userInfoJsonObject.getString("nickname"))
                .sex(userInfoJsonObject.getInteger("sex"))
                .userNum(KkUUID.generatePrimaryKeyId().substring(0, 16))
                .mobile(phone)
                .createdTime(LocalDateTime.now()).build();
        kkUserMapper.insert(userEntity);
        return KkUserBO.builder().id(userEntity.getId()).userNum(userEntity.getUserNum()).build();
    }

    private void checkUser(KkUserBO bo) {
        if (bo.getFreezeFlag().booleanValue()) {
            throw new BusinessException("该用户已被冻结！");
        }
    }
}
