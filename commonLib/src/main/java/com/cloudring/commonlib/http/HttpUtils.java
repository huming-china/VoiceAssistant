package com.cloudring.commonlib.http;


import android.util.Log;

import com.cloudring.commonlib.http.bean.APIResponse;
import com.cloudring.commonlib.http.bean.AddFamilyResponse;
import com.cloudring.commonlib.http.bean.ApkInfoResponse;
import com.cloudring.commonlib.http.bean.DeleteFamilyResponse;
import com.cloudring.commonlib.http.bean.FamilyListResponse;
import com.cloudring.commonlib.http.bean.HttpRequestRepsonse;
import com.cloudring.commonlib.http.bean.PhoneCheckRepsonse;
import com.cloudring.commonlib.http.bean.PhoneLoginThirdPartyResponse;
import com.cloudring.commonlib.http.bean.PosterResponse;
import com.cloudring.commonlib.http.bean.UserCenterResponse;
import com.cloudring.commonlib.http.bean.UserLoginResponse;
import com.cloudring.commonlib.http.bean.UserRegisterResponse;
import com.cloudring.commonlib.http.bean.UserResetPwdResponse;
import com.cloudring.commonlib.http.bean.WeatherResponse;
import com.cloudring.commonlib.http.event.AddFamilyEvent;
import com.cloudring.commonlib.http.event.DeleteFamilyEvent;
import com.cloudring.commonlib.http.event.FamilyListEvent;
import com.cloudring.commonlib.http.event.UserLoginEvent;
import com.cloudring.commonlib.http.event.UserRegisterEvent;
import com.cloudring.commonlib.http.event.UserResetPwdEvent;
import com.cloudring.commonlib.http.manager.RetrofitManager;
import com.cloudring.commonlib.http.request.AddFamilyRequest;
import com.cloudring.commonlib.http.request.DeleteFamilyRequest;
import com.cloudring.commonlib.http.request.FamilyListRequest;
import com.cloudring.commonlib.http.request.TelCheck;
import com.cloudring.commonlib.http.request.UserLoginRequest;
import com.cloudring.commonlib.http.request.UserRegisterRequest;
import com.cloudring.commonlib.http.request.UserResetPwdRequest;
import com.cloudring.commonlib.http.service.CloudringService;
import com.cloudring.commonlib.utils.LampLog;
import com.cloudring.commonlib.utils.MD5Utils;
import com.cloudring.commonlib.utils.SpName;
import com.cloudring.commonlib.utils.SpUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by zjq on 2016/7/15.
 */
public class HttpUtils implements HttpUtilable.Poster, HttpUtilable.Users, HttpUtilable.Weather {
    private static HttpUtils mInstance;
    private final CloudringService mNetService;
    private static final String TAG = "HttpUtils";

