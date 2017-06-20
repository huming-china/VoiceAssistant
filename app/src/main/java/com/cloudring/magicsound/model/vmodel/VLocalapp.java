package com.cloudring.magicsound.model.vmodel;

/**
 * 本地应用指令数据模型类
 * Created by TOM on 2016/9/2.
 */
public class VLocalapp extends VObject{
    private String action;

    private String name;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
