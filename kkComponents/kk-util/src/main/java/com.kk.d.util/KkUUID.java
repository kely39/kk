package com.kk.d.util;

import cn.hutool.core.lang.UUID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KkUUID {

    /**
     * 主键生成
     *
     * @author kk
     * @date 2019/12/28
     **/
    public static String generatePrimaryKeyId() {
        return String.format("%s%s", LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE), UUID.randomUUID()).replaceAll("-","");
    }
}
