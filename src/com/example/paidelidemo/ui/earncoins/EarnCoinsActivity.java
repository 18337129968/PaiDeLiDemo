package com.example.paidelidemo.ui.earncoins;

import android.content.Context;
import android.view.View;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * ׬ȡ�����
 * 
 * @author xiehaifeng
 */
public class EarnCoinsActivity extends HeadBaseActivity {
	private Context context;

	/** ���湹�췽�� */
	public EarnCoinsActivity() {
		AppManager.getInstance().addActivity(EarnCoinsActivity.this);
		context = EarnCoinsActivity.this;
	}

	/** ��ӱ����� */
	@Override
	public void appHead(View view) {
		top_text.setText("׬ȡ���");
		btn_left.setOnClickListener(this);
	}

	/** �滻��ʼ������ */
	@Override
	public void initReplaceView() {
		// TODO Auto-generated method stub

	}

	/** �����¼� */
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
