package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class DeleteMessageRequest {
    /*"data":{
        "userId":"141101231927fd20e22e592949b6b05b",
                "msgId":"00e6640488ff420f92c02fccc99e5b47,99b5f22706b64557b334603322829029,···"
    }*/
    public DeleteMessage data;
    public DeleteMessageRequest(DeleteMessage data) {
        this.data = data;
    }

    public static class DeleteMessage{
        public String userId;
        public String msgId;

        public DeleteMessage(String userId,String msgId) {
            this.msgId = msgId;
            this.userId = userId;
        }
    }
}
