package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.FamilyListResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class FamilyListEvent {
    public boolean isConnect;
    public FamilyListResponse response;

    public FamilyListEvent(boolean isConnect, FamilyListResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
