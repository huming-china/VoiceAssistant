package com.cloudring.voice.router.impl;


import com.clouding.magic.seriallib.Commands;
import com.clouding.magic.seriallib.SendToUartContract;
import com.clouding.magic.seriallib.SendToUartImple;
import com.cloudring.voice.router.IMagicSerialPort;

/**
 * 魔镜1.0串口通信 控制离子风阅读灯
 * Created by hm on 2016/12/22.
 */

public class MagicSerialProtImpl implements IMagicSerialPort {

    private SendToUartContract.Lamp sendLamp;
    private SendToUartContract.Fan sendFan;
    private SendToUartContract.ReadingLamp sendReadLamp;

    public MagicSerialProtImpl() {
        sendLamp = SendToUartImple.getInstance();
        sendFan = SendToUartImple.getInstance();
        sendReadLamp = SendToUartImple.getInstance();
    }
    @Override
    public void openFan() {
        sendFan.openFan();
    }

    @Override
    public void closeFan() {
        sendFan.closeFan();
    }

    @Override
    public void openFan(int level) {
        sendFan.setFanSpeedGear(level);
    }

    @Override
    public void openReadLamp() {
        sendReadLamp.openReadLamp();
    }

    @Override
    public void closeReadLamp() {
        sendReadLamp.closeReadLamp();
    }

    @Override
    public void openReadLamp(int level) {
        sendReadLamp.setReadBrightness(level);
    }

    @Override
    public void openLamp() {
        sendLamp.setLampBrightness(Commands.ALL_LAMP_CTRL,Commands.LAMP_LIGHT_SECEND);
        sendLamp.setLampColorPink(Commands.ALL_LAMP_CTRL);
    }

    @Override
    public void closeLamp() {
        sendLamp.closeLamp(Commands.ALL_LAMP_CTRL);
    }

    @Override
    public int getReadLampGears() {
        return sendReadLamp.getBrightnessStatue();
    }

    @Override
    public int getFanGears() {
        return sendFan.getFanSpeedGear();
    }

    @Override
    public int getLampGears() {
        return 0;
    }
}
