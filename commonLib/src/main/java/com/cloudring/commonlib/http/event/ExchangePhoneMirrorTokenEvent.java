package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.ExchangePhoneMirrorTokenResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class ExchangePhoneMirrorTokenEvent {
    public boolean isConnect;
    public ExchangePhoneMirrorTokenResponse response;

    public ExchangePhoneMirrorTokenEvent(boolean isConnect, ExchangePhoneMirrorTokenResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
