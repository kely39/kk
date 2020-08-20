package com.kk.d.framework.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 保留两位小数显示，不进行四舍五入
 * 针对BigDecimal,String,Double做相应的处理
 * 
 * @author jiangxinjun
 * @date 2017年10月18日
 */
public class KeepDecimalJsonSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value instanceof BigDecimal) {
            BigDecimal val = (BigDecimal)value;
            val = keepDecimal(val);
            jgen.writeNumber(val);
        } else if(value instanceof String) {
            BigDecimal val = new BigDecimal((String)value);
            val = keepDecimal(val);
            jgen.writeString(val.toString());
        } else if(value instanceof Double) {
            BigDecimal val = new BigDecimal((Double)value);
            val = keepDecimal(val);
            jgen.writeString(val.toString());
        }
    }
    
    private BigDecimal keepDecimal(BigDecimal val){
        return val.setScale(2, RoundingMode.DOWN);
    }
}