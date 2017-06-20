package com.cloudring.magicsound.event;

/**
 * Created by zengpeijin on 2016/7/18.
 */
public class WakeupEvent {
    public static final int START_WAKEUP=1;
    public static final int STOP_WAKEUP=-1;
    public static final int START_SPEECH=0;
    public int status;
    public WakeupEvent(int status){
        this.status=status;
    }
}
