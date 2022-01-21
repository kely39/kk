package com.kk.d.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author kk
 * @date 2019/2/22
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ExamChapterDTO对象", description = "章节")
public class ExamChapterDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "该章节下的习题数量汇总")
    private Integer total;

    @ApiModelProperty(value = "是否地方性(0-否|1-是)")
    private Boolean locality;

}
