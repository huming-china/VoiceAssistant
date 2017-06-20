package com.cloudring.magicsound.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.cloudring.magicsound.activity.RobotFragmentActivity;
import com.cloudring.magicsound.aidl.AidlVoiceInterface;
import com.cloudring.magicsound.event.SpeechEvent;
import com.cloudring.magicsound.services.interfaces.IWakeupSupport;
import com.fge.voice.util.FileWriteLog;
import com.fge.voice.util.VoicesWakeLock;

import org.greenrobot.eventbus.EventBus;

/**语音唤醒Service的基类
 * Created by hm on 2017/4/17.
 */

public abstract class VoiceService extends Service implements IWakeupSupport {
    public static boolean isSystemRecording = false;//系统录音机是否在使用录音机(状态来源为修改了源码的系统录音机发的广播)
    protected boolean isOpen = true;//设置的自动唤醒语音是否开启
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        /**判断设置界面语音自动唤醒开启或者关闭状态 */
        sharedPreferences = getSharedPreferences("share_data_1", Context.MODE_PRIVATE);
        isOpen = sharedPreferences.getBoolean("isopen_auto_voice", true);
        initWakeup();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    protected void sendMessage(String text) {
        FileWriteLog.writeLog("发送广播关闭屏保");
        sendBroadcast(new Intent("Intent.COM.MAGIC.ACTION_CLOSE_SCREEN_SAVER"));
        VoicesWakeLock.wakeUpAndUnlock(this);
        //助手的Activity是否在最前端   是则发送消息 否则启动Activity*//*
        if (RobotFragmentActivity.isTop) {
            //发送消息
            EventBus.getDefault().post(new SpeechEvent());
            return;
        }
        Intent intent = new Intent(this, RobotFragmentActivity.class);
        intent.putExtra("wakeup", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private final AidlVoiceInterface.Stub mBinder = new AidlVoiceInterface.Stub() {

        @Override
        public void startWakeup() throws RemoteException {
            startWakeup();
        }

        @Override
        public void stopWakeup() throws RemoteException {
            stopWakeup();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
