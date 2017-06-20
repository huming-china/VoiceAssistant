package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UserResetPwdRequest {
    public UserResetPwdRequestData data;

    public UserResetPwdRequest(UserResetPwdRequestData data) {
        this.data = data;
    }

    public static class UserResetPwdRequestData{
        public String mobile;
        public String captcha;
        public String password;
        public String userId;

        public UserResetPwdRequestData(String mobile, String captcha, String password, String userId) {
            this.mobile = mobile;
            this.captcha = captcha;
            this.password = password;
            this.userId = userId;
        }
    }
}
