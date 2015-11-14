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
 * ��ӭ����
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
		// ��ʼ����ͼ
		initView();
	}

	private void initView() {
		imageView = (ImageView) findViewById(R.id.imageView1_welcom);
		// ����֡������ʹ���˵��뵭��Ч��
		AnimationSet animationSet = new AnimationSet(true);
		// ���뵭��Ч��
		// TranslateAnimation alphaAnimation = new TranslateAnimation(0, 0, 100,
		// 0);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		// ���ö���ʱ��
		alphaAnimation.setDuration(2000);
		// ���ö�������ͣ��״̬
		alphaAnimation.setFillAfter(true);
		// �ѵ��뵭��Ч����ӵ�����������
		animationSet.addAnimation(alphaAnimation);
		// ��ͼ��������
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
		// ���SharedPreferences�洢û�и�ֵ����true
		boolean isFirst = (Boolean) SPUtils.get(context, "isFirst", true);

		if (isFirst) {
			Intent intent = new Intent(context, GuideActivity.class);
			startActivity(intent);
		} else {
			/* ������ת����¼���棬���ݲ��� */
			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("Login", Constant.LOGIN_TO_HOME_CODE);
			startActivity(intent);
		}
		this.finish();
	}

}
