package com.cloudring.commonlib.http.manager;


import com.cloudring.commonlib.base.CommonLib;
import com.cloudring.commonlib.http.service.Api;
import com.cloudring.commonlib.http.service.CloudringService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by zjq on 2016/7/14.
 */
public class RetrofitManager {
    private static final long DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50M
    private static final long SESSION_TIME_OUT_SECONDS = 15; //
    private static final long READ_TIME_OUT_SECONDS = 20; //
    private static final long WRITE_TIME_OUT_SECONDS = 20; //
    private static RetrofitManager mInstance = null;
    private static CloudringService mPosterService = null;
    private Retrofit retrofit = null;



    /**
     * 单例
     *
     * @return RetrofitManager
     */
    private static RetrofitManager newInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 配置Retrofit
     */
    private static Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create()).build();
        return retrofit;
    }

    private RetrofitManager() {
        retrofit = createRetrofit();
        mPosterService = retrofit.create(CloudringService.class);
    }


    public static CloudringService getNetService(){
        RetrofitManager.newInstance();
        return  mPosterService;
    }
    /**
     * 配置OkHttp网络客户端
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getOkHttpClient() {

        File cacheDir = new File(CommonLib.getContext().getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        OkHttpClient client = new OkHttpClient.Builder().cache(cache)
                .connectTimeout(SESSION_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT_SECONDS, TimeUnit.SECONDS).build();
        return client;
    }



}
