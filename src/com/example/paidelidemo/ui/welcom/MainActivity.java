package com.example.paidelidemo.ui.welcom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.ui.login.LoginActivity;
import com.example.paidelidemo.utils.SPUtils;

/*
 * 欢迎界面
 * @author xiehaifeng
 */
public class MainActivity extends Activity {
	private Context context;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		context = MainActivity.this;
		// 初始化视图
		initView();
	}

	private void initView() {
		imageView = (ImageView) findViewById(R.id.imageView1_welcom);
		// 创建帧动画，使用了淡入淡出效果
		AnimationSet animationSet = new AnimationSet(true);
		// 淡入淡出效果
		// TranslateAnimation alphaAnimation = new TranslateAnimation(0, 0, 100,
		// 0);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		// 设置动画时间
		alphaAnimation.setDuration(2000);
		// 设置动画最终停留状态
		alphaAnimation.setFillAfter(true);
		// 把淡入淡出效果添加到动画容器里
		animationSet.addAnimation(alphaAnimation);
		// 视图开启动画
		imageView.startAnimation(animationSet);

		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				gotoNextActivity();
			}
		});
	}

	private void gotoNextActivity() {
		// 如果SharedPreferences存储没有赋值返回true
		boolean isFirst = (Boolean) SPUtils.get(context, "isFirst", true);

		if (isFirst) {
			Intent intent = new Intent(context, GuideActivity.class);
			startActivity(intent);
		} else {
			/* 界面跳转到登录界面，传递参数 */
			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("Login", Constant.LOGIN_TO_HOME_CODE);
			startActivity(intent);
		}
		this.finish();
	}

}
