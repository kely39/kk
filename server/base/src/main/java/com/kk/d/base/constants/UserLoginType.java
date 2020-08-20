package com.kk.d.base.constants;

/**
 * 登录用户类型
 *
 * @author yangqh
 * @date 2019/12/27
 **/
public enum UserLoginType {
    MEMBER(1);

    private int val;

    UserLoginType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
