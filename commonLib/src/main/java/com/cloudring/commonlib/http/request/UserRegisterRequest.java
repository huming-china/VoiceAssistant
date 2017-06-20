package com.cloudring.commonlib.http.request;

import com.cloudring.commonlib.http.service.Api;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UserRegisterRequest {
    public UserRegisterData data;

    public UserRegisterRequest(UserRegisterData data) {
        this.data = data;
    }

    public static class UserRegisterData{
        public String app_id= Api.APP_ID;
        public String app_key=Api.APP_KEY;
        public String mobile;
        public String captcha;
        public String password;
        public String userId;

        public UserRegisterData(String mobile, String captcha, String password, String userId) {
            this.mobile = mobile;
            this.captcha = captcha;
            this.password = password;
            this.userId = userId;
        }
    }
}
