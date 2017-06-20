package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangePhoneMirrorTokenResponse {
//    {
//        "message": "成功",
//            "data": {
//        "token": "07c331de-a920-4278-aeec-31e9ad03ddfc"
//    },
//        "code": "0"
//    }
    @JsonProperty("code")
    public String code;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public ExchangePhoneMirrorTokenResponse.DataEntity data;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataEntity {
        @JsonProperty("token")
        public String token;
    }
}
