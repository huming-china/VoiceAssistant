package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class DeleteFamilyRequest {
    public FamilyData data;

    public DeleteFamilyRequest(FamilyData data) {
        this.data = data;
    }

    public static class FamilyData{
        public String userId;
        public String id;

        public FamilyData(String userId, String id) {
            this.userId = userId;
            this.id = id;
        }
    }
}
