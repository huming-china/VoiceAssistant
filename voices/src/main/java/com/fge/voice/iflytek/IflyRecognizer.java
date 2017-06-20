package com.fge.voice.iflytek;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import com.fge.voice.VConfigManager;
import com.fge.voice.VoiceConfig;
import com.fge.voice.base.BaseRecognizer;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.iflytek.options.IflyOptions;
import com.fge.voice.util.FileWriteLog;
import com.fge.voice.callback.RecognizerCallback;
import com.fge.voice.iflytek.model.Userword;
import com.fge.voice.iflytek.model.Userwords;
import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.util.ResourceUtil;

import java.util.List;

/**
 * 语音识别（讯飞）
 * Created by hm on 16/5/27
 */
public class IflyRecognizer extends BaseRecognizer implements InitListener {
    private RecognizerCallback callback;//数据回调
    private SpeechRecognizer mSpeechRecognizer;//语音听写对象
    private Context mContext;
    private String mLocalGrammar;//本地语法
    private String grmPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/msc/grm";
    private VoiceReadyCallback mVoiceReadyCallback;
    private IflyOptions options;
    private String mGrammarId;//语法构建id
    public IflyRecognizer(Context context, VoiceReadyCallback mVoiceReadyCallback) {
        this.mContext = context.getApplicationContext();
        options = VConfigManager.getIflyOptions();
        this.mVoiceReadyCallback = mVoiceReadyCallback;
        init();
        UnderstandManager.getInstance(mContext);
    }

    /***
     *
     */
    public void options(IflyOptions options) {
        if (options != null)
            this.options = options;
    }

