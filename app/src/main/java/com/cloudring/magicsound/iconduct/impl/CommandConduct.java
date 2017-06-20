package com.cloudring.magicsound.iconduct.impl;

import android.content.Context;
import android.view.KeyEvent;

import com.cloudring.commonlib.http.event.ExchangePhoneMirrorTokenEvent;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.activity.RobotFragmentActivity;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.fjtk.musiclib.utils.MusicManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 中控
 * Created by zengpeijin on 2017/4/7.
 */

public class CommandConduct extends BaseConduct<String>{
    private VoiceAdapter adapter;
    private RobotFragmentActivity mActivity;

    public CommandConduct(RobotFragmentActivity mActivity,VoiceAdapter adapter){
        this.mActivity=mActivity;
        this.adapter=adapter;
    }
    @Override
    public String pareseIfly(String json) {
        return null;
    }

    @Override
    public String parseAISpeech(String json) {
        return json;
    }

    @Override
    public void hand(String json) {
        try {
            String message="你可以对我说:\"播放歌曲\"";
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            JSONObject sdsJsonObject = resJsonObject.optJSONObject("sds");
            if("暂停".equals(sdsJsonObject.optString("operation"))){
                if (MusicManager.getInstance().isPlaying()) {
                    message = mActivity.getString(R.string.type_music_pause_label);
                    mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PAUSE);
                } else {
                    message=mActivity.getString(R.string.type_music_label1);
                }
            }
            adapter.add(new MessageItem(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
