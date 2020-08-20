package com.kk.d.qny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yangqh
 * @date 2020/1/14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QnyDTO implements Serializable {

    private String bucket;

    private Boolean isPrivate;

    private String prefix;
}
