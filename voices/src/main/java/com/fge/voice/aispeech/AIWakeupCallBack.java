package com.fge.voice.aispeech;

import com.aispeech.AIError;
import com.aispeech.common.AIConstant;

/** 唤醒 回调
 * Created by hm on 2016/5/27.
 */
public abstract class AIWakeupCallBack {

    public void onError(AIError error) {
        //showTip(error.toString());
    }
    public void onInit(int status) {
        if (status == AIConstant.OPT_SUCCESS) {
            // resultText.append("初始化成功!");
            // btnStart.setEnabled(true);
            // btnStop.setEnabled(true);
        } else {
            // resultText.setText("初始化失败!code:" + status);
        }
    }
    public void onRmsChanged(float rmsdB) {
        //showTip("rmsDB:" + rmsdB);
    }

    public void onWakeup(String recordId, double confidence, String wakeupWord) {
        // Log.d(Tag, "wakeup foreground");
        // resultText.append("唤醒成功  wakeupWord = " + wakeupWord + "  confidence = " + confidence + "\n");

        // startBeep.playBeep();
        //mWakeupEngine.stop();

    }

    public void onReadyForSpeech() {
        //resultText.append("说 你好小驰  可以唤醒\n");
    }

    public void onBufferReceived(byte[] buffer) {
        //mFifoQueue.add(buffer);
    }

    public void onRecorderReleased() {
        // mFifoQueue.clear();
    }
}
