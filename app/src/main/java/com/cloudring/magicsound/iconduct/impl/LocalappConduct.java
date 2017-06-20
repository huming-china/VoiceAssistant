package com.cloudring.magicsound.iconduct.impl;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.activity.RobotFragmentActivity;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.TaskQueue;
import com.cloudring.magicsound.model.vmodel.VLocalapp;
import com.fge.voice.aispeech.util.GrammarHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class LocalappConduct extends BaseConduct<VLocalapp> {
    private MagicLampFragment fragment;
    private Activity mActivity;
    private VoiceAdapter adapter;

    public LocalappConduct(MagicLampFragment fragment, VoiceAdapter adapter) {
        this.fragment = fragment;
        this.mActivity = fragment.getActivity();
        this.adapter = adapter;
    }

    @Override
    public void hand(VLocalapp vlocalapp) {
        new GrammarHelper(mActivity).getApps();
        String name = GrammarHelper.appMaps.get(vlocalapp.getName());
        if (mActivity.getString(R.string.type_app_open).equals(vlocalapp.getAction())) {
            try {
                if (name.endsWith("AllInOneActivity") || name.endsWith("AlarmListActivity")
                        || name.endsWith("MusicPlayActivity") || name.endsWith("com.fgecctv.lilin.lcamera.ui.CameraActivity")) {
                    fragment.taskQueue = new TaskQueue(TaskQueue.TaskQueueType.START_ACTIVITY, name);
                    fragment.startIntent = new Intent();
                    fragment.startIntent.setClassName(mActivity, name);
                } else {
                    fragment.taskQueue = new TaskQueue(TaskQueue.TaskQueueType.START_APP, name);
                    fragment.startIntent = mActivity.getPackageManager()
                            .getLaunchIntentForPackage(name);
                }
                if (mActivity.getPackageManager().resolveActivity(fragment.startIntent, 0) != null) {
                    // 说明系统中存在这个activity
                    adapter.add(new MessageItem(mActivity.getString(R.string.type_app_label1)));
                    fragment.speak(mActivity.getString(R.string.type_app_label1));
                } else {
                    fragment.taskQueue = null;
                    adapter.add(new MessageItem(mActivity.getString(R.string.type_app_label2)));
                    fragment.speak(mActivity.getString(R.string.type_app_label2));
                }
            } catch (Exception e) {
                fragment.taskQueue = null;
                e.printStackTrace();
            }
        } else if (mActivity.getString(R.string.type_app_close).equals(vlocalapp.getAction())) {
            if (name.endsWith("MusicPlayActivity")) {
                adapter.add(new MessageItem(mActivity.getString(R.string.type_music_exit_label)));
                fragment.speak(mActivity.getString(R.string.type_music_exit_label), null);
                ((RobotFragmentActivity) mActivity).sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_CLOSE);
            }
        }
    }

    @Override
    public VLocalapp pareseIfly(String json) {
        VLocalapp vlocalapp = new VLocalapp();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String input = jsonObject.optString("text");
            JSONObject slotsJsonObj = jsonObject.getJSONObject("semantic").getJSONObject("slots");
            String operation = jsonObject.optString("operation");
            if ("EXIT".equals(operation)) {
                operation = "close";
            } else if ("LAUNCH".equals(operation)) {
                operation = "open";
            }
            vlocalapp.setInput(input);
            vlocalapp.setAction(operation);
            vlocalapp.setName(slotsJsonObj.optString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vlocalapp;
    }

    @Override
    public VLocalapp parseAISpeech(String json) {
        VLocalapp vlocalapp = new VLocalapp();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String rec = resJsonObject.optString("rec");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            vlocalapp.setInput(rec);
            vlocalapp.setAction(semObject.getString("action"));
            vlocalapp.setName(semObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vlocalapp;
    }
}
