package com.cloudring.magicsound.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.aispeech.common.AIConstant;
import com.cloudring.commonlib.utils.SpUtil;
import com.cloudring.magicsound.event.VoiceDownEvent;
import com.fge.voice.VConfigManager;
import com.fge.voice.VoiceConfig;
import com.fge.voice.base.BaseVoiceManager;
import com.fge.voice.event.VoiceResourceEvent;
import com.fge.voice.util.FileWriteLog;
import com.fge.voice.util.VoicesVersion;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import okhttp3.Call;


/**
 * 语音资源文件下载
 * Created by hm on 2017/1/18.
 */

public class DownloadVoiceService extends Service {
    public static boolean isDownloading;
    private VoicesVersion version;
    private VoiceFileThread voiceFileThread;//文件解压缩线程

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        if(new File("mnt/sdcard/VT").exists()) {
            AIConstant.openLog();
        }
        FileWriteLog.writeLog("DownloadVoiceService onCreate");
        version = new VoicesVersion(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isDownloading) {
            try {
                isDownloading = true;
                String voice_url = SpUtil.readString("voice_url", "");
                String voice_hash = SpUtil.readString("voice_hash", "");
                String voice_ver = SpUtil.readString("voice_ver", "");
                String voice_type = SpUtil.readString("voice_type", "");
                voice_url = voice_url.replace(" ", "");
                FileWriteLog.writeLog("voice_url=" + voice_url + " voice_hash:" + voice_hash + "voice_ver:" + voice_ver + "voice_type:" + voice_type);
                if (!TextUtils.isEmpty(voice_url) && !TextUtils.isEmpty(voice_hash) && !TextUtils.isEmpty(voice_type) && !TextUtils.isEmpty(voice_ver)) {
                    boolean isUpdate = version.checkUpdate(voice_hash, voice_ver, voice_type);
                    if (isUpdate) {
                        downFile(voice_url, voice_hash);
                    } else {
                        checkMatching();
                    }
                } else {
                    checkMatching();
                }
            }catch (Exception e){checkMatching();}
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkMatching() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                checkMatching(version.checkMatching());
            }
        }.start();
    }

    private void checkMatching(String path) {
        isDownloading = false;
        if (!TextUtils.isEmpty(path)) {
            VoiceConfig.setVoiceResourcePath(path);
            FileWriteLog.writeLog("语音文件加载文件路径==" + VoiceConfig.getVoiceResourcePath());
            //根据条件启动对应的唤醒Service
            if(VoiceConfig.getEngine_type()== VoiceConfig.EngineType.TYPE_AISPEECH) {
                startService(new Intent(this, AIWakeupService.class));
            }else {
                startService(new Intent(this, IflyWakeupService.class));
            }
            EventBus.getDefault().post(new VoiceDownEvent());
            //跳转到MainActivity，并结束当前的LauncherActivity
        } else {
            EventBus.getDefault().post(new VoiceDownEvent(VoiceDownEvent.ERROR));
        }
        stopSelf();
    }

    /***
     * 语音资源文件下载
     *
     * @param url  文件网络地址
     * @param hash hashCode值
     */
    public void downFile(String url, final String hash) {
        int separatorIndex = url.lastIndexOf(File.separator);
        String fileName = (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(VoicesVersion.VOICES_FILEPATH, fileName) {


            @Override
            public void inProgress(float progress, long total, int id) {
                EventBus.getDefault().post(new VoiceDownEvent(progress));
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                FileWriteLog.writeLog("文件下载失败::" + e.getLocalizedMessage());
                e.printStackTrace();
                checkMatching();
            }

            @Override
            public void onResponse(File response, int id) {
                FileWriteLog.writeLog("语音资源文件下载成功=" + response.getAbsolutePath());
                if (voiceFileThread == null) {
                    voiceFileThread = new VoiceFileThread(response.getAbsolutePath(), hash);
                }
                if (!voiceFileThread.isInterrupted()) {
                    voiceFileThread.start();
                }
            }
        });
    }

    class VoiceFileThread extends Thread {
        private String path;
        private String hashCode;

        public VoiceFileThread(String path, String hashCode) {
            this.hashCode = hashCode;
            this.path = path;
        }

        @Override
        public void run() {
            version.check(path, hashCode);
        }
    }

    @Subscribe
    public void onEventMainThread(final VoiceResourceEvent event) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                checkMatching(event.path);
            }
        }.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
