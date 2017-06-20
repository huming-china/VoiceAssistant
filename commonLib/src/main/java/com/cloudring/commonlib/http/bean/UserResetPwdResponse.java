package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResetPwdResponse {
    @JsonProperty("message")
    public String message;
    @JsonProperty("code")
    public String code;
    @JsonProperty("data")
    public UserResetPwdResponseData data;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserResetPwdResponseData{

    }
}
