package com.cloudring.magicsound.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cloudring.magicsound.R;

/**
 * Created by zengpeijin on 2016/8/18.
 */
public class CookBookFragment extends Fragment {
    private String url;
    private WebView webView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        url = getArguments().getString("detailUrl", null);
        view = inflater.inflate(R.layout.fragment_cookbook, null);
        webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        if (url != null) {
            webView.loadUrl(url);
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });
        }
        return view;
    }

}
