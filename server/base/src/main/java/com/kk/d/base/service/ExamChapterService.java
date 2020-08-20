package com.kk.d.base.service;

import com.kk.d.base.bo.ExamChapterBO;
import com.kk.d.base.entity.ExamChapterEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 章节 服务类
 * </p>
 *
 * @author yangqh
 * @since 2019-12-26
 */
public interface ExamChapterService extends IService<ExamChapterEntity> {

    List<ExamChapterBO> getChapterList(Integer carType);
}
