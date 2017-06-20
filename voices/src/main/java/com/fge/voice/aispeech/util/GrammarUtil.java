package com.fge.voice.aispeech.util;

import android.content.Context;
import android.text.TextUtils;

import com.aispeech.AIError;
import com.aispeech.common.Util;
import com.aispeech.export.engines.AILocalGrammarEngine;
import com.aispeech.export.listeners.AILocalGrammarListener;
import com.fge.voice.VoiceConfig;
import com.fge.voice.aispeech.constants.AIConstants;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.util.FileWriteLog;

import static com.fge.voice.aispeech.AIMixRecognizer.BACK_BUILD;

/**
 * Created by hm on 2016/12/5.
 * 语法构建
 */

public class GrammarUtil {
    private AILocalGrammarEngine mGrammarEngine;
    private Context mContext;
    private VoiceReadyCallback callback;
    public GrammarUtil(Context mContext){
        this.mContext=mContext;
    }
    public void build(VoiceReadyCallback callback) {
        build(null,callback);
    }
    /**
     * @param grammarText  语法
     * @param callback
     */
    public void build(String grammarText,VoiceReadyCallback callback) {
        this.callback=callback;
        initGrammarEngine();
        // 生成ebnf语法
        GrammarHelper gh = new GrammarHelper(mContext);
        String contactString = gh.getConatcts();
        String appString = gh.getApps();
        // 如果手机通讯录没有联系人
        if (TextUtils.isEmpty(contactString)) {
            contactString = "无联系人";
        }
        FileWriteLog.writeLog("import  "+ VoiceConfig.getVoiceResourcePath());
        if(grammarText==null) {
            if (TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())) {
                grammarText = gh.importAssets(contactString, appString, AIConstants.xbnf_res);
            } else {
                grammarText = gh.importFile(contactString, appString, VoiceConfig.getVoiceResourcePath() + "/" + AIConstants.xbnf_res);
            }
        }
        if(TextUtils.isEmpty(grammarText)){
            callback.onReadyCallback(false,BACK_BUILD,"没有发现语音表达文件，请重新打开软件");
            destroy();return;
        }
        // 设置ebnf语法
        mGrammarEngine.setEbnf(grammarText);
        // 启动语法编译引擎，更新资源
        mGrammarEngine.update();
    }

    /**
     * 初始化资源编译引擎
     */
    private void initGrammarEngine() {
        if (mGrammarEngine != null) {
            mGrammarEngine.destroy();
        }
        mGrammarEngine = AILocalGrammarEngine.createInstance();
        FileWriteLog.writeLog(VoiceConfig.getVoiceResourcePath()+" 语法");
        if(!TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())) {
            mGrammarEngine.setResStoragePath(VoiceConfig.getVoiceResourcePath());//设置自定义路径，请将相关文件预先放到该目录下
        }
        mGrammarEngine.setResFileName(AIConstants.ebnfc_res);//("ebnfc.aihome.0.3.0.bin");//(/*"ebnfc.aicar.0.0.10.bin"*/"ebnfc.aihome.0.3.0.bin");

        mGrammarEngine
                .init(mContext, new AILocalGrammarListenerImpl(), AIConstants.APPKEY, AIConstants.SECRETKEY);
        mGrammarEngine.setDeviceId(Util.getIMEI(mContext));
    }

    /**
     * 语法编译引擎回调接口，用以接收相关事件
     */
    public class AILocalGrammarListenerImpl implements AILocalGrammarListener {

        @Override
        public void onError(AIError error) {
            FileWriteLog.writeLog("语法构建失败  "+error.getError());
            callback.onReadyCallback(false,BACK_BUILD,error.getError());
            destroy();
        }

        @Override
        public void onUpdateCompleted(String recordId, String path) {
            FileWriteLog.writeLog("语法构建成功   "+path);
            callback.onReadyCallback(true,BACK_BUILD,path);
            destroy();
        }

        @Override
        public void onInit(int status) {
            FileWriteLog.writeLog("语法构建初始化  "+status);
            if (status != 0) {
                callback.onReadyCallback(false,BACK_BUILD,"Grammar onInit errCode :"+status);
            }
        }
    }
    private void destroy(){
        if(mGrammarEngine!=null){
            mGrammarEngine.destroy();
            mGrammarEngine=null;
        }

    }
}
