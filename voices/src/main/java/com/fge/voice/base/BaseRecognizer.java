package com.fge.voice.base;

import com.fge.voice.callback.RecognizerCallback;

/**
 * Created by zengpeijin on 2017/4/26.
 */

public abstract class BaseRecognizer {
    /**
     * 开始录音
     */
    public abstract void startRecording(RecognizerCallback callback);

    /**
     * 取消识别(放弃本次的录音和识别结果)
     */
    public abstract void cancel();

    /***
     * 停止录音 发送数据请求识别
     */
    public abstract  void stopRecording();

    /**
     * 销毁引擎
     */
    public abstract void destory();

    /**
     * 初始化识别引擎
     */
    public abstract void init();

    /**
     * 是否在使用引擎中
     */
    public abstract boolean isListening();
}
