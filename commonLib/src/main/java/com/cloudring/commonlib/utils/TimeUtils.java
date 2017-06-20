package com.cloudring.commonlib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zjq on 2016/8/11.
 */
public class TimeUtils {


    public static String[] getTime2String() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);//获取年份
        int month = ca.get(Calendar.MONTH) + 1;//获取月份
        int day = ca.get(Calendar.DATE);//获取日
        int minute = ca.get(Calendar.MINUTE);//分
        int hour = ca.get(Calendar.HOUR);//小时
        int second = ca.get(Calendar.SECOND);//秒
        int WeekOfYear = ca.get(Calendar.DAY_OF_WEEK);
        String week = "";

        switch (WeekOfYear) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        String[] timeDate = new String[2];
        timeDate[0] = hour + ":" + minute;
        timeDate[1] = month + "月" + day + "日" + " " + week;

        return timeDate;
    }
    public static String getTime(Context context) {
        return DateFormat.getTimeFormat(context).format(new Date()).replaceAll("[\u4e00-\u9fa5]+", "");
    }
    /**
     * 得到城市名
     * @param addr  详细地址
     * @return  如 深圳、北京
     */
    public  static String getCity(String addr) {
        String cityName = "";
        if (!TextUtils.isEmpty(addr)) {
            int indexProvince = addr.indexOf("省");
            int indexCity = addr.indexOf("市");
            if (indexProvince == -1 && indexCity > -1) {
                //直辖市
                cityName = addr.substring(indexCity - 2, indexCity);
            } else if (indexProvince > -1 && indexCity > -1) {
                //地级市
                cityName = addr.substring(indexProvince+1, indexCity);
            }
        }
        return cityName;
    }

}