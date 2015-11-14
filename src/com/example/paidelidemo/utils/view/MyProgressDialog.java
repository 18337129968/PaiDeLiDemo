package com.example.paidelidemo.utils.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.example.paidelidemo.R;

/**
 * 自定义对话框
 * 
 * @author xiehaifeng
 */
public class MyProgressDialog extends Dialog {

	private Animation animation;

	public MyProgressDialog(Context context) {
		super(context);
		initDialog(context);
	}

	public MyProgressDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	private void initDialog(Context context) {
		setContentView(R.layout.dialog_progress);
		setCancelable(false);
		Window window = getWindow();
		window.setWindowAnimations(R.style.pop_menu_animation);
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.width = LayoutParams.WRAP_CONTENT;
		window.setAttributes(layoutParams);
	}

	@Override
	public void show() {
		if (animation == null) {
			animation = createForeverRotationAnimation();
			animation.setDuration(3000);
		}
		findViewById(R.id.progress_layout_imageView).startAnimation(animation);
		super.show();
	}

	/** 创建一个不停旋转的动画 */
	public static Animation createForeverRotationAnimation() {
		Animation rotationAnimation = new RotateAnimation(0, 1080,
				Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
		rotationAnimation.setInterpolator(new LinearInterpolator());
		rotationAnimation.setRepeatCount(Animation.INFINITE);
		rotationAnimation.setRepeatMode(Animation.RESTART);
		rotationAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
		return rotationAnimation;
	}
}
