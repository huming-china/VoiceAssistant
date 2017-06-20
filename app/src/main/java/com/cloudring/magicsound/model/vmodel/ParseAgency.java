//package com.cloudring.magicsound.model.vmodel;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.cloudring.commonlib.utils.LampLog;
//import com.fge.voice.RecognizerCallback;
//import com.fge.voice.VoiceManager;
//import com.fge.voice.iflytek.Exp;
//import com.fge.voice.iflytek.JsonParser;
//import com.fge.voice.iflytek.model.VControl;
//import com.fge.voice.iflytek.model.VLocalapp;
//import com.fge.voice.iflytek.model.VLocalmusic;
//import com.fge.voice.iflytek.model.VLocalphone;
//import com.fge.voice.iflytek.model.VLocalvolume;
//import com.fge.voice.iflytek.model.VNear;
//import com.fge.voice.iflytek.model.VRemind;
//import com.fge.voice.util.DateTimeTools;
//import com.fjtk.musiclib.beans.Data;
//import com.fjtk.musiclib.beans.Dbdata;
//import com.google.gson.Gson;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 数据解析 中间层
// * Created by hm on 2016/8/31.
// */
//public class ParseAgency {
//    public static final String TAG = "ParseAgency";
//
//    private void parseIflytek(String result, RecognizerCallback callback) throws JSONException {
//        Log.e(TAG, "result  ==  " + result);
//        JSONObject jsonObject = new JSONObject(result);
//        String input = jsonObject.optString("text");
//        int rc = jsonObject.optInt("rc");
//        Log.e(TAG, "input  ==  " + input);
//        Log.e(TAG, "rc  ==  " + rc);
//        if (rc == 0) {
//            String service = jsonObject.optString("service");
//            if ("weather".equals(service)) {
//                JSONObject dateTimeJobj = jsonObject.getJSONObject("semantic").getJSONObject("slots").getJSONObject("datetime");
//                String date = null;
//                if (dateTimeJobj != null) {
//                    date = dateTimeJobj.getString("date");
//                }
//                JSONArray resultJArray = jsonObject.getJSONObject("data").optJSONArray("result");
//                int length = resultJArray == null ? 0 : resultJArray.length();
//                int needIndex = -1;
//                for (int i = 0; i < length; i++) {
//                    if ("CURRENT_DAY".equals(date)) {
//                        needIndex = 0;
//                        break;
//                    } else if (date.equals(resultJArray.optJSONObject(i).getString("date"))) {
//                        needIndex = i;
//                    }
//                }
//                if (needIndex == -1) {
//                    VObject vObject = new VObject();
//                    vObject.input = input;
//                    vObject.output = "没有查到天气";
//                    callback.onResult(vObject);
//                } else {
//                    date = resultJArray.optJSONObject(needIndex).getString("date");
//                    String wind = resultJArray.optJSONObject(needIndex).getString("wind");
//                    String weather = resultJArray.optJSONObject(needIndex).getString("weather");
//                    String tempRange = resultJArray.optJSONObject(needIndex).getString("tempRange");
//                    String city = resultJArray.optJSONObject(needIndex).getString("city");
//                    VWeather vWeather = new VWeather();
//                    vWeather.date = date;
//                    vWeather.weather = weather;
//                    vWeather.wind = wind;
//                    vWeather.area = city;
//                    vWeather.temperature = tempRange;
//                    vWeather.input = input;
//                    vWeather.output = city + date + weather + tempRange + wind;
//                    callback.onResult(vWeather);
//                }
//            } else if ("schedule".equals(service)) {
//                JSONObject slotsJsonObj = jsonObject.getJSONObject("semantic").getJSONObject("slots");
//                String repeat = slotsJsonObj.optString("repeat");
//                JSONObject dateTimeJobj = slotsJsonObj.getJSONObject("datetime");
//                String dateOrig = dateTimeJobj.optString("dateOrig");
//                String time = dateTimeJobj.optString("time");
//                String timeOrig = dateTimeJobj.optString("timeOrig");
//                String date = dateTimeJobj.optString("date");
//                String name = slotsJsonObj.optString("name");
//                String content = slotsJsonObj.optString("content");
//                VRemind vRemind = new VRemind();
//                vRemind.setRepeat(repeat);
//                int hour_minute[] = DateTimeTools.parseTime(time);
//                long longtime = DateTimeTools.getTime(date + " " + time);
//                vRemind.setTime(longtime);
//                vRemind.setLabel(content);
//                vRemind.setHour(hour_minute[0]);
//                vRemind.setMinutes(hour_minute[1]);
//                vRemind.input = input;
//                callback.onResult(vRemind);
//            } else if ("restaurant".equals(service)) {
////                    VNear vNear=new VNear();
////                    JSONObject slotsJsonObj=jsonObject.getJSONObject("semantic").getJSONObject("slots");
////                    String name=slotsJsonObj.optString("name");
////                    String category=slotsJsonObj.optString("category");
////                    vNear.setName(name);
////                    vNear.input=input;
////                    vNear.setCategory(category);
////                    callback.onResult(vNear);
//                VTuRing vTuRing = new VTuRing();
//                vTuRing.input = input;
//                callback.onResult(vTuRing);
//
//            } else if ("chat".equals(service) || "openQA".equals(service)) {
//                String text = jsonObject.getJSONObject("answer").getString("text");
//                VChat vChat = new VChat();
//                vChat.input = input;
//                vChat.setText(text);
//                callback.onResult(vChat);
//            } else if ("stock".equals(service)) {
//                VStock vStock = new VStock();
//                JSONObject slotsJsonObj = jsonObject.getJSONObject("semantic").getJSONObject("slots");
//                String url = jsonObject.getJSONObject("webPage").optString("url");
//                if (!(url.length() > 0)) {
//                    url = "";
//                }
//                vStock.setUrl(url);
//                vStock.setName(slotsJsonObj.optString("name"));
//                vStock.setCode(slotsJsonObj.optString("code"));
//                vStock.setInput(input);
//                callback.onResult(vStock);
//            } else if ("faq".equals(service)) {
//                String text = jsonObject.getJSONObject("answer").getString("text");
//                VObject vObject = new VObject();
//                vObject.input = input;
//                vObject.output = text;
//                callback.onResult(vObject);
//            } else if ("datetime".equals(service)) {
//                String text = jsonObject.getJSONObject("answer").getString("text");
//                VObject vObject = new VObject();
//                vObject.input = input;
//                vObject.output = text;
//                callback.onResult(vObject);
//            } else if ("cookbook".equals(service)) {
//                VTuRing vTuRing = new VTuRing();
//                vTuRing.input = input;
//                callback.onResult(vTuRing);
//            } else if ("app".equals(service)) {
//                VLocalapp vlocalapp = new VLocalapp();
//                JSONObject slotsJsonObj = jsonObject.getJSONObject("semantic").getJSONObject("slots");
//                String operation = jsonObject.optString("operation");
//                if ("EXIT".equals(operation)) {
//                    operation = "close";
//                } else if ("LAUNCH".equals(operation)) {
//                    operation = "open";
//                }
//                vlocalapp.setInput(input);
//                vlocalapp.setAction(operation);
//                vlocalapp.setName(slotsJsonObj.optString("name"));
//                LampLog.e("input  ==  " + input);
//                LampLog.e("operation  ==  " + operation);
//                LampLog.e("name  ==  " + slotsJsonObj.optString("name"));
//                callback.onResult(vlocalapp);
//                return;
//            } else if ("telephone".equals(service)) {
//                VLocalphone vlocalphone = new VLocalphone();
//                JSONObject slotsJsonObj = jsonObject.getJSONObject("semantic").getJSONObject("slots");
//                vlocalphone.setInput(input);
//                vlocalphone.setName(slotsJsonObj.optString("name"));
//                callback.onResult(vlocalphone);
//                return;
//            } else if ("music".equals(service)) {
//                VMusic vMusic = new VMusic();
//                vMusic.setInput(input);
//                JSONArray jsonArr = jsonObject.getJSONObject("data").optJSONArray("result");
//                if (jsonArr == null) {
//                    vMusic.setOutput("无音乐资源");
//                } else {
//                    List<Dbdata> list = new ArrayList<>();
//                    int length = jsonArr.length();
//                    for (int i = 0; i < length; i++) {
//                        Dbdata dbdata = new Dbdata();
//                        JSONObject musicJson = jsonArr.getJSONObject(i);
//                        dbdata.setTitle(musicJson.getString("name"));
//                        dbdata.setArtist(musicJson.getString("singer"));
//                        String url = musicJson.getString("downloadUrl");
//                        dbdata.setUrl(url);
////                            LampLog.d("url111  ==   " + url);
////                            dbdata.setUrl(url.replaceAll("\\\\", ""));
////                            LampLog.d("url222  ==    " + url.replaceAll("\\\\", ""));
//                        list.add(dbdata);
//                    }
//                    Data data = new Data(list);
//                    vMusic.setData(data);
//                }
//                callback.onResult(vMusic);
//            } else if ("voice".equals(service)) {//相声
//                VMusic vMusic = new VMusic();
//                vMusic.setInput(input);
//                JSONArray jsonArr = jsonObject.getJSONObject("data").optJSONArray("result");
//                if (jsonArr == null) {
//                    vMusic.setOutput("无资源");
//                } else {
//                    List<Dbdata> list = new ArrayList<>();
//                    int length = jsonArr.length();
//                    for (int i = 0; i < length; i++) {
//                        Dbdata dbdata = new Dbdata();
//                        JSONObject musicJson = jsonArr.getJSONObject(i);
//                        dbdata.setTitle(musicJson.getString("title"));
////                            dbdata.setArtist(musicJson.getString("singer"));
//                        String url = musicJson.getString("playUrl");
//                        dbdata.setUrl(url);
////                            LampLog.d("url111  ==   " + url);
////                            dbdata.setUrl(url.replaceAll("\\\\", ""));
////                            LampLog.d("url222  ==    " + url.replaceAll("\\\\", ""));
//                        list.add(dbdata);
//                    }
//                    Data data = new Data(list);
//                    vMusic.setData(data);
//                }
//                callback.onResult(vMusic);
//            } else {
//                VObject vObject = Exp.getMatcherType(input);
//                if (vObject != null) {
//                    vObject.input = input;
//                } else {
//                    vObject = new VObject();
//                    vObject.input = input;
//                    vObject.output = input;
//                }
//                callback.onResult(vObject);
//            }
//        } else if (rc == 4) {
//            LampLog.e("input  ==  " + input);
//            VObject vObject = Exp.getMatcherType(input);
//            if (vObject != null) {
//                vObject.input = input;
//                callback.onResult(vObject);
//            } else {
//                VTuRing vTuRing = new VTuRing();
//                vTuRing.input = input;
//                callback.onResult(vTuRing);
//            }
//            return;
//        } else {
//            Log.e(TAG, "input 222    ==  " + input);
//            Log.e(TAG, "rc  333  ==  " + rc);
//            VObject vObject = Exp.getMatcherType(input);
//            if (vObject != null) {
//                vObject.input = input;
//            } else {
//                vObject = new VObject();
//                vObject.input = input;
//                vObject.output = input;
//            }
//            callback.onResult(vObject);
//        }
//    }
//
//    private void parseAISpeech(String result, RecognizerCallback callback) throws JSONException {
//        Log.e(TAG, "result  ==  " + result);
//        result = result.replace("\"dbdata\":{}", "\"dbdata\":[]");
//        JSONObject resJsonObject = new JSONObject(result).optJSONObject("result");
//        Double conf = resJsonObject.optDouble("conf", 0d);
//        Log.e(TAG, "conf111  ==  " + conf);
//        if (conf != 0) {
//            Log.e(TAG, "conf222  ==  " + conf);
//            if (conf < 0.6) {
//                callback.onError(-2, "置信度低于0.6");
//                return;
//            }
//            String rec = resJsonObject.optString("rec");
//            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
//            String domain = semObject.getString("domain");
//            if ("adjust".equals(domain)) {
//                String name = semObject.getString("name");
//                String position = semObject.getString("position");
//                VAdjust vAdjust = new VAdjust();
//                vAdjust.setDeviceName(name);
//                vAdjust.input = rec;
//                vAdjust.setPosition(position);
//                callback.onResult(vAdjust);
//                return;
//            } else if ("updown".equals(domain)) {
//                VUpDown vUpDown = new VUpDown();
//                String name = semObject.optString("name");
//                String action = semObject.optString("action");
//                vUpDown.setDeviceName(name);
//                vUpDown.setAction(action);
//                vUpDown.input = rec;
//                callback.onResult(vUpDown);
//                return;
//            } else if ("control".equals(domain)) {
//                String name = semObject.getString("name");
//                String action = semObject.getString("action");
//                VControl vControl = new VControl();
//                vControl.setOnoff(action);
//                vControl.setDeviceName(name);
//                vControl.input = rec;
//                callback.onResult(vControl);
//                return;
//            } else if ("near".equals(domain)) {
//                VNear vNear = new VNear();
//                vNear.input = rec;
//                vNear.setCategory(semObject.getString("name"));
//                callback.onResult(vNear);
//                return;
//            } else if ("localmusic".equals(domain)) {
//                VLocalmusic vlocalmusic = new VLocalmusic();
//                vlocalmusic.setInput(rec);
//                vlocalmusic.setAction(semObject.getString("action"));
//                callback.onResult(vlocalmusic);
//                return;
//            } else if ("localvolume".equals(domain)) {
//                VLocalvolume vlocalvolume = new VLocalvolume();
//                vlocalvolume.setInput(rec);
//                vlocalvolume.setAction(semObject.getString("action"));
//                callback.onResult(vlocalvolume);
//                return;
//            } else if ("app".equals(domain)) {
//                VLocalapp vlocalapp = new VLocalapp();
//                vlocalapp.setInput(rec);
//                vlocalapp.setAction(semObject.getString("action"));
//                vlocalapp.setName(semObject.getString("name"));
//                callback.onResult(vlocalapp);
//                return;
//            } else if ("phone".equals(domain)) {
//                VLocalphone vlocalphone = new VLocalphone();
//                vlocalphone.setInput(rec);
//                vlocalphone.setName(semObject.getString("name"));
//                callback.onResult(vlocalphone);
//                return;
//            } else if ("groupon".equals(domain)) {
//                VGroupon vGroupon = new VGroupon();
//                vGroupon.setInput(rec);
//                vGroupon.setName(semObject.optString("name"));
//                callback.onResult(vGroupon);
//                return;
//            } else {
//                callback.onResult(new VObject(rec));
//            }
//        } else {
//            Log.e(TAG, "conf333  ==  " + conf);
//            String input = resJsonObject.optString("input");
//            JSONObject sdsJsonObject = resJsonObject.optJSONObject("sds");
//            String domain = sdsJsonObject.optString("domain");
//            if ("weather".equals(domain)) {//云端语法
//                sdsJsonObject = sdsJsonObject.getJSONObject("data").optJSONArray("dbdata").optJSONObject(0);
//                String area = sdsJsonObject.optString("area");
//                JSONObject todayJsonObj = sdsJsonObject.getJSONObject("today");//目标日期的 天气
//                if (todayJsonObj != null) {
//                    String date = todayJsonObj.getString("date");
//                    String wind = todayJsonObj.getString("wind");
//                    String weather = todayJsonObj.getString("weather");
//                    String temperature = todayJsonObj.getString("temperature");
//                    VWeather vWeather = new VWeather();
//                    vWeather.date = date;
//                    vWeather.weather = weather;
//                    vWeather.wind = wind;
//                    vWeather.area = area;
//                    vWeather.temperature = temperature;
//                    vWeather.input = input;
//                    callback.onResult(vWeather);
//                }
//                return;
//            } else if ("reminder".equals(domain)) {
//                JSONObject nluObject = sdsJsonObject.optJSONObject("nlu");
//                String time = nluObject.optString("time", "");
//                String date = nluObject.optString("date");
//                VRemind vRemind = new VRemind();
//                int hour_minute[] = DateTimeTools.parseAITime(time);
//                long longtime = DateTimeTools.getAITime(date + " " + time);
//                vRemind.setTime(longtime);
//                String label=nluObject.optString("event");
//                vRemind.setLabel(label);
//                vRemind.setHour(hour_minute[0]);
//                vRemind.setMinutes(hour_minute[1]);
//                vRemind.input = input;
//                callback.onResult(vRemind);
//                return;
//            } else if ("music".equals(domain)) {
//                String sdsResult = resJsonObject.getString("sds");
//                Gson gson = new Gson();
//                Sds msds = gson.fromJson(sdsResult, Sds.class);
//                VMusic vMusic = new VMusic();
//                vMusic.setData(msds.getData());
//                vMusic.setOutput(msds.getOutput());
//                vMusic.setInput(input);
//                callback.onResult(vMusic);
//                return;
//            } else if ("netfm".equals(domain)) {
//                String sdsResult = resJsonObject.getString("sds");
//                Gson gson = new Gson();
//                Sds msds = gson.fromJson(sdsResult, Sds.class);
//                VNetFm vNetfm = new VNetFm();
//                vNetfm.setData(msds.getData());
//                vNetfm.setOutput(msds.getOutput());
//                vNetfm.setInput(input);
//                callback.onResult(vNetfm);
//                return;
//            } else if ("calendar".equals(domain)) {
//                VObject object = new VObject();
//                object.setOutput(sdsJsonObject.optString("output"));
//                object.setInput(input);
//                callback.onResult(object);
//                return;
//            } else if ("stock".equals(domain)) {
//                VStock vStock = new VStock();
//                vStock.setName(sdsJsonObject.optString("output"));
//                vStock.setInput(input);
//                callback.onResult(vStock);
//                return;
//            } else if ("poetry".equals(domain)) {
//                String sdsResult = resJsonObject.getString("sds");
//                Gson gson = new Gson();
//                Sds msds = gson.fromJson(sdsResult, Sds.class);
//                VPoetry vPoetry = new VPoetry();
//                vPoetry.setData(msds.getData());
//                vPoetry.setOutput(msds.getOutput());
//                vPoetry.setInput(input);
//                callback.onResult(vPoetry);
//                return;
//            } else if ("chat".equals(domain)) {
//                VChat chat = new VChat();
//                chat.setInput(input);
//                callback.onResult(chat);
//                return;
//            } else {
//                resJsonObject = resJsonObject.optJSONObject("semantics");
//                if (resJsonObject != null) {
//                    resJsonObject = resJsonObject.optJSONObject("request");
//                    if (resJsonObject != null) {
//                        String chatAction = resJsonObject.optString("action");
//                        if ("笑话".equals(chatAction)) {
//                            VTuRing vTuRing = new VTuRing();
//                            vTuRing.input = input;
//                            vTuRing.setType(VTuRing.FUN);
//                            callback.onResult(vTuRing);
//                            return;
//                        } else if ("菜谱".equals(chatAction) || "故事".equals(chatAction)) {
//                            VTuRing vTuRing = new VTuRing();
//                            vTuRing.input = input;
//                            callback.onResult(vTuRing);
//                            return;
//                        } else if ("电台".equals(chatAction)) {
//                            VTuRing vTuRing = new VTuRing();
//                            vTuRing.input = input;
//                            callback.onResult(vTuRing);
//                            return;
//                        }
//                        callback.onResult(new VObject(input));
//                    }
//                } else {
//                    callback.onResult(new VObject(input));
//                }
//            }
//        }
//
//            /*result = result.replace("\"dbdata\":{}", "\"dbdata\":[]");
//            JSONObject jsonObject = new JSONObject(result);
//            jsonObject = jsonObject.getJSONObject("result");
//            if (jsonObject != null) {
//                String sdsResult = jsonObject.getString("sds");
//                if (input.length() <= 0) {
//                    input = "";
//                }
//                Gson gson = new Gson();
//                Sds msds = gson.fromJson(sdsResult, Sds.class);
//                Data data = null;
//                int type = getType(msds.getDomain());
//                Log.e(TAG, "type  ==  " + type);
//                if (type == AIObject.MUSIC) {
//                    VMusic vMusic = new VMusic();
//                    vMusic.setData(msds.getData());
//                    vMusic.setOutput(msds.getOutput());
//                    vMusic.setInput(input);
//                    callback.onResult(vMusic);
//                }
//                if (type == AIObject.CALENDAR) {
//                    VObject object = new VObject();
//                    object.setOutput(msds.getOutput());
//                    object.setInput(input);
//                    callback.onResult(object);
//                }
//            }*/
//
//    }
//
//
//    public void parseJson(RecognizerCallback callback, String result, VoiceManager.EngineType engineType) {
////        Log.e(TAG, "result  ==  " + result);
//        if (engineType == VoiceManager.EngineType.TYPE_IFLYTEK) {
//            String text = JsonParser.parseGrammarResult(result, callback);
//            if (!TextUtils.isEmpty(text))
//                try {
//                    parseIflytek(text, callback);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    callback.onError(-3, "JSONException  :" + e.toString());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    callback.onError(-4, "Exception  :" + e.toString());
//                }
//        } else {
//            try {
//                parseAISpeech(result, callback);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                callback.onError(-3, "JSONException  :" + e.toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//                callback.onError(-4, "Exception  :" + e.toString());
//            }
//        }
//    }
//}
