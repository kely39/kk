package com.kk.d.base.controller.c;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.kk.d.authorization.annotation.Authorization;
import com.kk.d.authorization.manager.TokenManager;
import com.kk.d.authorization.util.UserUtil;
import com.kk.d.base.bo.KkUserBO;
import com.kk.d.base.bo.RegisterBO;
import com.kk.d.base.bo.WxBoundBO;
import com.kk.d.base.constants.ResultCode;
import com.kk.d.base.constants.UserConstant;
import com.kk.d.base.dto.TokenDTO;
import com.kk.d.base.dto.WxLoginDTO;
import com.kk.d.base.entity.KkUserWeixinInfoEntity;
import com.kk.d.base.param.RegisterParam;
import com.kk.d.base.param.UpdatePwdParam;
import com.kk.d.base.param.WxBoundParam;
import com.kk.d.base.service.KkSmsService;
import com.kk.d.base.service.KkUserService;
import com.kk.d.base.service.KkUserWeixinInfoService;
import com.kk.d.framework.web.BaseController;
import com.kk.d.framework.web.HttpCode;
import com.kk.d.framework.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "COMMON")
@Slf4j
@RestController
@RequestMapping("/c/")
public class CommonController extends BaseController {

    @Autowired
    private TokenManager tokenManager;
    @Resource
    private KkUserService kkUserService;
    @Resource
    private KkSmsService kkSmsService;
    @Resource
    private KkUserWeixinInfoService weixinInfoService;

    /**
     * @author kk
     * @date 2019/12/27
     **/
    @ApiOperation(value = "账号密码登录", notes = "根据账号密码登录，成功返回token。[1000-成功，1100|]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @PostMapping("login/{account}")
    public Result<TokenDTO> login(@PathVariable @ApiParam(required = true, value = "账号") String account,
                                  @RequestParam @ApiParam(required = true, value = "密码") String pwd) {
        KkUserBO kkUserBO = kkUserService.findByAccount(account);
        if (ObjectUtil.isNull(kkUserBO)) {
            return successGet(ResultCode.USER_NOT_EXIST);
        }
        if (!BCrypt.checkpw(pwd, kkUserBO.getPassword())) {
            return failServerError("密码错误");
        }
        TokenDTO tokenDTO = getLoginUser(kkUserBO.getId(), kkUserBO.getUserNum(), kkUserBO.getAccount());
        return successCreated(tokenDTO);
    }

    /**
     * @author kk
     * @date 2019/12/27
     **/
    @ApiOperation(value = "注册（手机号+密码）", notes = "会员注册。", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @PostMapping("register/{verifyCodeId}")
    public Result register(@PathVariable @ApiParam(required = true, value = "手机验证码ID") String verifyCodeId,
                           @ModelAttribute @Validated @ApiParam RegisterParam registerParam) {
        KkUserBO kkUserBO = kkUserService.findByAccount(registerParam.getAccount());
        if (ObjectUtil.isNotNull(kkUserBO)) {
            return successGet(ResultCode.USER_EXIST);
        }
        int codeRet = kkSmsService.checkSmsCode(verifyCodeId, registerParam.getSmsCode(), registerParam.getAccount());
        if (codeRet != ResultCode.SUCCESS) {
            return successGet(codeRet);
        }
        kkUserService.register(Convert.convert(RegisterBO.class, registerParam), verifyCodeId);
        return successGet();
    }

    /**
     * @author kk
     * @date 2019/12/28
     **/
    @ApiOperation(value = "微信登录-APP", notes = "APP微信授权登录。[1109]", httpMethod = "GET")
    @GetMapping(value = "mpWx/login")
    public Result<WxLoginDTO> wxLogin(@RequestParam @ApiParam(name = "code", required = true, value = "code") String code) {
        WxLoginDTO wxLoginDTO = new WxLoginDTO();
        int rtnCode = kkUserService.wxLogin(code, wxLoginDTO);
        if (ResultCode.SUCCESS != rtnCode) {
            return successGet(rtnCode, wxLoginDTO);
        }

        TokenDTO tokenDTO = getLoginUser(wxLoginDTO.getId(), wxLoginDTO.getUserNum(), wxLoginDTO.getAccount());
        return successGet(WxLoginDTO.builder().token(tokenDTO.getToken()).userNum(tokenDTO.getUserNum()).build());
    }

    /**
     * @author kk
     * @date 2019/12/28
     **/
    @ApiOperation(value = "微信绑定用户", notes = "微信登录返回1109时需要调用此接口进行账号绑定")
    @RequestMapping(value = "mpWx/boundWeiXinUser", method = RequestMethod.POST)
    public Result<WxLoginDTO> boundWeiXinUser(@RequestBody @Validated WxBoundParam wxBoundParam) {
        KkUserWeixinInfoEntity weixinInfoEntity = weixinInfoService.selectByOpenid(wxBoundParam.getOpenId());
        if (weixinInfoEntity == null) {
            return failServerError("无法获取当前用户的微信信息！");
        }
        if (StrUtil.isNotBlank(weixinInfoEntity.getUserId())) {
            return failServerError("当前微信账号已与其他用户绑定！");
        }
        int codeRet = kkSmsService.checkSmsCode(wxBoundParam.getVerifyCodeId(), wxBoundParam.getSmsCode(), wxBoundParam.getPhone());
        if (codeRet != ResultCode.SUCCESS) {
            return successGet(codeRet);
        }
        WxBoundBO wxBoundBO = kkUserService.boundWeiXinUser(wxBoundParam.getPhone(), wxBoundParam.getOpenId(), weixinInfoEntity);

        TokenDTO tokenDTO = getLoginUser(wxBoundBO.getUserId(), wxBoundBO.getUserNum(), wxBoundParam.getPhone());
        return successGet(WxLoginDTO.builder().token(tokenDTO.getToken()).userNum(tokenDTO.getUserNum()).build());
    }

    /**
     * @author kk
     * @date 2019/12/31
     **/
    @Authorization
    @ApiOperation(value = "修改密码", notes = "")
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
    public Result updatePwd(@RequestBody @Validated UpdatePwdParam updatePwdParam) {
        int codeRet = kkSmsService.checkSmsCode(updatePwdParam.getVerifyCodeId(), updatePwdParam.getSmsCode(), updatePwdParam.getPhone());
        if (codeRet != ResultCode.SUCCESS) {
            return successGet(codeRet);
        }
        String account = UserUtil.getCurrentAccount(getRequest());
        if (!StrUtil.equals(account, updatePwdParam.getPhone())) {
            return successGet(ResultCode.SMS_CODE_MOBILE_ACCOUNT_NOMATCH);
        }
        kkUserService.updatePwd(UserUtil.getCurrentUserId(getRequest()),updatePwdParam.getPassword());
        return successGet();
    }

    private TokenDTO getLoginUser(String userId, String userNum, String account) {
        String token = tokenManager.createToken(UserConstant.MEMBER_TOKEN_TYPE, userNum, userId, account);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUserNum(userNum);
        tokenDTO.setToken(token);
        return tokenDTO;
    }
}
