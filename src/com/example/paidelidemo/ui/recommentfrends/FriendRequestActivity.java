package com.example.paidelidemo.ui.recommentfrends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * 推荐好友类
 * 
 * @author xiehaifeng
 * 
 */
public class FriendRequestActivity extends HeadBaseActivity
{

	private Context context;
	private LinearLayout data, nodata, weixin1, weixin, QQ, Qzone, sina;
	private ListView referencelist;

	public FriendRequestActivity()
	{
		AppManager.getInstance().addActivity(FriendRequestActivity.this);
		context = FriendRequestActivity.this;

	}

	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		Bundle bundle = new Bundle();
		bundle.putString("url", Constant.CONNECT_URL1);
		switch (v.getId())
		{
		case R.id.btn_head_left:
			finish();
			break;

		case R.id.friend_request_weixin1:
			bundle.putString("share", "WEIXIN_CIRCLE");
			intent = new Intent(this, ProManagerActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.friend_request_weixin:

			break;
		case R.id.friend_request_qq:

			break;
		case R.id.friend_request_qzone:

			break;
		case R.id.friend_request_sina:

			break;

		default:
			break;
		}
	}

	@Override
	public void appHead(View view)
	{
		top_text.setText("推荐好友");
		btn_left.setOnClickListener(this);
	}

	@Override
	public void initReplaceView()
	{
		// 获取base布局文件中的布局
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// 使用login布局文件封装view容器
		View view = View.inflate(context, R.layout.activity_friend_request,
				null);
		// 布局添加view
		base_frameLayout.addView(view);
		initView(view);
	}

	private void initView(View view)
	{
		weixin = (LinearLayout) getView(view, R.id.friend_request_weixin);
		weixin1 = (LinearLayout) getView(view, R.id.friend_request_weixin1);
		QQ = (LinearLayout) getView(view, R.id.friend_request_qq);
		Qzone = (LinearLayout) getView(view, R.id.friend_request_qzone);
		sina = (LinearLayout) getView(view, R.id.friend_request_sina);
		weixin1.setOnClickListener(this);
		weixin.setOnClickListener(this);
		QQ.setOnClickListener(this);
		Qzone.setOnClickListener(this);
		sina.setOnClickListener(this);
	}
}
