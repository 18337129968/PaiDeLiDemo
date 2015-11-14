package com.example.paidelidemo.ui.merchantaccount;

import android.content.Context;
import android.view.View;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * �̼ҹ�����
 * 
 * @author Administrator
 * 
 */
public class MerchantAccountActivity extends HeadBaseActivity {
	private Context context;

	/** ���湹�췽�� */
	public MerchantAccountActivity() {
		AppManager.getInstance().addActivity(MerchantAccountActivity.this);
		context = MerchantAccountActivity.this;
	}

	/** ��ӱ����� */
	@Override
	public void appHead(View view) {
		top_text.setText("�̼ҹ���");
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
