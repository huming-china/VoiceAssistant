package com.fge.voice;

/**
 * 播报 目标抽象类
 */
public abstract class SpeakCallback {

    public void onInit(int code){}//初始化  只有思必驰有

    public void onError(int errorCode, String errorMsg){}//错误

    public void onSpeakBegin(){}//开始播报

    public void onCompletion(){};//播报完成

    public void onProgress(int percent){}//进度

}
