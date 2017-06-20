package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/11/22.
 *
 * @Description: ${todo}
 */

public class UpdateUserHealthDataRequest {
    public UserHealthData data;

    public UpdateUserHealthDataRequest(UserHealthData data) {
        this.data = data;
    }

    public static class UserHealthData{
        public String userId;
        public String weight;
        public String height;
        public String sex;
        public String birthday;

        public UserHealthData(String userId, String sex, String birthday, String height, String weight) {
            this.userId = userId;
            this.weight = weight;
            this.sex = sex;
            this.height = height;
            this.birthday = birthday;
        }
    }
}
