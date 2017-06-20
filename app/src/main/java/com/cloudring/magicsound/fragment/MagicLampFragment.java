package com.cloudring.magicsound.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.cloudring.commonlib.http.bean.APIResponse;
import com.cloudring.magicsound.R;
import com.cloudring.magicsound.activity.ASettingActivity;
import com.cloudring.magicsound.activity.IflySettingActivity;
import com.cloudring.magicsound.activity.RobotFragmentActivity;
import com.cloudring.magicsound.adapter.VoiceAdapter;
import com.cloudring.magicsound.constants.Constants;
import com.cloudring.magicsound.event.CookBookEvent;
import com.cloudring.magicsound.event.SpeechEvent;
import com.cloudring.magicsound.event.WakeupEvent;
import com.cloudring.magicsound.iconduct.impl.AdjustConduct;
import com.cloudring.magicsound.iconduct.impl.ChatConduct;
import com.cloudring.magicsound.iconduct.impl.CommandConduct;
import com.cloudring.magicsound.iconduct.impl.ControlConduct;
import com.cloudring.magicsound.iconduct.impl.GrammarConduct;
import com.cloudring.magicsound.iconduct.impl.GrouponConduct;
import com.cloudring.magicsound.iconduct.base.IConduct;
import com.cloudring.magicsound.iconduct.impl.LocalappConduct;
import com.cloudring.magicsound.iconduct.impl.LocalmusicConduct;
import com.cloudring.magicsound.iconduct.impl.LocalphoneConduct;
import com.cloudring.magicsound.iconduct.impl.LocalvolumeConduct;
import com.cloudring.magicsound.iconduct.impl.MusicConduct;
import com.cloudring.magicsound.iconduct.impl.NearConduct;
import com.cloudring.magicsound.iconduct.impl.NetFmConduct;
import com.cloudring.magicsound.iconduct.impl.PoetryConduct;
import com.cloudring.magicsound.iconduct.impl.RemindConduct;
import com.cloudring.magicsound.iconduct.impl.StockConduct;
import com.cloudring.magicsound.iconduct.impl.TuRingConduct;
import com.cloudring.magicsound.iconduct.impl.UpDownConduct;
import com.cloudring.magicsound.iconduct.VoiceType;
import com.cloudring.magicsound.iconduct.impl.WeatherConduct;
import com.cloudring.magicsound.model.CookBook;
import com.cloudring.magicsound.model.MessageItem;
import com.cloudring.magicsound.model.TaskQueue;
import com.cloudring.magicsound.view.WaveMicView;
import com.cloudring.voice.router.IMagicSerialPort;
import com.cloudring.voice.router.impl.MagicSerialProtImpl;
import com.fge.voice.RecognizeManager;
import com.fge.voice.callback.RecognizerCallback;
import com.fge.voice.SpeakCallback;
import com.fge.voice.TTSManager;
import com.fge.voice.VoiceConfig;
import com.fge.voice.base.VoiceReadyCallback;
import com.fge.voice.callback.StringRecognizerCallback;
import com.fge.voice.util.FileWriteLog;
import com.fjtk.musiclib.utils.ActivityUtils;
import com.fjtk.musiclib.utils.MusicManager;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.mikepenz.itemanimators.SlideUpAlphaAnimator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recognition.MyHttpRequestWatcher;
import recognition.TulingRecognitionManager;
import turing.os.http.core.ErrorMessage;
import turing.os.http.core.RequestResult;

/***
 * 语音助手
 */
public class MagicLampFragment extends BaseRobotFragment {
    //记录需要TTS说完话后再执行的任务
    public TaskQueue taskQueue = null;
    private EasyRecyclerView recyclerView;
    private VoiceAdapter adapter;
    private WaveMicView mWaveMicView;
    private LinearLayoutManager mLinearLayoutManager;
    private int mPadingBottom = 0, itemHeight;
    //图灵
    private TulingRecognitionManager mTulingRecognitionManager;
    //语音识别
    private RecognizeManager mRecognizeManager;
    //语音播报 TTS
    private TTSManager ttsManager;
    //ZCQ的音乐
    private MusicManager mManager;
    private RobotFragmentActivity mActivity;
    //串口解耦接口
    private IMagicSerialPort mIMagicSerialPort;
    public Intent startIntent;//

