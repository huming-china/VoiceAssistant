package com.cloudring.commonlib.location;

import android.content.Context;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.cloudring.commonlib.base.CommonLib;
import com.cloudring.commonlib.utils.LampLog;
import com.cloudring.commonlib.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zjq on 2016/7/25.
 */
public class BDGPSLocation {

    private static BDGPSLocation instance;
    private LocationClient mLocationClient;

    public static BDGPSLocation getInstance() {
        if (instance == null) {
            synchronized (BDGPSLocation.class) {
                if (instance == null)
                    instance = new BDGPSLocation();
            }
        }
        return instance;
    }

    private BDGPSLocation() {
        mLocationClient = new LocationClient(CommonLib.getContext());
        mLocationClient.registerLocationListener(new MyLocationListener());//注册定位监听;
        InitLocation();
        start();
    }

    /**
     * 初始化定位
     */
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式（高精度）
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，百度加密经纬度坐标
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//反向地理编码
        mLocationClient.setLocOption(option);
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation||location.getLocType() == BDLocation.TypeOffLineLocation) {
                location.getAddrStr(); //返回详细地址信息;
                CommonLib.getContext().getSharedPreferences("location", Context.MODE_PRIVATE)
                        .edit()
                        .putString("longitude", String.valueOf(location.getLongitude()))
                        .putString("latitude", String.valueOf(location.getLatitude())).commit();
                String cityName = TimeUtils.getCity(location.getAddrStr());
                LampLog.i("定位地址: " + location.getAddrStr() + " 解析城市: " + cityName);
                    EventBus.getDefault().post(cityName);
                    stop();
            }
        }
    }
    /**
     * 开始定位
     */
    public void start() {
        mLocationClient.start();

    }

    /**
     * 结束定位
     */
    public void stop() {
        mLocationClient.stop();
        instance=null;
    }


}
