package com.cloudring.commonlib.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by slx on 2016/7/26.
 */
public class Tools {
    /**
     * dpתpx
     *
     */
    public static int dip2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     *	pxתdp
     */
    public static int px2dip(Context ctx,float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String intoJson(String mobile,String userId){
        JSONObject object=new JSONObject();
        try {
            object.put("mobile",mobile);
            object.put("userId",userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
