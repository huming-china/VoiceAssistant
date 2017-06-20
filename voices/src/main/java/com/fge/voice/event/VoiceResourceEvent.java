package com.fge.voice.event;

/**
 * Created by zengpeijin on 2016/12/12.
 */

public class VoiceResourceEvent {
    public String path;
    private boolean isFinsh;
    private int pro;
    public VoiceResourceEvent(String path, boolean isFinsh, int pro){
        this.path=path;
        this.isFinsh=isFinsh;
        this.pro=pro;
    }
}
