package com.cloudring.magicsound.iconduct.impl;

import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.voice.router.IMagicSerialPort;
import com.fge.voice.util.FileWriteLog;

/**
 * 讯飞控制设备 本地语法
 * Created by zengpeijin on 2017/3/20.
 */

public class GrammarConduct extends BaseConduct<String> {
    private enum  SwitchStatus{
        ON,OFF,UNKOWN
    }
    private SwitchStatus switchStatus;
    private String json;
    private MagicLampFragment fragment;
    private IMagicSerialPort mIMagicSerialPort;
    private VoiceAdapter adapter;
    public GrammarConduct(MagicLampFragment fragment,String json,IMagicSerialPort mIMagicSerialPort,VoiceAdapter adapter){
        this.json=json;
        this.mIMagicSerialPort=mIMagicSerialPort;
        this.fragment=fragment;
        this.adapter=adapter;
    }

    @Override
    public void hand(String json) {
        String msg="可以对我说打开佛光";
        switchStatus = SwitchStatus.UNKOWN;
        if (contains(1234)) {
            msg="好的";
            switchStatus = SwitchStatus.ON;
        }else if (contains(4321)) {
            msg="好的";
            switchStatus = SwitchStatus.OFF;
        }
        if(contains(101)){//离子风
            if(switchStatus==SwitchStatus.ON){
                mIMagicSerialPort.openFan(); /*打开离子风扇*/FileWriteLog.writeLog("openFan()");
            }else if(switchStatus==SwitchStatus.OFF){
                mIMagicSerialPort.closeFan();FileWriteLog.writeLog("closeFan()");
            }
        }else if(contains(102)){//阅读灯
            if(switchStatus==SwitchStatus.ON){
                mIMagicSerialPort.openReadLamp();FileWriteLog.writeLog("openReadLamp()");
            }else if(switchStatus==SwitchStatus.OFF){
                mIMagicSerialPort.closeReadLamp();FileWriteLog.writeLog("closeReadLamp()");
            }
        }else if(contains(103)){//氛围灯
            if(switchStatus==SwitchStatus.ON){
                FileWriteLog.writeLog("openLamp()");
                mIMagicSerialPort.openLamp();
            }else if(switchStatus==SwitchStatus.OFF){
                FileWriteLog.writeLog("closeLamp()");
                mIMagicSerialPort.closeLamp();
            }
        }
        fragment.speak(msg);
        adapter.add(new MessageItem(msg));
    }

    @Override
    public String pareseIfly(String json) {
        return json;
    }

    private boolean contains(int id){
        FileWriteLog.writeLog("json=="+json);
        FileWriteLog.writeLog("id=="+id);
        String strid="\"id\":"+id;
        boolean b=json.contains(strid);
        return b;
    }

    @Override
    public String parseAISpeech(String json) {
        return json;
    }
}
