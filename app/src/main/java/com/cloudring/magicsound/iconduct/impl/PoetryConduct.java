package com.cloudring.magicsound.iconduct.impl;

import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.Sds;
import com.cloudring.magicsound.model.vmodel.VPoetry;
import com.fjtk.musiclib.beans.Data;
import com.fjtk.musiclib.beans.Dbdata;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 诗歌
 */
public class PoetryConduct extends BaseConduct<VPoetry> {
    private MagicLampFragment fragment;
    private VoiceAdapter adapter;

    public PoetryConduct(MagicLampFragment fragment, VoiceAdapter adapter) {
        this.fragment = fragment;
        this.adapter = adapter;
    }

    @Override
    public void hand(VPoetry data) {
        Data mdata = data.getData();
        if (mdata != null) {
            List<Dbdata> list = mdata.getDbdata();
            if (list != null && list.size() > 0) {
                StringBuilder builder = new StringBuilder();
                if (list.get(0).getSuggestion() != null) {
                    fragment.speak(list.get(0).getSuggestion());
                    builder.append(list.get(0).getSuggestion());
                }
                builder.append("\n").append(list.get(0).getTitle())
                        .append("\n");
                for (int i = 0; i < list.get(0).getPoetry().size(); i++) {
                    builder.append(list.get(0).getPoetry().get(i))
                            .append("\n");
                }
                adapter.add(new MessageItem(builder.toString()));
            }
        }
    }

    @Override
    public VPoetry pareseIfly(String json) {
        return null;
    }

    @Override
    public VPoetry parseAISpeech(String json) {
        VPoetry vPoetry = new VPoetry();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String input = resJsonObject.optString("input");
            String sdsResult = resJsonObject.getString("sds");
            Gson gson = new Gson();
            Sds msds = gson.fromJson(sdsResult, Sds.class);
            vPoetry.setData(msds.getData());
            vPoetry.setOutput(msds.getOutput());
            vPoetry.setInput(input);
        } catch (JSONException e) {
            e.printStackTrace();
            vPoetry.input = "VPoetry JSONException";
        }
        return vPoetry;
    }
}
