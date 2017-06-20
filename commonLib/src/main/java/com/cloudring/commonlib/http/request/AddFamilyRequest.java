package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/7.
 *
 * @Description: ${todo}
 */

public class AddFamilyRequest {
    public FamilyData data;

    public AddFamilyRequest(FamilyData data) {
        this.data = data;
    }

    public static class FamilyData{
        public String userId;
        public String mobile;

        public FamilyData(String userId, String mobile) {
            this.userId = userId;
            this.mobile = mobile;
        }
    }
}
