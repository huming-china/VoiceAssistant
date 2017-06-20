package com.fge.voice;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.cloudring.commonlib.utils.SpUtil;
import com.fge.voice.util.FileWriteLog;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.io.File;

/**
 * Created by lee on 16/1/25.
 */
public class VoiceConfig {

    public static boolean isSupportVoiceWake = false;
    public static String appid = "55a5c837";

    public enum EngineType {
        TYPE_AISPEECH, EngineType, TYPE_IFLYTEK
    }


    public static EngineType engine_type;

    public static void setEngineType(EngineType type) {
        engine_type = type;
    }

    public static EngineType getEngine_type() {
        return engine_type;
    }

    public static String getVoiceResourcePath() {
        String voice_path = SpUtil.readString("voice_path", null);
        if (!TextUtils.isEmpty(voice_path)) {
            File file = new File(voice_path);
            if (!file.exists() || !file.canRead()) {
                voice_path = null;
            }
        }
        FileWriteLog.writeLog("voice_path==" + voice_path);
        return voice_path;
    }

    public static void setVoiceResourcePath(String path) {
        SpUtil.writeString("voice_path", path);
    }

    /**
     * 必须在application里面做初始化
     */
    public static void initSpeech(Application app) {
        StringBuffer param = new StringBuffer();
        param.append("appid=" + appid);
        // param.append(",");
        // 设置使用v5+
        //param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(app, param.toString());
    }

    public static void initSpeech(Context app, String app_id) {
        appid = app_id;
        StringBuffer param = new StringBuffer();
        param.append("appid=" + app_id);
        param.append(",");
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(app, param.toString());
    }
}
