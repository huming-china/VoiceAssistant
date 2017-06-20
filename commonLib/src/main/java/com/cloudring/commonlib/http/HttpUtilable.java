package com.cloudring.commonlib.http;

import com.cloudring.commonlib.http.request.TelCheck;

/**
 * Created by zjq on 2016/7/15.
 */
public interface HttpUtilable {

    interface Poster {
        /**
         * 异步获取轮播图的广告图片
         *
         * @param DesData 加密数据
         */
        void getPosterImageUrls(String DesData);

        /**
         * 异步获取配置的APK信息
         * @param DesData
         */
       void getConfigApkInfo(String DesData);
    }

    interface Weather {
        /**
         * 异步获取四天天气数据
         * @param cityname 城市名  中文
         */
        void getFourDayWeatherData(String cityname);
    }


    interface Users {
        /**
         * 异步获取手机短信验证码
         *
         * @param telCheck 手机号
         */
        void getVerificationCode(TelCheck telCheck);

        /**
         * 异步用户注册
         *
         * @param tel      手机号
         * @param password 密码
         * @param code     短信验证码
         */
        void register(final String tel, final String password, String code);
        /**
         * 异步用户注册
         *
         * @param tel      手机号
         * @param password 密码
         * @param code     短信验证码
         */
        void registion(final String tel, final String password, String code,String usedId);

        /**
         * 异步用户登录
         *
         * @param telPhone 手机号
         * @param password 密码
         */
        void login(String telPhone, String password,String usedId);

        /**
         * 异步重置密码
         *
         * @param password 密码
         * @param phoneNum 手机号
         */
        void resetPassword(String phoneNum,final String password,String captcha ,String usedId);

        /**
         * 异步验证短信验证码
         *
         * @param tel  手机号
         * @param code 短信验证码
         */
//        void validateMsgCode(String tel, String code);

        /**
         * 异步修改用户资料
         *
         * @param userId   用户ID
         * @param phone    手机号
         * @param nickName 昵称
         * @param sex      性别
         * @param icon     头像
         * @param local    位置
         */
        void editUserInfo(String userId, String phone, String nickName, final String sex, String icon, String local,int requestCode);

        /**
         * 异步获取用户信息
         *
         * @param
         */
        void getUserInfo(String userId);

        /***
         * 获取百度糯米团购
         * @param city_id 城市id
         * @param keyword 关键词
         * @param location 坐标
         */
        void getNuomiList(String city_id,String keyword,String location);

		/***
         * 第三方登录
         */
        void thirdPartyLogin(TelCheck telCheck);

        /**
         * 获取家人信息列表
         * @param userId
         * @param msgType
         */
        void getFamilyList(String userId,String msgType);

        /**
         * 添加家人
         * @param userId
         * @param tel
         */
        void addFamilyNumber(String userId,String tel);

        /**
         * 删除家人信息
         * @param userId
         * @param id
         */
        void deleteFamilyNumber(String userId,String id);
    }

}
