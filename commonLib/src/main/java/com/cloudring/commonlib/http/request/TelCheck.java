package com.cloudring.commonlib.http.request;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class TelCheck {
    private Phone data;

    public TelCheck(Phone data) {
        this.data = data;
    }

    public Phone getData() {
        return data;
    }

    public void setData(Phone data) {
        this.data = data;
    }

    public  static  class  Phone{
        private String mobile;
        private String userId;

        public Phone(String mobile, String userId) {
            this.mobile = mobile;
            this.userId = userId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
