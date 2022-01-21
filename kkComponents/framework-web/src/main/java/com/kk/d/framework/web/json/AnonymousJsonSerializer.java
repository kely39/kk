package com.kk.d.framework.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对昵称进行统一的匿名处理
 *
 * @author kk
 * @date 2019/12/26
 **/
public class AnonymousJsonSerializer extends JsonSerializer<String> {

    private final static Pattern emoji = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]");

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        int length = value.length();
        Matcher matcher = emoji.matcher(value);
        /*
         * 初始和结尾字符的长度，可能为表情，是两个字符
         */
        int startCharLength = 1;
        int endCharLength = 1;
        while (matcher.find()) {
            if (matcher.start() == 0) {
                startCharLength = 2;
            }
            if (matcher.end() == length) {
                endCharLength = 2;
            }
        }
        StringBuilder sb = new StringBuilder();
        if (length > startCharLength) {
            sb.append(value.substring(0, startCharLength)).append("***").append(value.substring(length - endCharLength, length));
        } else {
            sb.append(value);
        }
        jgen.writeString(sb.toString());
    }
}
