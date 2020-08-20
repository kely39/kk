package com.kk.d.framework.web.util;

import com.kk.d.framework.web.constants.VisitConstants;

import javax.servlet.http.HttpServletRequest;

public class HeaderUtil {


    /**
     * 获取当前登录用户channel
     *
     * @param request
     * @return
     */
    public static String getRequestChannel(HttpServletRequest request) {
        Object channel = request.getAttribute(VisitConstants.REQUEST_CHANNEL);
        if (channel == null) {
            return "";
        }
        return channel.toString();
    }

    /**
     * 获取当前登录用户platform
     *
     * @param request
     * @return
     */
    public static String getRequestPlatform(HttpServletRequest request) {
        Object platform = request.getAttribute(VisitConstants.REQUEST_PLATFORM);
        if (platform == null) {
            return "";
        }
        return platform.toString();
    }

    /**
     * 获取当前登录用户app_version
     *
     * @param request
     * @return
     */
    public static String getRequestAppVersion(HttpServletRequest request) {
        Object appVersion = request.getAttribute(VisitConstants.REQUEST_APP_VERSION);
        if (appVersion == null) {
            return "";
        }
        return appVersion.toString();
    }

    /**
     * 获取当前登录用户设备的版本号
     * @param request
     * @return
     */
    public static String getRequestPlatformVersion(HttpServletRequest request){
        Object platformVersion = request.getAttribute(VisitConstants.REQUEST_PLATFORM_VERSION);
        if (platformVersion == null) {
            return "";
        }
        return platformVersion.toString();
    }

}
