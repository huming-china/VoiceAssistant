package com.cloudring.magicsound.iconduct.impl;

import android.content.Context;
import android.text.TextUtils;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.VoiceLib;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.VDevice;
import com.cloudring.magicsound.model.vmodel.VUpDown;
import com.cloudring.voice.router.IMagicSerialPort;
import com.fge.voice.util.FileWriteLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 串口调节设备
 * Created by hm on 2017/1/10.
 */

public class UpDownConduct extends BaseConduct<VUpDown> {
    private MagicLampFragment fragment;
    private Context mContext;
    private VoiceAdapter adapter;
    private IMagicSerialPort mIMagicSerialPort;

    public UpDownConduct(MagicLampFragment fragment, VoiceAdapter adapter, IMagicSerialPort mIMagicSerialPort) {
        this.fragment = fragment;
        this.mContext = fragment.getContext();
        this.adapter = adapter;
        this.mIMagicSerialPort = mIMagicSerialPort;
    }

    @Override
    public void hand(VUpDown vUpDown) {
        FileWriteLog.writeLog("result instanceof vUpDown " + vUpDown.getDeviceName() + "  " + vUpDown.getAction());
        String message = mContext.getString(R.string.tts_ok);
        String deviceName = vUpDown.getDeviceName();
        if (TextUtils.isEmpty(deviceName)) {
            deviceName = VoiceLib.getDefault().getSmartDevice();
        }
        String action = vUpDown.getAction();
        FileWriteLog.writeLog("result instanceof vUpDown222 " + vUpDown.getDeviceName() + "  " + vUpDown.getAction());
        if (TextUtils.isEmpty(deviceName)) {//没有打开的设备
            adapter.add(new MessageItem(mContext.getString(R.string.type_updown_label1)));
            fragment.speak(mContext.getString(R.string.type_updown_label1));
        } else {
            if (VDevice.DEVICE_FAN.equals(deviceName)) {
                if (VUpDown.UP.equals(action)) {
                    int fanSpeedGear = mIMagicSerialPort.getFanGears();
                    if (fanSpeedGear < 4) {
                        mIMagicSerialPort.openFan(fanSpeedGear + 1);
                    } else {
                        message = mContext.getString(R.string.type_updown_label2);
                    }
                } else if (VUpDown.DOWN.equals(action)) {
                    int fanSpeedGear = mIMagicSerialPort.getFanGears();
                    FileWriteLog.writeLog("风扇-1");
                    if (fanSpeedGear > 1) {
                        mIMagicSerialPort.openFan(fanSpeedGear + 1);
                    } else {
                        message = mContext.getString(R.string.type_updown_label3);
                    }
                } else {
                    message = mContext.getString(R.string.type_updown_label4);
                }
            } else if (VDevice.DEVICE_READ.equals(deviceName)) {//阅读灯
                if (VUpDown.LUP.equals(action) || VUpDown.UP.equals(action)) {
                    FileWriteLog.writeLog("阅读灯+1");
                    int status = mIMagicSerialPort.getReadLampGears();
                    if (status < 4) {
                        mIMagicSerialPort.openReadLamp(status + 1);
                    } else {
                        message = mContext.getString(R.string.type_updown_label2);
                    }
                } else if (VUpDown.DOWN.equals(action) || VUpDown.LDOWN.equals(action)) {
                    FileWriteLog.writeLog("阅读灯-1");
                    int status = mIMagicSerialPort.getReadLampGears();
                    if (status > 1) {
                        mIMagicSerialPort.openReadLamp(status - 1);
                    } else {
                        message = mContext.getString(R.string.type_updown_label3);
                    }
                }
            } else if (VDevice.DEVICE_ATMO.equals(deviceName)) {//氛围灯

            }
            adapter.add(new MessageItem(message));
            fragment.speak(message);
        }
    }

    @Override
    public VUpDown pareseIfly(String json) {
        return null;
    }

    @Override
    public VUpDown parseAISpeech(String json) {
        VUpDown vUpDown = new VUpDown();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            String name = semObject.optString("name");
            String action = semObject.optString("action");
            vUpDown.setDeviceName(name);
            vUpDown.setAction(action);
        } catch (JSONException ex) {
            ex.printStackTrace();
            vUpDown.input = "VUpDown JSONException" + ex.getMessage();
        }
        return vUpDown;
    }
}