    @Subscribe
    public void onEventMainThread(APIResponse.PosterInfo event) {
        if (event.errno == 0) {//网络请求成功
            if (event.data != null && event.data.deals != null && event.data.deals.size() > 0) {
                List list = event.data.deals;
                this.adapter.add(list);
            } else {
                this.adapter.add(new MessageItem(getString(R.string.type_nearby_noinfo)));
            }
        } else if (event.errno == -1) {//网络错误
            this.adapter.add(new MessageItem(getString(R.string.type_nearby_nonet)));
        } else {
            Toast.makeText(mActivity, event.errno + event.msg, Toast.LENGTH_LONG).show();
        }
        sendStartEvent();
        this.mWaveMicView.showSay();
    }

    /***
     * 成功唤醒发送来的消息
     * @param event
     */
    @Subscribe
    public void onEventMainThread(SpeechEvent event) {
        FileWriteLog.writeLog("接收唤醒Event ");
        if (!mRecognizeManager.isListening())
            startSpeech();
    }

    /****
     * 显示菜谱详情ragment 消息来源于点击菜谱item
     * @param event
     */
    @Subscribe
    public void onEventMainThread(CookBookEvent event) {
        showFragment(event.detailUrl);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity != null) {
            mActivity = (RobotFragmentActivity) getActivity();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_voice;
    }

    @Override
    public void onResume() {
        super.onResume();
        sendStopEvent();//发送消息 停止唤醒监听 释放麦克风
        if (mRecognizeManager.isReadySucess()) {
            startSpeech();
        }
    }

