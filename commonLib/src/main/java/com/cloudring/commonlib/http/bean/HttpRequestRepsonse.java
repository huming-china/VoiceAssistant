package com.cloudring.commonlib.http.bean;

/**
 * desc:
 * Created by FJTK_Zengcaiqing on 2016/8/8.
 */
public class HttpRequestRepsonse {
    /**
     * 手机发送验证码成功
     */
    public static final int KEY_PHONE_VerificationCode_SUCCESS=901;
    /**
     * 手机发送验证码失败
     */
    public static final int KEY_PHONE_VerificationCode_FAILURE=1902;

    /**
     * 手机验证码是正确的
     */
    public static final int KEY_PHONE_YZHVerificationCode_SUCCESS=1801;
    /**
     * 手机验证码是错误的
     */
    public static final int KEY_PHONE_YZHVerificationCode_FAILURE=1802;

    /**
     * 手机登录成功
     */
    public static final int KEY_PHONE_Login_SUCCESS=1903;
    /**
     * 手机登录失败
     */
    public static final int KEY_PHONE_Login_FAILURE=1904;
    /**
     * 手机注册成功
     */
    public static final int KEY_PHONE_registered_SUCCES=1905;
    /**
     * 手机注册失败
     */
    public static final int KEY_PHONE_registered_FAILURE=1906;
    /**
     * 忘记密码
     */
    public static final int KEY_PHONE_forgetpassword_ACTION=1907;

    /**
     * 手机重新设置密码成功
     */
    public static final int KEY_PHONE_resetpassword_SUCCES=1908;
    /**
     * 手机重新设置密码失败
     */
    public static final int KEY_PHONE_resetpassword_FAILURE=1909;

    /**
     * 手机重新设置资料失败
     */
    public static final int KEY_PHONE_resetuserinfo_SUCCES=2000;
    /**
     * 手机重新设置资料失败
     */
    public static final int KEY_PHONE_resetuserinfo_FAILURE=2001;


    //public static final int YES_GOTO_HOMEPAGE = 2001;
   // public static final int NO_GOTO_HOMEPAGE = 2002;

    /**
     * 尝试去密码
     */
    public static final int KEY_PHONE_TryToModifyPassWord_ACTION=1908;

    private int resultStation=-1;//返回状态
    private int resultSecondStation=-1;//第二状态action
    private String account;//账号
    private String password;//密码
    private String userId;
    private String gidId;

    private String imauri;
    private String nickName;
    private String hint;

    private int whichOne;

    public int getWhichOne() {
        return whichOne;
    }

    public void setWhichOne(int whichOne) {
        this.whichOne = whichOne;
    }

    public HttpRequestRepsonse(int httpRequestRepsonse_Code) {
        this.resultStation = httpRequestRepsonse_Code;
    }

    public static int getKEY_PHONE_VerificationCode_SUCCESS() {
        return KEY_PHONE_VerificationCode_SUCCESS;
    }

    public String getImauri() {
        return imauri;
    }

    public void setImauri(String imauri) {
        this.imauri = imauri;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getResultStation() {
        return resultStation;
    }
    public void setResultStation(int resultStation) {
        this.resultStation = resultStation;
    }

    public int getResultSecondStation() {
        return resultSecondStation;
    }

    public void setResultSecondStation(int resultSecondStation) {
        this.resultSecondStation = resultSecondStation;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGidId() {
        return gidId;
    }

    public void setGidId(String gidId) {
        this.gidId = gidId;
    }
}
