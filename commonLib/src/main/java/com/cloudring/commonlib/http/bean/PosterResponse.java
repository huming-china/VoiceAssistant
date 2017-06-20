package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by slx on 2016/8/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PosterResponse {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PosterInfo {
        /**
         * action : page
         * errno : 0
         * err :
         * tm : 20160612122104
         * list : [{"no":1,"imageUrl":"http://localhost","Md5":"111","type":"1","apkUrl":"","apkName":"","apkVersion":"","pageUrl":"http://www.sina.com.cn"},{"no":"2","imageUrl":"http://localhost2","Md5":"222","type":"0","apkUrl":"http://apkurl","apkName":"apk1","apkVersion":"1.1","pageUrl":""}]
         */
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

        /**
         * no : 1
         * imageUrl : http://localhost
         * Md5 : 111
         * type : 1
         * apkUrl :
         * apkName :
         * apkVersion :
         * pageUrl : http://www.sina.com.cn
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Data {

            public int no;
            @JsonProperty("imageUrl")
            public String imageUrl;
            @JsonProperty("Md5")
            public String Md5;
            @JsonProperty("type")
            public String type;
            @JsonProperty("apkUrl")
            public String apkUrl;
            @JsonProperty("apkName")
            public String apkName;
            @JsonProperty("apkVersion")
            public String apkVersion;
            @JsonProperty("pageUrl")
            public String pageUrl;

        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameter {
        /**
         * action : page
         * pm : HRS1
         * po : A1
         * version: 2.0
         */
        @JsonProperty("action")
        public String action;
        @JsonProperty("pm")
        public String pm;
        @JsonProperty("po")
        public String po;
        @JsonProperty("version")
        public String version;
    }
}