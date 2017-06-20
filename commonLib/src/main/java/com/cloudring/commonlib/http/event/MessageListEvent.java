package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.MessageListResponse;
import com.cloudring.commonlib.http.bean.MessageStateResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class MessageListEvent {
    public boolean isConnect;
    public MessageListResponse response;

    public MessageListEvent(boolean isConnect, MessageListResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
