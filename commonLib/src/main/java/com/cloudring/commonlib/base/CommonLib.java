package com.cloudring.commonlib.base;

import android.content.Context;

import com.cloudring.commonlib.utils.ToastUtil;

/**
 * Created by slx on 2016/8/9.
 */
public class CommonLib {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        ToastUtil.init(context);
    }

    public static Context getContext() {
        return mContext;
    }
}
