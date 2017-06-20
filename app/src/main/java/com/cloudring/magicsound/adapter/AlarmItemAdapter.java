package com.cloudring.magicsound.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudring.lib.clock.alarm.Alarms;
import com.cloudring.lib.clock.fragment.AlarmListActivity;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.model.vmodel.VRemind;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * 闹钟
 * Created by hm on 2016/6/7.
 */
public class AlarmItemAdapter extends BaseViewHolder<VRemind> {
    private TextView tvTime,tvEnable,tvContent,tvType;
    public AlarmItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_alarm);
        tvTime = $(R.id.tv_time);
        tvEnable=$(R.id.tv_enable);
        tvContent=$(R.id.tv_content);
        tvType=$(R.id.tv_type);


    }


    @Override
    public void setData(final VRemind data) {
        super.setData(data);
        tvTime.setText(data.timeOrg());
        tvEnable.setSelected(data.isEnabled());
        tvContent.setText(data.getRepeat());
        tvType.setText(TextUtils.isEmpty(data.getLabel())?getContext().getString(R.string.alarm_default_label):data.getLabel());
        tvEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEnable.isSelected()) {
                    data.setEnabled(false);
                    tvEnable.setSelected(false);
                    Alarms.enableAlarm(getContext(), data.getId(), false);

                } else {
                    data.setEnabled(true);
                    tvEnable.setSelected(true);
                    Alarms.enableAlarm(getContext(), data.getId(), true);
                }

            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), AlarmListActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(it);
            }
        });

    }
}
