package com.cloudring.magicsound.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudring.commonlib.http.bean.APIResponse;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.event.CookBookEvent;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 团购
 * Created by hm on 2016/6/7.
 */
public class GrouponItemAdapter extends BaseViewHolder<List<APIResponse.Deals>> {
    private LinearLayout layoutCookBook;

    public GrouponItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_cookbook);
        layoutCookBook = $(R.id.layout_cookbook);
    }

    @Override
    public void setData(final List<APIResponse.Deals> data) {
        int size=data.size();
        layoutCookBook.removeAllViewsInLayout();
        for (int i=0;i<size;i++){
            final APIResponse.Deals deals=data.get(i);
            View v= LayoutInflater.from(getContext()).inflate(R.layout.layout_voice_type_near_items,null,true);
            ((TextView)v.findViewById(R.id.text_name)).setText(deals.title);
            ((TextView)v.findViewById(R.id.text_address)).setText(deals.description);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.cook_item_width), LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutCookBook.addView(v,layoutParams);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CookBookEvent(deals.deal_murl));
                }
            });
        }
    }
}
