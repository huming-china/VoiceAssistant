package com.cloudring.magicsound.iconduct.impl;

import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.fge.voice.util.FileWriteLog;

import recognition.TulingRecognitionManager;

/** 图灵
 * Created by hm on 2017/1/10.
 */

public class TuRingConduct extends BaseConduct<Object> {
    private TulingRecognitionManager mTulingRecognitionManager;
    private String input;

    public TuRingConduct(TulingRecognitionManager mTulingRecognitionManager, String input) {
        this.mTulingRecognitionManager = mTulingRecognitionManager;
        this.input = input;
    }

    @Override
    public void hand(Object obj) {
        FileWriteLog.writeLog("result instanceof VTuRing");
        if (input.contains("笑话")) {
            mTulingRecognitionManager.recognitionApi("讲个笑话");
        } else {
            mTulingRecognitionManager.recognitionApi(input);
        }
    }

    @Override
    public Object pareseIfly(String json) {
        return null;
    }

    @Override
    public Object parseAISpeech(String json) {
        return null;
    }


}
