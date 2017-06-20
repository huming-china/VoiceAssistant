package com.cloudring.magicsound.iconduct.impl;

import android.content.Context;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.VoiceLib;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.VControl;
import com.cloudring.magicsound.model.vmodel.VDevice;
import com.cloudring.voice.router.IMagicSerialPort;
import com.fge.voice.util.FileWriteLog;

import org.json.JSONException;
import org.json.JSONObject;

/** 思必驰控制设备本地语法
 * Created by zengpeijin on 2017/1/10.
 */

public class ControlConduct extends BaseConduct<VControl> {
    private MagicLampFragment fragment;
    private Context mContext;
    private IMagicSerialPort mIMagicSerialPort;
    private VoiceAdapter adapter;

    public ControlConduct(MagicLampFragment fragment, IMagicSerialPort mIMagicSerialPort, VoiceAdapter adapter) {
        this.fragment = fragment;
        this.mIMagicSerialPort = mIMagicSerialPort;
        this.adapter = adapter;
        mContext = fragment.getContext();
    }

    @Override
    public void hand(VControl data) {
        String message = mContext.getString(R.string.tts_ok);
        VControl vControl = data;
        String deviceName = vControl.getDeviceName();
        boolean isOnoff = vControl.isOnoff();
        FileWriteLog.writeLog("ControlConduct开关:" + isOnoff + " " + deviceName);
        if (isOnoff) {
            VoiceLib.getDefault().addDevice(deviceName);//记录状态  表示风扇已经打开
            if (VDevice.DEVICE_FAN.equals(deviceName)) {
                mIMagicSerialPort.openFan(); /*打开离子风扇*/
            } else if (VDevice.DEVICE_READ.equals(deviceName)) {
                mIMagicSerialPort.openReadLamp();
            } else if (VDevice.DEVICE_ATMO.equals(deviceName)) {
                mIMagicSerialPort.openLamp();
            }
        } else {/*关闭离子风扇*/
            VoiceLib.getDefault().removeDevice(deviceName);//移除状态记录
            //关闭风扇
            if (VDevice.DEVICE_FAN.equals(deviceName)) {
                mIMagicSerialPort.closeFan();
            } else if (VDevice.DEVICE_READ.equals(deviceName)) {
                //关闭阅读灯
                mIMagicSerialPort.closeReadLamp();
            } else if (VDevice.DEVICE_ATMO.equals(deviceName)) {
                //关闭氛围
                mIMagicSerialPort.closeLamp();
            }
        }
        adapter.add(new MessageItem(message));
        fragment.speak(message);
    }

    @Override
    public VControl pareseIfly(String json) {
        return null;
    }

    @Override
    public VControl parseAISpeech(String json) {
        VControl vControl = new VControl();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            String name = semObject.getString("name");
            String action = semObject.getString("action");
            vControl.setOnoff(action);
            vControl.setDeviceName(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vControl;
    }
}
