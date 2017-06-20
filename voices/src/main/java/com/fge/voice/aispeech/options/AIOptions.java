package com.fge.voice.aispeech.options;

import android.content.Context;
import android.text.TextUtils;
import com.fge.voice.VConfigManager;
import com.fge.voice.base.BaseOptions;

import static com.fge.voice.VConfigManager.getConfigFloatValue;

/**
 * 思必驰的设置配置
 * Created by hm on 2016/12/14.
 */

public class AIOptions extends BaseOptions{


    public final int compositionMode;
    public final String[] wakeup_words;
    public final float[] wakeup_threshold;
    public final float tts_speechRate;
    public final long waitCloudTimeout;
    public final int maxSpeechTime;
    public final int pauseTime;
    public final int noSpeechTimeOut;
    public final float athThreshold;
    public final String server;
    public final boolean isOpenEcho;
    public final int recChannel;
    public final boolean network_tts;

    private AIOptions(AIOptions.Builder builder) {
        this.wakeup_words = builder.wakeup_words;
        this.wakeup_threshold = builder.wakeup_threshold;
        this.tts_speechRate = builder.tts_speechRate;
        this.waitCloudTimeout = builder.waitCloudTimeout;
        this.pauseTime = builder.pauseTime;
        this.maxSpeechTime = builder.maxSpeechTime;
        this.noSpeechTimeOut = builder.noSpeechTimeOut;
        this.athThreshold = builder.athThreshold;
        this.server = builder.server;
        this.isOpenEcho = builder.isOpenEcho;
        this.recChannel = builder.recChannel;
        this.network_tts = builder.network_tts;
        this.compositionMode=builder.compositionMode;
    }

    public static class Builder {
        private String[] wakeup_words;
        private float[] wakeup_threshold;
        private float tts_speechRate;//语速
        private long waitCloudTimeout;
        private int pauseTime;
        private int maxSpeechTime=60;
        private int noSpeechTimeOut;
        private float athThreshold;
        private String server;
        private boolean isOpenEcho;
        private int recChannel;
        private boolean network_tts=false;
        private int compositionMode = MIX_LOCAL_SEMANTIC;

        public Builder(Context context) {
            /***唤醒词**/
            String words = VConfigManager.getConfigStringValue(context, "words", null);
            if (TextUtils.isEmpty(words)) {
                words = "shen deng ni hao";
            }
            wakeup_words = new String[]{words};
            /***唤醒词阀值**/
            float threshold = getConfigFloatValue(context, "threshold", 0.2f);
            wakeup_threshold = new float[]{threshold};
            /***播报语速**/
            float speechRate = VConfigManager.getConfigFloatValue(context, "speechRate", 0.9f);
            tts_speechRate = speechRate;
            /***等待云端结果的 超时时间**/
            int waitCloudTimeout = VConfigManager.getConfigIntValue(context, "waitCloudTimeout", 5000);
            this.waitCloudTimeout = waitCloudTimeout;

            int pauseTime = VConfigManager.getConfigIntValue(context, "pauseTime", 1000);
            this.pauseTime = pauseTime;

            int noSpeechTimeOut = VConfigManager.getConfigIntValue(context, "noSpeechTimeOut", 5000);
            this.noSpeechTimeOut = noSpeechTimeOut;
            /**精准度的水平线*/
            float athThreshold = VConfigManager.getConfigFloatValue(context, "athThreshold", 0.6f);
            this.athThreshold = athThreshold;
            /**服务的地址**/
            String server = VConfigManager.getConfigStringValue(context, "server", null);
            if (TextUtils.isEmpty(server)) {
                server = "ws://s.api.aispeech.com:1028,ws://s.api.aispeech.com:80";
            }
            this.server = server;
            //aec
            int echo = VConfigManager.getConfigIntValue(context, "isOpenEcho", 1);
            if (echo == 1) {
                this.isOpenEcho = true;
            } else {
                this.isOpenEcho = false;
            }
            int recChannel = VConfigManager.getConfigIntValue(context, "recChannel", 1);
            this.recChannel = recChannel;

            int networktts = VConfigManager.getConfigIntValue(context, "network_tts", 0);
            if (networktts == 1) {
                this.network_tts = true;
            } else {
                this.network_tts = false;
            }
        }
        public Builder maxSpeechTime(int maxSpeechTime) {
            this.maxSpeechTime = maxSpeechTime;
            return this;
        }
        public Builder compositionMode(int compositionMode) {
            this.compositionMode = compositionMode;
            return this;
        }

        public Builder wakeupWords(String[] wakeup_words) {
            this.wakeup_words = wakeup_words;
            return this;
        }

        public Builder wakeupThreshold(float[] wakeup_threshold) {
            this.wakeup_threshold = wakeup_threshold;
            return this;
        }

        public Builder speechRate(float tts_speechRate) {
            this.tts_speechRate = tts_speechRate;
            return this;
        }

        public Builder waitCloudTimeout(long waitCloudTimeout) {
            this.waitCloudTimeout = waitCloudTimeout;
            return this;
        }

        public Builder pauseTime(int pauseTime) {
            this.pauseTime = pauseTime;
            return this;
        }

        public Builder noSpeechTimeOut(int noSpeechTimeOut) {
            this.noSpeechTimeOut = noSpeechTimeOut;
            return this;
        }

        public Builder athThreshold(float athThreshold) {
            this.athThreshold = athThreshold;
            return this;
        }

        public Builder setServer(String server) {
            this.server = server;
            return this;
        }

        public Builder openEcho(boolean sOpenEcho) {
            this.isOpenEcho = sOpenEcho;
            return this;
        }

        public AIOptions build() {
            return new AIOptions(this);
        }
    }


}
