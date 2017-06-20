package com.cloudring.magicsound.iconduct.impl;

import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.VWeather;
import com.fge.voice.util.FileWriteLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 天气
 * Created by hm on 2017/1/9.
 */

public class WeatherConduct extends BaseConduct<VWeather> {
    private VoiceAdapter adapter;
    private MagicLampFragment fragment;
    private final String TIP_MESSAGE = "您可以这么对我说:\"明天北京的天气\"";

    public WeatherConduct(MagicLampFragment fragment, VoiceAdapter adapter) {
        this.adapter = adapter;
        this.fragment = fragment;
    }

    @Override
    public void hand(VWeather vWeather) {
        FileWriteLog.writeLog("vWeather.output " + vWeather.output);
        if (vWeather != null && vWeather.date != null) {
            fragment.speak(vWeather.output);
            adapter.add(vWeather);
        } else {
            adapter.add(new MessageItem(vWeather.output));
            fragment.speak(vWeather.output);
        }
    }

    @Override
    public VWeather pareseIfly(String json) {
        VWeather vWeather = new VWeather();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject dateTimeJobj = jsonObject.getJSONObject("semantic").getJSONObject("slots").getJSONObject("datetime");
            String date = null;
            if (dateTimeJobj != null) {
                date = dateTimeJobj.getString("date");
            }
            JSONArray resultJArray = jsonObject.getJSONObject("data").optJSONArray("result");
            int length = resultJArray == null ? 0 : resultJArray.length();
            int needIndex = -1;
            for (int i = 0; i < length; i++) {
                if ("CURRENT_DAY".equals(date)) {
                    needIndex = 0;
                    break;
                } else if (date.equals(resultJArray.optJSONObject(i).getString("date"))) {
                    needIndex = i;
                }
            }
            if (needIndex == -1) {
                vWeather.output = TIP_MESSAGE;
            } else {
                date = resultJArray.optJSONObject(needIndex).getString("date");
                String wind = resultJArray.optJSONObject(needIndex).getString("wind");
                String weather = resultJArray.optJSONObject(needIndex).getString("weather");
                String tempRange = resultJArray.optJSONObject(needIndex).getString("tempRange");
                String city = resultJArray.optJSONObject(needIndex).getString("city");
                vWeather.date = date;
                vWeather.weather = weather;
                vWeather.wind = wind;
                vWeather.area = city;
                vWeather.temperature = tempRange;
                vWeather.output = city + date + weather + tempRange + wind;
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            vWeather.output = TIP_MESSAGE;
        }
        return vWeather;
    }

    @Override
    public VWeather parseAISpeech(String json) {
        VWeather vWeather = new VWeather();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            JSONObject sdsJsonObject = resJsonObject.optJSONObject("sds");
            sdsJsonObject = sdsJsonObject.getJSONObject("data").optJSONArray("dbdata").optJSONObject(0);
            String area = sdsJsonObject.optString("area");
            JSONObject todayJsonObj = sdsJsonObject.getJSONObject("today");//目标日期的 天气
            if (todayJsonObj != null) {
                String date = todayJsonObj.getString("date");
                String wind = todayJsonObj.getString("wind");
                String weather = todayJsonObj.getString("weather");
                String temperature = todayJsonObj.getString("temperature");
                vWeather.date = date;
                vWeather.weather = weather;
                vWeather.wind = wind;
                vWeather.area = area;
                vWeather.temperature = temperature;
                vWeather.output = date + weather + temperature + wind;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            vWeather.output = TIP_MESSAGE;
        }
        return vWeather;
    }
}
