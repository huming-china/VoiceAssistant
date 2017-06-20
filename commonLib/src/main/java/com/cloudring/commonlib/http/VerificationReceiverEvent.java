package com.cloudring.commonlib.http;

import com.cloudring.commonlib.http.bean.PhoneCheckRepsonse;

/**
 * Created by Manna on 2016/9/21.
 *
 * @Description: ${todo}
 */
public class VerificationReceiverEvent {

    public boolean isConnect ;
    public PhoneCheckRepsonse repsonse;

    public VerificationReceiverEvent(boolean isConnect,PhoneCheckRepsonse repsonse){
        this.isConnect = isConnect;
        this.repsonse=repsonse;
    }
}
