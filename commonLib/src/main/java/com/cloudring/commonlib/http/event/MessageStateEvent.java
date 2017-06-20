package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.MessageStateResponse;
import com.cloudring.commonlib.http.bean.MessageTypeResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class MessageStateEvent {
    public boolean isConnect;
    public MessageStateResponse response;

    public MessageStateEvent(boolean isConnect, MessageStateResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
