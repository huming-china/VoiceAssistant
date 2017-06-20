package com.cloudring.magicsound.model.vmodel;


/**
 * 对智能设备的控制  (只限于 离子风,阅读灯的打开 关闭)
 * Created by hm on 2016/9/2.
 */
public class VControl extends VObject{
    private String deviceName;
    private boolean onoff;//操作

    public boolean isOnoff() {
        return onoff;
    }

    public void setOnoff(String onoff) {
        if("open".equals(onoff)){
            this.onoff=true;
        }else {
            this.onoff = false;
        }
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
