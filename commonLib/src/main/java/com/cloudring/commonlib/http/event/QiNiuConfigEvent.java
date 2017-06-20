package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.QiNiuConfigResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class QiNiuConfigEvent {
    public boolean isConnect;
    public QiNiuConfigResponse response;

    public QiNiuConfigEvent(boolean isConnect, QiNiuConfigResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
