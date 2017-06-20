package com.cloudring.magicsound.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudring.magicsound.R;
import com.fge.voice.VConfigManager;
import com.fge.voice.util.FileWriteLog;


/**
 * 隐藏的思必驰设置界面
 * Created by zengpeijin on 2016/12/8.
 */

public class ASettingActivity extends Activity {
    EditText edwords, edthreshold, edspeechRate, edwaitCloudTimeout, edpauseTime, ednoSpeechTimeOut, edathThreshold, edserver,edAEC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aispeech_setting);
        edwords = (EditText) findViewById(R.id.et_words);
        edthreshold = (EditText) findViewById(R.id.et_threshold);
        edspeechRate = (EditText) findViewById(R.id.et_speechRate);
        edwaitCloudTimeout = (EditText) findViewById(R.id.et_waitCloudTimeout);
        edpauseTime = (EditText) findViewById(R.id.et_pauseTime);
        ednoSpeechTimeOut = (EditText) findViewById(R.id.et_noSpeechTimeOut);
        edathThreshold = (EditText) findViewById(R.id.et_athThreshold);
        edserver = (EditText) findViewById(R.id.et_server);
        edAEC=(EditText) findViewById(R.id.et_aec);

        edwords.setText(VConfigManager.getConfigStringValue(this,"words","mo jing ni hao"));
        edthreshold.setText(VConfigManager.getConfigFloatValue(this,"threshold",0.2f)+"");
        edspeechRate.setText(VConfigManager.getConfigFloatValue(this,"speechRate",0.9f)+"");
        edwaitCloudTimeout.setText(VConfigManager.getConfigIntValue(this,"waitCloudTimeout",5000)+"");
        edpauseTime.setText(VConfigManager.getConfigIntValue(this,"pauseTime",1000)+"");
        ednoSpeechTimeOut.setText(VConfigManager.getConfigIntValue(this,"noSpeechTimeOut",5000)+"");
        edathThreshold.setText(VConfigManager.getConfigFloatValue(this,"athThreshold",0.6f)+"");
        edserver.setText(VConfigManager.getConfigStringValue(this,"server","ws://s.api.aispeech.com:1028,ws://s.api.aispeech.com:80"));
        edAEC.setHint("0(关闭) 1(打开)");

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        findViewById(R.id.ed_rec_channel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VConfigManager.setConfigIntValue(ASettingActivity.this, "recChannel", 1);
            }
        });
        findViewById(R.id.ed_rec_channel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VConfigManager.setConfigIntValue(ASettingActivity.this, "recChannel", 2);
            }
        });
        findViewById(R.id.ed_show_log_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileWriteLog.SHOW_LOG=true;
                VConfigManager.setConfigIntValue(ASettingActivity.this, "show_log", 1);
            }
        });
        findViewById(R.id.ed_show_log_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileWriteLog.SHOW_LOG=false;
                VConfigManager.setConfigIntValue(ASettingActivity.this, "show_log", 0);
            }
        });
        findViewById(R.id.ed_write_log_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileWriteLog.WRITE_LOG=true;
                VConfigManager.setConfigIntValue(ASettingActivity.this, "write_log", 1);
            }
        });
        findViewById(R.id.ed_write_log_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileWriteLog.WRITE_LOG=false;
                VConfigManager.setConfigIntValue(ASettingActivity.this, "write_log", 0);
            }
        });
    }

    private void save() {
        try {
            if (!isNull(edwords.getText().toString().trim())) {
                VConfigManager.setConfigStringValue(this, "words", edwords.getText().toString().trim());
            }
            if (!isNull(edthreshold.getText().toString().trim())) {
                VConfigManager.setConfigFloatValue(this, "threshold", Float.valueOf(edthreshold.getText().toString().trim()));
            }
            if (!isNull(edspeechRate.getText().toString().trim())) {
                VConfigManager.setConfigFloatValue(this, "speechRate", Float.valueOf(edspeechRate.getText().toString().trim()));
            }
            if (!isNull(edwaitCloudTimeout.getText().toString().trim())) {
                VConfigManager.setConfigIntValue(this, "waitCloudTimeout", Integer.valueOf(edwaitCloudTimeout.getText().toString().trim()));
            }
            if (!isNull(edpauseTime.getText().toString().trim())) {
                VConfigManager.setConfigIntValue(this, "pauseTime", Integer.valueOf(edpauseTime.getText().toString().trim()));
            }
            if (!isNull(ednoSpeechTimeOut.getText().toString().trim())) {
                VConfigManager.setConfigIntValue(this, "noSpeechTimeOut", Integer.valueOf(ednoSpeechTimeOut.getText().toString().trim()));
            }
            if (!isNull(edathThreshold.getText().toString().trim())) {
                VConfigManager.setConfigFloatValue(this, "athThreshold", Float.valueOf(edathThreshold.getText().toString().trim()));
            }
            if (!isNull(edserver.getText().toString().trim())) {
                VConfigManager.setConfigStringValue(this, "server", edserver.getText().toString().trim());
            }if (!TextUtils.isEmpty(edAEC.getText().toString().trim())) {
                VConfigManager.setConfigIntValue(this, "isOpenEcho", Integer.valueOf(edAEC.getText().toString().trim()));
            }

        }catch (Exception e){
            Toast.makeText(this,"格式错误:"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        Process.killProcess(Process.myPid());
    }
    private boolean isNull(String s){
        if(TextUtils.isEmpty(s)){
            return true;
        }
        if(s.equals("0")||s.equals("0.0")||s.equals(".")||s.equals("0.")||s.equals(".0")){
            return true;
        }
        return false;
    }
}
