package com.cloudring.magicsound.iconduct.impl;

import android.app.Activity;
import android.view.KeyEvent;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.activity.RobotFragmentActivity;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.TaskQueue;
import com.cloudring.magicsound.model.vmodel.Sds;
import com.cloudring.magicsound.model.vmodel.VNetFm;
import com.fjtk.musiclib.beans.Data;
import com.fjtk.musiclib.beans.Dbdata;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NetFmConduct extends BaseConduct<VNetFm> {
    private MagicLampFragment fragment;
    private VoiceAdapter adapter;
    private Activity activity;
    private boolean isPlayed;

    public NetFmConduct(MagicLampFragment fragment, VoiceAdapter adapter, boolean isPlayed) {
        this.fragment = fragment;
        this.adapter = adapter;
        this.activity = fragment.getActivity();
        this.isPlayed = isPlayed;
    }

    @Override
    public void hand(VNetFm result) {
        Data data = result.getData();
        if (data != null) {
            List<Dbdata> list = data.getDbdata();
            if (list != null && list.size() > 0) {
                adapter.add(new MessageItem(list.get(0).getTrack()));
                fragment.speak(list.get(0).getTrack());
                fragment.taskQueue = new TaskQueue(data);
            } else {
                adapter.add(new MessageItem(result.getOutput()));
                fragment.speak(result.getOutput());
            }
        } else {
            adapter.add(new MessageItem(activity.getString(R.string.type_music_noresourse)));
            fragment.speak(activity.getString(R.string.type_music_noresourse));
            if (isPlayed) {
                ((RobotFragmentActivity) activity).sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PLAY);
            }
        }
    }

    @Override
    public VNetFm pareseIfly(String json) {
        return null;
    }

    @Override
    public VNetFm parseAISpeech(String json) {
        VNetFm vNetfm = new VNetFm();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String sdsResult = resJsonObject.getString("sds");
            Gson gson = new Gson();
            Sds msds = gson.fromJson(sdsResult, Sds.class);
            vNetfm.setData(msds.getData());
            vNetfm.setOutput(msds.getOutput());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vNetfm;
    }

}
