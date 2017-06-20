package com.cloudring.commonlib.http.service;

import com.cloudring.commonlib.http.bean.APIResponse;
import com.cloudring.commonlib.http.bean.AddFamilyResponse;
import com.cloudring.commonlib.http.bean.DeleteFamilyResponse;
import com.cloudring.commonlib.http.bean.FamilyListResponse;
import com.cloudring.commonlib.http.bean.PhoneCheckRepsonse;
import com.cloudring.commonlib.http.bean.PhoneLoginThirdPartyResponse;

import com.cloudring.commonlib.http.bean.ApkInfoResponse;
import com.cloudring.commonlib.http.bean.PosterResponse;
import com.cloudring.commonlib.http.bean.UserCenterResponse;
import com.cloudring.commonlib.http.bean.UserLoginResponse;
import com.cloudring.commonlib.http.bean.UserRegisterResponse;
import com.cloudring.commonlib.http.bean.UserResetPwdResponse;
import com.cloudring.commonlib.http.bean.WeatherResponse;
import com.cloudring.commonlib.http.request.AddFamilyRequest;
import com.cloudring.commonlib.http.request.DeleteFamilyRequest;
import com.cloudring.commonlib.http.request.FamilyListRequest;
import com.cloudring.commonlib.http.request.TelCheck;
import com.cloudring.commonlib.http.request.UserLoginRequest;
import com.cloudring.commonlib.http.request.UserRegisterRequest;
import com.cloudring.commonlib.http.request.UserResetPwdRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zjq
 * @version V2.0
 * @Package com.cloudring.magic.poster.http.service
 * @Description: 轮播图的APi
 * @date 2016/7/31 10
 */
public interface CloudringService {

    /*获取百度糯米团购信息*/
    @Headers("apikey:cec4e963be2a3073785eeaa9c3aa237f")
    @GET("http://apis.baidu.com/baidunuomi/openapi/searchdeals")
    Call<APIResponse.PosterInfo> getNuomiList(@Query("city_id") String city_id, @Query("keyword") String keyword, @Query("location") String location);

    /*获取轮播图的广告图片*/
    @Headers("apikey:cec4e963be2a3073785eeaa9c3aa237f")
    @GET(Api.POSTER_URL)
    Call<PosterResponse.PosterInfo> getPosterImageUrls(@Query("params") String params);

    /*获取配置的APK*/
    @Headers("apikey: cec4e963be2a3073785eeaa9c3aa237f")
    @GET(Api.CONFIG_APK_URL)
    Call<ApkInfoResponse.ApkInfo> getConfigApkInfo(@Query("params") String params);

    //==================================================================================================================================================
    /*百度未来4天天气的api*/
    @Headers("apikey:cec4e963be2a3073785eeaa9c3aa237f")
    @GET("http://apis.baidu.com/apistore/weatherservice/recentweathers")
    Call<WeatherResponse.FourDay> getFourDayWeatherData(@Query("cityname") String city);

    //==================================================================================================================================================
    @FormUrlEncoded
    @POST(Api.REGIST)
    Call<UserCenterResponse.Register> register(@Field("userName") String phone, @Field("passWord") String password, @Field("code") String code);


    @POST("http://fge.cloudring.net:8888/cloudring-user-center-interface-web/userInter/1.0/sendCodeToPhone")
    Call<PhoneCheckRepsonse> getMsgCode(@Body TelCheck telCheck);


    @POST("http://fge.cloudring.net:8888/cloudring-user-center-interface-web/userInter/1.0/moblieLogin")
    Call<UserLoginResponse> login(@Body UserLoginRequest userLoginRequest);

    @FormUrlEncoded
    @POST(Api.USER_INFO)
    Call<UserCenterResponse.UserInfo> getUserInfo(@Field("userId") String userId);

