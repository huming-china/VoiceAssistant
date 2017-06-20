package com.cloudring.magicsound.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.model.vmodel.VWeather;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * 天气
 * Created by hm on 2016/6/7.
 */
public class WeatherItemAdapter extends BaseViewHolder<VWeather> {
    private TextView tvWeather,tvWin,tvTitle,tvTemperature,tvDate;
    public WeatherItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_weather);
        tvWeather = $(R.id.tv_weather);
        tvWin=$(R.id.tv_win);
        tvTitle=$(R.id.tv_title);
        tvTemperature=$(R.id.tv_temperature);
        tvDate=$(R.id.tv_date);
    }


    @Override
    public void setData(final VWeather data) {
        super.setData(data);
        tvWeather.setText(data.weather);
        tvWin.setText(data.wind);
        tvTitle.setText(data.area);
        tvDate.setText(data.date);
        tvTemperature.setText(data.temperature);

    }
}
