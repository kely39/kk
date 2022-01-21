package com.kk.d.framework.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kk
 * @date 2019/12/26
 **/
public class BaseResultCode {

    public static Map<Integer, String> messageMap = new HashMap<>();

    // 公共代码
    public static final int SUCCESS = 1000;
    public static final int FAIL = 1001;

    // 初始化状态码与文字说明
    static {
        // 公共代码 1xxx
        messageMap.put(SUCCESS, "success");
        messageMap.put(FAIL, "fail");
    }

    public static String get(int code) {
        return messageMap.get(code);
    }

}
