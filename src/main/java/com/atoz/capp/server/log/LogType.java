package com.atoz.capp.server.log;

/**
 * <p>User: lp
 * <p>Date: 13-3-11 下午3:19
 * <p>Version: 1.0
 */
public enum LogType {

    SYS("内部系统"), REST("REST");

    private final String info;

    private LogType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
