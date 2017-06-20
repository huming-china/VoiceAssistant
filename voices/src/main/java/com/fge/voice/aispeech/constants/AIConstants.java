package com.fge.voice.aispeech.constants;

import android.os.Environment;

/**
 * Created by zengpeijin on 2016/12/7.
 */

public class AIConstants {
    public static final String APPKEY= "14725256548595e7";
    public static final String SECRETKEY="125e2bc1c5bf85e85f8a103e4ae40ee3";
    public static String vad_res = "vad.aihome.v0.5.20160324.bin";
    public static String ebnfc_res ="ebnfc.aihome.0.3.0.bin";
    public static String ebnfr_res = "ebnfr.aihome.0.3.0.bin";
    public static String local_asr_net_bin ="asr.net.bin";

    /**唤醒词资源*/
    public static String wakeup_dnn_res = "fjtke_20160830.bin";

    /**语音库*/
    public static String tts_dict_dbname="aitts_sent_dict_v3.7.db";
    public static String tts_save_path= Environment.getExternalStorageDirectory() + "/linzhilin/" + "localtts.wav";
    /**发音人*/
    public static String tts_resource="zhilingf.v0.5.8.bin";

    public static String xbnf_res="grammar.xbnf";
}
