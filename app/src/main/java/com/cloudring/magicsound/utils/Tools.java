
package com.cloudring.magicsound.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.cloudring.magicsound.VoiceLib;
import com.cloudring.magicsound.Logger;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class Tools {

    private static final String TAG = "Tools";
    private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**one hour in ms*/
    private static final int ONE_HOUR = 1 * 60 * 60 * 1000;
    /**one minute in ms*/
    private static final int ONE_MIN = 1 * 60 * 1000;
    /**one second in ms*/
    private static final int ONE_SECOND = 1 * 1000;

   /* public void getCurrTime(){
        Calendar c = Calendar.getInstance();
        int hour,day;
        hour = c.get(Calendar.HOUR_OF_DAY) + 1;
        c.set(Calendar.HOUR_OF_DAY,hour);//设置一小时后
        day = c.get(Calendar.DAY_OF_MONTH) + 1;
        c.set(Calendar.DAY_OF_MONTH, day);//设置一天后
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(c.getTime());
        long mill = c.getTimeInMillis();//获取毫秒数
    }*/

    public void testTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        //        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH) + 1;
        c.set(Calendar.DAY_OF_MONTH, day);//设置一天后
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        String time = format.format(c.getTime());
        //        String time = format.format(date);
        long mill = c.getTimeInMillis();
        Logger.d(TAG," time = " + time);
        Logger.d(TAG," mill = " + mill);
        Date date = new Date(mill);
        String str = format.format(date);
        Logger.d(TAG," str = " + str);
    }

    public String setDayLater(int daylater,int hour,int minute){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH) + daylater;
        c.set(Calendar.DAY_OF_MONTH, day);//设置几天后
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        return format.format(c.getTime());
    }

    public String setHourLater(int hour,int minute){
        int currhour,currmin;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
        Calendar c = Calendar.getInstance();
        currhour = c.get(Calendar.HOUR_OF_DAY) + hour;
        c.set(Calendar.HOUR_OF_DAY,currhour);//设置几小时后，可以为0
        currmin = c.get(Calendar.MINUTE) + minute;
        c.set(Calendar.MINUTE, currmin);//设置几分钟后，可以为0
        return format.format(c.getTime());
    }


    public static long getSystemTime() {
        Time time = new Time();
        time.setToNow();
        long t = 0;
        // long t = ( ( ( ( ( time.year * 100 + time.month ) * 100 + time.month
        // ) * 100 + time.monthDay ) * 100 + time.hour ) * 100 + time.minute ) *
        // 100 + time.second;
        t = time.year * 100 + time.month + 1;
        t = t * 100 + time.monthDay;
        t = t * 100 + time.hour;
        t = t * 100 + time.minute;
        t = t * 100 + time.second;
        return t;
    }

    /**
     *
     *
     * @param context
     * @return boolean
     */
    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     *
     * @param context
     * @return boolean
     */
    public static boolean isHasSim() {
        TelephonyManager tm = (TelephonyManager) VoiceLib.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);// 取得相关系统服务
        Logger.d(TAG, "SimState: " + tm.getSimState());
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     *
     * @param context
     * @return boolean
     */
    public static boolean isWifiConnected() {
        ConnectivityManager conMan = (ConnectivityManager) VoiceLib.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        return wifi.equals(State.CONNECTED);
    }

    /**
     *
     */
    public static int getDisplayDensity(Context context) {

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metric);
        Logger.i(TAG, "getDisplayDensity " + "metric  = " + metric, "tom");

        return metric.densityDpi;
    }

    /**
     *
     */
    public static int getDisplayWidth(Context context) {

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);
        return size.x;
    }

    /**
     *
     */
    public static int getDisplayHeight(Context context) {

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);
        return size.y;
    }

    /**
     *
     * @param sigBytes
     * @return
     */
    private static String toCharsString(byte[] sigBytes) {
        byte[] sig=sigBytes;
        final int N=sig.length;
        final int N2=N * 2;
        char[] text=new char[N2];
        for(int j=0; j < N; j++) {
            byte v=sig[j];
            int d=(v >> 4) & 0xf;
            text[j * 2]=(char)(d >= 10 ? ('a' + d - 10) : ('0' + d));
            d=v & 0xf;
            text[j * 2 + 1]=(char)(d >= 10 ? ('a' + d - 10) : ('0' + d));
        }
        return new String(text);
    }

    public  String getAPPSecretString(){
        String backString="";
        try {
            Logger.i(TAG, "getPackageName  = " + VoiceLib.getAppContext().getPackageName(), "tom");
            PackageInfo mPackageInfo= VoiceLib.getAppContext().getPackageManager().getPackageInfo(VoiceLib.getAppContext().getPackageName(), PackageManager.GET_SIGNATURES);
            //            Signature xx[]=mPackageInfo.signatures;
            byte[] arrayOfByte= mPackageInfo.signatures[0].toByteArray();
            //            backString= mPackageInfo.signatures[0].toCharsString();
            backString= digest(arrayOfByte);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return backString;
    }

    public  final String digest(byte[] strTemp) {
        try {
            // byte[] strTemp = message.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            Log.i("test", "j : " + j);
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**HH:mm:ss*/
    public static String formatTime(long ms)
    {
        StringBuilder sb = new StringBuilder();
        int hour = (int) (ms / ONE_HOUR);
        int min = (int) ((ms % ONE_HOUR) / ONE_MIN);
        int sec = (int) (ms % ONE_MIN) / ONE_SECOND;
        if (hour == 0)
        {
            //			sb.append("00:");
        }
        else if (hour < 10)
        {
            sb.append("0").append(hour).append(":");
        }
        else
        {
            sb.append(hour).append(":");
        }
        if (min == 0)
        {
            sb.append("00:");
        }
        else if (min < 10)
        {
            sb.append("0").append(min).append(":");
        }
        else
        {
            sb.append(min).append(":");
        }
        if (sec == 0)
        {
            sb.append("00");
        }
        else if (sec < 10)
        {
            sb.append("0").append(sec);
        }
        else
        {
            sb.append(sec);
        }
        return sb.toString();
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * 随机生成UUID
     * @return
     */
    public static synchronized String getUUID(){
        UUID uuid= UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    public static Bitmap getDrawable(Context context,int id){
        Drawable drawable = context.getResources().getDrawable(id);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

}
