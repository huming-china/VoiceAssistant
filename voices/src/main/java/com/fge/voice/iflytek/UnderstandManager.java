package com.fge.voice.iflytek;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.fge.voice.util.FileWriteLog;
import com.fge.voice.callback.RecognizerCallback;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by hm on 2016/5/5.
 * 语义理解管理类
 */
public class UnderstandManager {
    private final String TAG = "UnderstandManager";
    private SpeechUnderstander mSpeechUnderstander;
    private TextUnderstander mTextUnderstander;
    private RecognizerCallback mRecognizerCallback;
    private Context mContext;

    private static UnderstandManager instance;

    public static UnderstandManager getInstance(Context context){
         if(instance!=null){
             return instance;
         }else{
             instance=new UnderstandManager(context);
         }
        return instance;
     }

    public UnderstandManager(Context context) {
        this.mContext = context.getApplicationContext();
        create();
        //createTextUnderstander();
        // 设置参数
        setParam();
    }

    /**
     * 语义转语义 引擎创建
     */
    private void create() {
        FileWriteLog.writeLog("创建 SpeechUnderstander");
        mSpeechUnderstander = SpeechUnderstander.createUnderstander(
                mContext, initListener);
    }

    /**
     * 文本转语义引擎创建
     */
    private void createTextUnderstander() {
        mTextUnderstander = TextUnderstander.createTextUnderstander(
                mContext, initListener);
    }

    private InitListener initListener = new InitListener() {
        @Override
        public void onInit(int code) {
            FileWriteLog.writeLog("语义理解初始化 "+code);
            if (code != ErrorCode.SUCCESS) {

            }
        }
    };

    /**
     * 语音到语义
     */
    public int start(RecognizerCallback recognizerCallback) {
        if (mSpeechUnderstander == null) {
            create();
        }
        // 开始前检查状态
        if (mSpeechUnderstander.isUnderstanding()) {
            mSpeechUnderstander.cancel();
        }
        this.mRecognizerCallback = recognizerCallback;
        FileWriteLog.writeLog("开始 startUnderstanding");
        setParam();
        int ret = mSpeechUnderstander.startUnderstanding(mSpeechUnderstanderListener);
       // byte [] bb=File2byte("mnt/sdcard/msc/iat.wav");
        //FileWriteLog.writeLog("文件读取完毕 len="+bb.length);
        //mSpeechUnderstander.writeAudio(bb,0,bb.length);
        if (ret != ErrorCode.SUCCESS&&mRecognizerCallback!=null) {
            FileWriteLog.writeLog("错误  "+ret);
            mRecognizerCallback.onError(-1990, "startUnderstanding error code " + ret);
        }
        return ret;

    }
    public static byte[] File2byte(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 文本到语义
     **/
    public int startTextUnderstand(String text, final RecognizerCallback underTextCallBack) {
        if (mTextUnderstander == null) {
            createTextUnderstander();
        }
        this.mRecognizerCallback = underTextCallBack;
        // 开始前检查状态
        if (mTextUnderstander.isUnderstanding()) {
            mTextUnderstander.cancel();
        }
        int ret = mTextUnderstander.understandText(text, new TextUnderstanderListener() {
            @Override
            public void onResult(UnderstanderResult understanderResult) {
                FileWriteLog.writeLog("语义结果:"+understanderResult.getResultString());
                if (mRecognizerCallback != null) {

                }
            }

            @Override
            public void onError(SpeechError speechError) {
                FileWriteLog.writeLog("语义错误:"+speechError.getErrorCode(), speechError.getMessage());

                underTextCallBack.onError(speechError.getErrorCode(), speechError.getMessage());
            }
        });
        return ret;
    }

    /**
     * 语义理解回调。
     */
    private SpeechUnderstanderListener mSpeechUnderstanderListener = new SpeechUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            FileWriteLog.writeLog("语义结果:"+result.getResultString());
            // mCheckStand.checkStand(result);
            if (mRecognizerCallback != null) {

            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            if (mRecognizerCallback != null) {
                mRecognizerCallback.onVolumeChanged(volume);
            }
        }

        @Override
        public void onEndOfSpeech() {
            FileWriteLog.writeLog("============= onEndOfSpeech");
            if (mRecognizerCallback != null) {
                mRecognizerCallback.onEndOfSpeech();
            }
            FileWriteLog.writeLog("语义onEndOfSpeech");

            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
        }

        @Override
        public void onBeginOfSpeech() {
            FileWriteLog.writeLog("语义 onBeginOfSpeech:");

            if (mRecognizerCallback != null) {
                mRecognizerCallback.onBeginOfSpeech();
            }
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
        }

        @Override
        public void onError(SpeechError error) {
            FileWriteLog.writeLog("语义错误:"+error.getErrorCode()+":"+ error.getMessage());
            if (mRecognizerCallback != null) {
                mRecognizerCallback.onError(error.getErrorCode(), error.getMessage());
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        }
    };


    /**
     * 参数设置
     */
    public void setParam() {
        if (mSpeechUnderstander == null) {
            FileWriteLog.writeLog("mSpeechUnderstander is null ");
            return;
        }
        FileWriteLog.writeLog("设置参数");
        String lang = "zh_cn";
        if (lang.equals("en_us")) {
            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mSpeechUnderstander.setParameter(SpeechConstant.ACCENT, lang);
        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mSpeechUnderstander.setParameter(SpeechConstant.VAD_BOS,
                "10000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mSpeechUnderstander.setParameter(SpeechConstant.VAD_EOS, "5000");
        // 设置标点符号，默认：1（有标点）
        mSpeechUnderstander.setParameter(SpeechConstant.ASR_PTT, "0");


        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeechUnderstander.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
        // mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
        mSpeechUnderstander.setParameter(SpeechConstant.ASR_SOURCE_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
        mSpeechUnderstander.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeechUnderstander.setParameter(SpeechConstant.ASR_AUDIO_PATH,
                Environment.getExternalStorageDirectory() + "/msc/sud.wav");
        mSpeechUnderstander.setParameter(SpeechConstant.DOMAIN, "fariat");
        FileWriteLog.writeLog("设置参数完毕=" + mSpeechUnderstander.toString());

    }

    public void stopUnderstanding() {
        if (mSpeechUnderstander != null) {
            mSpeechUnderstander.stopUnderstanding();
        }
    }

    public void cancel() {
        if (mSpeechUnderstander != null) {
            mSpeechUnderstander.cancel();
        }
    }

    public void destroy() {
        // 退出时释放连接
        if (mSpeechUnderstander != null) {
            mSpeechUnderstander.cancel();
            mSpeechUnderstander.destroy();
        }
//        if(mTextUnderstander!=null) {
//            mTextUnderstander.cancel();
//            mTextUnderstander.destroy();
//        }
    }

    public boolean isUnderstanding() {
        return mSpeechUnderstander.isUnderstanding();

    }
}