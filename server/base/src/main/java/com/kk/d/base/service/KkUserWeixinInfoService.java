package com.kk.d.base.service;

import com.kk.d.base.entity.KkUserWeixinInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * <p>
 * 用户微信信息 服务类
 * </p>
 *
 * @author kk
 * @since 2019-12-27
 */
public interface KkUserWeixinInfoService extends IService<KkUserWeixinInfoEntity> {

    /**
     * @author kk
     * @date 2019/12/28
     **/
    KkUserWeixinInfoEntity selectByOpenid(String openId);

    /**
     * 先初始化kk_user_weixin_info表数据，用户绑定账号时再关联
     *
     * @author kk
     * @date 2019/12/31
     **/
    void initWeixinUser(WxMpUser wxMpUser, String appid);
}
