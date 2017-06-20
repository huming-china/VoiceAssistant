package com.cloudring.magicsound.model.vmodel;

/**
 * 设备微调
 * Created by hm on 2016/9/5.
 */
public class VUpDown extends VDevice {
    private String action;
    public final static String UP="up";
    public final static String LUP="lup";
    public final static String DOWN="down";
    public final static String LDOWN="ldown";


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
