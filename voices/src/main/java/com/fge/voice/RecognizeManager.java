package com.fge.voice;

import android.content.Context;
import android.support.annotation.IntDef;
import com.fge.voice.aispeech.AIMixRecognizer;
import com.fge.voice.aispeech.AIRecognizer;
import com.fge.voice.base.BaseRecognizeManager;
import com.fge.voice.base.BaseRecognizer;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.callback.RecognizerCallback;
import com.fge.voice.iflytek.IflyRecognizer;

/**
 * 语音识别管理类
 * Created by HM on 2016/12/13.
 */

public class RecognizeManager extends BaseRecognizeManager {
    private volatile static RecognizeManager instance;
    private BaseRecognizer recognizer;
    private Context applicationContext;
    private VoiceConfig.EngineType mEnginetype;
    private int engineModel = MIX;

    public final static int MIX = 0x00000000;
    public final static int RECOGNIZER = 0x00000001;
    public final static int LOCALGRAMMAR = 0x00000002;
    public final static int CLOUDGRAMMAR = 0x00000003;

    @IntDef({MIX, RECOGNIZER, LOCALGRAMMAR, CLOUDGRAMMAR})
    public @interface EngineModel {}

    private RecognizeManager(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


    public void init(VoiceReadyCallback voiceReadyCallback) {
        init(RecognizeManager.MIX, voiceReadyCallback);
    }

    public void init(@EngineModel int model, VoiceReadyCallback voiceReadyCallback) {
        mEnginetype = VoiceConfig.getEngine_type();
        if (mEnginetype == null) {
            throw new VerifyError("年轻人请先设置mEnginetype");
        }
        if (engineModel != model) {//模式不一样时 销毁之前的
            if (recognizer != null) {
                recognizer.destory();
                recognizer = null;
                isSucess = false;
            }
        }
        if (recognizer != null && isSucess) {
            return;
        }
        engineModel = model;
        this.voiceReadyCallback = voiceReadyCallback;
        if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
            if (engineModel == MIX) {
                recognizer = new IflyRecognizer(applicationContext, mVoiceReadyCallback);
            }
        } else if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_AISPEECH) {
            if (engineModel == MIX) {
                recognizer = new AIMixRecognizer(applicationContext, mVoiceReadyCallback);
            } else if (engineModel == RECOGNIZER) {
                recognizer = new AIRecognizer(applicationContext, mVoiceReadyCallback);
            }
        }
    }

    public static RecognizeManager getInstance(Context context) {
        if (instance == null) {
            synchronized (RecognizeManager.class) {
                if (instance == null) {
                    instance = new RecognizeManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * 开始录音
     *
     * @param callback 语音识别结果的回调
     */
    public void startRecording(RecognizerCallback callback) {
        if (recognizer != null) {
            recognizer.startRecording(callback);
        }else{
            throw new VerifyError("must init");
        }
    }

    /**
     * 取消识别(放弃本次的录音和识别结果)
     */
    public void cancel() {
        if (recognizer != null) {
            recognizer.cancel();
        }
    }

    /**
     * 停止录音 发送数据请求识别
     */
    public void stopRecording() {
        if (recognizer != null) {
            recognizer.stopRecording();
        }
    }

    /**
     * 销毁引擎
     */
    public void destory() {
        if (recognizer != null) {
            recognizer.destory();
            recognizer = null;
        }
        voiceReadyCallback = null;
    }

    /**
     * 录音或者识别是否在使用中..
     *
     * @return
     */
    public boolean isListening() {
        if (recognizer != null) {
            return recognizer.isListening();
        }
        return true;
    }


}
