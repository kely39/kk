package com.kk.d.base.controller.c;

import com.kk.d.framework.web.BaseController;
import com.kk.d.framework.web.Result;
import com.kk.d.framework.web.exception.BusinessException;
import com.kk.d.qny.QnyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author yangqh
 * @date 2020/1/14
 **/
@Api(tags = "附件")
@Slf4j
@RestController
@RequestMapping("/c/attachment/")
public class AttachmentController extends BaseController {
    @Resource
    protected QnyService qnyService;

    @ApiOperation(value = "获取七牛云上传token")
    @GetMapping(value = "getUpToken")
    public Result<String> getUpToken(@RequestParam(value = "bucketName") @ApiParam("文件夹名称") String bucketName) {
        return successGet(qnyService.getUpToken(bucketName));
    }

    @ApiOperation(value = "获取下载链接")
    @GetMapping(value = "getDownUrlByKey")
    public Result<String> getDownUrlByKey(@RequestParam(value = "fileName") @ApiParam("文件名（对应返回key字段）") String fileName,
                                          @RequestParam(value = "bucketName") @ApiParam("文件夹名称") String bucketName,
                                          @RequestParam(value = "style", required = false) @ApiParam("图片样式，如：imageView2/1/w/200/h/200") String style) {
        return successGet(qnyService.getDownUrl(fileName, bucketName, style));
    }

    @ApiOperation(value = "上传文件", notes = "返回下载地址")
    @PostMapping(value = "upload")
    public Result<String> upload(@RequestParam(value = "bucketName") @ApiParam("文件夹名称") String bucketName,
                                 @RequestParam(value = "key") @ApiParam("保存文件名") String key,
                                 @RequestParam(value = "file") @ApiParam("文件") MultipartFile file) {
        String download = null;
        try {
            if (file != null) {
                download = qnyService.uploadFile(file.getInputStream(), bucketName, false, key);
            }
        } catch (Exception e) {
            throw new BusinessException("上传失败！");
        }
        return successGet(download);
    }
}