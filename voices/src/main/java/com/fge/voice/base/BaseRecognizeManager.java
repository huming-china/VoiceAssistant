package com.fge.voice.base;


public abstract class BaseRecognizeManager extends BaseVoiceManager {
    protected boolean isSucess = false;//引擎是否准备成功的标识
    protected VoiceReadyCallback voiceReadyCallback;

    /**
     * 准备引擎是否成功的结果回调
     */
    protected VoiceReadyCallback mVoiceReadyCallback = new VoiceReadyCallback() {
        @Override
        public void onReadyCallback(boolean suceess, int type, String errMsg) {
            isSucess = suceess;
            if(voiceReadyCallback!=null)
                voiceReadyCallback.onReadyCallback(isSucess, type, errMsg);
        }
    };

    public boolean isReadySucess() {
        return isSucess;
    }

}
