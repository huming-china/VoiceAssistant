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
public class GetUserHealthDataResponse {

    @JsonProperty("code")
    public String code;

    @JsonProperty("message")
    public String message;

    @JsonProperty("data")
    public GetUserHealthData data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetUserHealthData implements Serializable{

        @JsonProperty("sex")
        public String sex;

        @JsonProperty("birthday")
        public String birthday;

        @JsonProperty("weight")
        public String weight;

        @JsonProperty("height")
        public String height;

    }
}
