package com.cloudring.magicsound.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.model.Near;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.List;


/**
 * 附近
 * Created by hm on 2016/6/7.
 */
public class NearItemAdapter extends BaseViewHolder<List<Near>> {
    private LinearLayout layoutCookBook;

    public NearItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_cookbook);
        layoutCookBook = $(R.id.layout_cookbook);
    }

    @Override
    public void setData(final List<Near> data) {
        int size=data.size();
        layoutCookBook.removeAllViewsInLayout();
        for (int i=0;i<size;i++){
            final Near near=data.get(i);
            View v= LayoutInflater.from(getContext()).inflate(R.layout.layout_voice_type_near_items,null,true);
            ((TextView)v.findViewById(R.id.text_name)).setText(near.getName());
            ((TextView)v.findViewById(R.id.text_address)).setText(near.getAddress());
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.cook_item_width), LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutCookBook.addView(v,layoutParams);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PoiParaOption para = new PoiParaOption()
                            .key(near.getName())
                            .center(near.getLocation())
                            .radius(2000);

                    try {
                        BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(para, getContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
