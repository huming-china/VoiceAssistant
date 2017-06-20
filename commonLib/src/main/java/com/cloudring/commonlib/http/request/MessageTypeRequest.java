package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class MessageTypeRequest {
    public MessageType data;

    public MessageTypeRequest(MessageType data) {
        this.data = data;
    }

    public static class MessageType{
        public String userId;
        public MessageType(String userId) {
            this.userId = userId;
        }
    }
}
