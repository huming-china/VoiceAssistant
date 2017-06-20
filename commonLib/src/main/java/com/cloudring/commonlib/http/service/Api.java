package com.cloudring.commonlib.http.service;

/**
 * 请求接口
 * Created by zjq on 2016/7/14.
 */
public interface Api {
    //用户注册相关网址
    String BASE_URL = "http://www.cloudring.net/cloud/";
    String BASE_URL_LOCAL_TEST = "http://192.168.1.111:8080/cloud/";
    //用户注册相关网址
    String REGIST = "appUser/userRegist.do?";
    String LOGIN = "appUser/appUserLogin.do?";
    String VALIDATE = "appUser/verifyCode.do?";
    String CAPTCHA = "appUser/sendMSM.do?";
    String RESETPWD = "appUser/updatePwd.do?";
    //个人中心相关网址
    String USER_INFO = "appUser/appUserCenter.do?";
    String EDIT_INFO = "appUser/appUserDataEdit.do?";
    String PERMISSION = "appUser/appUserToRole.do?";
    String SEARCH = "appUser/appUserRoleNumberSearch.do?";
    String VERIFY_PASSWORD = "appUser/queryPwd.do?";
    String UPDATA_QRCODE = "appUser/userLoginScannedQRCode.do?";
    String RESET_DEVICE = "appUser/resetDevice.do?";
    String LOGIN_CHECK = "http://cloudlinks.cn/Users/LoginCheck.ashx";
    String REGISTER_CHECK = "http://cloudlinks.cn/Users/RegisterCheck.ashx";
    String URL_ESB_WEB = "http://192.168.1.113:8080/CloudringEsbWeb/proxy/";
    /**
     * 轮播图网址
     */
    String POSTER_URL = "http://lamp.cloudring.net/lamp/proxy/get";
   // String CONFIG_APK_URL = "http://120.24.209.167/lamp/proxy/get";
    String CONFIG_APK_URL = "http://120.24.209.167/lamp/proxy/get?";
    //String CONFIG_APK_URL = "http://lamp.cloudring.net/lamp/proxy/get";

    String BASE_URL_TEST = "http://fge.cloudring.net:8888/cloudring-user-center-interface-web/userInter/1.0/userRegistration?machineCode=1449640100146877&networkType=wifi&screen=1080x1920 &clientVersion=4.2.0&client=1&\n" +
            "d_brand=HUAWEI";
    final static String APP_ID="40c4c50d7bc3442bb8579aafb692d517";
    final static String APP_KEY="D9woJN6TB0iaD+F1nJFMEXKexp8teRzp5F31k5w1SRp83oRTpOIE/A==";

}
