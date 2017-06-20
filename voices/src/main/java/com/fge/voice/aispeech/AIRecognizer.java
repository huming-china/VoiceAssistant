package com.fge.voice.aispeech;

import android.content.Context;
import android.util.Log;

import com.aispeech.AIError;
import com.aispeech.AIResult;
import com.aispeech.common.AIConstant;
import com.aispeech.common.Util;
import com.aispeech.export.engines.AICloudASREngine;
import com.aispeech.export.listeners.AIASRListener;
import com.fge.voice.callback.RecognizerCallback;
import com.fge.voice.VConfigManager;
import com.fge.voice.VoiceConfig;
import com.fge.voice.aispeech.constants.AIConstants;
import com.fge.voice.aispeech.options.AIOptions;
import com.fge.voice.base.BaseRecognizer;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.util.FileWriteLog;

/**
 * Created by hm on 17/6/6
 * 语音听写思必驰
 */
public class AIRecognizer extends BaseRecognizer implements VoiceReadyCallback{
    public static final int BACK_AIAUTH = 1;
    public static final int BACK_ENGINE = 3;

    private RecognizerCallback callback;//数据回调
    private Context mContext;
    private AICloudASREngine asrEngine;
    private VoiceReadyCallback mVoiceReadyCallback;
    private AIOptions options;
    private boolean isListening;
    public AIRecognizer(Context context, VoiceReadyCallback mVoiceReadyCallback) {
        this.mContext=context;
        options= VConfigManager.getAIOptions();
        this.mVoiceReadyCallback=mVoiceReadyCallback;
        aiAuthInit();
    }

    public void options(AIOptions options){
        if(options!=null)
            this.options=options;
    }
    /**
     * 开始识别
     *
     * @param callback 识别结果回调
     */
    @Override
    public void startRecording(RecognizerCallback callback) {
        this.callback = callback;
        if (asrEngine != null) {
            FileWriteLog.writeLog("AsrEngine.start();");
            isListening=true;
            asrEngine.start();
        } else {
            FileWriteLog.writeLog("请先生成资源");
            callback.onError(-1, "语音语法资源文件未生成,请重新打开软件");
        }
    }

    @Override
    public void cancel() {
        isListening=false;
        if (asrEngine != null) {
            asrEngine.cancel();
        }
        callback = null;
    }

    @Override
    public void stopRecording() {
        if (asrEngine != null) {
            asrEngine.stopRecording();
        }
    }

    @Override
    public void destory() {
        isListening=false;
        if (asrEngine != null) {
            asrEngine.destroy();
            asrEngine = null;
        }
        mVoiceReadyCallback=null;
    }

    @Override
    public void init() {
        if (asrEngine != null) {
            asrEngine.destroy();
        }
        asrEngine = AICloudASREngine.createInstance();
        // 开启实时反馈
        asrEngine.setRTMode(AIConstant.RT_MODE_VAD);
        // 根据应用场景选择开启或关闭VAD
        asrEngine.setVadEnable(true);
        asrEngine.setVadResource(AIConstants.vad_res);
        asrEngine.setNoSpeechTimeOut(options.noSpeechTimeOut);
        asrEngine.setPauseTime(options.pauseTime);
        asrEngine.setVolEnable(true);
        asrEngine.setUseTxtPost(true);
        asrEngine.setMaxSpeechTimeS(options.maxSpeechTime);
        asrEngine.init(mContext, new AIASRListenerImpl(), AIConstants.APPKEY, AIConstants.SECRETKEY);
        asrEngine.setDeviceId(Util.getIMEI(mContext));
    }

    @Override
    public boolean isListening() {
        return isListening;
    }

    /**
     * 本地识别引擎回调接口，用以接收相关事件
     */
    public class AIASRListenerImpl implements AIASRListener {

        @Override
        public void onBeginningOfSpeech() {
            FileWriteLog.writeLog("检测到说话");
        }

        @Override
        public void onEndOfSpeech() {
            FileWriteLog.writeLog("检测到语音停止，开始识别...");
            if (callback != null)
                callback.onEndOfSpeech();
        }

        @Override
        public void onReadyForSpeech() {
            if(callback!=null) {
                callback.onBeginOfSpeech();
            }
            FileWriteLog.writeLog("请说话...");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            FileWriteLog.writeLog("onRmsChanged..."+rmsdB);
            if (callback != null)
                callback.onVolumeChanged(rmsdB);
        }

        @Override
        public void onError(AIError error) {
            isListening=false;
            FileWriteLog.writeLog("识别发生错误 :" + error.getErrId());//70904  错误码 不说话
            if (callback != null)
                callback.onError(error.getErrId(), error.getMessage());
        }

        @Override
        public void onResults(AIResult results) {
            isListening=false;
            Log.e("TEST", "onResults: 原始数据=="+results.resultObject );
            if (callback != null)
                callback.onResult(results.isLast(),callback.parseResult(results.getResultObject().toString()));
        }

        @Override
        public void onInit(int status) {
            FileWriteLog.writeLog(callback+"引擎初始化状态="+status);
            if (callback != null)
                callback.onInit(status);
            if (status == 0) {
                FileWriteLog.writeLog("本地识别引擎加载成功");
                if (mVoiceReadyCallback != null)
                    mVoiceReadyCallback.onReadyCallback(true, BACK_ENGINE, "AsrEngine init success");
            } else {
                FileWriteLog.writeLog("本地识别引擎加载失败:"+status);
                if (mVoiceReadyCallback != null)
                    mVoiceReadyCallback.onReadyCallback(false, BACK_ENGINE, "AsrEngine init failed,errCode :" + status);
            }
        }

        @Override
        public void onRecorderReleased() {
            FileWriteLog.writeLog("检测到录音机停止 onRecorderReleased");
            // showInfo("检测到录音机停止");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // TODO Auto-generated method stub

        }
    }

    /**********
     * 授权
     *********/
    public void aiAuthInit() {
        com.fge.voice.aispeech.AiAuthInit aiAuthInit = new com.fge.voice.aispeech.AiAuthInit();
        aiAuthInit.auth(mContext, this, VoiceConfig.getVoiceResourcePath());
    }

    @Override
    public void onReadyCallback(boolean isSuceess, int type, String errMsg) {
        if(mVoiceReadyCallback==null){return;}
        if (!isSuceess) {mVoiceReadyCallback.onReadyCallback(false, BACK_ENGINE, errMsg);}
        if (type == BACK_AIAUTH) {
            init();
        }
    }
}
