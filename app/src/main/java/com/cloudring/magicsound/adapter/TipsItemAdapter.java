package com.cloudring.magicsound.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.model.Tips;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * 天气
 * Created by hm on 2016/6/7.
 */
public class TipsItemAdapter extends BaseViewHolder<Tips> {
    private ImageView ivIcon;
    private TextView tvName,tvTips;
    public TipsItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_tips);
        ivIcon = $(R.id.icon);
        tvName=$(R.id.text_name);
        tvTips=$(R.id.text_tips);
    }


    @Override
    public void setData(Tips data) {
        super.setData(data);
        ivIcon.setImageResource(data.resId);
        tvName.setText(data.title);
        tvTips.setText(data.tips);
    }



}
