package com.kk.d.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.d.base.dto.AreaDTO;
import com.kk.d.base.entity.SysAreaEntity;
import com.kk.d.base.mapper.SysAreaMapper;
import com.kk.d.base.service.SysAreaService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 区域表 服务实现类
 * </p>
 *
 * @author yangqh
 * @since 2020-01-02
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysAreaEntity> implements SysAreaService {

    @Resource
    private SysAreaMapper sysAreaMapper;

    @Override
    public List<AreaDTO> getAreas() {
        List<SysAreaEntity> entityList = sysAreaMapper.selectList(new QueryWrapper<SysAreaEntity>().setEntity(SysAreaEntity.builder().delFlag("0").build()));
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }
        List<AreaDTO> rtn = new ArrayList<>();
        for (SysAreaEntity entity : entityList) {
            AreaDTO dto = new AreaDTO();
            BeanUtils.copyProperties(entity,dto);
            rtn.add(dto);
        }
        return rtn;
    }
}
