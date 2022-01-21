package com.kk.d.base.controller.c;

import com.kk.d.base.dto.AreaDTO;
import com.kk.d.base.service.SysAreaService;
import com.kk.d.framework.web.BaseController;
import com.kk.d.framework.web.Result;
import com.kk.d.mq.core.producer.RocketMQTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 区域表 前端控制器
 * </p>
 *
 * @author kk
 * @since 2020-01-02
 */
@Api(tags = "行政区域")
@RequestMapping("/c/area/")
@RestController
@Slf4j
@RefreshScope
public class SysAreaController extends BaseController {

    @Resource
    private SysAreaService sysAreaService;

    @Autowired(required = false)
    private RocketMQTemplate rocketMQTemplate;

    @Value("${kk.framework-web.swagger.name}")
    private String swaggerName;

    @GetMapping("getAreas")
    @ApiOperation(value = "查询", notes = "返回json", httpMethod = "GET")
    public Result<List<AreaDTO>> getAreas() {
        List<AreaDTO> areaDTOList = sysAreaService.getAreas();
        return successGet(areaDTOList);
    }

    @GetMapping("getSwaggerName")
    @ApiOperation(value = "getSwaggerName", httpMethod = "GET")
    public Result getSwaggerName() {
        return successGet(swaggerName);
    }
}

