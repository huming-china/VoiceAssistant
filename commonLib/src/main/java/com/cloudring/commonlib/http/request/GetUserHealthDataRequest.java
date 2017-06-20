package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/22.
 *
 * @Description: ${todo}
 */

public class GetUserHealthDataRequest {
    public UserHealthData data;

    public GetUserHealthDataRequest(UserHealthData data) {
        this.data = data;
    }

    public static class UserHealthData{
        public String userId;

        public UserHealthData(String userId) {
            this.userId = userId;
        }
    }
}
