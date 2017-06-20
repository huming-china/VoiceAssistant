package com.cloudring.magicsound.iconduct.impl;

import android.content.Context;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.VoiceLib;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.VAdjust;
import com.cloudring.magicsound.model.vmodel.VDevice;
import com.cloudring.voice.router.IMagicSerialPort;
import com.fge.voice.util.FileWriteLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zengpeijin on 2017/1/10.
 */

public class AdjustConduct extends BaseConduct<VAdjust> {
    private MagicLampFragment fragment;
    private Context mContext;
    private VoiceAdapter adapter;
    private IMagicSerialPort mIMagicSerialPort;

    public AdjustConduct( MagicLampFragment fragment,VoiceAdapter adapter, IMagicSerialPort mIMagicSerialPort) {
        this.adapter = adapter;
        this.mIMagicSerialPort = mIMagicSerialPort;
        this.fragment = fragment;
        this.mContext = fragment.getContext();
    }

    @Override
    public void hand(VAdjust data) {
        VAdjust vAdjust = data;
        FileWriteLog.writeLog("result instanceof VAdjust " + vAdjust.getDeviceName() + "  " + vAdjust.getPosition());

        String message = mContext.getString(R.string.tts_ok);;
        if (VDevice.DEVICE_READ.equals(vAdjust.getDeviceName())) {
            if (vAdjust.getPosition() == 0) {
                message = mContext.getString(R.string.type_adjust_readlight_label1);
            } else if (vAdjust.getPosition() > 4) {
                message = mContext.getString(R.string.type_adjust_readlight_label2);
            } else {
                mIMagicSerialPort.openReadLamp(vAdjust.getPosition());
                VoiceLib.getDefault().addDevice(mContext.getString(R.string.type_device_readlight));
            }
        }
        if (VDevice.DEVICE_FAN.equals(vAdjust.getDeviceName())) {
            if (vAdjust.getPosition() == 0) {
                message = mContext.getString(R.string.type_adjust_fan_label1);
            } else if (vAdjust.getPosition() > 4) {
                message = mContext.getString(R.string.type_adjust_fan_label2);
            } else {
                mIMagicSerialPort.openFan(vAdjust.getPosition());
                VoiceLib.getDefault().addDevice(mContext.getString(R.string.type_device_fan));
            }
        }
        adapter.add(new MessageItem(message));
        fragment.speak(message);
    }

    @Override
    public VAdjust pareseIfly(String json) {
        return null;
    }

    @Override
    public VAdjust parseAISpeech(String json) {
        VAdjust vAdjust = new VAdjust();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            String name = semObject.getString("name");
            String position = semObject.getString("position");
            vAdjust.setDeviceName(name);
            vAdjust.setPosition(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vAdjust;
    }
}
