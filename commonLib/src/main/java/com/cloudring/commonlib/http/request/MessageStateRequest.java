package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class MessageStateRequest {
    public MessageState data;

    public MessageStateRequest(MessageState data) {
        this.data = data;
    }

    public static class MessageState{
        public String userId;
        public String msgType;


        public MessageState(String userId,String msgType) {
            this.userId = userId;
            this.msgType=msgType;
        }
    }
}
