package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.GetUserHealthDataResponse;
import com.cloudring.commonlib.http.bean.QiNiuConfigResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class GetUserHealthDataEvent {
    public boolean isConnect;
    public GetUserHealthDataResponse response;

    public GetUserHealthDataEvent(boolean isConnect, GetUserHealthDataResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
