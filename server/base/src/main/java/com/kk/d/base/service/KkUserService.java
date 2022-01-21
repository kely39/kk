package com.kk.d.base.service;

import com.kk.d.base.bo.KkUserBO;
import com.kk.d.base.bo.RegisterBO;
import com.kk.d.base.bo.WxBoundBO;
import com.kk.d.base.dto.WxLoginDTO;
import com.kk.d.base.entity.KkUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.d.base.entity.KkUserWeixinInfoEntity;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kk
 * @since 2019-12-27
 */
public interface KkUserService extends IService<KkUserEntity> {

    /**
     * @author kk
     * @date 2019/12/27
     **/
    KkUserBO findByAccount(String account);

    /**
     * @author kk
     * @date 2019/12/27
     **/
    void register(RegisterBO convert, String verifyCodeId);

    /**
     * 微信登录
     *
     * @author kk
     * @date 2019/12/28
     **/
    int wxLogin(String code, WxLoginDTO wxLoginDTO);

    /**
     * 微信绑定用户
     *
     * @author kk
     * @date 2019/12/30
     **/
    WxBoundBO boundWeiXinUser(String phone, String openId, KkUserWeixinInfoEntity weixinInfoEntity);

    /**
     * @author kk
     * @date 2019/12/31
     **/
    void updatePwd(String currentUserId, String password);
}
