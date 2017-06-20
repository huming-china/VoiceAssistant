package com.cloudring.commonlib.http.bean;

/**
 * Created by slx on 2016/9/27.
 */

public interface ApkInfoCallBack<T> {

    public void onSuccess(T t);

    public void onFailure(T t);

}
