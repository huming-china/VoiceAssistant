package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class FamilyListRequest {
    public FamilyData data;

    public FamilyListRequest(FamilyData data) {
        this.data = data;
    }

    public static class FamilyData{
        public String userId;
        public String msgType;

        public FamilyData(String userId, String msgType) {
            this.userId = userId;
            this.msgType = msgType;
        }
    }
}
