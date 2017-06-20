package com.cloudring.magicsound.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.event.CookBookEvent;
import com.cloudring.magicsound.model.CookBook;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 菜谱
 * Created by hm on 2016/6/7.
 */
public class CookBookItemAdapter extends BaseViewHolder<List<CookBook>> {
    private LinearLayout layoutCookBook;

    public CookBookItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_cookbook);
        layoutCookBook = $(R.id.layout_cookbook);
    }

    @Override
    public void setData(final List<CookBook> data) {
        int size=data.size();
        layoutCookBook.removeAllViewsInLayout();
        for (int i=0;i<size;i++){
            final CookBook cookBook=data.get(i);
            View v= LayoutInflater.from(getContext()).inflate(R.layout.layout_voice_type_cookbook_items,null,true);
            ((TextView)v.findViewById(R.id.textView)).setText(cookBook.getInfo());
            ((TextView)v.findViewById(R.id.text_name)).setText(cookBook.getName());
            ImageView imageView= (ImageView) v.findViewById(R.id.imageView);
            Glide.with(getContext()).load(cookBook.getIcon()).into(imageView);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.cook_item_width), LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin=2;
            layoutCookBook.addView(v,layoutParams);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CookBookEvent(cookBook.getDetailurl()));
                }
            });
        }
    }
}
