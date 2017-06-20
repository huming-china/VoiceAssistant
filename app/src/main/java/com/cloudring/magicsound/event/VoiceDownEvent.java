package com.cloudring.magicsound.event;

/**
 * Created by zengpeijin on 2017/1/19.
 */

public class VoiceDownEvent {
    public final static int SUCEES=0x0000;
    public final static int ERROR=0x0001;
    public int code;
    public float progress;

    public VoiceDownEvent(float progress) {
        this.progress = progress;
    }
    public VoiceDownEvent() {
        this.code = SUCEES;
    }
    public VoiceDownEvent(int code) {
        this.code = code;
    }
}
