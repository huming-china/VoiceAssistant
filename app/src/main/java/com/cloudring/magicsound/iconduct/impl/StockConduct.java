package com.cloudring.magicsound.iconduct.impl;

import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.VStock;
import com.fge.voice.VoiceConfig;
import com.fge.voice.base.BaseRecognizeManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 股票
 */
public class StockConduct extends BaseConduct<VStock> {
    private MagicLampFragment fragment;
    private VoiceAdapter adapter;

    public StockConduct(MagicLampFragment fragment, VoiceAdapter adapter) {
        this.fragment = fragment;
        this.adapter = adapter;
    }

    @Override
    public void hand(VStock vStock) {
        if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
            adapter.add(vStock);
        } else {
            adapter.add(new MessageItem(vStock.getName()));
            fragment.speak(vStock.getName(), null);
        }
    }

    @Override
    public VStock pareseIfly(String json) {
        VStock vStock = new VStock();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String input = jsonObject.optString("text");
            JSONObject slotsJsonObj = jsonObject.getJSONObject("semantic").getJSONObject("slots");
            String url = jsonObject.getJSONObject("webPage").optString("url");
            if (!(url.length() > 0)) {
                url = "";
            }
            vStock.setUrl(url);
            vStock.setName(slotsJsonObj.optString("name"));
            vStock.setCode(slotsJsonObj.optString("code"));
            vStock.setInput(input);
        } catch (JSONException e) {
            e.printStackTrace();
            vStock.input="VStock JSONException:"+e.getMessage();
        }
        return vStock;
    }

    @Override
    public VStock parseAISpeech(String json) {
        VStock vStock = new VStock();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String input = resJsonObject.optString("input");
            vStock.setInput(input);
            JSONObject sdsJsonObject = resJsonObject.optJSONObject("sds");
            vStock.setName(sdsJsonObject.optString("output"));
        } catch (JSONException e) {
            e.printStackTrace();
            vStock.input="VStock JSONException:"+e.getMessage();
        }
        return vStock;
    }
}
