package com.cloudring.magicsound.iconduct.impl;

import android.app.Activity;

import com.cloudring.chat.activity.Chat;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.vmodel.VLocalphone;
import com.cloudring.magicsound.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

public class LocalphoneConduct extends BaseConduct<VLocalphone> {
    private Activity mActivity;
    private MagicLampFragment fragment;

    public LocalphoneConduct(MagicLampFragment fragment) {
        this.fragment = fragment;
        this.mActivity = fragment.getActivity();
    }

    @Override
    public void hand(VLocalphone vlocalphone) {
        String message = mActivity.getString(R.string.type_phone_label1);
        fragment.speak(message, null);
        Chat.getInstance().startVideo("2", "2", "1", vlocalphone.getName(),
                Tools.getDrawable(mActivity, R.drawable.ic_launcher));
        mActivity.finish();
    }

    @Override
    public VLocalphone pareseIfly(String json) {
        return null;
    }

    @Override
    public VLocalphone parseAISpeech(String json) {

        VLocalphone vlocalphone = new VLocalphone();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            vlocalphone.setName(semObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vlocalphone;
    }
}
