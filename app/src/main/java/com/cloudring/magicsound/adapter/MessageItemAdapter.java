package com.cloudring.magicsound.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudring.magicsound.R;
import com.cloudring.magicsound.model.MessageItem;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/** 消息回复
 * Created by hm on 2016/6/6.
 */
public class MessageItemAdapter extends BaseViewHolder<MessageItem> {
    private TextView mTextView;
    public MessageItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_message);
        mTextView = $(R.id.textView);
    }

    @Override
    public void setData(MessageItem data) {
        mTextView.setText(data.getText());
    }
}
