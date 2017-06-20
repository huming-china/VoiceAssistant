package com.cloudring.magicsound.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by tomqian on 2016/5/5.
 */
public abstract class BaseRobotFragment extends Fragment {
    protected View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rootView=inflater.inflate(getLayoutId(),container,false);
        initView();
        return rootView;
    }
    protected abstract int getLayoutId();
    protected abstract void initView();


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