    /**
     * 单例
     *
     * @return HttpUtils
     */
    public static HttpUtils newInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }

    private HttpUtils() {
        mNetService = RetrofitManager.getNetService();

    }


    /**
     * 异步获取手机短信验证码
     *
     * @param telCheck 手机号
     */
    public void getVerificationCode(TelCheck telCheck) {
        mNetService.getMsgCode(telCheck).enqueue(new Callback<PhoneCheckRepsonse>() {

            @Override
            public void onResponse(Call<PhoneCheckRepsonse> call, Response<PhoneCheckRepsonse> response) {
                PhoneCheckRepsonse phoneCheckRepsonse = response.body();

                EventBus.getDefault().post(new VerificationReceiverEvent(true, phoneCheckRepsonse));

            }

            @Override
            public void onFailure(Call<PhoneCheckRepsonse> call, Throwable t) {

                EventBus.getDefault().post(new VerificationReceiverEvent(false, null));
            }
        });
    }

    /**
     * 异步用户注册
     *
     * @param tel  手机号
     * @param pwd  密码
     * @param code 短信验证码
     */
    public void register(final String tel, final String pwd, String code) {
        mNetService.register(tel, MD5Utils.getMd5(pwd), code).enqueue(new Callback<UserCenterResponse.Register>() {
            @Override
            public void onResponse(Call<UserCenterResponse.Register> call, Response<UserCenterResponse.Register> response) {
                HttpRequestRepsonse resultCode = new HttpRequestRepsonse(HttpRequestRepsonse.KEY_PHONE_registered_FAILURE);
                if (response.isSuccessful()) {
                    UserCenterResponse.Register registerResponse = response.body();
                    if (registerResponse.isResult()) {
                        LampLog.d("注册成功");
                        resultCode.setResultStation(HttpRequestRepsonse.KEY_PHONE_registered_SUCCES);
                        EventBus.getDefault().post(resultCode);
                        return;
                    }
                }
                EventBus.getDefault().post(resultCode);
            }

            @Override
            public void onFailure(Call<UserCenterResponse.Register> call, Throwable t) {
                HttpRequestRepsonse resultCode = new HttpRequestRepsonse(HttpRequestRepsonse.KEY_PHONE_registered_FAILURE);
                EventBus.getDefault().post(resultCode);
            }
        });
    }

    /**
     * 异步用户注册
     *
     * @param tel    手机号
     * @param pwd    密码
     * @param code   短信验证码
     * @param usedId 用户id
     */
    public void registion(final String tel, final String pwd, String code, String usedId) {
        mNetService.registion(new UserRegisterRequest(new UserRegisterRequest.UserRegisterData(tel, code, MD5Utils.getMd5(pwd), ""))).enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                UserRegisterResponse userRegisterResponse = response.body();

                EventBus.getDefault().post(new UserRegisterEvent(true, userRegisterResponse));
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                EventBus.getDefault().post(new UserRegisterEvent(false, null));
            }
        });
    }

    /**
     * 异步用户登录
     *
     * @param telPhone 手机号
     * @param password 密码
     */
    public void login(final String telPhone, final String password, final String usedId) {
        mNetService.login(new UserLoginRequest(new UserLoginRequest.UserLoginData(telPhone, MD5Utils.getMd5(password), usedId))).enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                UserLoginResponse userLoginResponse = response.body();
                EventBus.getDefault().post(new UserLoginEvent(true, userLoginResponse));
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                EventBus.getDefault().post(new UserLoginEvent(false, null));
            }
        });
    }

    /**
     * 异步重置密码
     *
     * @param password 密码
     * @param phoneNum 手机号
     */
    public void resetPassword(String phoneNum, final String password, String captcha, String usedId) {
        mNetService.resetPassword(new UserResetPwdRequest(new UserResetPwdRequest.UserResetPwdRequestData(phoneNum, captcha, password, ""))).enqueue(new Callback<UserResetPwdResponse>() {
            @Override
            public void onResponse(Call<UserResetPwdResponse> call, Response<UserResetPwdResponse> response) {
                UserResetPwdResponse userResetPwdResponse = response.body();
                EventBus.getDefault().post(new UserResetPwdEvent(true, userResetPwdResponse));
            }

            @Override
            public void onFailure(Call<UserResetPwdResponse> call, Throwable t) {
                EventBus.getDefault().post(new UserResetPwdEvent(false, null));
            }
        });
    }

    /**
     * 修改用户资料
     *
     * @param userId   用户ID
     * @param phone    手机号
     * @param nickName 昵称
     * @param sex      性别
     * @param icon     头像
     * @param local    位置
     */
    public void editUserInfo(final String userId, final String phone, final String nickName, final String sex, String icon, String local, final int requestCode) {
        mNetService.editUserInfo(userId, phone, nickName, sex, icon, local).enqueue(new Callback<UserCenterResponse.EditInfo>() {
            @Override
            public void onResponse(Call<UserCenterResponse.EditInfo> call, Response<UserCenterResponse.EditInfo> response) {
                HttpRequestRepsonse resultCode = new HttpRequestRepsonse(HttpRequestRepsonse.KEY_PHONE_resetuserinfo_FAILURE);
                UserCenterResponse.EditInfo editInfoResponse = response.body();
                if (editInfoResponse.isResult()) {
                    LampLog.d("修改用户资料成功");
                    resultCode.setResultStation(HttpRequestRepsonse.KEY_PHONE_resetuserinfo_SUCCES);
                    resultCode.setUserId(userId);
                    resultCode.setAccount(phone);
                    resultCode.setNickName(nickName);
                    resultCode.setWhichOne(requestCode);


                    EventBus.getDefault().post(resultCode);
                    return;
                }
                EventBus.getDefault().post(resultCode);
            }

            @Override
            public void onFailure(Call<UserCenterResponse.EditInfo> call, Throwable t) {
                HttpRequestRepsonse resultCode = new HttpRequestRepsonse(HttpRequestRepsonse.KEY_PHONE_resetuserinfo_FAILURE);
                EventBus.getDefault().post(resultCode);
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param
     */
    public void getUserInfo(String userId) {
        mNetService.getUserInfo(userId).enqueue(new Callback<UserCenterResponse.UserInfo>() {
            @Override
            public void onResponse(Call<UserCenterResponse.UserInfo> call, Response<UserCenterResponse.UserInfo> response) {
                if (response.isSuccessful()) {
                    UserCenterResponse.UserInfo userInfoResponse = response.body();
                    if (userInfoResponse.isResult()) {
                        LampLog.d("获取用户信息");

                    }
                }
            }

            @Override
            public void onFailure(Call<UserCenterResponse.UserInfo> call, Throwable t) {

            }
        });
    }

    public void thirdPartyLogin(TelCheck telCheck) {
        mNetService.thirdPartyLogin(telCheck).enqueue(new Callback<PhoneLoginThirdPartyResponse>() {
            @Override
            public void onResponse(Call<PhoneLoginThirdPartyResponse> call, Response<PhoneLoginThirdPartyResponse> response) {
                if (response.isSuccessful()) {
                    LampLog.d("连接成功");
                    LampLog.d("信息：" + response.body().message);
                } else {
                    LampLog.d("连接失败");
                    LampLog.d("信息：" + response.body().message);

                }
            }

            public void onFailure(Call<PhoneLoginThirdPartyResponse> call, Throwable t) {
                LampLog.d("连接失败");
            }
        });

    }

    /**
     * 获取我的家人列表
     * @param userId
     * @param msgType
     */
    public void getFamilyList(String userId,String msgType) {
        mNetService.getFamilyList(new FamilyListRequest(new FamilyListRequest.FamilyData(userId,msgType))).enqueue(new Callback<FamilyListResponse>() {
            @Override
            public void onResponse(Call<FamilyListResponse> call, Response<FamilyListResponse> response) {
                FamilyListResponse familyListResponse=response.body();
                EventBus.getDefault().post(new FamilyListEvent(true,familyListResponse));
            }

            @Override
            public void onFailure(Call<FamilyListResponse> call, Throwable t) {
                EventBus.getDefault().post(new FamilyListEvent(false,null));
            }
        });
    }

    public void getNuomiList(String city_id, String keyword, String location) {
        mNetService.getNuomiList(city_id, keyword, location).enqueue(new Callback<APIResponse.PosterInfo>() {
            @Override
            public void onResponse(Call<APIResponse.PosterInfo> call, Response<APIResponse.PosterInfo> response) {
                if (response.isSuccessful()) {
                    APIResponse.PosterInfo posterInfo = response.body();
                    if (posterInfo != null) {
                        EventBus.getDefault().post(posterInfo);
                    } else {
                        posterInfo = new APIResponse.PosterInfo();
                        posterInfo.errno = -1;
                        EventBus.getDefault().post(posterInfo);
                    }
                }


            }

            @Override
            public void onFailure(Call<APIResponse.PosterInfo> call, Throwable t) {
                APIResponse.PosterInfo posterInfo = new APIResponse.PosterInfo();
                posterInfo.errno = -1;
                EventBus.getDefault().post(posterInfo);
            }

        });
    }


    @Override
    public void getPosterImageUrls(String DesData) {
        mNetService.getPosterImageUrls(DesData).enqueue(new Callback<PosterResponse.PosterInfo>() {
                                                            @Override
                                                            public void onResponse(Call<PosterResponse.PosterInfo> call, Response<PosterResponse.PosterInfo> response) {
                                                                if (response.isSuccessful() && response.code() == 200) {
                                                                    LampLog.d("展示网络图片");
                                                                    PosterResponse.PosterInfo posterInfo = response.body();
                                                                    if (posterInfo.list != null && posterInfo.list.size() > 0) {
                                                                        SpUtil.writeString(SpName.POSTER_DATA, SpName.POSTER_DATA_VALUE);
                                                                        EventBus.getDefault().post(posterInfo.list);
                                                                    } else {
                                                                        LampLog.d("展示本地图片");
                                                                        List<PosterResponse.PosterInfo.Data> list = new ArrayList<>();
                                                                        // list.add(addDefaultImages());
                                                                        EventBus.getDefault().post(list);
                                                                    }
                                                                } else {
                                                                    LampLog.d("展示本地图片");
                                                                    List<PosterResponse.PosterInfo.Data> list = new ArrayList<>();
                                                                    //list.add(addDefaultImages());
                                                                    EventBus.getDefault().post(list);
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<PosterResponse.PosterInfo> call, Throwable t) {
                                                                LampLog.d("失败，展示本地图片");
                                                                List<PosterResponse.PosterInfo.Data> list = new ArrayList<>();
                                                                // list.add(addDefaultImages());
                                                                EventBus.getDefault().post(list);
                                                            }
                                                        }

        );
    }

    @Override
    public void getConfigApkInfo(String DesData) {
        Log.d(TAG, "getConfigApkInfo: ");
        mNetService.getConfigApkInfo(DesData).enqueue(new Callback<ApkInfoResponse.ApkInfo>() {
            @Override
            public void onResponse(Call<ApkInfoResponse.ApkInfo> call, Response<ApkInfoResponse.ApkInfo> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    LampLog.d("配置文件请求成功");
                    List<ApkInfoResponse.ApkInfo.Data> itemInfos = response.body().list;
                    LampLog.d("itemInfos: " + itemInfos.size());
                    if (itemInfos != null && itemInfos.size() > 0) {
                        EventBus.getDefault().post(itemInfos);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApkInfoResponse.ApkInfo> call, Throwable t) {
                LampLog.d("getConfigApkInfo:onFailure");
            }
        });
    }

    @Override
    public void getFourDayWeatherData(String cityname) {

        mNetService.getFourDayWeatherData(cityname).enqueue(new Callback<WeatherResponse.FourDay>() {
            @Override
            public void onResponse(Call<WeatherResponse.FourDay> call, Response<WeatherResponse.FourDay> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body() != null) {
                        LampLog.d("response  " + "成功");
                        WeatherResponse.FourDay weatherData = response.body();
                        EventBus.getDefault().post(weatherData);
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse.FourDay> call, Throwable t) {

            }
        });
    }

    /**
     * 添加家人
     * @param userId
     * @param tel
     */
    public void addFamilyNumber(String userId,String tel){
        mNetService.addFamilyNumber(new AddFamilyRequest(new AddFamilyRequest.FamilyData(userId,tel))).enqueue(new Callback<AddFamilyResponse>() {
            @Override
            public void onResponse(Call<AddFamilyResponse> call, Response<AddFamilyResponse> response) {
                AddFamilyResponse addFamilyResponse=response.body();
                EventBus.getDefault().post(new AddFamilyEvent(true,addFamilyResponse));
            }

            @Override
            public void onFailure(Call<AddFamilyResponse> call, Throwable t) {
                EventBus.getDefault().post(new AddFamilyEvent(false,null));
            }
        });
    }

    public void deleteFamilyNumber(String userId,String id){
        mNetService.deleteFamilyNumber(new DeleteFamilyRequest(new DeleteFamilyRequest.FamilyData(userId, id))).enqueue(new Callback<DeleteFamilyResponse>() {
            @Override
            public void onResponse(Call<DeleteFamilyResponse> call, Response<DeleteFamilyResponse> response) {
                DeleteFamilyResponse deleteFamilyResponse=response.body();
                EventBus.getDefault().post(new DeleteFamilyEvent(true,deleteFamilyResponse));
            }

            @Override
            public void onFailure(Call<DeleteFamilyResponse> call, Throwable t) {
                EventBus.getDefault().post(new DeleteFamilyEvent(false,null));
            }
        });
    }
}