    @Override
    public void init() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(mContext, this);
    }

    /**
     * 构建离线识别的语法
     */
    private void buildLocalGrammar() {
        mLocalGrammar = FucUtil.readFile(mContext, "control.bnf", "utf-8");
        String mContent = new String(mLocalGrammar);
        mSpeechRecognizer.setParameter(SpeechConstant.PARAMS, null);
        // 设置文本编码格式
        mSpeechRecognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        // 设置引擎类型
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        // 设置语法构建路径
        mSpeechRecognizer.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath);
        // 设置资源路径
        mSpeechRecognizer.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
        int ret = mSpeechRecognizer.buildGrammar("bnf", mContent, grammarListener);
        if (ret != ErrorCode.SUCCESS) {
            FileWriteLog.writeLog("语法错误  " + ret);
        }
        mVoiceReadyCallback.onReadyCallback(true, 0, "IflyRecognizer is ready success");
    }

    /**
     * 构建在线识别的语法
     */
    private void buildCloudGrammar() {
        FileWriteLog.writeLog("mGrammarId  null");
        mGrammarId = null;
        StringBuilder sb = new StringBuilder("#ABNF 1.0 UTF-8;");
        sb.append("\n");
        sb.append("language zh-CN;");
        sb.append("\n");
        sb.append(" mode voice;");
        sb.append("\n");
        sb.append("root $main;");
        sb.append("\n");
        sb.append("$shutup = 安静 | 闭嘴| 退下|[把]嘴闭上 ;");
        sb.append("\n");
        sb.append(" $main = [小西]$shutup;");
        sb.append("\n");
        String mCloudGrammar = sb.toString();
        //2.构建语法文件
        mSpeechRecognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        int ret = mSpeechRecognizer.buildGrammar("abnf", mCloudGrammar, grammarListener);
        if (ret != ErrorCode.SUCCESS) {
            FileWriteLog.writeLog("CLOUD 语法构建失败,错误码：" + ret);
        } else {
            FileWriteLog.writeLog("CLOUD 语法构建成功");
        }
        mVoiceReadyCallback.onReadyCallback(true, 0, "IflyRecognizer is ready success");
    }

    /**
     * 开始录音
     */
    @Override
    public void startRecording(RecognizerCallback callback) {
        if (mSpeechRecognizer != null) {
            setMixParam();
            this.callback = callback;
            // 设置听写引擎参数
            int ret = mSpeechRecognizer.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                FileWriteLog.writeLog("mSpeechRecognizer启动 发生错误" + ret);
            }
        }
    }
    public void startAsr(RecognizerCallback callback) {
        if (mSpeechRecognizer != null) {
            FileWriteLog.writeLog("状态 :"+mSpeechRecognizer.isListening());
            setAsrParam();
            this.callback = callback;
            // 设置听写引擎参数
            int ret = mSpeechRecognizer.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                FileWriteLog.writeLog("mSpeechRecognizer启动 发生错误" + ret);
            }
        }
    }

    /***
     * 当在线命令词没有匹配时 发送语义
     */
    public void startAudioAsr( ) {
        if (mSpeechRecognizer != null) {
            FileWriteLog.writeLog("状态 :"+mSpeechRecognizer.isListening());
            setAsrParam();
            mSpeechRecognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
            // mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
            mSpeechRecognizer.setParameter(SpeechConstant.ASR_SOURCE_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
            // 设置听写引擎参数
            int ret = mSpeechRecognizer.startListening(new RecognizerListener() {
                /** 实时返回音频 音量数据 */
                @Override
                public void onVolumeChanged(int i, byte[] bytes) {

                }

                /** 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入 */
                @Override
                public void onBeginOfSpeech() {

                }

                /** 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入*/
                @Override
                public void onEndOfSpeech() {
                    FileWriteLog.writeLog("2次onEndOfSpeech");
                    if (callback != null)
                        callback.onEndOfSpeech();
                }

                /** 识别回调结果 */
                @Override
                public void onResult(RecognizerResult recognizerResult, boolean b) {
                    FileWriteLog.writeLog("识别结果:="+recognizerResult.getResultString());
                    if (callback != null)
                        callback.onResult(b,callback.parseResult(recognizerResult.getResultString()));
                }

                /** 识别回调错误 */
                @Override
                public void onError(SpeechError speechError) {
                    FileWriteLog.writeLog("识别错误:="+speechError.getErrorCode()+" :"+speechError.getErrorDescription());
                    if (callback != null)
                        callback.onError(speechError.getErrorCode(), speechError.getErrorDescription());
                }

                /** 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因若使用本地能力，会话id为null*/
                @Override
                public void onEvent(int i, int i1, int i2, Bundle bundle) {
                }
            });
            if (ret != ErrorCode.SUCCESS) {
                FileWriteLog.writeLog("mSpeechRecognizer启动 发生错误" + ret);
            }
        }
    }

    private void startCloudGrammar(){
        FileWriteLog.writeLog("startCloudGrammar");
        //if(TextUtils.isEmpty(mGrammarId)){
          //  Toast.makeText(mContext,"语法构建没有完成",Toast.LENGTH_LONG).show();
          //  return;
       // }
        //3.开始识别,设置引擎类型为云端
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
        //设置grammarId
        mSpeechRecognizer.setParameter(SpeechConstant.CLOUD_GRAMMAR, mGrammarId);
        int ret = mSpeechRecognizer.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            FileWriteLog.writeLog("CLOUD 识别失败,错误码" + ret);
        }
    }


    @Override
    public boolean isListening() {
        if (mSpeechRecognizer != null) {
            return mSpeechRecognizer.isListening();
        }
        return false;
    }

    /**
     * 取消识别(放弃本次的录音和识别结果)
     */
    @Override
    public void cancel() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.cancel();
        }
    }

    /***
     * 停止录音 发送数据请求识别
     */
    @Override
    public void stopRecording() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.stopListening();
        }
    }

    @Override
    public void destory() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.destroy();
        }

        mSpeechRecognizer = null;
    }

    /***
     * 许飞引擎初始化的结果回调
     *
     * @param code
     */
    @Override
    public void onInit(int code) {
        if (code == ErrorCode.SUCCESS) {
            buildLocalGrammar();
            //buildCloudGrammar();
        } else {
            mVoiceReadyCallback.onReadyCallback(false, 0, "IflyRecognizer onInit fail " + code);
            FileWriteLog.writeLog("讯飞引擎初始化错误 code=" + code);
            destory();
        }
    }

    public void writeAudio(byte[] bytes, RecognizerCallback callback) {
        mSpeechRecognizer.writeAudio(bytes, 0, bytes.length);
    }

    /***
     * 上传用户词表
     *
     * @param list 用户词表集合
     * @return
     */
    public int updateLexicon(List<Userword> list) {
        Userwords userwords = new Userwords();
        userwords.setUserword(list);
        Gson gson = new Gson();
        String contents = gson.toJson(userwords);
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mSpeechRecognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        int ret = mSpeechRecognizer.updateLexicon("userword", contents, mLexiconListener);
        return ret;
    }

    /**
     * 上传联系人/词表监听器。
     */
    private LexiconListener mLexiconListener = new LexiconListener() {

        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error == null) {
                FileWriteLog.writeLog("上传用户词表成功");
            } else {
                FileWriteLog.writeLog("上传用户词表失败:" + error.getErrorCode() + " : " + error.getErrorDescription());
            }
        }
    };

    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        /** 实时返回音频 音量数据 */
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            if (callback != null)
                callback.onVolumeChanged(i);
        }

        /** 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入 */
        @Override
        public void onBeginOfSpeech() {
            if (callback != null)
                callback.onBeginOfSpeech();
        }

        /** 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入*/
        @Override
        public void onEndOfSpeech() {
            FileWriteLog.writeLog("onEndOfSpeech");
            if (callback != null)
                callback.onEndOfSpeech();
        }

        /** 识别回调结果 */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            FileWriteLog.writeLog("识别结果:="+recognizerResult.getResultString());
            //startAudioAsr();
            if (callback != null)
                callback.onResult(b,callback.parseResult(recognizerResult.getResultString()));
            //mParseAgency.parseJson(callback, recognizerResult.getResultString(), VoiceManager.EngineType.TYPE_IFLYTEK);
            //callback.onResult(recognizerResult,b);
        }

        /** 识别回调错误 */
        @Override
        public void onError(SpeechError speechError) {
            FileWriteLog.writeLog("识别错误:="+speechError.getErrorCode()+" :"+speechError.getErrorDescription());
            if (callback != null)
                callback.onError(speechError.getErrorCode(), speechError.getErrorDescription());
        }

        /** 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因若使用本地能力，会话id为null*/
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
        }
    };

    //构建语法监听器
    private GrammarListener grammarListener = new GrammarListener() {
        @Override
        public void onBuildFinish(String grammarId, SpeechError error) {
            if(error == null){
//                if(!TextUtils.isEmpty(grammarId)){
//                    FileWriteLog.writeLog("mGrammarId   ====="+grammarId);
//                    mGrammarId=grammarId;
                    FileWriteLog.writeLog("语法构建成功");
//                }else{
//                    FileWriteLog.writeLog("语法构建失败:" + error.getErrorCode());
//                }
            }else{
                FileWriteLog.writeLog("语法构建失败:" + error.getErrorCode());

            }

        };
    };

    //获取识别资源路径
    private String getResourcePath() {
        StringBuffer tempBuffer = new StringBuffer();
        //识别通用资源
        if (TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())) {
            tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "asr/common.jet"));
        } else {
            tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.path,VoiceConfig.getVoiceResourcePath() + "/asr/common.jet"));
        }
        return tempBuffer.toString();
    }

    private void setParam() {

    }
    //语义
    private void setAsrParam(){
        setBaseParam();
        mSpeechRecognizer.setParameter("asr_sch", "1");
        mSpeechRecognizer.setParameter("nlp_version", "2.0");
    }
    //在线语法
    private void setCloudGrammarParam(){
        setBaseParam();
        mSpeechRecognizer.setParameter("asr_sch", "1");
        mSpeechRecognizer.setParameter("nlp_version", "2.0");
    }

    private void setBaseParam(){
        mSpeechRecognizer.setParameter(SpeechConstant.PARAMS, null);
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeechRecognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_PTT, "0");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_BOS, options.vad_bos);
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_EOS, options.vad_eos);
    }

    private void setMixParam() {
        setBaseParam();
        /***************************以下是开启混合模式的设置********************************/
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, "mix");
        mSpeechRecognizer.setParameter("asr_sch", "1");//是否进行语义识别
        mSpeechRecognizer.setParameter(SpeechConstant.NLP_VERSION, "2.0");//通过此参数，设置开放语义协议版本号。
        mSpeechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");//返回文本结果类型
        /*****************以下是控制本地结果和在线结果的优先级的*****************************/
        mSpeechRecognizer.setParameter("mixed_type", "realtime");//混合模式的类型
        mSpeechRecognizer.setParameter(SpeechConstant.MIXED_TIMEOUT, options.timeout);//在线结果超时控制. 0-30000, def:2000
        mSpeechRecognizer.setParameter(SpeechConstant.MIXED_THRESHOLD, options.mixed_threshold);//离线结果混合门限. 0-100, def:60
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_THRESHOLD, options.asr_threshold);//离线结果识别门限0-100, default:0
        mSpeechRecognizer.setParameter("local_prior", "1");
        /*****************具体的解释可以查阅一下《混合识别策略文档》**************************/
        // 设置本地识别资源
        mSpeechRecognizer.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
        // 设置语法构建路径
        mSpeechRecognizer.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath);
        // 设置返回结果格式
        mSpeechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置本地识别使用语法id
        mSpeechRecognizer.setParameter(SpeechConstant.LOCAL_GRAMMAR, "vcontrol");
        //远场拾音
        mSpeechRecognizer.setParameter(SpeechConstant.DOMAIN, "fariat");
    }
}
