package com.kk.d.base.service;

import com.kk.d.base.dto.AreaDTO;
import com.kk.d.base.entity.SysAreaEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 区域表 服务类
 * </p>
 *
 * @author kk
 * @since 2020-01-02
 */
public interface SysAreaService extends IService<SysAreaEntity> {

    /**
     * @author kk
     * @date 2020/1/2
     **/
    List<AreaDTO> getAreas();
}
