package com.example.paidelidemo.ui.earncoins;

import android.content.Context;
import android.view.View;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * 赚取金币类
 * 
 * @author xiehaifeng
 */
public class EarnCoinsActivity extends HeadBaseActivity {
	private Context context;

	/** 常规构造方法 */
	public EarnCoinsActivity() {
		AppManager.getInstance().addActivity(EarnCoinsActivity.this);
		context = EarnCoinsActivity.this;
	}

	/** 添加标题栏 */
	@Override
	public void appHead(View view) {
		top_text.setText("赚取金币");
		btn_left.setOnClickListener(this);
	}

	/** 替换初始化布局 */
	@Override
	public void initReplaceView() {
		// TODO Auto-generated method stub

	}

	/** 监听事件 */
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
