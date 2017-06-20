package com.cloudring.magicsound.model;

import android.text.TextUtils;

/**
 * Created by zengpeijin on 2016/6/12.
 */
public class MessageItem {
    private String text;

    public MessageItem(String text){this.text= TextUtils.isEmpty(text)?"":text;}
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
