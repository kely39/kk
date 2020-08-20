package com.kk.d.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.d.base.bo.ExamChapterBO;
import com.kk.d.base.converter.ExamChapterConverter;
import com.kk.d.base.entity.ExamChapterEntity;
import com.kk.d.base.mapper.ExamChapterMapper;
import com.kk.d.base.service.ExamChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 章节 服务实现类
 * </p>
 *
 * @author yangqh
 * @since 2019-12-26
 */
@Service
public class ExamChapterServiceImpl extends ServiceImpl<ExamChapterMapper, ExamChapterEntity> implements ExamChapterService {

    @Resource
    private ExamChapterMapper examChapterMapper;

    @Override
    public List<ExamChapterBO> getChapterList(Integer carType) {
        QueryWrapper<ExamChapterEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(ExamChapterEntity.builder().carType(carType).deleteFlag(Boolean.FALSE).build());
        queryWrapper.orderByAsc("num");
        List<ExamChapterEntity> examChapterEntityList = examChapterMapper.selectList(queryWrapper);
        return ExamChapterConverter.convertBO(examChapterEntityList);
    }
}
