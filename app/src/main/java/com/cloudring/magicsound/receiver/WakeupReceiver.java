package com.cloudring.magicsound.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cloudring.magicsound.activity.RobotFragmentActivity;
import com.cloudring.magicsound.event.SpeechEvent;
import com.fge.voice.util.FileWriteLog;
import com.fge.voice.util.VoicesWakeLock;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zengpeijin on 2017/3/20.
 */

public class WakeupReceiver extends BroadcastReceiver {
    private static final String ACTION_WAKE_UP = "com.android.internal.policy.impl";
    private long oldtime;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (oldtime != 0 && System.currentTimeMillis() - oldtime < 500) {
            return;
        }
        oldtime=System.currentTimeMillis();
        //获得Action
        String intentAction = intent.getAction();
        if (ACTION_WAKE_UP.equals(intentAction)) {
            FileWriteLog.writeLog("发送广播关闭屏保");
            context.sendBroadcast(new Intent("Intent.COM.MAGIC.ACTION_CLOSE_SCREEN_SAVER"));
            //助手的Activity是否在最前端   是则发送消息 否则启动Activity*//*
            if (RobotFragmentActivity.isTop) {
                //发送消息
                EventBus.getDefault().post(new SpeechEvent());
                return;
            }
            Intent it = new Intent(context, RobotFragmentActivity.class);
            it.putExtra("wakeup", true);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        }
    }
}
