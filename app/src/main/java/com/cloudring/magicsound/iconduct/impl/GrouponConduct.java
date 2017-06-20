package com.cloudring.magicsound.iconduct.impl;

import com.cloudring.commonlib.http.HttpUtils;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.vmodel.VGroupon;
import com.fge.voice.util.FileWriteLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 团购
 * Created by hm on 2017/1/10.
 */

public class GrouponConduct extends BaseConduct<VGroupon> {
    @Override
    public void hand(VGroupon vGroupon) {
        FileWriteLog.writeLog("result instanceof VGroupon " + vGroupon.getName());
        HttpUtils.newInstance().getNuomiList("300210000", vGroupon.getName(), "");
    }

    @Override
    public VGroupon pareseIfly(String json) {
        return null;
    }

    @Override
    public VGroupon parseAISpeech(String json) {
        VGroupon vGroupon = new VGroupon();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String rec = resJsonObject.optString("rec");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            vGroupon.setInput(rec);
            vGroupon.setName(semObject.optString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vGroupon;
    }
}
