package com.cloudring.magicsound.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.cloudring.magicsound.R;

/**
 * 语音下载文件进度的对话框
 * Created by hm on 2017/1/16.
 */

public class DownloadVoiceFragment extends DialogFragment {
    private View view;
    private ProgressBar progressBar;

    public void setProgress(float progress) {
        if (progressBar != null) {
            progressBar.setProgress((int) (progress * 100));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alert_voice_download, null);
        initView();
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private void initView() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }
}
