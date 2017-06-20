package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.DeleteFamilyResponse;
import com.cloudring.commonlib.http.bean.MessageTypeResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class MessageTypeEvent {
    public boolean isConnect;
    public MessageTypeResponse response;

    public MessageTypeEvent(boolean isConnect, MessageTypeResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
