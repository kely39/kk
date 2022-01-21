package com.kk.d.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.d.base.entity.KkUserWeixinInfoEntity;
import com.kk.d.base.mapper.KkUserWeixinInfoMapper;
import com.kk.d.base.service.KkUserWeixinInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.d.util.KkUUID;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户微信信息 服务实现类
 * </p>
 *
 * @author kk
 * @since 2019-12-27
 */
@Service
public class KkUserWeixinInfoServiceImpl extends ServiceImpl<KkUserWeixinInfoMapper, KkUserWeixinInfoEntity> implements KkUserWeixinInfoService {

    @Resource
    private KkUserWeixinInfoMapper kkUserWeixinInfoMapper;

    @Override
    public KkUserWeixinInfoEntity selectByOpenid(String openId) {
        List<KkUserWeixinInfoEntity> list = kkUserWeixinInfoMapper.selectList(new QueryWrapper<KkUserWeixinInfoEntity>().setEntity(KkUserWeixinInfoEntity.builder().openId(openId).deleteFlag(Boolean.FALSE).build()));
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void initWeixinUser(WxMpUser wxMpUser, String appid) {
        KkUserWeixinInfoEntity weixinInfoEntity = KkUserWeixinInfoEntity.builder().id(KkUUID.generatePrimaryKeyId())
                .unionId(wxMpUser.getUnionId())
                .appId(appid)
                .openId(wxMpUser.getOpenId())
                .userInfo(JSONObject.toJSONString(wxMpUser))
                .createdTime(LocalDateTime.now())
                .build();
        kkUserWeixinInfoMapper.insert(weixinInfoEntity);
    }
}
