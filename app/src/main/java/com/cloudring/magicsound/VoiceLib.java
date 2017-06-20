package com.cloudring.magicsound;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cloudring.chat.app.ChatApp;

import java.util.HashSet;
import java.util.Set;

public class VoiceLib {

    private static final String TAG = "VoiceLib";
    public static Context mAppContext = null;
    private Set smart_device = new HashSet();
    private volatile static VoiceLib defaultInstance;

    private VoiceLib() {};

    public static VoiceLib getDefault() {
        if (defaultInstance == null) {
            synchronized (VoiceLib.class) {
                if (defaultInstance == null) {
                    defaultInstance = new VoiceLib();
                }
            }
        }
        return defaultInstance;
    }

    public void init(Context context) {
        //		AIConstant.openLog();
        Log.d(TAG, "------App onCreate");
        mAppContext = context;
        mSharePrefences = mAppContext.getSharedPreferences(SHARED_PERFERENCE_NAME, 0);

		ChatApp.init(mAppContext);

//        if (!Vitamio.isInitialized(mAppContext)) {
//            MyTestUtil.printlnString("Vitamio未初始化A,准备初始化", "");
//            Vitamio.initialize(mAppContext);
//        }
//        if (Vitamio.isInitialized(mAppContext)) {
//            MyTestUtil.printlnString("Vitamio未初始化B,准备初始化", "");
//            Vitamio.initialize(mAppContext);
//        }
    }



    public void addDevice(String name) {
        smart_device.add(name);
    }

    public String getSmartDevice() {
        String result = null;
        for (Object obj : smart_device) {
            result = obj.toString();
        }
        return result;
    }

    public void removeDevice(String name) {
        smart_device.remove(name);
    }

    public static SharedPreferences mSharePrefences;

    public final static String SHARED_PERFERENCE_NAME = "magicsound.conf";

    //	private CommonRequest mXimalaya;
    private String mAppSecret = "9bebc7e8d723d32693dcbad16eab317c";


    public static Context getAppContext() {
        return mAppContext;
    }

    public static SharedPreferences getSharedPreference() {
        return mSharePrefences;
    }

}
