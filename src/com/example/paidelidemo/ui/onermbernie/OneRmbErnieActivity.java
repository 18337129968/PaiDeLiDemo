package com.example.paidelidemo.ui.onermbernie;

import android.content.Context;
import android.view.View;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * һԪҡ����
 * 
 * @author Administrator
 * 
 */
public class OneRmbErnieActivity extends HeadBaseActivity {
	private Context context;

	/** ���湹�췽�� */
	public OneRmbErnieActivity() {
		AppManager.getInstance().addActivity(OneRmbErnieActivity.this);
		context = OneRmbErnieActivity.this;
	}

	/** ��ӱ����� */
	@Override
	public void appHead(View view) {
		top_text.setText("һԪҡ��");
		btn_left.setOnClickListener(this);
	}

	/** �滻������ͼ */
	@Override
	public void initReplaceView() {
		// TODO Auto-generated method stub

	}

	/** ��Ӽ������� */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_head_left:
			finish();
			break;

		default:
			break;
		}
	}
}
