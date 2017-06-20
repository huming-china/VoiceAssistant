package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by slx on 2016/8/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse {
    public final static int NETWORK_ERROR=-1;
    public final static int SUCCESS=0;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class  PosterInfo {
        /**
         * action : page
         * errno : 0
         * err :
         * tm : 20160612122104
         * list : [{"no":1,"imageUrl":"http://localhost","Md5":"111","type":"1","apkUrl":"","apkName":"","apkVersion":"","pageUrl":"http://www.sina.com.cn"},{"no":"2","imageUrl":"http://localhost2","Md5":"222","type":"0","apkUrl":"http://apkurl","apkName":"apk1","apkVersion":"1.1","pageUrl":""}]
         */
        @JsonProperty("errno")
        public int errno;
        @JsonProperty("msg")
        public String msg;
        @JsonProperty("data")
        public Data data;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("total")
        public int total;
        @JsonProperty("deals")
        public List<Deals> deals;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Deals {
        @JsonProperty("deal_id")
        public int deal_id;
        @JsonProperty("image")
        public String image;
        @JsonProperty("tiny_image")
        public String tiny_image;
        @JsonProperty("title")
        public String title;
        @JsonProperty("description")
        public String description;


        @JsonProperty("market_price")
        public int market_price;
        @JsonProperty("current_price")
        public int current_price;
        @JsonProperty("promotion_price")
        public int promotion_price;
        @JsonProperty("publish_time")
        public String publish_time;
        @JsonProperty("deal_murl")
        public String deal_murl;


    }

}