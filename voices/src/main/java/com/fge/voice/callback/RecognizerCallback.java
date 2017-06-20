package com.fge.voice.callback;


/**
 * 识别 目标抽象类
 */
public abstract class RecognizerCallback<T> {
    public abstract T parseResult(String result);
    public void onResult(boolean islast,T result) {}

    public void onError(int code, String errorMsg) {}//会话发生错误回调接口

    public void onBeginOfSpeech() {}//开始录音

    public void onVolumeChanged(float volume) {}//音量值0~30

    public void onEndOfSpeech() {}//结束录音

    public void onInit(int code) {}//初始化

}
