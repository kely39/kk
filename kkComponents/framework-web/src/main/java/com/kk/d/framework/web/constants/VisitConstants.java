package com.kk.d.framework.web.constants;

/**
 * @author Leach
 * @date 2017/6/30
 */
public class VisitConstants {

    /**
     * 请求头部-设备ime
     */
    public final static String HEADER_IMEI = "imei";

    /**
     * 请求头部-请求类型平台。目前有：iOS、Android、pcweb、h5
     */
    public final static String HEADER_PLATFORM = "platform";

    /**
     * 请求头部-设备的版本号，如：Android4.4.4,、iOS10.0.2
     */
    public final static String HEADER_PLATFORM_VERSION = "platformVersion";

    /**
     * 请求头部-请求来源的version，指app的版本号
     */
    public final static String HEADER_APP_VERSION = "appVersion";

    /**
     * 请求头部-定位到的地区路径，如44/4403/440308
     */
    public final static String HEADER_LOCATION_PATH = "locationPath";

    /**
     * 请求头部-分发渠道标识
     */
    public final static String HEADER_CHANNEL = "channel";

    /**
     * REQUEST Attribute
     */
    public final static String REQUEST_PREFIX = "visitor_";

    public final static String REQUEST_IMEI = REQUEST_PREFIX + HEADER_IMEI;

    public final static String REQUEST_PLATFORM = REQUEST_PREFIX + HEADER_PLATFORM;

    public final static String REQUEST_PLATFORM_VERSION = REQUEST_PREFIX + HEADER_PLATFORM_VERSION;

    public final static String REQUEST_APP_VERSION = REQUEST_PREFIX + HEADER_APP_VERSION;

    public final static String REQUEST_LOCATION_PATH = REQUEST_PREFIX + HEADER_LOCATION_PATH;

    public final static String REQUEST_CHANNEL = REQUEST_PREFIX + HEADER_CHANNEL;
}