    private void initManager() {
        initTuling();
        //串口实现类
        mIMagicSerialPort = new MagicSerialProtImpl();
        //音乐
        mManager = MusicManager.getInstance();
        mRecognizeManager = RecognizeManager.getInstance(mActivity);
        mRecognizeManager.init(new VoiceReadyCallback() {
            @Override
            public void onReadyCallback(boolean isSuceess, int type, final String errMsg) {
                if (!isSuceess) {
                    mRecognizeManager.destory();
                    if (isVisible()) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mActivity, errMsg, Toast.LENGTH_LONG).show();
                                FileWriteLog.writeLog(errMsg);
                            }
                        });
                    }
                } else if (isVisible()) {
                    startSpeech();
                }
            }
        });
        ttsManager = TTSManager.getInstance(mActivity);
    }

    @Override
    protected void initView() {
        initManager();
        recyclerView = (EasyRecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter = new VoiceAdapter(mActivity));
        recyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(mActivity));
        recyclerView.getRecyclerView().setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        mWaveMicView = (WaveMicView) rootView.findViewById(R.id.waveMicView);
        mWaveMicView.startLoading();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerview, int dx, int dy) {
                super.onScrolled(recyclerview, dx, dy);
                /****根据dy的值 扩大或者缩小PaddingBottom*/
                int bottom = recyclerView.getRecyclerView().getPaddingBottom();
                if (mPadingBottom > 0 && mPadingBottom < itemHeight) {
                    recyclerView.getRecyclerView().setPadding(0, 0, 0, bottom + dy);
                }
            }
        });
        recyclerView.getRecyclerView().setItemAnimator(new SlideUpAlphaAnimator());
        recyclerView.getRecyclerView().getItemAnimator().setAddDuration(500);
        recyclerView.getRecyclerView().getItemAnimator().setRemoveDuration(500);
        recyclerView.getRecyclerView().getItemAnimator().setMoveDuration(500);
        recyclerView.getRecyclerView().getItemAnimator().setChangeDuration(500);
        mWaveMicView.ivSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStopEvent();//发送消息 停止唤醒监听 释放麦克风
                startSpeech();//开始监听说话
            }
        });
        mWaveMicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileWriteLog.writeLog("点击 停止 ");
                mWaveMicView.endSpeak();
                mRecognizeManager.stopRecording();
            }
        });
        /**启动隐藏的Activity 测试的时候使用的 **********************/
        hideBtnAndClickListen();
        /**end  启动隐藏的Activity 测试的时候使用的 **********************/

    }

    /**
     * 启动语音语义识别引擎
     */
    private void startSpeech() {
        //如果cookFragment在Top
        Fragment mFragment = mActivity.getSupportFragmentManager().findFragmentByTag("cookbook");
        if (mFragment != null && mFragment.isVisible()) {
            mActivity.onBackPressed();
        }
        isPlayed = mManager.isPlaying();
        //发送广播 关闭音乐
        mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PAUSE);
        ttsManager.stopSpeak();
        //执行开始录音动画
        mWaveMicView.startSpeak();
        //启动引擎录音
        mRecognizeManager.startRecording(mRecognizerCallback);
    }

    public void speak(String text) {
        ttsManager.startSpeak(text, new MySpeakCallback());
    }

    public void speak(String text, MySpeakCallback callback) {
        ttsManager.startSpeak(text, callback);
    }

    /**
     * 根据条件发送媒体广播播放音乐 显示话筒图标
     */
    private void sendMediaShowSay() {
        if (isPlayed) {
            mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PLAY);
        }
        mWaveMicView.showSay();
    }

    private RecognizerCallback mRecognizerCallback = new StringRecognizerCallback() {

        @Override
        public void onResult(boolean islast,String json) {
            try {
                FileWriteLog.writeLog("结果：：" + json);
                //启动唤醒监听
                sendStartEvent();
                IConduct iConduct = null;
                String type = VoiceType.parseType(json);//场景类型
                String input = VoiceType.getInput();//语音翻译的结果
                if (!TextUtils.isEmpty(input)) {
                    adapter.add(input);
                } else {//翻译结果为空时  如果列表iten为0 显示提示
                    if (adapter.getCount() == 0) {
                        showTips();
                    }
                    sendMediaShowSay();
                    return;
                }
                FileWriteLog.writeLog("类型===" + type);
                if ("ws".equals(type)) {//许飞本地语法
                    iConduct = new GrammarConduct(MagicLampFragment.this, json, mIMagicSerialPort, adapter);
                } else if ("music".equals(type)) {//音乐
                    iConduct = new MusicConduct(MagicLampFragment.this, adapter);
                } else if ("weather".equals(type)) {//天气
                    iConduct = new WeatherConduct(MagicLampFragment.this, adapter);
                } else if ("command".equals(type)) {//中控
                    iConduct = new CommandConduct(mActivity, adapter);
                } else if ("reminder".equals(type)) {//提醒
                    iConduct = new RemindConduct(adapter, getContext());
                } else if ("restaurant".equals(type) || "near".equals(type)) {//附近 周边
                    iConduct = new NearConduct(MagicLampFragment.this, adapter);
                } else if ("groupon".equals(type)) {//团购
                    iConduct = new GrouponConduct();
                } else if ("control".equals(type)) {//设备控制
                    iConduct = new ControlConduct(MagicLampFragment.this, mIMagicSerialPort, adapter);
                } else if ("adjust".equals(type)) {//设备打开
                    iConduct = new AdjustConduct(MagicLampFragment.this, adapter, mIMagicSerialPort);
                } else if ("updown".equals(type)) {//设备微调
                    iConduct = new UpDownConduct(MagicLampFragment.this, adapter, mIMagicSerialPort);
                } else if ("stock".equals(type)) {//股票
                    iConduct = new StockConduct(MagicLampFragment.this, adapter);
                } else if ("poetry".equals(type)) {//诗歌
                    iConduct = new PoetryConduct(MagicLampFragment.this, adapter);
                } else if ("app".equals(type)) {//
                    iConduct = new LocalappConduct(MagicLampFragment.this, adapter);
                } else if ("telephone".equals(type) || "phone".equals(type)) {//拨打电话
                    iConduct = new LocalphoneConduct(MagicLampFragment.this);
                } else if ("localmusic".equals(type) || "musicPlayer_smartHome".equals(type)) {//本地音乐控制命令
                    iConduct = new LocalmusicConduct(MagicLampFragment.this, adapter, isPlayed);
                } else if ("localvolume".equals(type)) {//本地音量调节
                    iConduct = new LocalvolumeConduct(mActivity, isPlayed, adapter);
                } else if ("netfm".equals(type)) {//电台
                    iConduct = new NetFmConduct(MagicLampFragment.this, adapter, isPlayed);
                } else if ("turing".equals(type)) {//
                    iConduct = new TuRingConduct(mTulingRecognitionManager, input);
                    iConduct.execute(json);
                    return;
                } else if ("turing".equals(type)) {//
                    iConduct = new TuRingConduct(mTulingRecognitionManager, input);
                } else if ("openQA".equals(type) || "chat".equals(type)) {//聊天
                    if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_AISPEECH) {
                        iConduct = new ChatConduct(MagicLampFragment.this, adapter, mTulingRecognitionManager);
                    } else {
                        iConduct = new TuRingConduct(mTulingRecognitionManager, input);
                    }
                } else {
                    iConduct = new TuRingConduct(mTulingRecognitionManager, input);
                }
                if (iConduct != null)
                    iConduct.execute(json);
            } catch (Exception e) {
                FileWriteLog.writeLog(e.getLocalizedMessage());
                e.printStackTrace();
            }
            mWaveMicView.showSay();
            scrollList();
        }

        @Override
        public void onVolumeChanged(float rmsdB) {
            super.onVolumeChanged(rmsdB);
            mWaveMicView.updateAmplitude(rmsdB);
        }

        @Override
        public void onEndOfSpeech() {
            super.onEndOfSpeech();
            mWaveMicView.endSpeak();
        }

        @Override
        public void onError(int code, String errorMsg) {//会话发生错误回调接口
            FileWriteLog.writeLog("callback code == :" + code + "  errorMsg : " + errorMsg);
            if (code == 70904 || code == 10118) {
                if (adapter.getCount() == 0) {
                    showTips();
                }
                FileWriteLog.writeLog(getString(R.string.type_error_norecord));
            } else if (code == 20005) {
                if (adapter.getCount() == 0) {
                    showTips();
                }
            } else if (code == 10121 || code == 10114) {
                adapter.add(new MessageItem(getString(R.string.type_nearby_nonet)));
                ttsManager.startSpeak(getString(R.string.type_nearby_nonet));
            } else if (code == 70910) {
                adapter.add(new MessageItem(getString(R.string.network_connect_timeout)));
                ttsManager.startSpeak(getString(R.string.network_connect_timeout));
            } else if (code == 70903) {
                FileWriteLog.writeLog(getString(R.string.type_error_recordfail));
                Toast.makeText(mActivity, errorMsg, Toast.LENGTH_LONG).show();
                //adapter.add(new MessageItem("录音失败!"));
            }  else if (code == 70905) {
                adapter.add(new MessageItem(getString(R.string.network_recording_out)));
                ttsManager.startSpeak(getString(R.string.network_recording_out));
            } else {
                Toast.makeText(mActivity, errorMsg, Toast.LENGTH_LONG).show();
            }
            sendStartEvent();
            mWaveMicView.endSpeak();
            mWaveMicView.showSay();
            if (isPlayed) {
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PLAY);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //如果引擎在退出之前都没有准备好 直接销毁下次重头开始初始化
        if (!mRecognizeManager.isReadySucess()) {
            mRecognizeManager.destory();
        }
        FileWriteLog.writeLog("Fragment onDestroy()");
    }

    /***
     * WebView打开网页的Fragment
     *
     * @param url 网址
     */
    private void showFragment(String url) {
        CookBookFragment mCookBookFragment = new CookBookFragment();
        Bundle bundle = new Bundle();
        bundle.putString("detailUrl", url);
        mCookBookFragment.setArguments(bundle);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, mCookBookFragment, "cookbook")
                .setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out)
                .addToBackStack(null)
                .commit();
    }

    /***
     * 启动唤醒监听
     */
    private void sendStartEvent() {
        FileWriteLog.writeLog("发送 sendStartEvent");
        EventBus.getDefault().post(new WakeupEvent(WakeupEvent.START_WAKEUP));
    }

    /***
     * 关闭唤醒监听 (开始使用识别引擎录音前 需关闭唤醒监听)
     */
    private void sendStopEvent() {
        EventBus.getDefault().post(new WakeupEvent(WakeupEvent.STOP_WAKEUP));
    }

    boolean isPlayed = false;

    private class MySpeakCallback extends SpeakCallback {
        @Override
        public void onCompletion() {
            if (isPlayed) {
                mActivity.sendMediaBroadcast(KeyEvent.KEYCODE_MEDIA_PLAY);
            }
            //判断说话完毕后 存在要启动某个界面的任务
            if (taskQueue != null) {
                if (taskQueue.getTaskQueueType() == TaskQueue.TaskQueueType.START_MUSIC) {
                    ActivityUtils.startOpenMusicActivity(mActivity,1,taskQueue.musicData,true);
                    mActivity.finish();
                } else {
                    startActivity(startIntent);
                    mActivity.finish();
                    taskQueue = null;
                }
            }
        }

        @Override
        public void onError(int errorCode, String errorMsg) {
            super.onError(errorCode, errorMsg);
            Toast.makeText(mActivity, errorMsg + ":" + errorCode, Toast.LENGTH_LONG).show();
            mWaveMicView.endSpeak();
        }
    }

    private void initTuling() {
        if (mTulingRecognitionManager == null) {
            mTulingRecognitionManager = new TulingRecognitionManager(mActivity);
        }
        mTulingRecognitionManager.initTulingApiManager(new MyHttpRequestWatcher() {
            @Override
            public void onSuccess(final RequestResult requestResult) {
                try {
                    JSONObject result_obj = new JSONObject(requestResult.getContent()
                            .toString());
                    if (result_obj.has("code")) {
                        int code = result_obj.getInt("code");
                        final String text = result_obj.optString("text");
                        if (code == 308000) {
                            if (result_obj.has("list")) {
                                JSONArray jsonArray = result_obj.optJSONArray("list");
                                List<CookBook> cookBooks = new ArrayList<CookBook>();
                                int arrayLength = jsonArray.length() > 3 ? 3 : jsonArray.length();
                                for (int i = 0; i < arrayLength; i++) {
                                    CookBook cookBook = new CookBook();
                                    cookBook.setDetailurl(jsonArray.getJSONObject(i).getString("detailurl"));
                                    cookBook.setIcon(jsonArray.getJSONObject(i).getString("icon"));
                                    cookBook.setInfo(jsonArray.getJSONObject(i).getString("info"));
                                    cookBook.setName(jsonArray.getJSONObject(i).getString("name"));
                                    cookBooks.add(cookBook);
                                }
                                adapter.add(new MessageItem(text));
                                adapter.add(cookBooks);
                            }
                        } else {
                            adapter.add(new MessageItem(text));
                        }
                        ttsManager.startSpeak(text, new MySpeakCallback());
                        scrollList();//滚动到最后两行
                        mWaveMicView.showSay();
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                } catch (JSONException e) {
                    FileWriteLog.writeLog( "JSONException:" + e.getMessage());
                } catch (Exception e) {
                    FileWriteLog.writeLog( "JSONException:" + e.getMessage());
                }
                sendStartEvent();
            }

            @Override
            public void onError(ErrorMessage errorMessage) {
                if(isAdded()) {
                    adapter.add(new MessageItem(getString(R.string.type_noresult1)));
                    sendStartEvent();
                }
            }
        });
    }

    private void scrollList() {
        int count = adapter.getCount();
        if (count >= 2) {//当item大于2  往上滑动2个item
            recyclerView.scrollToPosition(adapter.getCount() - 2);
        }
        View itemView = mLinearLayoutManager
                .getChildAt(mLinearLayoutManager.getChildCount() - 1);
        /**利用PaddingBottom 造成已经滑动到最底部还可以往上滑动的假象*/
        if (itemView != null) {
            itemView.measure(0, 0);
            itemHeight = itemView.getMeasuredHeight();
            mPadingBottom = recyclerView.getRecyclerView().getHeight()
                    - itemHeight;
            mPadingBottom = Math.abs(mPadingBottom);
            recyclerView.getRecyclerView().setPadding(0, 0, 0,
                    mPadingBottom);
        }
    }

    private void showTips() {
        scrollList();
        adapter.add(new MessageItem(getString(R.string.type_tips_label)));
        for (int i = 0; i < Constants.tipsArray.size(); i++) {
            adapter.add(Constants.tipsArray.get(i));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        FileWriteLog.writeLog("Fragment onPause");
        //停止说话
        ttsManager.stopSpeak();
        //停止控件动画
        mWaveMicView.cancel();
        //取消语音识别
        mRecognizeManager.cancel();
        //启动语音唤醒
        sendStartEvent();
    }


    /**
     * 启动隐藏的Activity 测试的时候使用的
     **********************/
    private void hideBtnAndClickListen() {
        rootView.findViewById(R.id.hide_btn).setOnClickListener(new View.OnClickListener() {
            int count = 0;
            long time;

            @Override
            public void onClick(View v) {
                if (count >= 6) {
                    count = 0;
                    if (VoiceConfig.getEngine_type() == VoiceConfig.EngineType.TYPE_IFLYTEK) {
                        mActivity.startActivity(new Intent(mActivity, IflySettingActivity.class));
                    } else {
                        mActivity.startActivity(new Intent(mActivity, ASettingActivity.class));
                    }
                    mActivity.finish();
                } else if (System.currentTimeMillis() - time > 3000 && time > 0) {
                    count = 0;
                    time = 0;
                } else {
                    count++;
                    time = System.currentTimeMillis();
                }
            }
        });
    }
}
