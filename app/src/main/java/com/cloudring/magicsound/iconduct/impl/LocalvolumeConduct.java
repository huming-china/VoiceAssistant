package com.cloudring.magicsound.iconduct.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;

import com.cloudring.magicsound.Logger;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.activity.RobotFragmentActivity;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.VLocalvolume;

import org.json.JSONException;
import org.json.JSONObject;

public class LocalvolumeConduct extends BaseConduct<VLocalvolume> {
    private static final String TAG = "VoiceActivity";

    private Context mContext;
    private boolean isPlayed;
    private RobotFragmentActivity mActivity;
    private VoiceAdapter adapter;

    public LocalvolumeConduct(Activity mActivity, boolean isPlayed, VoiceAdapter adapter) {
        this.mContext = mActivity;
        this.isPlayed = isPlayed;
        this.mActivity = (RobotFragmentActivity) mActivity;
        this.adapter = adapter;
    }

    Handler mHandler = new Handler();
    Runnable task = new Runnable() {
        @Override
        public void run() {
            mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PLAY);
        }
    };

    @Override
    public void hand(VLocalvolume vlocalvolume) {
        String message = mContext.getString(R.string.tts_ok);
        if ("louder".equals(vlocalvolume.getAction())) {//大点声 大声点
            if (isPlayed) {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_volume_louder_label)));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_VOLUME_UP);
                mHandler.postDelayed(task, 500);
            } else {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_label1)));
            }
        } else if ("softer".equals(vlocalvolume.getAction())) {//小声点 小声点
            if (isPlayed) {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_volume_softer_label)));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_VOLUME_DOWN);
                mHandler.postDelayed(task, 500);
            } else {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_label1)));
            }
        } else if ("mute_on".equals(vlocalvolume.getAction())) {//静音
            if (isPlayed) {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_volume_mute_label)));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_VOLUME_MUTE);
                mHandler.postDelayed(task, 500);
                Logger.e(TAG, "silence please");
            } else {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_label1)));
            }
        } else if ("mute_off".equals(vlocalvolume.getAction())) {//取消静音
            if (isPlayed) {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_volume_mutecancle_label)));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_VOLUME_MUTE);
                mHandler.postDelayed(task, 500);
            } else {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_label1)));
            }
        }
    }

    @Override
    public VLocalvolume pareseIfly(String json) {
        return null;
    }

    @Override
    public VLocalvolume parseAISpeech(String json) {
        VLocalvolume vlocalvolume = new VLocalvolume();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            vlocalvolume.setAction(semObject.getString("action"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vlocalvolume;
    }
}
