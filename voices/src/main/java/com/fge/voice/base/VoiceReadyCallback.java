package com.fge.voice.base;

/**
 * Created by zengpeijin on 2016/12/6.
 * 准备工作完成 or失败
 */

public interface VoiceReadyCallback {
    public void onReadyCallback(boolean isSuceess,int type,String errMsg);
}
