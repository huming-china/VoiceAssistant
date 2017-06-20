package com.cloudring.magicsound.iconduct.impl;

import android.content.Context;

import com.cloudring.lib.clock.alarm.Alarm;
import com.cloudring.lib.clock.alarm.Alarms;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.vmodel.VRemind;
import com.fge.voice.util.DateTimeTools;
import com.fge.voice.util.FileWriteLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 提醒
 * Created by hm on 2017/1/10.
 */

public class RemindConduct extends BaseConduct<VRemind> {
    private VoiceAdapter adapter;
    private Context mContext;

    public RemindConduct(VoiceAdapter adapter, Context mContext) {
        this.adapter = adapter;
        this.mContext = mContext;
    }

    @Override
    public void hand(VRemind vRemind) {
        FileWriteLog.writeLog("result instanceof VRemind");
        Alarm alarm = new Alarm(vRemind.getLabel());
        alarm.daysOfWeek = vRemind.getDaysOfWeek();
        alarm.hour = vRemind.getHour();
        alarm.minutes = vRemind.getMinutes();
        alarm.time = vRemind.getTime();
        Alarms.addAlarm(mContext, alarm);
        FileWriteLog.writeLog(" VRemind  id==" + alarm.id);
        vRemind.setId(alarm.id);
        adapter.add(vRemind);
    }

    @Override
    public VRemind pareseIfly(String json) {
        VRemind vRemind = new VRemind();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String input = jsonObject.optString("text");
            JSONObject slotsJsonObj = jsonObject.getJSONObject("semantic").getJSONObject("slots");
            String repeat = slotsJsonObj.optString("repeat");
            JSONObject dateTimeJobj = slotsJsonObj.getJSONObject("datetime");
            String dateOrig = dateTimeJobj.optString("dateOrig");
            String time = dateTimeJobj.optString("time");
            String timeOrig = dateTimeJobj.optString("timeOrig");
            String date = dateTimeJobj.optString("date");
            String name = slotsJsonObj.optString("name");
            String content = slotsJsonObj.optString("content");
            vRemind.setRepeat(repeat);
            int hour_minute[] = DateTimeTools.parseTime(time);
            long longtime = DateTimeTools.getTime(date + " " + time);
            vRemind.setTime(longtime);
            vRemind.setLabel(content);
            vRemind.setHour(hour_minute[0]);
            vRemind.setMinutes(hour_minute[1]);
            vRemind.input = input;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vRemind;
    }

    @Override
    public VRemind parseAISpeech(String json) {
        VRemind vRemind = new VRemind();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String input = resJsonObject.optString("input");
            JSONObject sdsJsonObject = resJsonObject.optJSONObject("sds");
            JSONObject nluObject = sdsJsonObject.optJSONObject("nlu");
            String time = nluObject.optString("time", "");
            String date = nluObject.optString("date");
            int hour_minute[] = DateTimeTools.parseAITime(time);
            long longtime = DateTimeTools.getAITime(date + " " + time);
            vRemind.setTime(longtime);
            String label = nluObject.optString("event");
            vRemind.setLabel(label);
            vRemind.setHour(hour_minute[0]);
            vRemind.setMinutes(hour_minute[1]);
            vRemind.input = input;
        } catch (JSONException e) {
            e.printStackTrace();
            vRemind.input="Remind JSONException:"+e.getMessage();
        }
        return vRemind;
    }
}
