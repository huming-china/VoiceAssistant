package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.ExchangePhoneMirrorTokenResponse;
import com.cloudring.commonlib.http.bean.UpdateTokenResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class UpdateTokenEvent {
    public boolean isConnect;
    public UpdateTokenResponse response;

    public UpdateTokenEvent(boolean isConnect, UpdateTokenResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
