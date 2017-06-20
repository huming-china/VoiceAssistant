package com.fge.voice.base;

import com.fge.voice.SpeakCallback;

/**
 * 语音播报约束
 */

public interface ITTSSupport {
    /**
     * 开始播报
     */
    void startSpeak(String msg,SpeakCallback callback);

    /***
     * 停止播报
     */
    void stopSpeak();

    /**
     * 销毁引擎
     */
    void destory();

    /**
     * 初始化引擎
     */
    void init();

    /**
     * 是否在说话中
     */
    boolean isSpeaking();
}
