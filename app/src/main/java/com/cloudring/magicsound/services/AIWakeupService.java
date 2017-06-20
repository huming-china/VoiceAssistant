package com.cloudring.magicsound.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.aispeech.AIError;
import com.cloudring.magicsound.event.WakeupEvent;
import com.fge.voice.aispeech.AIWakeupCallBack;
import com.fge.voice.aispeech.AIWakeupEngine;
import com.fge.voice.util.FileWriteLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.lang.reflect.Method;


/**
 * 语音唤醒Service（思必驰）
 * Created by hm on 2016/7/18.
 */
public class AIWakeupService extends VoiceService {
    //开 --> 关
    private final String ACTION_WAKEUP_CLOSE="com.cloudring.magicsound.wakeupservice.action.CLOSE";
    //关 --> 开
    private final String ACTION_WAKEUP_OPEN="com.cloudring.magicsound.wakeupservice.action.OPEN";


    private AIWakeupEngine mAIWakeupEngine;

    private Handler mHandler=new Handler();
    /**
     * 初始化唤醒
     **/
    @Override
    public void initWakeup() {
        registerLocalReceiver();
        FileWriteLog.writeLog("AI initWakeup");

        FileWriteLog.writeLog("开关状态  isOpen=" + isOpen);
        if (!isOpen) {
            return;
        }
        setProperty("1");
        if (mAIWakeupEngine == null) {
            mAIWakeupEngine=new AIWakeupEngine(this);
        }
        mAIWakeupEngine.init(mAIWakeupCallBack);
    }
    //注册本地广播 接收设置“自动唤醒语音” 的按钮开关变化
    private void registerLocalReceiver(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_WAKEUP_OPEN);
        intentFilter.addAction(ACTION_WAKEUP_CLOSE);
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver,intentFilter);
    }

    /**
     * 接收广播 来自系统设置自动语音唤醒的按钮
     */
    private BroadcastReceiver localReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            FileWriteLog.writeLog("接收到了 设置里按钮开关"+ action);
            if(ACTION_WAKEUP_OPEN.equals(action)){//关-->开
                isOpen = true;
                setProperty("1");
                if (mAIWakeupEngine == null) {
                    FileWriteLog.writeLog("new AIWakeupEngine()");
                    mAIWakeupEngine = new AIWakeupEngine(AIWakeupService.this);
                    mAIWakeupEngine.init(mAIWakeupCallBack);
                } else {
                    startWakeupDelayed();
                }
            }else if(ACTION_WAKEUP_CLOSE.equals(action)){//开-->关
                isOpen = false;
                setProperty("0");
                if (mAIWakeupEngine != null) {
                    stopWakeup();
                }
            }
        }
    };
    /**
     * 开始启动语音唤醒
     * 由于降噪切换会出现sdk拿不到麦的问题  延迟启动能降低拿不到麦的概率
     **/

    public void startWakeupDelayed() {
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable,1600);

    }
    /**
     * 开始启动语音唤醒
     * */
    @Override
    public void startWakeup() {
        mHandler.removeCallbacks(runnable);
        mAIWakeupEngine.start();
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            FileWriteLog.writeLog("启动唤醒");
            mAIWakeupEngine.start();
        }
    };

    /**
     * 关闭唤醒引擎 暂停录音 释放麦克风
     **/
    @Override
    public void stopWakeup() {
        mHandler.removeCallbacks(runnable);
        FileWriteLog.writeLog("stop");
        mAIWakeupEngine.onStop();
    }

    AIWakeupCallBack mAIWakeupCallBack = new AIWakeupCallBack() {
        @Override
        public void onError(AIError error) {
            FileWriteLog.writeLog("唤醒错误   " + error.getError());
            super.onError(error);
        }

        @Override
        public void onInit(int status) {
            FileWriteLog.writeLog("唤醒 onInit   " + status);
            super.onInit(status);
            if (status == -100) {//授权失败
                stopSelf();
            } else if (status == 0 && !isSystemRecording) {
                startWakeup();//mAIWakeupEngine.start();
            }
        }

        @Override
        public void onWakeup(String recordId, double confidence, String wakeupWord) {
            super.onWakeup(recordId, confidence, wakeupWord);
            //mAIWakeupEngine.onStop();
            stopWakeup();
            sendMessage(confidence + " ||||| " + wakeupWord);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        FileWriteLog.writeLog("唤醒Service 销毁");
        mHandler.removeCallbacks(runnable);
        setProperty("0");
        if (mAIWakeupEngine != null) {
            mAIWakeupEngine.onDestroy();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
    }

    @Subscribe
    public void onEventMainThread(WakeupEvent event) {
        if (event.status == WakeupEvent.START_WAKEUP) {
            FileWriteLog.writeLog("接收唤醒 START_WAKEUP");
            if (mAIWakeupEngine != null && isOpen) {
                setProperty("1");
                startWakeup();
                //mAIWakeupEngine.start();
            }
        } else if (event.status == WakeupEvent.STOP_WAKEUP) {
            FileWriteLog.writeLog("接收唤醒 STOP_WAKEUP");
            setProperty("1");
            if (mAIWakeupEngine != null) {
                stopWakeup();
                //mAIWakeupEngine.onStop();
            }
            //EventBus.getDefault().post(new SpeechEvent());
        }
    }

    @Subscribe
    public void onEventMainThread(Integer event) {
        if (event == 9999) {//接收消息停止麦克风占用 释放给其他使用
            FileWriteLog.writeLog("接收消息 9999");
            if (mAIWakeupEngine != null) {
                FileWriteLog.writeLog("设置值 0");
                setProperty("0");
                if (mAIWakeupEngine.isStarting())
                    mAIWakeupEngine.onStop();
//                if(isRecording)
//                    stop();
            }
        } else if (event == 9998) {//其他使用完毕 恢复唤醒监听
            FileWriteLog.writeLog("接收消息 9998");
            if (isSystemRecording) {
                return;
            }
            if (mAIWakeupEngine != null) {
                FileWriteLog.writeLog("设置值 1");
                setProperty("1");
                if (isOpen)
                    startWakeupDelayed();
                //mAIWakeupEngine.start();
            }

        } else if (event == 9000) {//测试使用
            stopWakeup();
        }
    }

    public String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, "unknown"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    /****
     * 反射 修改属性 声音降噪
     * @param key
     * @param value 0 降噪
     */
    private void setProperty(String key, String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value);
        } catch (Exception e) {
            FileWriteLog.writeLog("设置Exception" + e.getMessage());
            e.printStackTrace();
        }
        FileWriteLog.writeLog("设置Property后   persist.audio.capmode=" + getProperty("persist.audio.capmode", "unkonw"));
    }
    private void setProperty(String value){
        setProperty("persist.audio.capmode",value);
    }
}
