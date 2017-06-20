package com.cloudring.commonlib.http.bean;

import com.cloudring.commonlib.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by zjq on 2016/7/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCenterResponse {
    //注册
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Register extends BaseResponse {
        @JsonProperty("userId")
        public String userId;
        @JsonProperty("hint")
        public String hint;
    }

    //登录
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Login extends BaseResponse {
        @JsonProperty("userId")
        public String userId;
        @JsonProperty("gids")
        public String gids;
        @JsonProperty("pid")
        public String pid;
        @JsonProperty("headPortrait")
        public String avatar;
        @JsonProperty("niceName")
        public String niceName;
        @JsonProperty("hint")
        public String hint;
    }

    //用户信息
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfo extends BaseResponse {
        @JsonProperty("phone")
        public String phone;
        @JsonProperty("headPortrait")
        public String headPortrait;
        @JsonProperty("sex")
        public String sex;
        @JsonProperty("address")
        public String address;
        @JsonProperty("nickName")
        public String nickName;
    }

    //搜索手机
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchPhone extends BaseResponse {
        @JsonProperty("hint")
        public String hint;
        @JsonProperty("userList")
        public List<MemberInfo> userList;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class MemberInfo extends BaseResponse {
            @JsonProperty("sex")
            public String sex;
            @JsonProperty("phoneNumber")
            public String phoneNumber;
            @JsonProperty("location")
            public String location;
            @JsonProperty("userId")
            public String userId;
            @JsonProperty("nickName")
            public String nickName;
            @JsonProperty("headPortrait")
            public String headPortrait;
        }
    }

    //编辑用户信息
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EditInfo extends BaseResponse {
        @JsonProperty("headPortrait")
        public String avatar;
        @JsonProperty("phone")
        public String phone;
        @JsonProperty("sex")
        public String sex;
        @JsonProperty("address")
        public String address;
        @JsonProperty("nickName")
        public String nickName;
    }

    //修改用户信息
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Verify extends BaseResponse {
        @JsonProperty("hint")
        public String hint;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QRCode extends BaseResponse {
        @JsonProperty("hint")
        public String hint;
        @JsonProperty("pid")
        public String pid;
    }
        //空方法
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Simple extends BaseResponse {

    }
}
