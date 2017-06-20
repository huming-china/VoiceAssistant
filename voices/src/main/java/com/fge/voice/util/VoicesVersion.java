package com.fge.voice.util;

import android.content.Context;
import android.text.TextUtils;

import com.fge.voice.VoiceConfig;
import com.fge.voice.event.VoiceResourceEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * 语音的版本  用于资源文件的升级
 * Created by hm on 2016/12/8.
 */

public class VoicesVersion {


    public final static String VOICES_FILEPATH = "/mnt/sdcard/magicResource/voices/";
    //资源文件的清单文件
    public final static String VOICES_CONFIG_NAME = "vconfig.txt";
    //清单文件路径

    /**
     * 思必驰
     */
    public final int AISPEECH_VERSION = 1;
    /**
     * 讯飞
     */
    public final int IFLY_VERSION = 1;

    private final String TYPE_AISPEECH = "SBC";
    private final String TYPE_IFLY = "XF";

    private Context mContext;

    public VoicesVersion(Context context) {
        this.mContext = context;
    }

    /***
     * 获取基础路径
     * /mnt/sdcard/magicResource/voices/xxx.xxx.xxx/
     *
     * @return
     */
    private String getVoicesBasePath() {
        String pkName = mContext.getPackageName();
        return VOICES_FILEPATH + pkName + "/";
    }

    /***
     * 本地和接口数据对比  是否需要下载新的文件
     * @param hash
     * @param ver
     * @param type
     * @return  true 需要 false 不需要
     */
    public boolean checkUpdate(String hash,String ver,String type){
        Map<String, String> map = FileUtil.getFileConfig(getVoicesBasePath() + VOICES_CONFIG_NAME);
        String hashCode = map.get("hashcode");
        String typeStr = map.get("type");
        String versionStr = map.get("version");
        if(hash.equals(hashCode)&&type.equals(typeStr)&&ver.equals(versionStr)){
            return false;
        }
        return true;
    }

    /***
     * 检查本地的资源 文件和现有的 语音sdk版本 是否能兼容使用
     */
    public String checkMatching() {
        Map<String, String> map = FileUtil.getFileConfig(getVoicesBasePath() + VOICES_CONFIG_NAME);
        String typeStr = map.get("type");
        String versionStr = map.get("version");
        String hashCode = map.get("hashcode");
        String path = map.get("path");
        if (TextUtils.isEmpty(typeStr) || TextUtils.isEmpty(versionStr) || TextUtils.isEmpty(path)) {
            return null;
        }
        int version=0;
        if(versionStr!=null) {
            version = Integer.valueOf(versionStr.trim());
        }
        String type = typeStr.trim();
        boolean exists = new File(path).exists();
        if (getVoicesType().equals(type) && getVoicesVersion() == version && exists) {
            return path;
        }
        return null;
    }


    public String getVoicesType() {
        if (VoiceConfig.engine_type == VoiceConfig.EngineType.TYPE_AISPEECH) {
            return TYPE_AISPEECH;
        }
        return TYPE_IFLY;
    }

    public int getVoicesVersion() {
        if (VoiceConfig.engine_type == VoiceConfig.EngineType.TYPE_AISPEECH) {
            return AISPEECH_VERSION;
        }
        return IFLY_VERSION;
    }


    public String getVoicesVersionName() {
        if (VoiceConfig.engine_type == VoiceConfig.EngineType.TYPE_AISPEECH) {
            return "aispeech";
        }
        return "ifly";
    }


    private boolean writeConfig(String type, int version, String hashcode, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("type=");
        sb.append(type);
        sb.append("\n");

        sb.append("version=");
        sb.append(version);
        sb.append("\n");

        sb.append("hashcode=");
        sb.append(hashcode);
        sb.append("\n");

        sb.append("path=");
        sb.append(getVoicesBasePath()+hashcode);
        sb.append("\n");
        return FileUtil.writeConfig(getVoicesBasePath() +  VOICES_CONFIG_NAME, sb.toString());
    }

    public void check(String filename,String hashCode) {
        String result=null;
        try {
            boolean b = FileUtil.unZipFolder(filename, getVoicesBasePath()+hashCode);
            if (b) {
                writeConfig(getVoicesType(),getVoicesVersion(),hashCode,getVoicesBasePath() + VOICES_CONFIG_NAME);
                FileUtil.deleteFile(getVoicesBasePath(),getVoicesBasePath()+hashCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        result= checkMatching();
        EventBus.getDefault().post(new VoiceResourceEvent(result,true,1));
    }


}
