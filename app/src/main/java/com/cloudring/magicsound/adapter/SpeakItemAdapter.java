package com.cloudring.magicsound.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudring.magicsound.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hm on 2016/6/6.
 */
public class SpeakItemAdapter extends BaseViewHolder<String> {
    private TextView mTextView;
    public SpeakItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_speak);
        mTextView = $(R.id.textView);
    }

    @Override
    public void setData(String data) {
        mTextView.setText(data);
    }
}
