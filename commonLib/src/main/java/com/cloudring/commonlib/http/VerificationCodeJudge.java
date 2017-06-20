package com.cloudring.commonlib.http;

/**
 * Created by Manna on 2016/9/21.
 *
 * @Description: ${todo}
 */
public class VerificationCodeJudge {

    public boolean isConnect;
    public boolean isCodeResult;
    public String hint;

    public VerificationCodeJudge(boolean isConnect,boolean isCodeResult, String hint){
        this.isConnect=isConnect;
        this.isCodeResult=isCodeResult;
        this.hint = hint;
    }
}
