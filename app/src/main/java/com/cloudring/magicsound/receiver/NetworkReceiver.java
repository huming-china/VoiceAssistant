package com.cloudring.magicsound.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.cloudring.commonlib.utils.DESUtils;
import com.cloudring.commonlib.utils.SpUtil;
import com.cloudring.magicsound.services.DownloadVoiceService;
import com.fge.voice.util.FileWriteLog;
import com.fge.voice.util.VoicesVersion;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zengpeijin on 2017/3/31.
 */

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        //如果无网络连接activeInfo为null
        //也可获取网络的类型
        if(activeInfo != null) { //网络连接
            getVoiceData(context);
        }
    }
    private VoicesVersion verson;//语音资源版本管理

    private void getVoiceData(final Context mContext) {
        verson = new VoicesVersion(mContext);
        String desParam = getJsonDesData();
        if (desParam == null) {
            Toast.makeText(mContext, "参数错误", Toast.LENGTH_LONG).show();
            mContext.startService(new Intent(mContext, DownloadVoiceService.class));
            return;
        }
        OkHttpUtils
                .get()
                .addHeader("apikey", "cec4e963be2a3073785eeaa9c3aa237f")
                .url("http://lamp.cloudring.net/lamp/proxy/voice?params=" + desParam)
                .build()
                .execute(new StringCallback() {



                    @Override
                    public void onResponse(String response, int id) {
                        FileWriteLog.writeLog("response=" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String errno = jsonObject.optString("errno");
                            String type = jsonObject.optString("type");
                            String ver = jsonObject.optString("ver");
                            String url = jsonObject.optString("url");
                            String hash = jsonObject.optString("hash");
                            SpUtil.writeString("voice_url", url == null ? "" : url);
                            SpUtil.writeString("voice_hash", hash == null ? "" : hash);
                            SpUtil.writeString("voice_ver", ver == null ? "" : ver);
                            SpUtil.writeString("voice_type", type == null ? "" : type);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mContext.startService(new Intent(mContext, DownloadVoiceService.class));
                    }
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        FileWriteLog.writeLog("请求语音数据错误");
                        mContext.startService(new Intent(mContext, DownloadVoiceService.class));
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 参数封装
     *
     * @return 加密后的参数
     */
    private String getJsonDesData() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", verson.getVoicesType());
        map.put("ver", verson.getVoicesVersion());
        map.put("terminal", "HRM1");
        map.put("terminal_ver", "1.3");
        Gson gson = new Gson();
        String json = gson.toJson(map);
        FileWriteLog.writeLog("本地语音数据  json=" + json);
        String desParam = null;
        try {
            desParam = DESUtils.encrypt(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desParam;
    }
}
