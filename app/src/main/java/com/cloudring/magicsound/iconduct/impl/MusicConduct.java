package com.cloudring.magicsound.iconduct.impl;

import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.TaskQueue;
import com.cloudring.magicsound.model.vmodel.Sds;
import com.cloudring.magicsound.model.vmodel.VMusic;
import com.fge.voice.util.FileWriteLog;
import com.fjtk.musiclib.beans.Data;
import com.fjtk.musiclib.beans.Dbdata;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐
 * Created by hm on 2017/1/9.
 */

public class MusicConduct extends BaseConduct<VMusic> {
    private MagicLampFragment fragment;
    private VoiceAdapter adapter;

    public MusicConduct(MagicLampFragment fragment, VoiceAdapter adapter) {
        this.adapter = adapter;
        this.fragment = fragment;
    }


    @Override
    public void hand(VMusic mdata) {
        VMusic vMusic = mdata;
        Data data = vMusic.getData();
        FileWriteLog.writeLog("data " + data);
        if (data != null) {
            List<Dbdata> list = data.getDbdata();
            if (list != null && list.size() > 0) {
                adapter.add(new MessageItem(list.get(0).getTitle()
                        + "-" + list.get(0).getArtist()));
                fragment.speak("准备播放" + list.get(0).getTitle()
                        + "-" + list.get(0).getArtist());
                fragment.taskQueue = new TaskQueue(data);
            } else {
                adapter.add(new MessageItem(vMusic.getOutput()));
                fragment.speak(vMusic.getOutput());
            }
        } else {
            adapter.add(new MessageItem(vMusic.getOutput()));
            fragment.speak(vMusic.getOutput());
        }

    }

    @Override
    public VMusic pareseIfly(String json) {
        VMusic vMusic = new VMusic();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String input = jsonObject.optString("text");
            vMusic.setInput(input);
            JSONArray jsonArr = jsonObject.getJSONObject("data").optJSONArray("result");
            if (jsonArr == null) {
                vMusic.setOutput("没有找到歌曲，可以对我说：\"我要听周杰伦的歌\"");
            } else {
                List<Dbdata> list = new ArrayList<>();
                int length = jsonArr.length();
                for (int i = 0; i < length; i++) {
                    Dbdata dbdata = new Dbdata();
                    JSONObject musicJson = jsonArr.getJSONObject(i);
                    dbdata.setTitle(musicJson.getString("name"));
                    dbdata.setArtist(musicJson.getString("singer"));
                    String url = musicJson.getString("downloadUrl");
                    dbdata.setUrl(url);
                    list.add(dbdata);
                }
                Data data = new Data(list);
                vMusic.setData(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vMusic;
    }

    @Override
    public VMusic parseAISpeech(String json) {
        VMusic vMusic = new VMusic();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String sdsResult = resJsonObject.getString("sds");
            Gson gson = new Gson();
            Sds msds = gson.fromJson(sdsResult, Sds.class);
            vMusic.setData(msds.getData());
            vMusic.setOutput(msds.getOutput());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vMusic;
    }

}