    @FormUrlEncoded
    @POST(Api.SEARCH)
    Call<UserCenterResponse.Simple> searchNumber(@Field("userId") String userId, @Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST(Api.PERMISSION)
    Call<UserCenterResponse.Simple> getPremission(@Field("userId") String userId,
                                                  @Field("command") String command,
                                                  @Field("childPhone") String childPhone,
                                                  @Field("permission") String permission,
                                                  @Field("remark") String remark);

    @FormUrlEncoded
    @POST(Api.PERMISSION)
    Call<UserCenterResponse.Simple> deletePremission(@Field("userId") String userId,
                                                     @Field("command") String command,
                                                     @Field("childPhone") String childPhone,
                                                     @Field("permission") String permission,
                                                     @Field("remark") String remark);

    @FormUrlEncoded
    @POST(Api.PERMISSION)
    Call<UserCenterResponse.Simple> editPremission(@Field("userId") String userId,
                                                   @Field("command") String command,
                                                   @Field("childPhone") String childPhone,
                                                   @Field("permission") String permission,
                                                   @Field("remark") String remark);

    @FormUrlEncoded
    @POST(Api.EDIT_INFO)
    Call<UserCenterResponse.EditInfo> editUserInfo(@Field("userId") String userId,
                                                   @Field("phoneNumber") String phoneNumber,
                                                   @Field("nickName") String nickName,
                                                   @Field("sex") String sex,
                                                   @Field("headPortrait") String headPortrait,
                                                   @Field("location") String location);

    @FormUrlEncoded
    @POST(Api.VERIFY_PASSWORD)
    Call<UserCenterResponse.Simple>  verifyPassword(@Field("userId") String userId, @Field("password") String password);


    @POST("http://fge.cloudring.net:8888/cloudring-user-center-interface-web/userInter/1.0/updateNewPassword")
    Call<UserResetPwdResponse> resetPassword(@Body UserResetPwdRequest userResetPwdRequest);

    @FormUrlEncoded
    @POST(Api.VALIDATE)
    Call<UserCenterResponse.Verify> validateMsgCode(@Field("tel") String tel, @Field("code") String code);

    @FormUrlEncoded
    @POST(Api.UPDATA_QRCODE)
    Call<UserCenterResponse.QRCode> updataQRCodeInfo(@Field("userName") String userName,
                                                     @Field("passWord") String passWord,
                                                     @Field("boxId") String boxId);

    @FormUrlEncoded
    @POST(Api.RESET_DEVICE)
    Call<UserCenterResponse.Simple> resetDevice(@Field("boxId") String boxId);

    //==================================================================================================================================================

    //add by Manna
    @POST("http://fge.cloudring.net:8888/cloudring-user-center-interface-web/userInter/1.0/userRegistration")
    Call<UserRegisterResponse> registion(@Body UserRegisterRequest userRegisterRequest);

//    http://183.16.192.217:8080/cloudring-user-center-interface-web/userInter/1.0/toPhoneLoginThirdParty

    @POST("http://192.168.1.113:8080/cloudring-user-center-interface-web/userInter/1.0/toPhoneLoginThirdParty?token=2cb64350d2604cba8dd55f8c303b668e")
    Call<PhoneLoginThirdPartyResponse> thirdPartyLogin(@Body TelCheck telCheck);

    @POST("http://192.168.1.140:8080/cloudring-property-mobile-web/myFamily/1.0/findMyFamilyList")
    Call<FamilyListResponse> getFamilyList(@Body FamilyListRequest request);

    @POST("http://192.168.1.140:8080/cloudring-property-mobile-web/myFamily/1.0/addMyFamily")
    Call<AddFamilyResponse> addFamilyNumber(@Body AddFamilyRequest request);

    @POST("http://192.168.1.140:8080/cloudring-property-mobile-web/myFamily/1.0/deleteMyFamilyById")
    Call<DeleteFamilyResponse> deleteFamilyNumber(@Body DeleteFamilyRequest request);
}
