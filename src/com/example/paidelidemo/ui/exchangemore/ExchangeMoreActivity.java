package com.example.paidelidemo.ui.exchangemore;

import android.content.Context;
import android.view.View;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * 兑换商城类
 * 
 * @author Administrator
 * 
 */
public class ExchangeMoreActivity extends HeadBaseActivity {
	private Context context;

	/** 常规构造方法 */
	public ExchangeMoreActivity() {
		AppManager.getInstance().addActivity(ExchangeMoreActivity.this);
		context = ExchangeMoreActivity.this;
	}

	/** 添加标题栏 */
	@Override
	public void appHead(View view) {
		top_text.setText("兑换商城");
		btn_left.setOnClickListener(this);
	}

	/** 替换布局视图 */
	@Override
	public void initReplaceView() {
		// TODO Auto-generated method stub

	}

	/** 添加监听方法 */
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
