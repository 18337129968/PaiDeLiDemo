package com.example.paidelidemo.ui.welcom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.adapter.GuideAdapter;
import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.ui.login.LoginActivity;
import com.example.paidelidemo.utils.SPUtils;

/*
 * ע��ǰ������ҳ��
 * @author xiehaifeng
 */
public class GuideActivity extends Activity {
	private Context context;
	private ViewPager viewPager;
	private ImageView imageView;
	private int[] images_id;
	private GuideAdapter guideAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_guide);
		context = GuideActivity.this;
		// ��ͼ��ʼ��
		initView();
	}

	private void initView() {
		// ��ȡͼƬid
		images_id = new int[] { R.drawable.guid_item1, R.drawable.guid_item2,
				R.drawable.guid_item3, R.drawable.guid_item4 };
		guideAdapter = new GuideAdapter(context, imageView, images_id);
		// ����viewPager��������������������page
		viewPager = (ViewPager) findViewById(R.id.viewpager_guide);
		viewPager.setAdapter(guideAdapter);
		viewPager.setOnPageChangeListener(new PageChangeLisener());
	}

	// page����
	private class PageChangeLisener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int position) {
			// �ж�����ҳ��λ��
			if (position == images_id.length - 1) {
				imageView = guideAdapter.imageView;
				// ���һҳʱ��������
				imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						/* ������ת�����ݲ��� */
						Intent intent = new Intent(context, LoginActivity.class);
						intent.putExtra("Login", Constant.LOGIN_TO_HOME_CODE);
						startActivity(intent);
						/* ��������״̬ */
						SPUtils.put(context, "isFirst", false);
						/* �������� */
						finish();
					}
				});
			}

		}

	}
}
