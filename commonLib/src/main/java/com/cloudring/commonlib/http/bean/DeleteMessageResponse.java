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
public class DeleteMessageResponse {
    /*｛/*code: "0",
    message: "成功",
    data: {
    }｝*/
    @JsonProperty("code")
    public String code;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public DeleteMessageResponse.DataEntity data;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataEntity {

    }
}
