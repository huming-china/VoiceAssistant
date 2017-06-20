package com.cloudring.commonlib.http.event;

import com.cloudring.commonlib.http.bean.ExitLoginResponse;
import com.cloudring.commonlib.http.bean.UpdateUserHealthDataResponse;

/**
 * Created by Manna on 2016/9/28.
 *
 * @Description: ${todo}
 */

public class ExitLoginEvent {
    public boolean isConnect;
    public ExitLoginResponse response;

    public ExitLoginEvent(boolean isConnect, ExitLoginResponse response) {
        this.isConnect = isConnect;
        this.response = response;
    }
}
