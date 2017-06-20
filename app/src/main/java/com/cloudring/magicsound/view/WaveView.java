package com.cloudring.magicsound.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**.
 * 声波图View
 * @author hm
 * */
public class WaveView extends View {
	private static final float MIN_AMPLITUDE = 0.0575f;
	private float mPrimaryWidth = 2.0f;
	private float mSecondaryWidth = 0.5f;
	private float mAmplitude = MIN_AMPLITUDE;
	private int mWaveColor = Color.WHITE;
	private int mDensity = 2;
	private int mWaveCount = 4;
	private float mFrequency = 0.1875f;
	private float mPhaseShift = -0.1975f;
	private float mPhase = mPhaseShift;
	private float speed=0.05f;
	private Paint mPrimaryPaint;
	private Paint mSecondaryPaint;

	private Path mPath;
	private int startLine = 0;// 中间or两边 缩放的直线
	private int lineMoveSpeed = 80;// 直线缩放的速度
	private int isStartSpeak = 0;//-1  取消说话 0初始状态 1开始说话

	// private float mLastX;
	// private float mLastY;

	public WaveView(Context context) {
		this(context, null);
		initialize();
	}

	public WaveView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initialize();
	}

	public WaveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	private void initialize() {
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setKeepScreenOn(true);

		mPrimaryPaint = new Paint();
		mPrimaryPaint.setStrokeWidth(mPrimaryWidth);
		mPrimaryPaint.setAntiAlias(true);
		mPrimaryPaint.setStyle(Paint.Style.STROKE);
		mPrimaryPaint.setColor(mWaveColor);

		mSecondaryPaint = new Paint();
		mSecondaryPaint.setStrokeWidth(mSecondaryWidth);
		mSecondaryPaint.setAntiAlias(true);
		mSecondaryPaint.setStyle(Paint.Style.STROKE);
		mSecondaryPaint.setColor(mWaveColor);

		mPath = new Path();
	}

	public void updateAmplitude(float amplitude) {
		float ma=Math.max(amplitude, MIN_AMPLITUDE);
		if(ma>mAmplitude){
		if(ma>mAmplitude-speed){
			mAmplitude= (float) (mAmplitude+speed);
		}else {
			mAmplitude = ma;
		}}else{
			if(ma<mAmplitude-speed){
				mAmplitude= (float) (mAmplitude-speed);
			}else {
				mAmplitude = ma;
			}

		}
	}

	private OnAnimatorEndListener listener;

	public void setOnAnimatorEndListener(OnAnimatorEndListener listener) {
		this.listener = listener;
	}

	public void startSpeack() {
		isStartSpeak = 1;
		invalidate();
	}

	public void endSpeack() {
		isStartSpeak = -1;
	}
	public void cancel(){
		isStartSpeak=0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (isStartSpeak==1) {
			drawSpeaking(canvas);
		} else if(isStartSpeak==-1) {
			drawSpeakend(canvas);
		}
	}

	/** 开始说话 */
	private void drawSpeaking(Canvas canvas) {
		canvas.restore();
		int width = getWidth();
		int height = getHeight();
		float midH = height / 2.0f;
		float midW = width / 2.0f;
		// 中间延伸到两边的直线
		if (startLine > 0) {
			canvas.drawLine(0 + startLine, midH, width - startLine, midH,
					mPrimaryPaint);
			startLine -= lineMoveSpeed;
		} else {
			for (int l = 0; l < mWaveCount; ++l) {
				float maxAmplitude = midH / 2f - 4.0f;
				float progress = 1.0f - l * 1.0f / mWaveCount;
				float normalAmplitude = (1.5f * progress - 0.5f) * mAmplitude;

				float multiplier = (float) Math.min(1.0,
						(progress / 3.0f * 2.0f) + (1.0f / 3.0f));
				if (l != 0) {
					mSecondaryPaint.setAlpha((int) (multiplier * 255));
				}
				mPath.reset();
				for (int x = 0; x < width + mDensity; x += mDensity) {
					float scaling = 1f - (float) Math.pow(
							1 / midW * (x - midW), 2);
					float y = scaling
							* maxAmplitude
							* normalAmplitude
							* (float) Math.sin(180 * x * mFrequency
									/ (width * Math.PI) + mPhase) + midH;
					if (x == 0) {
						mPath.moveTo(x, y);
					} else {
						mPath.lineTo(x, y);
					}
				}
				if (l == 0) {
					canvas.drawPath(mPath, mPrimaryPaint);
				} else {
					canvas.drawPath(mPath, mSecondaryPaint);
				}
			}
			mPhase += mPhaseShift;
		}
		postInvalidate();
	}

	// 说话结束
	private void drawSpeakend(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		float midH = height / 2.0f;
		float midW = width / 2.0f;
		if (startLine <= midW - 20) {
			canvas.drawLine(0 + startLine, midH, width - startLine, midH,
					mPrimaryPaint);
			startLine += lineMoveSpeed;
		} else {
			if (listener == null) {
				new Throwable("must implements OnAnimatorEndListener");
			}
			listener.onAnimatorEnd(1);
			return;
		}
		invalidate();
	}

	public interface OnAnimatorEndListener {
		public static int STATR_SPEAK = 1;
		public static int END_SPEAK = -1;

		public void onAnimatorEnd(int speak);
	}

}
