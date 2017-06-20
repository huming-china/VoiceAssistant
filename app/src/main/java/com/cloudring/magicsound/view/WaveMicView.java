package com.cloudring.magicsound.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cloudring.magicsound.R;

/***
 * 语音助手声波+麦克风组合动画View
 * 
 * @author hm
 * 
 */
public class WaveMicView extends FrameLayout {
	private WaveView mWaveView;
	public ImageView ivWait, ivSay;
	private ObjectAnimator loadingObjectAnimator;
	private boolean isSpeech;
	public WaveMicView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		// TODO Auto-generated constructor stub
	}

	public WaveMicView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		// TODO Auto-generated constructor stub
	}

	private void initView() {
		// loading
		ivWait = new ImageView(getContext());
		ivWait.setVisibility(View.VISIBLE);
		ivWait.setImageResource(R.drawable.loading);

		// 麦克风图标
		ivSay = new ImageView(getContext());
		ivSay.setImageResource(R.drawable.say);
		ivSay.setVisibility(View.GONE);
		// 声波图
		mWaveView = new WaveView(getContext());

		// 添加视图
		addView(mWaveView, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER));
		
		addView(ivWait, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, Gravity.CENTER));
		addView(ivSay, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, Gravity.CENTER));
		// 监听动画直线结果
		mWaveView.setOnAnimatorEndListener(new WaveView.OnAnimatorEndListener() {

			@Override
			public void onAnimatorEnd(int speak) {
				// TODO Auto-generated method stub
				startLoading();
			}
		});
		startLoading();
	}
	public void cancel() {
		if(mWaveView!=null)
		mWaveView.cancel();
	}
	// 显示加载动画
	public void startLoading() {
		if(ivSay.getVisibility()==VISIBLE){return;}
		ivWait.setVisibility(View.VISIBLE);
		if (loadingObjectAnimator == null) {
			loadingObjectAnimator = ObjectAnimator.ofFloat(ivWait, "rotation", 0F, 360F);
			loadingObjectAnimator.setDuration(1500);
			loadingObjectAnimator.setInterpolator(new LinearInterpolator());
			loadingObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
			loadingObjectAnimator.start();// 360度旋转
			
		}

	}

	// 隐藏加载动画
	private void hideLoading() {
		if(loadingObjectAnimator!=null) {
			loadingObjectAnimator.cancel();
			loadingObjectAnimator = null;
		}
		ivWait.setVisibility(View.GONE);
	}
	/**识别完成 动画显示麦克风图标*/
	public void showSay() {
		hideLoading();
		if(ivSay.getVisibility()==View.VISIBLE){return;}
		ivSay.setVisibility(View.VISIBLE);
		ivSay.setEnabled(false);
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0, 1f);
		PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0, 1f);
		ObjectAnimator animator=ObjectAnimator.ofPropertyValuesHolder(ivSay, pvhX, pvhY, pvhZ);
		animator.setDuration(300).start();
		animator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				ivSay.setEnabled(true);
				isSpeech=false;
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});

	}

	// 更新声波图
	public void updateAmplitude(float rmsdB) {
		/**定制化处理 */
		if (rmsdB >= 20) {
			rmsdB = (float) (1.8 + rmsdB / 300);
			if (rmsdB > 2.0f) {
				rmsdB = 2.0f;
			}
		} else if (rmsdB >= 15) {
			rmsdB = (float) (1.4 + (rmsdB - 15) / 10);
		} else if (rmsdB >= 10) {
			rmsdB = (float) (1.0 + (rmsdB - 10) / 10);
		} else if (rmsdB >= 5) {
			rmsdB = (float) (0.6 + (rmsdB - 5) / 10);
		} else if (rmsdB > 2) {
			rmsdB = (float) (0.4 + (rmsdB - 5) / 10);
		}
		mWaveView.updateAmplitude(rmsdB);
	}

	private void hideSay() {
		ivSay.setVisibility(View.GONE);
	}
	/**点击了麦克风图标 准备开始说话*/
	public void startSpeak() {
		hideSay();
		hideLoading();
		mWaveView.startSpeack();
		isSpeech=true;
	}
	/**结束了说话 准备识别*/
	public void endSpeak() {
		mWaveView.endSpeack();
	}


	public void setOnClickListener(OnClickListener listener){
		mWaveView.setOnClickListener(listener);
	}
	public boolean isSpeech(){
			return  isSpeech;
	}
}
