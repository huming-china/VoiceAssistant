package com.cloudring.magicsound.iconduct.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.event.WakeupEvent;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.Near;
import com.cloudring.magicsound.model.vmodel.VNear;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 周边
 * Created by hm on 2017/1/10.
 */

public class NearConduct extends BaseConduct<VNear> {
    private MagicLampFragment fragment;
    private VoiceAdapter adapter;
    private Context mContext;

    @Override
    public void hand(VNear data) {
        getNear(data.getKeyName());
    }

    public NearConduct(MagicLampFragment fragment, VoiceAdapter adapter) {
        this.fragment = fragment;
        this.adapter = adapter;
        this.mContext = fragment.getContext();
    }

    @Override
    public VNear pareseIfly(String json) {
        VNear vNear = new VNear();
        JSONObject jsonObject = null;
        try {
            String input = jsonObject.optString("text");
            jsonObject = new JSONObject(json);
            JSONObject slotsJsonObj = jsonObject.getJSONObject("semantic").getJSONObject("slots");
            String name = slotsJsonObj.optString("name");
            String category = slotsJsonObj.optString("category");
            vNear.setName(name);
            vNear.input = input;
            vNear.setCategory(category);
        } catch (JSONException e) {
            e.printStackTrace();
            vNear.output = "Near JSONException";
        }
        return vNear;
    }

    @Override
    public VNear parseAISpeech(String json) {
        VNear vNear = new VNear();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String rec = resJsonObject.optString("rec");
            JSONObject semObject = resJsonObject.getJSONObject("post").getJSONObject("sem");
            vNear.input = rec;
            vNear.setCategory(semObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
            vNear.output = "Near JSONException";
        }
        return vNear;
    }

    private void getNear(String keyword) {
        //第一步，创建POI检索实例
        PoiSearch mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {

            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult != null && poiResult.getAllPoi() != null && poiResult.getAllPoi().size() > 0) {
                    List<Near> nears = new ArrayList<Near>();
                    for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                        Near near = new Near();
                        near.setName(poiResult.getAllPoi().get(i).name);
                        near.setAddress(poiResult.getAllPoi().get(i).address);
                        near.setLocation(poiResult.getAllPoi().get(i).location);
                        nears.add(near);
                    }
                    adapter.add(nears);
                } else {
                    adapter.add(new MessageItem(mContext.getString(R.string.type_nearby_nomessage)));
                }
                EventBus.getDefault().post(new WakeupEvent(WakeupEvent.START_WAKEUP));
                //mWaveMicView.showSay();
                //scrollList();
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        });
        SharedPreferences sp = mContext.getSharedPreferences("location", Context.MODE_PRIVATE);
        double latitude = Double.valueOf(sp.getString("latitude", "22.54587"));
        double longitude = Double.valueOf(sp.getString("longitude", "113.950511"));
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(keyword)
                .pageCapacity(10).sortType(PoiSortType.comprehensive)
                .location(new LatLng(latitude, longitude))
                .radius(500).pageNum(0);
        mPoiSearch.searchNearby(nearbySearchOption);
    }
}
