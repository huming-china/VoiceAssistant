package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTokenResponse {

    @JsonProperty("code")
    public String code;

    @JsonProperty("message")
    public String message;

    @JsonProperty("data")
    public UpdateTokenData data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpdateTokenData{

        @JsonProperty("token")
        public String token;

        @JsonProperty("userId")
        public String userId;

        @JsonProperty("mobile")
        public String mobile;

        @JsonProperty("userName")
        public String userName;

        @JsonProperty("nickName")
        public String nickName;

        @JsonProperty("headPic")
        public String headPic;

    }
}
