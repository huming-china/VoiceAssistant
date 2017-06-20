package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QiNiuConfigResponse {
//    {
//    "accessKey": "7tLSlQXYl60Eoc3AJpq43M",
//            "secretKey": "YCAIP16bc2iYahr3b",
//            "accessUrl ": "http://oawueeh1w.bkt.clouddn.com",
//            "token": "R3415FS45C5C1S"
    //    }
    @JsonProperty("code")
    public String code="-1";
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public QiNiuConfigResponse.DataEntity data;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataEntity {

        @JsonProperty("accessKey")
        public String accessKey;
        @JsonProperty("secretKey")
        public String secretKey;
        @JsonProperty("accessUrl")
        public String accessUrl;
        @JsonProperty("token")
        public String token;
    }
}
