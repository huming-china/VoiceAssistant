package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.DeleteFamilyResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class DeleteFamilyEvent {
    public boolean isConnect;
    public DeleteFamilyResponse response;

    public DeleteFamilyEvent(boolean isConnect, DeleteFamilyResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
