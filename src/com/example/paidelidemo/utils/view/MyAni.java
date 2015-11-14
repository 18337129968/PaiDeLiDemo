package com.example.paidelidemo.utils.view;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

/** home界面的Animation */
public class MyAni {
	int circleXR;
	float circleX;
	int circleYR;
	float circleY;
	long duration;
	boolean first;

	@SuppressWarnings("deprecation")
	public MyAni(Context mContext) {
		super();
		WindowManager manager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = manager.getDefaultDisplay();
		this.circleXR = Animation.RELATIVE_TO_SELF;
		this.circleX = 0.5f;
		this.circleYR = Animation.ABSOLUTE;
		this.circleY = defaultDisplay.getWidth();
		// this.duration = 200;
		this.first = true;
	}

	public MyAni(int circleXR, float circleX, int circleYR, float circleY) {
		super();
		this.circleXR = circleXR;
		this.circleX = circleX;
		this.circleYR = circleYR;
		this.circleY = circleY;
		this.duration = 200;
	}

	public void setCircle(int circleXR, float circleX, int circleYR,
			float circleY) {
		this.circleXR = circleXR;
		this.circleX = circleX;
		this.circleYR = circleYR;
		this.circleY = circleY;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Object myAnimation(Object object, float start, float finish,
			float begin, float end) {
		
		AnimationSet animationSet = new AnimationSet(true);
		//旋转效果（开始角度，结束角度，x轴伸缩模式，x轴伸缩值，y轴伸缩模式，y轴伸缩值）
		RotateAnimation rotateAnimation = new RotateAnimation(start, finish,
				circleXR, circleX, circleYR, (float) (circleY * 1.2));
		//缩放效果（开始大小，结束大小，x轴伸缩模式，x轴伸缩值，y轴伸缩模式，y轴伸缩值）
		ScaleAnimation scaleAnimation = new ScaleAnimation(begin, end, begin,
				end, Animation.RELATIVE_TO_SELF, 1f,
				Animation.RELATIVE_TO_SELF, 1f);
		
		animationSet.addAnimation(rotateAnimation);
		animationSet.addAnimation(scaleAnimation);
		animationSet.setFillAfter(true);
		if (first) {
			duration = 0;
			first = false;
		} else {
			duration = 200;
		}
		animationSet.setDuration(duration);
		RelativeLayout relativeLayout = (RelativeLayout) object;
		relativeLayout.startAnimation(animationSet);
		return animationSet;
	}

}
