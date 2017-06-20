package com.cloudring.magicsound.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cloudring.magicsound.services.AIWakeupService;
import com.fge.voice.util.FileWriteLog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

/**
 * 接收系统录音的广播
 * Created by hm on 2017/3/29.
 */

public class SystemRecordingReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        FileWriteLog.writeLog("收到系统录音机发来的广播");
        if("com.magic.voice.recorder.start".equals(intent.getAction())){
            AIWakeupService.isSystemRecording=false;
            EventBus.getDefault().post(9998);
        }else if("com.magic.voice.recorder.stop".equals(intent.getAction())){
            //系统录音机发来广播要求 唤醒停止
            setProperty("persist.audio.capmode", "0");
            AIWakeupService.isSystemRecording=true;
            EventBus.getDefault().post(9999);
        }
    }
    public  void setProperty(String key, String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value);
        } catch (Exception e) {
            FileWriteLog.writeLog("设置Exception"+e.getMessage());
            e.printStackTrace();
        }
    }
}
