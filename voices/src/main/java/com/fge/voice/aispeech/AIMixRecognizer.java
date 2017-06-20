package com.fge.voice.aispeech;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.aispeech.AIError;
import com.aispeech.AIResult;
import com.aispeech.common.Util;
import com.aispeech.export.engines.AILocalGrammarEngine;
import com.aispeech.export.engines.AIMixASREngine;
import com.aispeech.export.listeners.AIASRListener;
import com.fge.voice.R;
import com.fge.voice.VConfigManager;
import com.fge.voice.callback.RecognizerCallback;
import com.fge.voice.VoiceConfig;
import com.fge.voice.aispeech.constants.AIConstants;
import com.fge.voice.aispeech.options.AIOptions;
import com.fge.voice.aispeech.util.GrammarUtil;
import com.fge.voice.base.BaseRecognizer;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.util.FileWriteLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Created by hm on 16/5/27
 * 思必驰的引擎准备工作 流程如下
 * 1、授权，判断以前是否授权   是：构建语法  否：进行授权  授权成功：构建语法，失败：回调
 * 2、构建语法
 * 3、初始化引擎
 * 3个步骤必须都要成功 否则回调错误并且语音无法使用
 */
public class AIMixRecognizer extends BaseRecognizer implements VoiceReadyCallback{
    public static final int BACK_AIAUTH = 1;
    public static final int BACK_BUILD = 2;
    public static final int BACK_ENGINE = 3;

    private RecognizerCallback callback;//数据回调
    private Context mContext;
    private AIMixASREngine mAsrEngine;
    private VoiceReadyCallback mVoiceReadyCallback;
    private AIOptions options;

    public AIMixRecognizer(Context context, VoiceReadyCallback mVoiceReadyCallback) {
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
        if (mAsrEngine != null) {
            FileWriteLog.writeLog("AsrEngine.start();");
            mAsrEngine.start();
        } else {
            FileWriteLog.writeLog("请先生成资源");
            callback.onError(-1, "语音语法资源文件未生成,请重新打开软件");
        }
    }
    @Override
    public boolean isListening() {
        if (mAsrEngine != null) {
            return mAsrEngine.isBusy();
        }
        return false;
    }
    @Override
    public void cancel() {
        if (mAsrEngine != null) {
            mAsrEngine.cancel();
        }
        callback = null;
    }

    @Override
    public void stopRecording() {
        if (mAsrEngine != null) {
            mAsrEngine.stopRecording();
        }
    }

    @Override
    public void destory() {
        if (mAsrEngine != null) {
            mAsrEngine.destroy();
            mAsrEngine = null;
        }
        mVoiceReadyCallback=null;
    }

    @Override
    public void init() {
        if (mAsrEngine != null) {
            mAsrEngine.destroy();
        }
        mAsrEngine = AIMixASREngine.createInstance();
        if(!TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())) {
            FileWriteLog.writeLog("mAsrEngine  voiceResourcePath="+VoiceConfig.getVoiceResourcePath());
            mAsrEngine.setResStoragePath(VoiceConfig.getVoiceResourcePath());
        }
        mAsrEngine.setResBin(AIConstants.ebnfr_res);
        mAsrEngine.setNetBin(AILocalGrammarEngine.OUTPUT_NAME, true);
        mAsrEngine.setVadResource(AIConstants.vad_res);
//        mAsrEngine.setServer("ws://s-test.api.aispeech.com:10000"); //灰度环境

