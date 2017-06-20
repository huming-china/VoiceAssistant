package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterChatDataResponse {

    @JsonProperty("code")
    public String code;

    @JsonProperty("message")
    public String message;

    @JsonProperty("data")
    public RegisterChatData data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RegisterChatData implements Serializable{

    }
}
