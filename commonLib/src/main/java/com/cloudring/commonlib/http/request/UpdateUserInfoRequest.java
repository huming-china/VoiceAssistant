package com.cloudring.commonlib.http.request;

import com.cloudring.commonlib.http.service.Api;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class UpdateUserInfoRequest {
    /*{
"data":{
"userId": "54a9e2df6ab2432c8e41c70b4d13dd18",
"userName":"张三",
"nickName":"吉娃娃",
"headPic":"213123.jpg"
 }
}
    }*/
    public UserInfo data;
    public UpdateUserInfoRequest(UserInfo data) {
        this.data = data;
    }

    public static class UserInfo{
        public String userId;
        public String userName;
        public String nickName;
        public String headPic;
        public String app_id= Api.APP_ID;
        public ExtendedJson extendedJson;

        public UserInfo(String userId, String userName, String nickName, String headPic) {
            this.userId = userId;
            this.userName = userName;
            this.nickName = nickName;
            this.headPic = headPic;
        }
        public static class ExtendedJson{
            public String height="";
            public String bodyWeight="";
            public String bloodType="";

        }
    }
}
