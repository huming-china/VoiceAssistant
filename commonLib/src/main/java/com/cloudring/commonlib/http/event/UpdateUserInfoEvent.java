package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.UpdatePhonePwdResponse;
import com.cloudring.commonlib.http.bean.UpdateUserInfoResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UpdateUserInfoEvent {
    public boolean isConnect;
    public UpdateUserInfoResponse response;

    public UpdateUserInfoEvent(boolean isConnect, UpdateUserInfoResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
