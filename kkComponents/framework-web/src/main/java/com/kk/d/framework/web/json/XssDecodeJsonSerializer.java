package com.kk.d.framework.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 处理经过Xss过滤的数据
 *
 * @author kk
 * @date 2019/12/26
 **/
public class XssDecodeJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        if (value != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                switch (c) {
                    case '＞':
                        stringBuilder.append('>');//全角大于号
                        break;
                    case '＜':
                        stringBuilder.append('<');//全角小于号
                        break;
                    case '＇':
                        stringBuilder.append('\'');//全角单引号
                        break;
                    case '＆':
                        stringBuilder.append('&');//全角
                        break;
                    case '（':
                        stringBuilder.append('(');//全角做括号
                        break;
                    case '）':
                        stringBuilder.append(')');//全角右括号
                        break;
                    case '！':
                        stringBuilder.append('!');//全角感叹号
                        break;
                    case '＊':
                        stringBuilder.append('*');//全角星号
                        break;
                    case '＋':
                        stringBuilder.append('+');//全角星号
                        break;
                    case '＝':
                        stringBuilder.append('=');//全角星号
                        break;
                    default:
                        stringBuilder.append(c);
                        break;
                }
            }
            value = stringBuilder.toString();
        }
        jgen.writeString(value);
    }
}