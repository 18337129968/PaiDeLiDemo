package com.example.paidelidemo.ui.merchantaccount;

import android.content.Context;
import android.view.View;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * 商家管理类
 * 
 * @author Administrator
 * 
 */
public class MerchantAccountActivity extends HeadBaseActivity {
	private Context context;

	/** 常规构造方法 */
	public MerchantAccountActivity() {
		AppManager.getInstance().addActivity(MerchantAccountActivity.this);
		context = MerchantAccountActivity.this;
	}

	/** 添加标题栏 */
	@Override
	public void appHead(View view) {
		top_text.setText("商家管理");
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
