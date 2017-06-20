package com.cloudring.commonlib.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


/**
 * Created by fgtk on 2016/6/7.
 */

public class ToastUtil {

    private static Handler mHandler = null;
    private static int DEFAULT_SHOW_TIME = Toast.LENGTH_LONG;

    /**
     * 在application 中初始化
     * @param context
     */
    public static void init(final Context context){
        mHandler = new Handler(){
            private Toast mToast = null;
            @SuppressWarnings("WrongConstant")
            @Override
            public void handleMessage(Message msg) {

                if(mToast ==null) {
                    mToast = Toast.makeText(context, "", DEFAULT_SHOW_TIME);
                }
                if (msg.obj instanceof Integer) {
                    mToast.setText((Integer) msg.obj);
                } else {
                    mToast.setText((String) msg.obj);
                }
                if(msg.arg1 > 0){
                    mToast.setDuration(msg.arg1);
                }else {
                    mToast.setDuration(DEFAULT_SHOW_TIME);
                }
                mToast.show();
            }
        };
    }

    public static void showLong(String msg){
        show(msg, Toast.LENGTH_LONG);
    }

    public static void showShort(String msg){
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void show(String msg, int time){
        if(mHandler == null){
            LampLog.e("the handler is null while toast the message");
            return;
        }
        Message message = mHandler.obtainMessage();
        message.arg1 = time;
        message.obj = msg;
        message.what = 1;
        message.sendToTarget();
    }
}
