package com.kk.d.qny.enums;

public enum BucketEnum {
    /**
     * 私有
     **/
    PRIVATE("chetailian-private"),

    /**
     * 共用
     **/
    PUBLIC("chetailian-public");

    private String val;

    BucketEnum(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public static BucketEnum getEnum(String value) {
        BucketEnum[] values = BucketEnum.values();
        for (BucketEnum object : values) {
            if (object.val.equals(value)) {
                return object;
            }
        }
        return null;
    }
}
