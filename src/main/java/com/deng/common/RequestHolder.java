package com.deng.common;

import com.deng.model.SysUser;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {
    public static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();
    public static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest httpServletRequest) {
        requestHolder.set(httpServletRequest);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
