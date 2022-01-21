package com.kk.d.qny.dto;

import com.qiniu.http.Response;
import com.qiniu.storage.model.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kk
 * @date 2020/1/14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QnyStatResDTO implements Serializable {

    /*响应值*/
    private boolean status;

    /*响应信息*/
    private Response response;

    /*文件信息*/
    private FileInfo fileInfo;
}
