package com.kk.d.base.controller.c;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.kk.d.base.constants.KeyConstant;
import com.kk.d.base.constants.ResultCode;
import com.kk.d.base.param.SendMsgParam;
import com.kk.d.base.service.KkSmsService;
import com.kk.d.base.service.sys.RedisUtilService;
import com.kk.d.framework.web.BaseController;
import com.kk.d.framework.web.Result;
import com.kk.d.framework.web.exception.BusinessException;
import com.kk.d.util.ShearCaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * <p>
 * 短信表 前端控制器
 * </p>
 *
 * @author yangqh
 * @since 2019-12-27
 */
@Api(tags = "验证码")
@RestController
@Slf4j
@RequestMapping("/c/")
public class KkCodeController extends BaseController {

    @Resource
    private RedisUtilService redisUtilService;

    @Resource
    private KkSmsService kkSmsService;

    /**
     * @author yangqh
     * @date 2019/12/27
     **/
    @ApiOperation(value = "图形验证码", notes = "返回{“image”:“验证码图片的base64字符串”,“uuid”:“”} uuid将作为发短信参数，图形验证码时效10分钟")
    @GetMapping("imageCode")
    public void getCodeImage(@RequestParam(defaultValue = "290") int width, @RequestParam(defaultValue = "100") int height, @RequestParam(defaultValue = "4") int codeCount, @RequestParam(defaultValue = "6") int circleCount) {
        /*生成图片，和图片对应的code，key*/
        //定义图形验证码的长和宽
        ShearCaptchaUtil shearCaptchaUtil = ShearCaptchaUtil.createShearCaptcha(width, height, codeCount, circleCount);
        String uuid = IdUtil.simpleUUID();
        HttpServletResponse response = getResponse();
        /*将图片流转为base64编码进行输出*/
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try {
            /*将key设置到相应头中*/
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            Map<String, String> map = ImmutableMap.of("image", "data:image/jpg;base64," + shearCaptchaUtil.getImageBase64(), "uuid", uuid);
            redisUtilService.setString(String.format("%s_%s", KeyConstant.IMG_CODE, uuid), shearCaptchaUtil.getCode(), 600);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(successGet(map)));
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("生成验证码失败!");
        } finally {
            try {
                bs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author yangqh
     * @date 2019/12/28
     **/
    @ApiOperation(value = "发送短信", notes = "1000-成功（返回id用于业务提交），1107-验证码错误，1108-验证码已失效。时效5分钟")
    @PostMapping("sendMsg")
    public Result sendMsg(@RequestBody @Validated SendMsgParam sendMsgParam) {
        String imgCode = redisUtilService.getString(String.format("%s_%s", KeyConstant.IMG_CODE, sendMsgParam.getUuid()));
        if (StrUtil.isBlank(imgCode)) {
            return successCreated(ResultCode.IMGCODE_OVERTIME);
        }
        if (!StrUtil.equals(imgCode, sendMsgParam.getImgCode())) {
            return successCreated(ResultCode.IMGCODE_ERROR);
        }
        String id = kkSmsService.sendMsg(sendMsgParam.getMobile());
        return successGet(id);
    }
}

