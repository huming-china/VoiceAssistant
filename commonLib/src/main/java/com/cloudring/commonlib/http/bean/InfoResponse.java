package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by slx on 2016/10/8.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoResponse {
    @JsonProperty("action")
    public String action;
    @JsonProperty("errno")
    public String errno;
    @JsonProperty("err")
    public String err;
    @JsonProperty("tm")
    public String tm;

    @JsonProperty("list")
    public List<Data> list;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Data {
        @JsonProperty("catagoryName")
        public String catagoryName;
        @JsonProperty("title")
        public String title;
        @JsonProperty("url")
        public String url;
    }

}
