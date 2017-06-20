package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.UserResetPwdResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UserResetPwdEvent {
    public boolean isConnect;
    public UserResetPwdResponse response;

    public UserResetPwdEvent(boolean isConnect, UserResetPwdResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
