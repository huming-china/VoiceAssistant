package com.cloudring.magicsound.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.event.CookBookEvent;
import com.cloudring.magicsound.model.vmodel.VStock;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import org.greenrobot.eventbus.EventBus;


/**
 * 股票
 * Created by hm on 2016/6/7.
 */
public class StockItemAdapter extends BaseViewHolder<VStock> {
    private TextView mtvCode,mtvName;
    private RelativeLayout mLayout;

    public StockItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_stock);
        mtvCode = $(R.id.tv_code);
        mtvName = $(R.id.tv_name);
        mLayout = $(R.id.parent);
    }

    @Override
    public void setData(final VStock data) {
        mtvCode.setText(data.getCode());
        mtvName.setText(data.getName());
        /*mLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CookBookEvent(data.getUrl()));
            }
        });*/
    }
}
