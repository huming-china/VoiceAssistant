package com.cloudring.magicsound.iconduct.impl;

import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.fragment.MagicLampFragment;
import com.cloudring.magicsound.iconduct.base.BaseConduct;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.vmodel.VChat;
import com.fge.voice.VoiceConfig;
import com.fge.voice.base.BaseRecognizeManager;

import org.json.JSONException;
import org.json.JSONObject;

import recognition.TulingRecognitionManager;

/**聊天
 * Created by hm on 2017/1/10.
 */

public class ChatConduct extends BaseConduct<VChat> {
    private MagicLampFragment fragment;
    private VoiceAdapter adapter;
    private TulingRecognitionManager mTulingRecognitionManager;

    public ChatConduct(MagicLampFragment fragment, VoiceAdapter adapter, TulingRecognitionManager mTulingRecognitionManager) {
        this.fragment = fragment;
        this.adapter = adapter;
        this.mTulingRecognitionManager = mTulingRecognitionManager;
    }

    @Override
    public void hand(VChat data) {
        VChat chat = data;
        if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
            adapter.add(new MessageItem(chat.getText()));
            fragment.speak(chat.getText());
        } else {
            String chatInput = chat.getInput();
            if (chatInput.length() > 0) {
                mTulingRecognitionManager.recognitionApi(chatInput);
            }
        }
    }

    @Override
    public VChat pareseIfly(String json) {
        VChat vChat = new VChat();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String text = jsonObject.getJSONObject("answer").getString("text");
            vChat.setText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vChat;
    }

    @Override
    public VChat parseAISpeech(String json) {
        VChat chat = new VChat();
        try {
            JSONObject resJsonObject = new JSONObject(json).optJSONObject("result");
            String input = resJsonObject.optString("input");
            chat.setInput(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chat;
    }
}
