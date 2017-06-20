package com.fge.voice.aispeech;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.aispeech.export.listeners.AIAuthListener;
import com.aispeech.speech.AIAuthEngine;
import com.fge.voice.aispeech.constants.AIConstants;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.util.FileWriteLog;

import java.io.FileNotFoundException;

/**
 * Created by hm on 2016/12/5.
 * 授权
 */

public class AiAuthInit {
    private AIAuthEngine mAIAuthEngine;

    //1 ,判断是否已经授权
    //2,开始进行授权
    //3，授权是否成功


    public synchronized void auth(Context mContext, final VoiceReadyCallback callback, String path) {
        boolean isAuthed;
        mAIAuthEngine = AIAuthEngine.getInstance(mContext);
        FileWriteLog.writeLog("wakeup path:" + path);
        if (!TextUtils.isEmpty(path)) {
            mAIAuthEngine.setResStoragePath(path);
        }
        try {
            //初始化授权
            mAIAuthEngine.init(AIConstants.APPKEY, AIConstants.SECRETKEY, "");
            isAuthed = mAIAuthEngine.isAuthed();
            FileWriteLog.writeLog("wakeup isAuthed:" + isAuthed);
            if (isAuthed) {//已经授权过
                destroy();
                if(callback!=null) {
                    callback.onReadyCallback(true, AIRecognizer.BACK_AIAUTH, "Engine is Authed");
                }
                return;
            }
            mAIAuthEngine.setOnAuthListener(new AIAuthListener() {
                @Override
                public void onAuthSuccess() {
                    destroy();
                    if(callback!=null)
                        callback.onReadyCallback(true, AIRecognizer.BACK_AIAUTH, "Auth is success");
                }

                @Override
                public void onAuthFailed(String s) {
                    destroy();
                    FileWriteLog.writeLog("授权失败:" + s);
                    if(callback!=null)
                        callback.onReadyCallback(false, AIRecognizer.BACK_AIAUTH, "Auth is failed :" + s);
                }
            });
            startThreadAuth();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if(callback!=null)
                callback.onReadyCallback(false, AIRecognizer.BACK_AIAUTH, "AIAuthEngine File NotFound");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            FileWriteLog.writeLog("RuntimeException:" + ex.getMessage());

        }
    }

    private void startThreadAuth() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                mAIAuthEngine.doAuth();
            }
        }.start();
    }

    private void destroy() {
        if (mAIAuthEngine != null)
            mAIAuthEngine.destroy();
        mAIAuthEngine = null;
    }
}