        mAsrEngine.setServer(options.server);
        mAsrEngine.setUseXbnfRec(true);
        mAsrEngine.setRes("aihome");
        mAsrEngine.setUsePinyin(false);
        mAsrEngine.setUseForceout(false);
        mAsrEngine.setAthThreshold(options.athThreshold);
        mAsrEngine.setIsRelyOnLocalConf(true);
        mAsrEngine.setIsPreferCloud(false);
        mAsrEngine.setLocalBetterDomains(new String[]{"localmusic", "localvolume", "phone", "adjust", "app", "updown", "control", "near", "groupon"});
        mAsrEngine.setWaitCloudTimeout(options.waitCloudTimeout);
        mAsrEngine.setPauseTime(options.pauseTime);
        mAsrEngine.setUseConf(true);
        mAsrEngine.setNoSpeechTimeOut(options.noSpeechTimeOut);//语音停止后端点时间
        mAsrEngine.setDeviceId(Util.getIMEI(mContext).equals("") ? Util.generateDeviceId16(mContext) : Util.getIMEI(mContext));
        mAsrEngine.setCloudVadEnable(false);
        if (mContext.getExternalCacheDir() != null) {
            mAsrEngine.setUploadEnable(true);//设置上传音频使能
            mAsrEngine.setTmpDir(mContext.getExternalCacheDir().getAbsolutePath());//设置上传的音频保存在本地的目录
        }
        if(new File("mnt/sdcard/VT").exists()) {
            mAsrEngine.setEchoWavePath("mnt/sdcard/AsrEngineEchoWave");
            mAsrEngine.setSaveWavePath("mnt/sdcard/AsrEngineSaveWave");
        }
        mAsrEngine.init(mContext, new AIASRListenerImpl(), AIConstants.APPKEY, AIConstants.SECRETKEY);
        mAsrEngine.setUseCloud(true);//该方法必须在init之后
        SharedPreferences mSharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String city = mSharedPref.getString("city",mContext.getString(R.string.def_city));
        mAsrEngine.setLbsCity(city);
        mAsrEngine.setCoreType("cn.sds"); //cn.sds为云端对话服务，cn.dlg.ita为云端语义服务，默认为云端语义,想要访问对话服务时，才设置为cn.sds，否则不用设置

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
            if (callback != null)
                callback.onVolumeChanged(rmsdB);
        }

        @Override
        public void onError(AIError error) {
            FileWriteLog.writeLog("识别发生错误 :" + error.getErrId());//70904  错误码 不说话
            if (callback != null)
                callback.onError(error.getErrId(), error.getMessage());
        }

        @Override
        public void onResults(AIResult results) {
            String result = printResult(results);
            FileWriteLog.writeLog("result  ==  " + result);
            JSONObject jo = null;
            try {
                jo = new JSONObject(results.getResultObject().toString());
                JSONObject rejo = jo.getJSONObject("result");
                JSONObject sdsjo = rejo.getJSONObject("sds");
                String domain = sdsjo.optString("domain");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FileWriteLog.writeLog(results.getResultObject().toString());
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
        AiAuthInit aiAuthInit = new AiAuthInit();
        aiAuthInit.auth(mContext, this, VoiceConfig.getVoiceResourcePath());
    }

    @Override
    public void onReadyCallback(boolean isSuceess, int type, String errMsg) {
        if (!isSuceess && mVoiceReadyCallback != null) {
            mVoiceReadyCallback.onReadyCallback(false, BACK_ENGINE, errMsg);
        } else if (type == BACK_AIAUTH&&isSuceess) {
           if(Looper.getMainLooper().getThread() == Thread.currentThread()){
              new Thread(){
                  @Override
                  public void run() {
                      startResGen();
                  }
              }.start();
           }else {
               startResGen();
           }

        } else if (type == BACK_BUILD) {
            // 检测是否已生成并存在识别资源，若已存在，则立即初始化本地识别引擎，否则等待编译生成资源文件后加载本地识别引擎
            String grammarPath;
            if(TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())){
                grammarPath= Util.getResourceDir(mContext) + File.separator + AILocalGrammarEngine.OUTPUT_NAME;
            }else {
                grammarPath= VoiceConfig.getVoiceResourcePath() + File.separator + AILocalGrammarEngine.OUTPUT_NAME;
            }
            if (new File(grammarPath)
                    .exists()) {
                FileWriteLog.writeLog("初始化引擎");
                init();
            } else if (mVoiceReadyCallback != null) {
                FileWriteLog.writeLog("错误:Resource file not exists,AsrEngine could not be initialized");
                mVoiceReadyCallback.onReadyCallback(false,BACK_ENGINE, " Resource file not exists,AsrEngine could not be initialized");
            }
        }else if(mVoiceReadyCallback!=null){
            mVoiceReadyCallback.onReadyCallback(false,BACK_ENGINE,"发生错误，请重新打开"+errMsg);
        }
    }
    /**
     * 开始生成识别资源
     */
    public void startResGen() {
        GrammarUtil grammarUtil = new GrammarUtil(mContext);
        grammarUtil.build(this);
    }
    private String printResult(AIResult results) {
        String text = "";
        try {
            text = new JSONObject(results.getResultObject().toString()).toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return text;
    }
}
