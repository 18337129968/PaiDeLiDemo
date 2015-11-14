package com.example.paidelidemo.ui.myfriend;

import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的好友类
 * 
 * @author 谢海烽
 * 
 */
public class MyFriendActivity extends HeadBaseActivity {
	
	@ViewInject(R.id.btn_news)
	private Button btn_news;
	@ViewInject(R.id.btn_friend)
	private Button btn_friend;
	@ViewInject(R.id.btn_spread)
	private Button btn_spread;
	
	private Context context;
	private FriendFragmentActivity friendFragmentActivity;
	private FragmentTransaction fragmentTransaction;
	// 账号在别处登录
	public boolean isConflict = false;
	// 账号被移除
	private boolean isCurrentAccountRemoved = false;
	
	/**
	 * 检查当前用户是否被删除
	 */
	public boolean getCurrentAccountRemoved() {
		return isCurrentAccountRemoved;
	}
	
	/** 常规构造方法 */
	public MyFriendActivity() {
		AppManager.getInstance().addActivity(MyFriendActivity.this);
		context = MyFriendActivity.this;
	}

	/** 添加标题栏 */
	@Override
	public void appHead(View view) {
		top_text.setText("我的好友");
		btn_left.setOnClickListener(this);
	}

	/** 替换布局视图 */
	@Override
	public void initReplaceView() {
		// 获取base布局文件中的布局
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// 使用login布局文件封装view容器
		View view = View.inflate(context, R.layout.friend, null);
		// 布局添加view
		base_frameLayout.addView(view);
		//注入view和事件
		ViewUtils.inject(this);
		initview(view);
	}
	private void initview(View view) {
		btn_news.setOnClickListener(this);
		btn_friend.setOnClickListener(this);
		btn_spread.setOnClickListener(this);
		fragmentTransaction = getFragmentManager().beginTransaction();
		friendFragmentActivity =new FriendFragmentActivity(context);
		fragmentTransaction.add(R.id.frame, friendFragmentActivity);
		fragmentTransaction.commit();
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
