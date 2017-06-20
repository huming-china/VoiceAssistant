package com.cloudring.commonlib.http.request;

import com.cloudring.commonlib.http.service.Api;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class UpdatePhonePwdRequest {
    /*"data":{
        "userId":"141101231927fd20e22e592949b6b05b",
                "msgId":"00e6640488ff420f92c02fccc99e5b47,99b5f22706b64557b334603322829029,···"
    }*/
    public PhonePwd data;
    public UpdatePhonePwdRequest(PhonePwd data) {
        this.data = data;
    }

    public static class PhonePwd{
        public String userId;
        public String app_id= Api.APP_ID;
        public String oldPassword;
        public String newPassword;

        public PhonePwd(String userId, String oldPassword, String newPassword) {
            this.userId = userId;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }
    }
}
