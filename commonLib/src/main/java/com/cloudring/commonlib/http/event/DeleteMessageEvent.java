package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.DeleteMessageResponse;
import com.cloudring.commonlib.http.bean.MessageStateResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class DeleteMessageEvent {
    public boolean isConnect;
    public DeleteMessageResponse response;

    public DeleteMessageEvent(boolean isConnect, DeleteMessageResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
