package com.cloudring.commonlib.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * desc:
 * Created by FJTK_ZCQ on 2016/7/25.
 */
public class FragmentUtil {
    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment, int frameId) {
        if (fragmentManager==null||fragment==null)
        {
            throw new NullPointerException("fragmentManager or fragment could not null");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        // transaction.addToBackStack("123");
        transaction.commit();
    }
}
