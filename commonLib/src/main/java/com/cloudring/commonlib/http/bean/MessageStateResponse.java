package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageStateResponse {


    @JsonProperty("code")
    public String code;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public DataEntity data;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataEntity {

    }
}
