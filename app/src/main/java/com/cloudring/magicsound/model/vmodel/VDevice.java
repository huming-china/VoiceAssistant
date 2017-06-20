package com.cloudring.magicsound.model.vmodel;

/**
 * Created by hm on 2016/11/25.
 * 设备相关控制的基类
 */

public class VDevice extends VObject {
    private String deviceName;
    public final static String DEVICE_FAN="fan";
    public final static String DEVICE_READ="readinglight";
    public final static String DEVICE_ATMO="atmolight";
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
