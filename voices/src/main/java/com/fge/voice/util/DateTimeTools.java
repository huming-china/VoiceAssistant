package com.fge.voice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hm on 2016/5/16.
 */
public class DateTimeTools {

    public static long getTime(String datetime) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            date = sdf.parse(datetime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getAITime(String datetime) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm", Locale.getDefault());
        try {
            date = sdf.parse(datetime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 得到时分
     *
     * @param stime
     * @return
     */
    public static int[] parseTime(String stime) {
        int hour_minute[] = new int[2];
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        try {
            Date date = sdf.parse(stime);
            hour_minute[0] = date.getHours();
            hour_minute[1] = date.getMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hour_minute;
    }

    public static int[] parseAITime(String stime) {
        int hour_minute[] = new int[2];
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date date = sdf.parse(stime);
            hour_minute[0] = date.getHours();
            hour_minute[1] = date.getMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hour_minute;
    }

}
