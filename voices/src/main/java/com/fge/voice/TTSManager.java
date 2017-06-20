package com.fge.voice;

import android.content.Context;

import com.fge.voice.aispeech.AITTS;
import com.fge.voice.aispeech.options.AIOptions;
import com.fge.voice.base.BaseOptions;
import com.fge.voice.base.BaseVoiceManager;
import com.fge.voice.base.ITTSSupport;
import com.fge.voice.iflytek.IflyTTS;
import com.fge.voice.iflytek.options.IflyOptions;

/**
 * 语音播报TTS  管理类
 * Created by hm on 2016/12/13.
 */

public class TTSManager extends BaseVoiceManager {
    private volatile static TTSManager instance;
    private ITTSSupport tts;

    public static TTSManager getInstance(Context context) {
        if (instance == null) {
            synchronized (TTSManager.class) {
                if (instance == null) {
                    instance = new TTSManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * @param option
     */
//    public void initOptions(Object option) {
//        if(VoiceConfig.getEngine_type() == null) {
//            throw new VerifyError("年轻人不要着急 先设置EngineType再来");
//        } else {
//            if(VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
//                if(option instanceof IflyOptions) {
//                    tts.options((IflyOptions)option);
//                }
//            } else if(VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_AISPEECH && option instanceof AIOptions) {
//                tts.options((AIOptions)option);
//            }
//        }
//    }
    private TTSManager(Context applicationContext) {
        if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
            tts = new IflyTTS(applicationContext);
        } else if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_AISPEECH) {
            tts = new AITTS(applicationContext);
        }
    }

    public void startSpeak(String text, SpeakCallback callback) {
        if (tts != null) tts.startSpeak(text, callback);
    }

    public void startSpeak(String text) {
        startSpeak(text, null);
    }

    public void stopSpeak() {
        if (tts != null) tts.stopSpeak();
    }

    public void destory() {
        if (tts != null) tts.destory();
    }

    /**
     * 是否正在说话
     *
     * @return
     */
    public boolean isSpeaking() {
        if (tts != null)
            return tts.isSpeaking();
        return false;
    }
}
