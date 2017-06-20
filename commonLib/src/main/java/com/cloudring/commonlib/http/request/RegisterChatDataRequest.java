package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/22.
 *
 * @Description: ${todo}
 */

public class RegisterChatDataRequest {
    public RegisterChatData data;

    public RegisterChatDataRequest(RegisterChatData data) {
        this.data = data;
    }

    public static class RegisterChatData{
        public String username;
        public String password;
        public String nickname;

        public RegisterChatData(String username, String nickname, String password) {
            this.username = username;
            this.nickname = nickname;
            this.password = password;
        }
    }
}
