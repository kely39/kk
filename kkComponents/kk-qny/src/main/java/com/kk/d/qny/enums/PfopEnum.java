package com.kk.d.qny.enums;

public enum PfopEnum {
    /**
     * 人脸图像添加时间水印
     **/
    FACE_IMG(1, "watermark/2/text/${dateStrBase64}/font/5b6u6L2v6ZuF6buR/fontsize/400/fill/I0ZGMDMwMw==/dissolve/75/gravity/NorthEast/dx/50/dy/10","pipeline-chetailian","/api/system/inner/attachment/v1/persistentNotify.pass");

    private Integer type;

    private String fops;

    private String pipeline;

    private String notifyURL;

    PfopEnum(Integer type, String fops, String pipeline, String notifyURL) {
        this.type = type;
        this.fops = fops;
        this.pipeline = pipeline;
        this.notifyURL = notifyURL;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFops() {
        return fops;
    }

    public void setFops(String fops) {
        this.fops = fops;
    }

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

    public static PfopEnum getEnumByType(Integer type) {
        PfopEnum[] values = PfopEnum.values();
        for (PfopEnum object : values) {
            if (object.type.equals(type)) {
                return object;
            }
        }
        return null;
    }
}
