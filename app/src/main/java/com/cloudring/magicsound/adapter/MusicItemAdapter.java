
package com.cloudring.magicsound.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudring.magicsound.R;
import com.fjtk.musiclib.utils.MusicManager;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * 天气
 * Created by hm on 2016/6/7.
 */
/*public class MusicItemAdapter extends BaseViewHolder<EMusic> {
    private TextView tvTitle,tvArtist;
    private ImageView ivDefault,ivPre,ivPlay,ivNext,ivCancle;
    private MusicManager mManager;

    public MusicItemAdapter(ViewGroup parent) {
        super(parent, R.layout.layout_voice_type_music);
        tvTitle = $(R.id.music_tv_title);
        tvArtist = $(R.id.music_tv_artist);
        ivDefault = $(R.id.music_iv_default);
        ivPre = $(R.id.music_iv_pre);
        ivPlay = $(R.id.music_iv_play);
        ivNext = $(R.id.music_iv_next);
        ivCancle = $(R.id.music_iv_cancel);
        ivPre.setOnClickListener(mClickListener);
        ivPlay.setOnClickListener(mClickListener);
        ivNext.setOnClickListener(mClickListener);
        ivCancle.setOnClickListener(mClickListener);
    }


    @Override
    public void setData(final EMusic data) {
        super.setData(data);
        mManager = MusicManager.getInstance();
        tvTitle.setText(data.title);
        tvArtist.setText(data.artist);
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.music_iv_pre){
                mManager.preMusic(getContext());
            }if(id == R.id.music_iv_play){

                mManager.pauseMusic(getContext());
            }if(id == R.id.music_iv_next){
                mManager.nextMusic(getContext());
            }if(id == R.id.music_iv_cancel){
                mManager.exitMusic(getContext());
            }
        }
    };

}*/
