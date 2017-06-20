package com.cloudring.commonlib.http.request;

import com.cloudring.commonlib.http.service.Api;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UserLoginRequest {
    public UserLoginData data;

    public UserLoginRequest(UserLoginData data) {
        this.data = data;
    }

    public static class UserLoginData{
        public String mobile;
        public String deviceType="1";
        public String password;
        public String app_key= Api.APP_KEY;
        public String app_id=Api.APP_ID;
        public String userId;

        public UserLoginData(String mobile, String password, String userId) {
            this.mobile = mobile;
            this.password = password;
            this.userId = userId;
        }
    }
}
