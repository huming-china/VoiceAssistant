package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.AddFamilyResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class AddFamilyEvent {
    public boolean isConnect;
    public AddFamilyResponse response;

    public AddFamilyEvent(boolean isConnect, AddFamilyResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
