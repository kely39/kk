package com.kk.d.base.controller.c;

import com.alibaba.fastjson.JSONObject;
import com.kk.d.base.config.UeditorConfig;
import com.kk.d.qny.QnyService;
import com.kk.d.qny.enums.BucketEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangqh
 * @date 2020/1/14
 **/
@Api(tags = "百度富文本框")
@Slf4j
@RestController
@RequestMapping("/c/ueditor/")
public class BaiduUeditorController {

    @Resource
    protected QnyService qnyService;

    @ApiModelProperty(value = "百度富文本读取配置信息")
    @GetMapping("/config")
    public Object ueditorConfig(HttpServletRequest request, String callback) {
        //获取百度富文本配置信息返回给前端，不返回，前端上传功能不可用
        String myresult = UeditorConfig.CONFIG.getConfigStr(callback);
        if (StringUtil.isNotEmpty(myresult)) {
            return JSONObject.parse(myresult);
        }
        log.error("【调用接口失败】，没有匹配到操作");
        return "【调用接口失败】，没有匹配到操作";
    }

    @ApiModelProperty(value = "百度富文本上传图片")
    @PostMapping("/config")
    public Object upload(HttpServletRequest request) {
        MultipartFile file = null;
        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
            MultipartHttpServletRequest multipartRequest =
                    WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            file = multipartRequest.getFile("upfile");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //重新生成的文件名
        String fileName = file.getOriginalFilename();
        String last = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件类型

        String remotePath = null;
        String key = "system/ueditor/" + System.currentTimeMillis() + "/" + fileName;
        if (inputStream != null) {
            /*获取七牛上传token*/
            remotePath = qnyService.uploadFile(inputStream, BucketEnum.PUBLIC.getVal(), false, key);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("state", "SUCCESS");
        result.put("url", remotePath);
        result.put("original", "");
        result.put("type", last);
        result.put("size", file.getSize());
        result.put("title", key);
        return result;
    }
}