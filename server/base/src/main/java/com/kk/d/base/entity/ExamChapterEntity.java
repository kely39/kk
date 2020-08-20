package com.kk.d.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 章节
 * </p>
 *
 * @author yangqh
 * @since 2019-12-26
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("exam_chapter")
@ApiModel(value="ExamChapterEntity对象", description="章节")
public class ExamChapterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "车型(2-客车 3-货车 4-客运 5-货运 6-危险品 7-教练员)")
    private Integer carType;

    @ApiModelProperty(value = "所属科目（字典：1. 科一，2. 科二，3. 科三，4. 科三理论，0.资格证）")
    private Integer subject;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "该章节下的习题数量汇总")
    private Integer total;

    @ApiModelProperty(value = "排序值")
    private Integer num;

    @ApiModelProperty(value = "是否地方性(0-否|1-是)")
    private Boolean locality;

    @ApiModelProperty(value = "逻辑删除标识（0.未删除 1.已删除）")
    private Boolean deleteFlag;

    @ApiModelProperty(value = "创建人id")
    private String createdUserId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新人id")
    private String updatedUserId;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime lastUpdatedTime;


}
