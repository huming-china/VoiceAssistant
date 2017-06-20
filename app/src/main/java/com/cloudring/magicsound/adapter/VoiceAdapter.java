package com.cloudring.magicsound.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.cloudring.commonlib.http.bean.APIResponse;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.Near;
import com.cloudring.magicsound.model.Tips;
import com.cloudring.magicsound.model.VoiceViewHolder;
import com.cloudring.magicsound.model.vmodel.VRemind;
import com.cloudring.magicsound.model.vmodel.VStock;
import com.cloudring.magicsound.model.vmodel.VWeather;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * reclerview适配器
 */
public class VoiceAdapter extends RecyclerArrayAdapter<Object> {
    public static final int TYPE_ALARM = 0x00001;//闹钟
    public static final int TYPE_SPEAK = 0x00002;//说话
    public static final int TYPE_MESSAGE = 0x00003;//文回答
    public static final int TYPE_CALENDAR = 0x00004;//日历
    public static final int TYPE_MUSIC = 0x00005;//音乐
    public static final int TYPE_WEATHER= 0x00006;//
    public static final int TYPE_TIPS=0x00007;//提示
    public static final int TYPE_COOKBOOK=0x00008;//菜谱
    public static final int TYPE_NEAR=0x00009;//附近
    public static final int TYPE_STOCK=0x00010;//股票
    public static final int TYPE_GROUPON=0x000012;//团购

    public VoiceAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ALARM){
            return new AlarmItemAdapter(parent);
        }else if(viewType==TYPE_SPEAK){
            return new SpeakItemAdapter(parent);
        }else if(viewType==TYPE_MESSAGE){
            return new MessageItemAdapter(parent);
        }else if(viewType==TYPE_CALENDAR){
        }/*else if(viewType==TYPE_MUSIC){
            return new MusicItemAdapter(parent);
        }*/else if(viewType==TYPE_WEATHER){
            return new WeatherItemAdapter(parent);
        }else if(viewType==TYPE_TIPS){
            return new TipsItemAdapter(parent);
        }else if(viewType==TYPE_COOKBOOK){
            return new CookBookItemAdapter(parent);
        }else if(viewType==TYPE_NEAR){
            //return new NearItemAdapter(parent);
        }else if(viewType==TYPE_STOCK){
            return new StockItemAdapter(parent);
        }else if(viewType==TYPE_GROUPON){
            return new GrouponItemAdapter(parent);
        }
        return new VoiceViewHolder(parent);
    }

    @Override
    public int getViewType(int position) {
        if(getItem(position) instanceof MessageItem){
            return TYPE_MESSAGE;
        }else if(getItem(position) instanceof VRemind) {
            return TYPE_ALARM;
        }else if(getItem(position) instanceof VWeather) {
            return TYPE_WEATHER;
        }/*else if(getItem(position) instanceof EMusic) {
            return TYPE_MUSIC;
        }*/else if(getItem(position) instanceof Tips) {
            return TYPE_TIPS;
        }else if(getItem(position) instanceof List) {
            if(((List) getItem(position)).get(0) instanceof APIResponse.Deals) {
                return TYPE_GROUPON;
            }else if(((List) getItem(position)).get(0) instanceof Near) {
                return TYPE_NEAR;
            }
            return TYPE_COOKBOOK;
        }else if(getItem(position) instanceof VStock) {
            return TYPE_STOCK;
        }
        return TYPE_SPEAK;
    }

}
