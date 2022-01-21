package com.kk.d.base.bo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 章节
 * </p>
 *
 * @author kk
 * @since 2019-02-21
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExamChapterBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Integer subject;

    private String name;

    private Integer total;

    private Integer num;

    private Boolean locality;

    private Boolean deleteFlag;

    private String createdUserId;

    private LocalDateTime createdTime;

    private String updatedUserId;

    private LocalDateTime lastUpdatedTime;


}
