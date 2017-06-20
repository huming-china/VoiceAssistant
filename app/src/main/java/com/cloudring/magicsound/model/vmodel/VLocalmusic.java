package com.cloudring.magicsound.model.vmodel;

/**
 * 本地音乐指令数据模型类
 * Created by TOM on 2016/9/2.
 */
public class VLocalmusic extends VObject{
    private String action;
    private int offset;
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
