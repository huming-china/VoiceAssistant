package com.cloudring.magicsound.iconduct.base;

import com.fge.voice.VoiceConfig;
import com.fge.voice.base.BaseRecognizeManager;

/**
 * 语义场景数据解析和业务处理基类
 * Created by hm on 2017/1/10.
 */

public abstract class BaseConduct<T> implements IConduct<T> {
    private T data;

    /****
     * 对业务处理的执行
     */
    @Override
    public void execute(String json) {
        //解析数据
        parse(json);
        hand(data);
    }

    /***
     * json数据的解析
     **/
    public void parse(String json) {
        if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
            data = pareseIfly(json);
        } else {
            json = json.replace("\"dbdata\":{}", "\"dbdata\":[]");
            data = parseAISpeech(json);
        }
    }

}
