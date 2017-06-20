package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.DeleteMessageResponse;
import com.cloudring.commonlib.http.bean.UpdatePhonePwdResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UpdatePwdEvent {
    public boolean isConnect;
    public UpdatePhonePwdResponse response;

    public UpdatePwdEvent(boolean isConnect, UpdatePhonePwdResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
