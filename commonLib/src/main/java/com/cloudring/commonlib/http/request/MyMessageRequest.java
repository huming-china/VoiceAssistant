package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class MyMessageRequest {
    public MyMessage data;

    public MyMessageRequest(MyMessage data) {
        this.data = data;
    }

    public static class MyMessage{
        public String userId;
        public String msgType;
        public String pageNumber;
        public String pageSize;

        public MyMessage(String userId, String msgType, String pageNumber, String pageSize) {
            this.userId = userId;
            this.msgType = msgType;
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }
    }
}
