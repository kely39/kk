package com.kk.d.framework.web.exception;

/**
 * @author kk
 * @date 2019/12/26
 **/
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3455708526465670030L;
    private int code;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
