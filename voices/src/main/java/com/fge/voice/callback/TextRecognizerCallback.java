package com.fge.voice.callback;

import android.text.TextUtils;
import com.aispeech.common.JSONResultParser;

/**
 * 语音翻译成纯文字
 */
public class TextRecognizerCallback extends RecognizerCallback<String> {
    StringBuilder recSb = new StringBuilder();
    String recordId;

    @Override
    public String parseResult(String result) {
        JSONResultParser parser = new JSONResultParser(result);
        String rec = parser.getRec();
        if (!parser.getRecordId().equals(recordId)) {
            recSb.delete(0, recSb.length());
            recordId = parser.getRecordId();
        }
        if (!TextUtils.isEmpty(rec)) {
            recSb.delete(0, recSb.length());
            recSb.append(rec);
        } else {
            String var = parser.getVar();
            if (!TextUtils.isEmpty(var)) {
                return var;
            }
        }
        return recSb.toString();
    }
}
