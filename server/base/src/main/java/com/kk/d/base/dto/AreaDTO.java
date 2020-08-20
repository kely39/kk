package com.kk.d.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangqh
 * @date 2019/12/27
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AreaDTO {

    @ApiModelProperty(value = "code", required = true)
    private String code;

    @ApiModelProperty(value = "name", required = true)
    private String name;

    @ApiModelProperty(value = "parentId", required = true)
    private String parentId;

    @ApiModelProperty(value = "1-国|2-省、直辖市|3-市|4-区、县", required = true)
    private String type;

}
