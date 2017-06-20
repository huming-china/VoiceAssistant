package com.cloudring.magicsound.iconduct;

import com.cloudring.magicsound.utils.JsonParser;
import com.fge.voice.VoiceConfig;
import com.fge.voice.base.BaseRecognizeManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zengpeijin on 2017/1/9.
 */

public class VoiceType {
    private static String input;
    public static String getInput(){
        return input;
    }

    public static String parseType(String json) {
        input=null;
        try {
            if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.isNull("rc")) {//本地
                    input= JsonParser.parseIatResult(json);
                    return "ws";
                }
                input= jsonObject.optString("text");
                int rc = jsonObject.optInt("rc");
                if (rc != 0) {//理解失败
                    return null;
                }
                String service = jsonObject.optString("service");
                return service;
            } else {
                String result = json.replace("\"dbdata\":{}", "\"dbdata\":[]");
                JSONObject resJsonObject = new JSONObject(result).optJSONObject("result");
                Double conf = resJsonObject.optDouble("conf", -1);
                if (conf == -1) {//云端
                    input = resJsonObject.optString("input");
                    JSONObject sdsJsonObject = resJsonObject.optJSONObject("sds");
                    String domain = sdsJsonObject.optString("domain");
                    resJsonObject = resJsonObject.optJSONObject("semantics");
                    if (resJsonObject != null) {
                        resJsonObject = resJsonObject.optJSONObject("request");
                        if (resJsonObject != null) {
                            String chatAction = resJsonObject.optString("action");
                            if ("故事".equals(chatAction)||"笑话".equals(chatAction)||"菜谱".equals(chatAction)) {
                                return "turing";
                            }  else if ("电台".equals(chatAction)) {
                                return "netfm";
                            }
                        }
                    }
                    return domain;
                } else if (conf < 0.6) {//置信度太低
                    return null;
                } else {//本地 符合置信度的
                    JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
                    input = resJsonObject.optString("rec");
                    String domain = semObject.getString("domain");
                    return domain;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
