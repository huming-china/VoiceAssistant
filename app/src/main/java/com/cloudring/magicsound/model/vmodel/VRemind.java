package com.cloudring.magicsound.model.vmodel;

import com.cloudring.lib.clock.alarm.Alarm;

/** 闹钟 提醒
 * Created by zengpeijin on 2016/9/13.
 */
public class VRemind extends VObject{
    private int id;
    private String label;
    private String repeat="一律不";
    private boolean enabled=true;
    private long time;
    private int hour;
    private int minutes;
    public String timeOrg(){
        String result="";
        if(hour<10){
            result+="0"+hour;
        }else{
            result+=hour;
        }
        result+=":";
        if(minutes<10){
            result+="0"+minutes;
        }else{
            result+=minutes;
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRepeat(){
        return repeat;
    }
    public Alarm.DaysOfWeek getDaysOfWeek() {
        Alarm.DaysOfWeek daysOfWeek=new Alarm.DaysOfWeek(0);
        if(repeat.equals("W1")){
            daysOfWeek.set(0,true);
            repeat="周一";
        }else if(repeat.equals("W2")){
            daysOfWeek.set(1,true);
            repeat="周二";
        }else if(repeat.equals("W3")){
            daysOfWeek.set(2,true);
            repeat="周三";
        }else if(repeat.equals("W4")){
            daysOfWeek.set(3,true);
            repeat="周四";
        }else if(repeat.equals("W5")){
            daysOfWeek.set(4,true);
            repeat="周五";
        }else if(repeat.equals("W6")){
            daysOfWeek.set(5,true);
            repeat="周六";
        }else if(repeat.equals("W7")){
            daysOfWeek.set(6,true);
            repeat="周日";
        }else{
            int size=0;
            if(repeat.equals("WORKDAY")){
                size=5;
                repeat="工作日";
            }else if(repeat.equals("EVERYDAY")){
                size=7;
            }
            for (int i=0;i<size;i++){
                daysOfWeek.set(i,true);
                repeat="每天";
            }
        }
        return daysOfWeek;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
