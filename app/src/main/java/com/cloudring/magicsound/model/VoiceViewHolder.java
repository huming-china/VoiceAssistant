package com.cloudring.magicsound.model;

import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudring.magicsound.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by admin on 2016/6/3.
 */
public class VoiceViewHolder extends BaseViewHolder<String> {
    private TextView mTv_voice;

    public VoiceViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_voice);
        mTv_voice = $(R.id.voice_text);
    }

    @Override
    public void setData(final String text){
        mTv_voice.setText(text);
    }
}
