package com.kk.d.framework.web.json;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;
import java.util.Set;

/**
 * @author Leach
 * @date 2017/5/18
 */
public class JsonBeanSerializerModifier extends BeanSerializerModifier {

    private JsonSerializer<Object> nullArrayJsonSerializer = new NullArrayJsonSerializer();
    private JsonSerializer<Object> nullBooleanJsonSerializer = new NullBooleanJsonSerializer();
    private JsonSerializer<Object> nullNumberJsonSerializer = new NullNumberJsonSerializer();
    private JsonSerializer<Object> nullStringJsonSerializer = new NullStringJsonSerializer();
    private JsonSerializer<Object> nullObjectJsonSerializer = new NullObjectJsonSerializer();
    private JsonSerializer<Object> nullEnumJsonSerializer = new NullEnumJsonSerializer();
    private JsonSerializer<Object> nullOtherJsonSerializer = new NullOtherJsonSerializer();

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        // 循环所有的beanPropertyWriter
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            // 判断字段的类型，如果是array，list，set则注册nullSerializer
            if (isArrayType(writer)) {
                //给writer注册一个自己的nullSerializer
                writer.assignNullSerializer(this.defaultNullArrayJsonSerializer());
            } else if(isStringType(writer)) {
                writer.assignNullSerializer(this.defaultNullStringJsonSerializer());
            } else if(isNumberType(writer)) {
                writer.assignNullSerializer(this.defaultNullNumberJsonSerializer());
            } else if(isBooleanType(writer)) {
                writer.assignNullSerializer(this.defaultNullBooleanJsonSerializer());
            } else if(isEnumType(writer)) {
                writer.assignNullSerializer(this.defaultNullEnumJsonSerializer());
            } else if(isObjectType(writer)) {
                writer.assignNullSerializer(this.defaultNullObjectJsonSerializer());
            } else {
                writer.assignNullSerializer(this.defaultNullOtherJsonSerializer());
            }
        }
        return beanProperties;
    }

    protected boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getPropertyType();
        return clazz.isArray() || clazz.equals(List.class) || clazz.equals(Set.class);

    }

    protected boolean isStringType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getPropertyType();
        return clazz.equals(String.class);

    }

    protected boolean isNumberType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getPropertyType();
        return clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class);

    }

    protected boolean isBooleanType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getPropertyType();
        return clazz.equals(Boolean.class);

    }

    protected boolean isEnumType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getPropertyType();
        return clazz.isEnum();

    }

    protected boolean isObjectType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getPropertyType();
        return clazz.getSuperclass() == Object.class;

    }

    protected JsonSerializer<Object> defaultNullArrayJsonSerializer() {
        return nullArrayJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullBooleanJsonSerializer() {
        return nullBooleanJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullNumberJsonSerializer() {
        return nullNumberJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullStringJsonSerializer() {
        return nullStringJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullObjectJsonSerializer() {
        return nullObjectJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullEnumJsonSerializer() {
        return nullEnumJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullOtherJsonSerializer() {
        return nullOtherJsonSerializer;
    }
}