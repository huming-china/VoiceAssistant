package com.cloudring.magicsound.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.cloudring.magicsound.R;
import com.fge.voice.VConfigManager;

/**
 * Created by zengpeijin on 2016/12/8.
 */

public class IflySettingActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ifly_setting);
        final EditText ed= (EditText) findViewById(R.id.et_threshold);
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    VConfigManager.getConfigIntValue(IflySettingActivity.this, "IVW_THRESHOLD", Integer.valueOf(ed.getText().toString()));
                }catch (Exception e){}
            }
        });
    }
}
