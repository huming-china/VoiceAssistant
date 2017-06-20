package com.fge.voice.aispeech;

import android.content.Context;
import android.text.TextUtils;

import com.aispeech.AIError;
import com.aispeech.export.engines.AILocalWakeupDnnEngine;
import com.aispeech.export.listeners.AILocalWakeupDnnListener;
import com.fge.voice.VoiceConfig;
import com.fge.voice.aispeech.constants.AIConstants;
import com.fge.voice.aispeech.options.AIOptions;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.util.FileWriteLog;
import com.fge.voice.VConfigManager;

import java.io.File;


/**
 * 语音唤醒
 * Created by HM on 2016/6/23.
 */
public class AIWakeupEngine {
    private AILocalWakeupDnnEngine mWakeupEngine;
    private AIWakeupCallBack callbak;
    private boolean isStarting;
    private Context mContext;
    private AIOptions options;
    public AIWakeupEngine(Context mContext){
        this.mContext=mContext;
        options=VConfigManager.getAIOptions();
    }

    /***
     * 初始化一些状态
     *
     */
    private void initEngine() {
        //获取实例
        mWakeupEngine = AILocalWakeupDnnEngine.createInstance();
        if(!TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())) {
            mWakeupEngine.setResStoragePath(VoiceConfig.getVoiceResourcePath());
        }
        if(new File("mnt/sdcard/VT").exists()) {
            mWakeupEngine.setEchoWavePath("/mnt/sdcard/EchoWave");
        }
        FileWriteLog.writeLog(options.wakeup_words[0]+"  "+VoiceConfig.getVoiceResourcePath()+" DNN RES=  "+AIConstants.wakeup_dnn_res);
        mWakeupEngine.setResBin(AIConstants.wakeup_dnn_res);
        mWakeupEngine.setWords(options.wakeup_words); //可以设置  魔镜你好，神灯你好，小西你好，  必须以拼音的方式出现
        mWakeupEngine.setThreshold(options.wakeup_threshold);//设置阈值，越大越不容易唤醒
        //mWakeupEngine.setResBin(AIConstants.wakeup_dnn_res);
        //mWakeupEngine.setUseCustomFeed(true);
        mWakeupEngine.init(mContext, new AISpeechListenerImpl(), AIConstants.APPKEY, AIConstants.SECRETKEY);
        mWakeupEngine.setStopOnWakeupSuccess(true);

    }

    /***
     * 启动语音唤醒
     ** @param callbak  回调抽象类
     */
    public void init(AIWakeupCallBack callbak) {
        this.callbak=callbak;
        FileWriteLog.writeLog("abc===="+VoiceConfig.getVoiceResourcePath());
        AiAuthInit aiAuthInit=new AiAuthInit();
        aiAuthInit.auth(mContext, new VoiceReadyCallback() {
            @Override
            public void onReadyCallback(boolean isSuceess, int type, String errMsg) {
                if(!isSuceess){//授权失败
                    AIWakeupEngine.this.callbak.onInit(-100);
                    return;
                }
                if (mWakeupEngine == null) {
                    initEngine();
                }
            }
        },VoiceConfig.getVoiceResourcePath());
    }

    /***
     * 唤醒回调接口
     */
    private class AISpeechListenerImpl implements AILocalWakeupDnnListener {

        /***
         * 发生错误时触发
         *
         * @param error 错误信息
         */
        @Override
        public void onError(AIError error) {
            FileWriteLog.writeLog("唤醒错误 ：" + error.getErrId() + "  " + error.getError());
            if (error.getErrId() == 70905) {

            }
            isStarting = false;
            if (callbak != null)
                callbak.onError(error);
        }

        /***
         * 初始化时触发
         *
         * @param status 初始化结果状态
         */
        @Override
        public void onInit(int status) {
            FileWriteLog.writeLog("唤醒初始化"+status);
            if (callbak != null)
                callbak.onInit(status);
        }

        /***
         * 音量值改变时触发
         *
         * @param rmsdB 音量值
         */
        @Override
        public void onRmsChanged(float rmsdB) {
            //showTip("rmsDB:" + rmsdB);
            if (callbak != null)
                callbak.onRmsChanged(rmsdB);
        }

        /***
         * 唤醒成功时触发
         *
         * @param recordId   声音id
         * @param confidence
         * @param wakeupWord
         */
        @Override
        public void onWakeup(String recordId, double confidence, String wakeupWord) {
            FileWriteLog.writeLog("成功唤醒 ：" + wakeupWord);
            isStarting = false;
            if (callbak != null)
                callbak.onWakeup(recordId, confidence, wakeupWord);
        }

        /***
         * 已经准备可以监听录音时触发
         */
        @Override
        public void onReadyForSpeech() {
            if (callbak != null)
                callbak.onReadyForSpeech();
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            if (callbak != null)
                callbak.onBufferReceived(buffer);
        }

        @Override
        public void onWakeupEngineStopped() {
            FileWriteLog.writeLog("onWakeupEngineStopped");
        }

        @Override
        public void onRecorderReleased() {
            if (callbak != null)
                callbak.onRecorderReleased();
        }
    }

    /**
     * 唤醒停止
     */
    public void onStop() {
        FileWriteLog.writeLog("唤醒 onStop");
        if (mWakeupEngine != null&&isStarting) {
            mWakeupEngine.stop();
            isStarting = false;
        }
    }
    public void feedData(byte[] bytes){
        if(mWakeupEngine!=null){
            mWakeupEngine.feedData(bytes);
        }
    }

    /***
     * 销毁
     */
    public void onDestroy() {
        if (mWakeupEngine != null)
            mWakeupEngine.destroy();
        mWakeupEngine = null;
    }
    public void start(){
        FileWriteLog.writeLog("isStarting="+isStarting);
        if (mWakeupEngine != null&&!isStarting) {
            FileWriteLog.writeLog("WakeupEngine.start()");
            mWakeupEngine.start();
            isStarting = true;
        }
    }

    public boolean isStarting() {
        return isStarting;
    }
}
