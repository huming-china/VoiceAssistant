package com.cloudring.commonlib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.cloudring.commonlib.base.CommonLib;

/**
 * Created by lee on 15/12/11.
 */
public class KeyBoardUtil {

    private static KeyBoardUtil instance;
    private InputMethodManager mInputMethodManager;

    private KeyBoardUtil() {
        mInputMethodManager = (InputMethodManager) CommonLib.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static KeyBoardUtil getInstance() {
        if (instance == null) {
            instance = new KeyBoardUtil();
        }
        return instance;
    }

    /**
     * 强制显示输入法
     */
    public void show(Activity activity) {
        show(activity.getWindow().getCurrentFocus());
    }

    public void show(View view) {
        mInputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 强制关闭输入法
     */
    public void hide(Activity activity) {
        hide(activity.getWindow().getCurrentFocus());
    }

    public void hide(View view) {
        if (mInputMethodManager != null && view != null)
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 如果输入法已经显示，那么就隐藏它；如果输入法现在没显示，那么就显示它
     */
    public void showOrHide() {
        if (mInputMethodManager != null)
            mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
