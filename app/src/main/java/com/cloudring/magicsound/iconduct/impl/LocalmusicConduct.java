package com.cloudring.magicsound.iconduct.impl;

import android.content.Context;
import android.view.KeyEvent;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.activity.RobotFragmentActivity;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.VLocalmusic;
import com.fjtk.musiclib.utils.MusicManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LocalmusicConduct extends BaseConduct<VLocalmusic> {
    private boolean isPlayed;
    private Context mContext;
    private VoiceAdapter adapter;
    private RobotFragmentActivity mActivity;
    private MagicLampFragment fragment;

    public LocalmusicConduct(MagicLampFragment fragment, VoiceAdapter adapter, boolean isPlayed) {
        this.isPlayed = isPlayed;
        this.fragment = fragment;
        this.mContext = fragment.getContext();
        this.adapter = adapter;
        this.mActivity = (RobotFragmentActivity) fragment.getActivity();
    }

    @Override
    public void hand(VLocalmusic vlocalmusic) {
        String message = "";
        String action = vlocalmusic.getAction();
        if ("next".equals(action) || "下一首".equals(action)) {//下一首
            if (isPlayed) {
                message = mContext.getString(R.string.type_music_next_label);
                adapter.add(new MessageItem(message));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_NEXT);
            } else {
                message = mContext.getString(R.string.type_music_label1);
                adapter.add(new MessageItem(message));
                fragment.speak(message, null);
            }
            fragment.speak(message, null);
        } else if ("prev".equals(action) || "上一首".equals(action)) {//上一首
            if (isPlayed) {
                message = mContext.getString(R.string.type_music_pre_label);
                adapter.add(new MessageItem(message));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                //                                        mManager.preMusic(mActivity);
            } else {
                message = mContext.getString(R.string.type_music_label1);
                adapter.add(new MessageItem(message));
                fragment.speak(message, null);
            }
            fragment.speak(message, null);
        } else if ("random".equals(vlocalmusic.getAction())) {//随机播放暂时使用下一首命令
            if (isPlayed) {
                message = mContext.getString(R.string.type_music_random_label);
                adapter.add(new MessageItem(message));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_NEXT);
                //                                        mManager.nextMusic(mActivity);
            } else {
                message = mContext.getString(R.string.type_music_label1);
                adapter.add(new MessageItem(message));
            }
            fragment.speak(message, null);
        } else if ("pause".equals(action) || "暂停".equals(action)) {//暂停
            if (isPlayed) {
                message = mContext.getString(R.string.type_music_pause_label);
                adapter.add(new MessageItem(message));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PAUSE);
                //                                        mManager.pauseMusic(mActivity);
            } else {
                adapter.add(new MessageItem(mContext.getString(R.string.type_music_label1)));
                fragment.speak(mContext.getString(R.string.type_music_label1), null);
            }
        } else if ("resume".equals(action) || "播放".equals(action)) {//继续播放
            if (MusicManager.getInstance().isPause()) {
                message = mContext.getString(R.string.type_music_continue_label);
                adapter.add(new MessageItem(message));
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PLAY);
                //                                        mManager.continueMusic(mActivity);
            } else {
                if (MusicManager.getInstance().isPlaying()) {
                    message = mContext.getString(R.string.type_music_label2);
                    adapter.add(new MessageItem(message));
                } else {
                    message = mContext.getString(R.string.type_music_label1);
                    adapter.add(new MessageItem(message));
                }
            }
            fragment.speak(message, null);
        } else if ("stop".equals(action) || "停止".equals(action)) {//停止
            message = mContext.getString(R.string.type_music_stop_label);
            adapter.add(new MessageItem(message));
            fragment.speak(message, null);
            mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PAUSE);
        } else {
            message = mContext.getString(R.string.type_noresult1);
            adapter.add(new MessageItem(message));
            fragment.speak(message, null);
        }
    }

    @Override
    public VLocalmusic pareseIfly(String json) {
        VLocalmusic vlocalmusic = new VLocalmusic();
        try {
            JSONObject slotsJsonObject = new JSONObject(json).optJSONObject("semantic").getJSONObject("slots");
            String attr = slotsJsonObject.optString("attr", "");
            String attrValue = null;
            int offset = 2;
            if ("歌曲顺序".equals(attr) || "开关".equals(attr)) {
                attrValue = slotsJsonObject.optString("attrValue");
            } else if ("音量".equals(attr)) {
                attrValue = slotsJsonObject.getJSONObject("attrValue").optString("direct");
                offset = slotsJsonObject.getJSONObject("attrValue").optInt("offset");

            }
            vlocalmusic.setAction(attrValue);
            vlocalmusic.setOffset(offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vlocalmusic;
    }

    @Override
    public VLocalmusic parseAISpeech(String json) {
        VLocalmusic vlocalmusic = new VLocalmusic();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String rec = resJsonObject.optString("rec");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            vlocalmusic.setInput(rec);
            vlocalmusic.setAction(semObject.getString("action"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vlocalmusic;
    }
}
