package com.kk.d.framework.web.util;

import cn.hutool.core.util.StrUtil;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Leach
 * @date 2018/3/21
 */
public class SignUtil {
    private final static String SIGN_PARAM = "sign";

    /**
     * MD5签名
     * @param params  参数信息
     * @param signKey 签名Key
     * @return 签名字符串
     */
    public static String createSign(Map<String, String> params, String signKey) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (!SIGN_PARAM.equals(key) && StrUtil.isNotEmpty(value)) {

                toSign.append(key).append("=").append(value).append("&");
            }

        }

        toSign.append("key=").append(signKey);
        return DigestUtils.md5DigestAsHex(StringUtils.getBytesUtf8(toSign.toString())).toUpperCase();
    }


    /**
     * 校验签名是否正确.
     * @param params  需要校验的参数Map
     * @param signKey 校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     */
    public static boolean checkSign(Map<String, String> params, String signKey) {
        String sign = createSign(params, signKey);
        return sign.equals(params.get(SIGN_PARAM));
    }
}
