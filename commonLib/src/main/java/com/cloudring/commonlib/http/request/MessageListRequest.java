package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class MessageListRequest {
//    "data":{
//        "userId":"123456"，
//        "msgType":" 1",
//                "pageNumber":" 10",
//                "pageSize": "1"
//    }
    public MessageList data;
    public MessageListRequest(MessageList data) {
        this.data = data;
    }

    public static class MessageList{
        public String userId;
        public String msgType;
        public String pageNumber;
        public String pageSize;

        public MessageList(String userId, String msgType, String pageNumber, String pageSize) {
            this.userId = userId;
            this.msgType = msgType;
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }
    }
}
