package com.kk.d.qny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author kk
 * @date 2020/1/14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QnyStatDTO implements Serializable {

    /*异常状态文件key集合*/
    private List<String> errorStatusKeys;

    /*异常文件映射*/
    private Map<String,QnyStatResDTO> fileInfoMap;
}
