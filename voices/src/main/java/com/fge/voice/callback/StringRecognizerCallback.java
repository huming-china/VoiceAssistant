package com.fge.voice.callback;

/**
 * Created by zengpeijin on 2017/6/6.
 */

public class StringRecognizerCallback extends RecognizerCallback<String>{
    @Override
    public String parseResult(String result) {
        return result;
    }
}
