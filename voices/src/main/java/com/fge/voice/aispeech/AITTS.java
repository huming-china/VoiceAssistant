package com.fge.voice.aispeech;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.aispeech.AIError;
import com.aispeech.common.AIConstant;
import com.aispeech.common.Util;
import com.aispeech.export.engines.AICloudTTSEngine;
import com.aispeech.export.engines.AILocalTTSEngine;
import com.aispeech.export.listeners.AITTSListener;
import com.fge.voice.SpeakCallback;
import com.fge.voice.VConfigManager;
import com.fge.voice.VoiceConfig;
import com.fge.voice.aispeech.constants.AIConstants;
import com.fge.voice.aispeech.options.AIOptions;
import com.fge.voice.base.ITTSSupport;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.util.NetWorkUtils;
import com.fge.voice.util.FileWriteLog;

/**
 * 语音播报 TTS 思必驰
 * Created by HM on 2016/12/13.
 */

public class AITTS implements ITTSSupport {
    private AILocalTTSEngine mlocalEngine;
    private AICloudTTSEngine mCloudEngine;
    private SpeakCallback callback;
    private Context mContext;
    private boolean isSpeaking;
    private AIOptions options ;
    public AITTS(Context context) {
        this.mContext = context;
        options= VConfigManager.getAIOptions();
        aiAuthInit();
    }

    public void options(AIOptions options){
        if(options!=null){
            this.options=options;
        }
    }
    /**
     * 语音播报
     * @param content  要播报的文字
     * @param callback 状态回调
     */
    @Override
    public void startSpeak(final String content, SpeakCallback callback) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        isSpeaking=true;
        this.callback = callback;
        FileWriteLog.writeLog("TTS ：" + content);
        if (options.network_tts&& NetWorkUtils.isConnected(mContext)) {//有网络
            // 创建云端合成播放器
            if (mCloudEngine == null) {
                mCloudEngine = AICloudTTSEngine.createInstance();
                mCloudEngine.init(mContext, new MyAITTSListener(), AIConstants.APPKEY, AIConstants.SECRETKEY);
                // 指定默认中文合成
                mCloudEngine.setLanguage(AIConstant.CN_TTS);
                // 默认女声
                mCloudEngine.setRes("syn_chnsnt_zhilingf");
                mCloudEngine.setDeviceId(Util.getIMEI(mContext));
                mCloudEngine.setSavePath(/*"/sdcard/"*/Environment.getExternalStorageDirectory().getPath() + "aispeechpath/"  + "tts.mp3");
            }
            mCloudEngine.stop();
            mCloudEngine.speak(content, "1024");
        } else {
            new AiAuthInit().auth(mContext, new VoiceReadyCallback() {
                @Override
                public void onReadyCallback(boolean isSuceess, int type, String errMsg) {
                    if (isSuceess) {
                        initLocalEngine(content);
                    } else {
                        FileWriteLog.writeLog("授权失败");
                    }
                }
            }, null);
        }
    }
    private void aiAuthInit(){
        new AiAuthInit().auth(mContext,null, null);
    }


    private void initLocalEngine(String content) {
        if (mlocalEngine == null) {
            mlocalEngine = AILocalTTSEngine.createInstance();//创建实例
            String tts_resource = TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath()) ? AIConstants.tts_resource : VoiceConfig.getVoiceResourcePath() + "/" + AIConstants.tts_resource;
            mlocalEngine.setResource(tts_resource);
            String tts_dict_dbname = TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath()) ? AIConstants.tts_dict_dbname : VoiceConfig.getVoiceResourcePath() + "/" + AIConstants.tts_dict_dbname;
            mlocalEngine.setDictDbName(tts_dict_dbname);
            mlocalEngine.setRealBack(true);//设置本地合成使用实时反馈
            mlocalEngine.init(mContext, new MyAITTSListener(), AIConstants.APPKEY, AIConstants.SECRETKEY);//初始化合成引擎
            mlocalEngine.setSpeechRate(options.tts_speechRate);//设置语速
            mlocalEngine.setDeviceId(Util.getIMEI(mContext));
            mlocalEngine.setSavePath(AIConstants.tts_save_path);
        }
        mlocalEngine.stop();
        FileWriteLog.writeLog("开始合成声音");
        mlocalEngine.speak(content, "1024");
    }

    private class MyAITTSListener implements AITTSListener {

        @Override
        public void onInit(int i) {
            if (callback != null) {
                FileWriteLog.writeLog("TTS init code =" + i);
                callback.onInit(i);
            }
        }

        @Override
        public void onError(String s, AIError aiError) {
            isSpeaking=false;
            FileWriteLog.writeLog("TTS 错误 =" + s);
            if (callback != null) {
                callback.onError(aiError.getErrId(), s);
            }
        }

        @Override
        public void onReady(String s) {
            if (callback != null) {
                callback.onSpeakBegin();
            }
        }

        @Override
        public void onCompletion(String s) {
            if (callback != null) {
                callback.onCompletion();
            }
            isSpeaking=false;
        }

        @Override
        public void onProgress(int i, int i1, boolean b) {
            if (callback != null) {
                callback.onProgress(i);
            }
        }
    }

    @Override
    public void stopSpeak() {
        isSpeaking=false;
        if (mlocalEngine != null) {
            mlocalEngine.stop();
        }
        if (mCloudEngine != null) {
            mCloudEngine.stop();
        }
    }

    @Override
    public void destory() {
        if (mlocalEngine != null) {
            mlocalEngine.destroy();
            mlocalEngine = null;
        }
        if (mCloudEngine != null) {
            mCloudEngine.destroy();
            mCloudEngine = null;
        }
        callback = null;
    }

    @Override
    public void init() {

    }

    @Override
    public boolean isSpeaking() {
        return isSpeaking;
    }
}
