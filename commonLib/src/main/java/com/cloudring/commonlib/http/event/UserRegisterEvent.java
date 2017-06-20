package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.UserRegisterResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UserRegisterEvent {
    public boolean isConnectSuccess;
    public UserRegisterResponse response;

    public UserRegisterEvent(boolean isConnectSuccess, UserRegisterResponse response) {
        this.isConnectSuccess = isConnectSuccess;
        this.response=response;
    }
}
