package com.fge.voice.iflytek.model;

/**
 * Created by hm on 2016/5/10.
 */
public class Slots {
    //智能家居
    public Location location;//地点
    public Datetime datetime;//时间
    public String onOff;//开关  ": "PLAY",
    public String repeat;//": "W6",
    public String songSelector;//": "上一首",
    public Object volume;//音量调整
    public String  content;//": "4点开会"
    //music
    public String artist;
    public String song;
    //radio
    public String name;
    public String waveband;//fm
    public String code;//": "90.8";

}
