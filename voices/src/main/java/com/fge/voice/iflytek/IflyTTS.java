package com.fge.voice.iflytek;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import com.fge.voice.SpeakCallback;
import com.fge.voice.VConfigManager;
import com.fge.voice.VoiceConfig;
import com.fge.voice.base.BaseVoiceManager;
import com.fge.voice.base.ITTSSupport;
import com.fge.voice.iflytek.options.IflyOptions;
import com.fge.voice.util.NetWorkUtils;
import com.fge.voice.util.FileWriteLog;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;

/**
 * 语音播放  讯飞
 * Created by HM on 2016/12/19.
 */

public class IflyTTS extends BaseVoiceManager implements ITTSSupport{
    private SpeechSynthesizer mTts;
    private Context mContext;
    private SpeakCallback callBack;
    private IflyOptions options;

    public IflyTTS(Context context) {
        this.mContext = context;
        options= VConfigManager.getIflyOptions();
        init();

    }

    public void options(IflyOptions options){
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
    public void startSpeak(String content,SpeakCallback callback){
        startSpeak(content,callback,null);
    }
    public void startSpeak(String content,SpeakCallback callback,IflyOptions options){
        if(options!=null){
            this.options=options;
        }
        this.callBack = callback;
        if (mTts == null) {
            init();
        }
        setParam();
        startSpeak(content);
    }

    /**
     * 语音播报
     * @param msg  要播报的文字
     */

    private void startSpeak(String msg) {

        mTts.startSpeaking(msg, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {
                if (callBack != null)
                    callBack.onSpeakBegin();
            }

            @Override
            public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            }

            @Override
            public void onSpeakPaused() {
            }

            @Override
            public void onSpeakResumed() {
            }

            @Override
            public void onSpeakProgress(int percent, int beginPos, int endPos) {
                if (callBack != null)
                    callBack.onProgress(percent);
            }

            @Override
            public void onCompleted(SpeechError speechError) {
                if (callBack != null) {
                    if (speechError == null) {
                        callBack.onCompletion();
                    } else {
                        callBack.onError(speechError.getErrorCode(), speechError.getMessage());
                    }
                }
            }

            @Override
            public void onEvent(int eventType, int i1, int i2, Bundle bundle) {
            }
        });

    }

    /***
     * 停止录音 发送数据请求识别
     */
    @Override
    public void stopSpeak() {
        if (mTts != null) {
            mTts.stopSpeaking();
        }
    }

    /**
     * 销毁引擎
     */
    @Override
    public void destory() {
        if (mTts != null) {
            mTts.destroy();
        }
    }

    /**
     * 初始化识别引擎
     */
    @Override
    public void init() {
        mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            FileWriteLog.writeLog("Ifly TTS InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {

            } else {
                destory();
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 是否在使用引擎中
     */
    @Override
    public boolean isSpeaking() {
        if (mTts != null) {
            return mTts.isSpeaking();
        }
        return false;
    }

    /**
     * 参数设置
     * @return
     */
    private void setParam(){
        if(mTts==null){return;}
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        //设置合成
        if(NetWorkUtils.isConnected(mContext)&&options.networkTTS)
        {
            FileWriteLog.writeLog("TYPE_CLOUD");
            //设置使用云端引擎
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //设置发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME,options.voiceCloudName);
        }else {
            FileWriteLog.writeLog("TYPE_LOCAL");
            //设置使用本地引擎
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            //设置发音人资源路径
            mTts.setParameter(ResourceUtil.TTS_RES_PATH,getResourcePath());
            //设置发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME,options.voiceLocalName);
        }
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, options.speed);
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH,options.pitch);
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, options.volume);
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, options.streamType);

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }

    //获取发音人资源路径
    private String getResourcePath(){
        StringBuffer tempBuffer = new StringBuffer();
        //合成通用资源
        if(TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())){
            FileWriteLog.writeLog("TTS资源参数:assets");
            tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/common.jet"));
            tempBuffer.append(";");
            //发音人资源
            tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/" + options.voiceLocalName + ".jet"));
        }else{
            FileWriteLog.writeLog("TTS资源参数:path");
            tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.path, VoiceConfig.getVoiceResourcePath()+"/tts/common.jet"));
            tempBuffer.append(";");
            //发音人资源
            tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.path, VoiceConfig.getVoiceResourcePath()+"/tts/" + options.voiceLocalName + ".jet"));
        }
        return tempBuffer.toString();
    }
}
