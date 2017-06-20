package com.cloudring.magicsound.model.vmodel;

import android.text.TextUtils;

/**
 * 周边相关数据模型类
 * Created by HM on 2016/9/2.
 */
public class VNear extends VObject{
    private String name;
    private String category;

    public String getKeyName() {
        if(TextUtils.isEmpty(name)){
            return category;
        }
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
