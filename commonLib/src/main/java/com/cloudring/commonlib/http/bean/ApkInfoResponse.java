package com.cloudring.commonlib.http.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by slx on 2016/9/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApkInfoResponse {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApkInfo {
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
        public List<ApkInfoResponse.ApkInfo.Data> list;

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
            @JsonProperty("no")
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
            @JsonProperty("mark")
            public String mark;
            @JsonProperty("catagoryId")
            public String catagoryId;
            @JsonProperty("catagoryName")
            public String catagoryName;

            public boolean isTitle;

            public boolean isTitle() {
                return isTitle;
            }

            public void setTitle(boolean title) {
                isTitle = title;
            }

            public void setNo(int no) {
                this.no = no;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public void setMd5(String md5) {
                Md5 = md5;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setApkUrl(String apkUrl) {
                this.apkUrl = apkUrl;
            }

            public void setApkName(String apkName) {
                this.apkName = apkName;
            }

            public void setApkVersion(String apkVersion) {
                this.apkVersion = apkVersion;
            }

            public void setPageUrl(String pageUrl) {
                this.pageUrl = pageUrl;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public void setCatagoryId(String catagoryId) {
                this.catagoryId = catagoryId;
            }

            public void setCatagoryName(String catagoryName) {
                this.catagoryName = catagoryName;
            }

            public int getNo() {
                return no;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public String getMd5() {
                return Md5;
            }

            public String getType() {
                return type;
            }

            public String getApkUrl() {
                return apkUrl;
            }

            public String getApkName() {
                return apkName;
            }

            public String getApkVersion() {
                return apkVersion;
            }

            public String getPageUrl() {
                return pageUrl;
            }

            public String getMark() {
                return mark;
            }

            public String getCatagoryId() {
                return catagoryId;
            }

            public String getCatagoryName() {
                return catagoryName;
            }

            @Override
            public String toString() {
                return "Data{" +
                        "no=" + no +
                        ", imageUrl='" + imageUrl + '\'' +
                        ", Md5='" + Md5 + '\'' +
                        ", type='" + type + '\'' +
                        ", apkUrl='" + apkUrl + '\'' +
                        ", apkName='" + apkName + '\'' +
                        ", apkVersion='" + apkVersion + '\'' +
                        ", pageUrl='" + pageUrl + '\'' +
                        ", mark='" + mark + '\'' +
                        ", catagoryId='" + catagoryId + '\'' +
                        ", catagoryName='" + catagoryName + '\'' +
                        ", isTitle=" + isTitle +
                        '}';
            }
        }


        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getErrno() {
            return errno;
        }

        public void setErrno(String errno) {
            this.errno = errno;
        }

        public String getErr() {
            return err;
        }

        public void setErr(String err) {
            this.err = err;
        }

        public String getTm() {
            return tm;
        }

        public void setTm(String tm) {
            this.tm = tm;
        }

        public List<Data> getList() {
            return list;
        }

        public void setList(List<Data> list) {
            this.list = list;
        }
    }

}
