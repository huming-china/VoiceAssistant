package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.UserLoginResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UserLoginEvent {
    public boolean isConnect;
    public UserLoginResponse response;

    public UserLoginEvent(boolean isConnect, UserLoginResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
