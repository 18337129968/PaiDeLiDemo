package com.example.paidelidemo.ui.home;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * 我的消息类
 * 
 * @author xiehaifeng
 * 
 */
public class MyMessageActivity extends HeadBaseActivity {

	private Context context;

	/** 常规构造方法 */
	public MyMessageActivity() {
		AppManager.getInstance().addActivity(this);
		context = MyMessageActivity.this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_head_left:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void appHead(View view)
	{
		top_text.setText("消息");
		btn_left.setOnClickListener(this);
	}

	@Override
	public void initReplaceView()
	{
		// TODO Auto-generated method stub
		
	}
}
