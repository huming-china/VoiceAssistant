package com.fge.voice.iflytek.options;

import android.os.Environment;

import static com.fge.voice.base.BaseOptions.MIX_LOCAL_SEMANTIC;

/**
 * Created by hm on 2016/5/26.
 */
public final class IflyOptions {
    //TTS
    public final String voiceCloudName;
    public final String voiceLocalName;
    public final String speed;
    public final String volume;
    public final String pitch;
    public final boolean networkTTS;
    public final String streamType;
    public final String keyRequestFocus;
    public final String audioFormat;
    public final String ttsAudioPath;
    public final boolean isInterruptSpeak;
    public final int compositionMode;
    //识别
    public final String timeout;
    public final String mixed_threshold;
    public final String asr_threshold;
    public final String vad_bos;//前端点
    public final String vad_eos;//后端点
    //唤醒
    public final String ivw_threshold;

    private IflyOptions(Builder builder) {
        this.voiceCloudName = builder.voiceCloudName;
        this.voiceLocalName = builder.voiceLocalName;
        this.speed = builder.speed;
        this.volume = builder.volume;
        this.networkTTS = builder.networkTTS;
        this.pitch = builder.pitch;
        this.streamType = builder.streamType;
        this.keyRequestFocus = builder.keyRequestFocus;
        this.audioFormat = builder.audioFormat;
        this.ttsAudioPath = builder.ttsAudioPath;
        this.isInterruptSpeak = builder.isInterruptSpeak;
        this.ivw_threshold = builder.ivw_threshold;
        this.compositionMode = builder.compositionMode;
        this.timeout = builder.timeout;
        this.mixed_threshold = builder.mixed_threshold;
        this.asr_threshold = builder.asr_threshold;
        this.vad_bos = builder.vad_bos;
        this.vad_eos = builder.vad_eos;
    }

    public static class Builder {
        private boolean isInterruptSpeak = true;
        private String voiceCloudName = "vinn";
        private String voiceLocalName = "xiaoyan";

        private String streamType = "3";
        private String keyRequestFocus = "true";
        private String audioFormat = "wav";
        private String ttsAudioPath = Environment.getExternalStorageDirectory() + "/msc/tts.wav";
        private String speed = "50";
        private String pitch = "50";
        private String volume = "100";
        private boolean networkTTS = false;
        //识别
        private int compositionMode = MIX_LOCAL_SEMANTIC;
        private String timeout = "3000";
        private String mixed_threshold = "35";
        private String asr_threshold = "20";
        private String vad_bos = "4000";//前端点
        private String vad_eos = "1500";//后端点
        //唤醒
        private String ivw_threshold = "6";

        /**
         * 唤醒词的门限值
         * @param ivw_threshold
         */
        public void setIvw_threshold(String ivw_threshold) {
            this.ivw_threshold = ivw_threshold;
        }

        /**
         * 语音前端点
         * @param vad_eos
         */
        public void setVad_eos(String vad_eos) {
            this.vad_eos = vad_eos;
        }

        /**
         * 语音后端点
         * @param vad_bos
         */
        public void setVad_bos(String vad_bos) {
            this.vad_bos = vad_bos;
        }
        //参照文档
        public void setAsr_threshold(String asr_threshold) {
            this.asr_threshold = asr_threshold;
        }
        //混合门限 参照文档
        public void setMixed_threshold(String mixed_threshold) {
            this.mixed_threshold = mixed_threshold;
        }

        /**
         * 识别组合的模式
         *
         * @param compositionMode @MIX_LOCAL_SEMANTIC or @MIX_CLOUD_SEMANTIC
         */
        public void compositionMode(int compositionMode) {
            this.compositionMode = compositionMode;
        }

        /**
         * 音调
         *
         * @param pitch 0-100
         */
        public void setPitch(String pitch) {
            this.pitch = pitch;
        }

        /**
         * 语速
         *
         * @param speed 0-100
         * @return
         */
        public Builder setSpeed(String speed) {
            this.speed = speed;
            return this;
        }

        /**
         * 音量大小
         *
         * @param volume 0-100
         * @return
         */
        public Builder setVolume(String volume) {
            this.volume = volume;
            return this;
        }

        public Builder keyRequestNoFocus() {
            this.keyRequestFocus = "false";
            return this;
        }

        public Builder queueSpeaking() {
            this.isInterruptSpeak = false;
            return this;
        }

        public Builder setAudioFormat(String audioFormat) {
            this.audioFormat = audioFormat;
            return this;
        }

        /**
         * 播报音频文件保存的地址
         *
         * @param ttsAudioPath 物理路径
         * @return
         */
        public Builder setTtsAudioPath(String ttsAudioPath) {
            this.ttsAudioPath = ttsAudioPath;
            return this;
        }

        public Builder cloudVoiceName(String voiceCloudName) {
            this.voiceCloudName = voiceCloudName;
            return this;
        }

        public Builder localVoiceName(String voiceLocalName) {
            this.voiceLocalName = voiceLocalName;
            return this;
        }


        public Builder useNetworkTTS() {
            this.networkTTS = true;
            return this;
        }


        public IflyOptions build() {
            return new IflyOptions(this);
        }
    }
}
