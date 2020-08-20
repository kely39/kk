package com.kk.d.pay.wx.ret;

import com.kk.d.pay.wx.EntPayResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntPayQueryResultDTO {

    /**
     * 状态
     **/
    private EntPayResultEnum resultEnum;

    /**
     * 错误信息
     **/
    private String errMsg;
}
