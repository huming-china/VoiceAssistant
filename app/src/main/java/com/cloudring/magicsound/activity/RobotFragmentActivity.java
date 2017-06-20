package com.cloudring.magicsound.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.event.VoiceDownEvent;
import com.cloudring.magicsound.fragment.DownloadVoiceFragment;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.services.DownloadVoiceService;
import com.fge.voice.VoiceConfig;
import com.fge.voice.util.FileWriteLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by admin on 2016/11/10.
 */
public class RobotFragmentActivity extends AppCompatActivity {
    public static boolean isTop = false;
    private MagicLampFragment magicLampFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTop = true;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_robot);
        EventBus.getDefault().register(this);
        if (!DownloadVoiceService.isDownloading) {
            showMagicFragment();

        }
    }

    private void showMagicFragment() {
        if (TextUtils.isEmpty(VoiceConfig.getVoiceResourcePath())) {
            Toast.makeText(this, "没有找到语音资源文件,请重新启动", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        FragmentManager mFragmentManager = getSupportFragmentManager();
        if (mFragmentManager == null) {
            return;
        }
        Fragment fragmentAlert = mFragmentManager.findFragmentByTag("alert");
        if (fragmentAlert != null) {
            mFragmentManager.beginTransaction().remove(fragmentAlert).commit();
        }
        Fragment fragmentVoice = mFragmentManager.findFragmentByTag("voice");
        if (fragmentVoice != null) {
            return;
        }
        if (magicLampFragment == null) {
            magicLampFragment = new MagicLampFragment();
            mFragmentManager.beginTransaction().add(R.id.fl_container, magicLampFragment, "voice").commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isTop = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void sendMediaBroadcast(int keyAction) {
        Intent mbIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        //构造一个KeyEvent对象
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, keyAction);
        //作为附加值添加至mbIntent对象中
        mbIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);

        //此时China_MBReceiver和England_MBReceiver都会接收到该广播
        sendBroadcast(mbIntent);
    }

    DownloadVoiceFragment downloadVoiceFragment;

    private void showAlert() {
        if (downloadVoiceFragment == null) {
            downloadVoiceFragment = new DownloadVoiceFragment();
        }
        if (!downloadVoiceFragment.isVisible())
            downloadVoiceFragment.show(getSupportFragmentManager(), "alert");
        //getSupportFragmentManager().beginTransaction().add(R.id.fl_container, downloadVoiceFragment).commit();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(VoiceDownEvent event) {
        if (!isTop) {
            return;
        }
        if (DownloadVoiceService.isDownloading) {
            if (downloadVoiceFragment == null) {
                showAlert();
            }
            downloadVoiceFragment.setProgress(event.progress);
        } else if (event.code == VoiceDownEvent.ERROR) {
            FileWriteLog.writeLog("VoiceDownEvent     下载失败");
            //没有资源 （下载失败了）
            getSupportFragmentManager().beginTransaction().remove(downloadVoiceFragment).commit();
            alert();
        } else {
            FileWriteLog.writeLog("VoiceDownEvent     下载完成");
            showMagicFragment();
        }
    }

    private void alert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(RobotFragmentActivity.this);
        AlertDialog dialog = alert.setTitle(R.string.voice_not_resource_title)
                .setMessage(R.string.voice_not_resource)
                .setPositiveButton(R.string.voice_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //getIntent().putExtra("wakeup",intent.getBooleanExtra("wakeup", true));
    }
}
