package com.cloudring.magicsound.services;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.cloudring.magicsound.event.WakeupEvent;
import com.fge.voice.RecognizeManager;
import com.fge.voice.VConfigManager;
import com.fge.voice.VoiceConfig;
import com.fge.voice.util.FileWriteLog;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.util.ResourceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 讯飞语音唤醒
 * Created by HM on 2017/4/14.
 */

public class IflyWakeupService extends VoiceService {
    private VoiceWakeuper mIvw;//语音唤醒引擎

    /***
     * 初始化唤醒引擎
     */
    public void initWakeup() {
        mIvw = VoiceWakeuper.createWakeuper(this, null);
        startWakeup();
    }

    /**
     * 启动引擎 开始录音
     */
    public void startWakeup() {
        if(!isOpen){stopSelf();return;}
        FileWriteLog.writeLog("mIvw.isListening() ==" + mIvw.isListening());
        if (mIvw.isListening()) {
            return;
        }
        RecognizeManager.getInstance(this).cancel();
        mIvw = VoiceWakeuper.getWakeuper();
        // 清空参数
        mIvw.setParameter(SpeechConstant.PARAMS, null);
        // 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
        mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:" + VConfigManager.getConfigIntValue(this, "IVW_THRESHOLD", 8));
        // 设置唤醒模式
        mIvw.setParameter(SpeechConstant.IVW_SST, "wakeup");
        // 设置持续进行唤醒
        mIvw.setParameter(SpeechConstant.KEEP_ALIVE, "0");
        // 设置闭环优化网络模式
        mIvw.setParameter(SpeechConstant.IVW_NET_MODE, "0");
        // 设置唤醒资源路径
        mIvw.setParameter(SpeechConstant.IVW_RES_PATH, getResource());
        // 启动唤醒
        FileWriteLog.writeLog("Service 启动唤醒");
        mIvw.startListening(mWakeuperListener);
    }

    /**
     * 关闭唤醒引擎 暂停录音 释放麦克风
     */
    public void stopWakeup() {
        if (mIvw != null) {
            mIvw.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileWriteLog.writeLog("唤醒Service 销毁");
        if (mIvw != null) {
            mIvw.destroy();
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(WakeupEvent event) {
        if (event.status == WakeupEvent.START_WAKEUP) {
            FileWriteLog.writeLog("接收唤醒 START_WAKEUP");
            startWakeup();
        } else if (event.status == WakeupEvent.STOP_WAKEUP) {
            FileWriteLog.writeLog("接收唤醒 STOP_WAKEUP");
            if (mIvw != null) {
                mIvw.cancel();
            }
        }
    }

    @Subscribe
    public void onEventMainThread(Integer event) {
        if (event == 9999) {//接收消息停止麦克风占用 释放给其他使用
            FileWriteLog.writeLog("接收消息 9999");
            if (mIvw != null && mIvw.isListening()) {
                mIvw.cancel();
            }
        } else if (event == 9998) {//其他使用完毕 恢复唤醒监听
            FileWriteLog.writeLog("接收消息 9998");
            if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
                startWakeup();
            }
        }
    }

    //获取语音唤醒的资源文件
    private String getResource() {
        if (TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())) {
            return ResourceUtil.generateResourcePath(this,
                    ResourceUtil.RESOURCE_TYPE.assets, "ivw/" + VoiceConfig.appid + ".jet");
        } else {
            return ResourceUtil.generateResourcePath(this,
                    ResourceUtil.RESOURCE_TYPE.path, VoiceConfig.getVoiceResourcePath() + "/ivw/" + VoiceConfig.appid + ".jet");
        }

    }

    //听写监听器
    private WakeuperListener mWakeuperListener = new WakeuperListener() {
        public void onResult(WakeuperResult result) {
            FileWriteLog.writeLog("mWakeuperListener");
            sendMessage(result.getResultString());
        }

        public void onError(SpeechError error) {
            FileWriteLog.writeLog("唤醒错误" + error.getErrorCode() + "   " + error.getMessage());
            Toast.makeText(getApplicationContext(), error.getErrorCode() + ":" + error.getMessage(), Toast.LENGTH_LONG).show();
            stopSelf();
        }

        public void onBeginOfSpeech() {
            FileWriteLog.writeLog("唤醒onBeginOfSpeech");

        }

        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            if (com.iflytek.cloud.SpeechEvent.EVENT_IVW_RESULT == eventType) {

            }
        }

        @Override
        public void onVolumeChanged(int i) {
            FileWriteLog.writeLog("onVolumeChanged" + i);
        }
    };

}
