package com.kk.d.base.controller.b;


import com.kk.d.authorization.annotation.Authorization;
import com.kk.d.base.bo.ExamChapterBO;
import com.kk.d.base.converter.ExamChapterConverter;
import com.kk.d.base.dto.ExamChapterDTO;
import com.kk.d.base.service.ExamChapterService;
import com.kk.d.framework.web.BaseController;
import com.kk.d.framework.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 章节 前端控制器
 * </p>
 *
 * @author kk
 * @since 2019-12-26
 */
@Api(value = "ExamChapterController", tags = "章节-运营端")
@RestController
@RequestMapping("/b/")
@Slf4j
@Validated
public class ExamChapterController extends BaseController {

    @Resource
    private ExamChapterService examChapterService;

    @Authorization
    @GetMapping("getChapterList")
    @ApiOperation(value = "章节-查询章节列表", notes = "查询章节列表", httpMethod = "GET")
    public Result<List<ExamChapterDTO>> getChapterList(@RequestParam @ApiParam(value = "2-客车 3-货车 4-客运 5-货运 6-危险品 7-教练员", required = true) Integer carType) {
        List<ExamChapterBO> examChapterBOList = examChapterService.getChapterList(carType);
        ExamChapterDTO examChapterDTO = new ExamChapterDTO();
        return successGet(ExamChapterConverter.convertDTO(examChapterBOList));
    }
}

