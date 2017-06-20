package com.cloudring.commonlib.http.request;

import com.cloudring.commonlib.http.service.Api;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class ExitLoginRequest {
    /*"data":{
        "userId":"141101231927fd20e22e592949b6b05b",
                "msgId":"00e6640488ff420f92c02fccc99e5b47,99b5f22706b64557b334603322829029,···"
    }*/
    public ExitLogin data;
    public ExitLoginRequest(ExitLogin data) {
        this.data = data;
    }

    public static class ExitLogin{
        public String userId;
        public String app_id= Api.APP_ID;
        public String userToken;

        public ExitLogin(String userId, String userToken) {
            this.userId = userId;
            this.userToken = userToken;
        }
    }
}
