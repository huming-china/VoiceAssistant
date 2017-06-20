package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterResponse {
    @JsonProperty("code")
    public String code;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public UserRegisterregistionData data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserRegisterregistionData{
        @JsonProperty("token")
        public String token;
        @JsonProperty("userId")
        public String userId;
        @JsonProperty("mobile")
        public String mobile;
        @JsonProperty("userName")
        public String userName;
        @JsonProperty("niceName")
        public String niceName;
        @JsonProperty("headPic")
        public String headPic;
    }
}