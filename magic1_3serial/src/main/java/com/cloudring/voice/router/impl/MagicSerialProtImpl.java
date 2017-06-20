package com.cloudring.voice.router.impl;

import com.clouding.magic.seriallib.SendToUartContract;
import com.clouding.magic.seriallib.SendToUartImple;
import com.cloudring.voice.router.IMagicSerialPort;

/**
 * 魔镜1.3串口通信 控制离子风阅读灯
 * Created by hm on 2016/12/22.
 */

public class MagicSerialProtImpl implements IMagicSerialPort {
    private SendToUartContract.Lamp sendLamp;
    private SendToUartContract.Fan sendFan;
    private SendToUartContract.ReadLamp sendReadLamp;

    public MagicSerialProtImpl() {
        sendLamp = SendToUartImple.getInstance();
        sendFan = SendToUartImple.getInstance();
        sendReadLamp = SendToUartImple.getInstance();
    }

    @Override
    public void openFan() {
        sendFan.setFanGears(sendFan.getFanGears() == 0 ? 1 : sendFan.getFanGears()); /*打开离子风扇*/
    }

    @Override
    public void closeFan() {
        sendFan.setFanGears(0);
    }

    @Override
    public void openFan(int level) {
        sendFan.setFanGears(level);
    }

    @Override
    public void openReadLamp() {
        sendReadLamp.setReadLampGears(sendReadLamp.getReadLampGears() == 0 ? 1 : sendReadLamp.getReadLampGears());
    }

    @Override
    public void closeReadLamp() {
        sendReadLamp.closeReadLamp();
    }

    @Override
    public void openReadLamp(int level) {
        sendReadLamp.setReadLampGears(level);
    }

    @Override
    public void openLamp() {
        sendLamp.setColorfulBreathing(0x0a, (byte) 0x05);
    }

    @Override
    public void closeLamp() {
        sendLamp.closeLamp(0x0a);
    }

    @Override
    public int getReadLampGears() {
        return sendReadLamp.getReadLampGears();
    }

    @Override
    public int getFanGears() {
        return sendFan.getFanGears();
    }

    @Override
    public int getLampGears() {
        return 0;
    }
}
