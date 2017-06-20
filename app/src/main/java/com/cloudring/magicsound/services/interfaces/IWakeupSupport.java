package com.cloudring.magicsound.services.interfaces;

/**
 * Created by zengpeijin on 2017/4/19.
 */

public interface IWakeupSupport {

    /***
     * 初始化唤醒引擎
     */
   void initWakeup();
    /**
     * 启动引擎 开始录音
     */
   void startWakeup();

    /**
     * 关闭唤醒引擎 暂停录音 释放麦克风
     */
   void stopWakeup();
}
