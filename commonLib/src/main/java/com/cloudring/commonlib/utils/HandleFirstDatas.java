package com.cloudring.commonlib.utils;

import com.cloudring.commonlib.http.bean.ApkInfoResponse;
import com.cloudring.commonlib.http.dataBean.ContentMode;
import com.cloudring.commonlib.http.dataBean.TitleMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slx on 2016/11/1.
 */

public class HandleFirstDatas {
    private static ArrayList<Object> arr;
    private static Map<String, Integer> map;
    private static Map<String, Integer> map11;
    private static List<Object> gridModes;
    private static int size_ori;

    public static List<Object> getHandleData(List<ApkInfoResponse.ApkInfo.Data> apkInfo) {
        arr = new ArrayList<>();
        map = new HashMap<>();
        map11 = new HashMap<>();
        arr.clear();
        map.clear();
        for (int i = 0; i < apkInfo.size(); i++) {
            //(1)找出所有的标题。
            String titleName = apkInfo.get(i).catagoryName;
            LampLog.d("titleName: " + titleName);
            map.put(titleName, apkInfo.get(i).no);
        }
        for (String key : map.keySet()) {
            gridModes = new ArrayList<>();
            TitleMode titleMode = new TitleMode();
            //遍历所有key  titleName;
            for (int i = 0; i < apkInfo.size(); i++) {
                if (key.equals(apkInfo.get(i).catagoryName)) {
                    ContentMode content = new ContentMode();
                    content.setNo(apkInfo.get(i).no + "");
                    content.setImageUrl(apkInfo.get(i).imageUrl);
                    content.setType(apkInfo.get(i).type);
                    content.setApkUrl(apkInfo.get(i).apkUrl);
                    content.setApkName(apkInfo.get(i).apkName);
                    content.setApkVersion(apkInfo.get(i).apkVersion);
                    content.setPageUrl(apkInfo.get(i).pageUrl);
                    content.setMark(apkInfo.get(i).mark);
                    content.setCatagoryName(apkInfo.get(i).catagoryName);
                    titleMode.setTitle(key);
                    LampLog.d("zhangjianiqng  :  " + key);
                    LampLog.d("zhangjianiqng 111 :  " + content.getImageUrl());
                    size_ori = map11.size();
                    map11.put(key, 1);
                    if (map11.size() > size_ori) {
                        gridModes.add(0, titleMode);
                    }
                    gridModes.add(content);
                }
                }
                arr.addAll(gridModes);
            }

            return arr;
        }


    public static void clear() {
        arr.clear();
        map.clear();
        gridModes.clear();
    }
}
