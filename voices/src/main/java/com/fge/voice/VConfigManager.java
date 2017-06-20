package com.fge.voice;

import android.content.Context;
import android.content.SharedPreferences;

import com.aispeech.common.AIConstant;
import com.fge.voice.aispeech.options.AIOptions;
import com.fge.voice.iflytek.options.IflyOptions;
import com.fge.voice.util.FileWriteLog;

import java.io.File;
import java.util.IllegalFormatException;

/**
 * 优先Options  再读取SharedPreferences
 * Created by hm on 2016/12/14.
 */

public class VConfigManager {
    private static AIOptions mAIOptions;
    private static IflyOptions mIflyOptions;

    public static void init(Context context, com.fge.voice.aispeech.options.AIOptions aiOptions, IflyOptions iflyOptions) {
        if (VoiceConfig.getEngine_type() == null) {
            throw new VerifyError("验证不通过，需要先用setEngineType设置EngineType");
        }
        if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
            mIflyOptions = iflyOptions == null ? new IflyOptions.Builder().build() : iflyOptions;
        } else if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_AISPEECH) {
            mAIOptions = aiOptions == null ? new AIOptions.Builder(context).build() : aiOptions;
            initConfig();
        }
        FileWriteLog.SHOW_LOG = getConfigIntValue(context, "show_log", new File("mnt/sdcard/VT").exists() ? 1 : 0) == 1 ? true : false;
        FileWriteLog.WRITE_LOG = getConfigIntValue(context, "write_log", new File("mnt/sdcard/VT").exists() ? 1 : 0) == 1 ? true : false;

    }

    public static void init(Context context, IflyOptions iflyOptions) {
        init(context, null, iflyOptions);
    }

    public static void init(Context context, AIOptions aiOptions) {
        init(context, aiOptions, null);
    }

    private static void initConfig() {
        boolean isOpenEcho = mAIOptions.isOpenEcho;
        FileWriteLog.writeLog("AEC " + isOpenEcho);
        if (isOpenEcho) {
            AIConstant.setNewEchoEnable(true);// 打开AEC
            AIConstant.setEchoCfgFile("aec.cfg");// 设置AEC的配置文件
            FileWriteLog.writeLog("声道  " + mAIOptions.recChannel);
            AIConstant.setRecChannel(mAIOptions.recChannel);
        }
    }

    public static IflyOptions getIflyOptions() {
        return mIflyOptions;
    }

    public static AIOptions getAIOptions() {
        return mAIOptions;
    }

    public static String getConfigStringValue(Context context, String key, String defValue) {
        return getSp(context).getString(key, defValue);
    }

    public static int getConfigIntValue(Context context, String key, int defValue) {
        return getSp(context).getInt(key, defValue);
    }

    public static float getConfigFloatValue(Context context, String key, float defValue) {
        return getSp(context).getFloat(key, defValue);
    }

    public static void setConfigStringValue(Context context, String key, String value) {
        editor(context).putString(key, value).commit();
    }

    public static void setConfigIntValue(Context context, String key, int value) {
        editor(context).putInt(key, value).commit();
    }

    public static void setConfigFloatValue(Context context, String key, float value) {
        editor(context).putFloat(key, value).commit();
    }

    private static SharedPreferences.Editor editor(Context context) {
        return getSp(context).edit();
    }

    private static SharedPreferences getSp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("configs.txt", Context.MODE_PRIVATE);
        return sharedPreferences;
    }
}
