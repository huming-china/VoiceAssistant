package com.cloudring.magicsound.model.vmodel;

import com.fge.voice.iflytek.util.ChineseToInt;

/**
 * 调节档位
 * Created by hm on 2016/9/5.
 */
public class VAdjust extends VDevice {
    private final int MAX=4,MIN=1;
    private String position;
    public int getPosition() {
        if(position.equals("最大")){
            return  MAX;
        }else if(position.equals("最小")){
            return MIN;
        }
        return ChineseToInt.chineseToInt(position);
        //return position.contains("两")?2:Integer.valueOf(position);
    }

    public void setPosition(String position) {
        this.position = position.trim();
    }
}
