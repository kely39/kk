package com.kk.d.third.party.weixin.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WeXinUserRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //手机号
    private String phone;

    //openId
    private String openId;

}