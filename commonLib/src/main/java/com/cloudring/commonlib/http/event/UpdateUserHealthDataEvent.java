package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.GetUserHealthDataResponse;
import com.cloudring.commonlib.http.bean.UpdateUserHealthDataResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UpdateUserHealthDataEvent {
    public boolean isConnect;
    public UpdateUserHealthDataResponse response;

    public UpdateUserHealthDataEvent(boolean isConnect, UpdateUserHealthDataResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
