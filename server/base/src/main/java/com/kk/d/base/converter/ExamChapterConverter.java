package com.kk.d.base.converter;

import com.kk.d.base.bo.ExamChapterBO;
import com.kk.d.base.dto.ExamChapterDTO;
import com.kk.d.base.entity.ExamChapterEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节转换器
 *
 * @author yangqh
 * @date 2019/2/22
 **/
@Slf4j
public class ExamChapterConverter {

    private ExamChapterConverter() {
    }

    public static List<ExamChapterBO> convertBO(List<ExamChapterEntity> examChapterEntityList) {
        List<ExamChapterBO> examChapterBOList = new ArrayList<>();
        examChapterEntityList.forEach(item -> {
            ExamChapterBO examChapterBO = new ExamChapterBO();
            BeanUtils.copyProperties(item, examChapterBO);
            examChapterBOList.add(examChapterBO);
        });
        return examChapterBOList;
    }

    public static List<ExamChapterDTO> convertDTO(List<ExamChapterBO> examChapterBOList) {
        List<ExamChapterDTO> examChapterDataDTOList = new ArrayList<>();
        examChapterBOList.forEach(item -> {
            ExamChapterDTO examChapterDataDTO = new ExamChapterDTO();
            BeanUtils.copyProperties(item, examChapterDataDTO);
            examChapterDataDTOList.add(examChapterDataDTO);
        });
        return examChapterDataDTOList;
    }
}
