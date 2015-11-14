package com.example.paidelidemo.ui.shineprize;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.earnpoints.widget.PullToRefreshListView;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * 用户晒奖类
 * 
 * @author xiehaifeng
 * 
 */
public class ShinePrizeActivity extends HeadBaseActivity
{
	private Context context;
	private Button btn_jf_showprice;
	private Button btn_yy_showprice;
	private Button btn_show_my_prize;
	private PullToRefreshListView pl_prize_list;
	/** 常规构造方法 */
	public ShinePrizeActivity()
	{
		AppManager.getInstance().addActivity(ShinePrizeActivity.this);
		context = ShinePrizeActivity.this;
	}

	/** 添加标题栏 */
	@Override
	public void appHead(View view)
	{
		top_text.setText("用户晒奖");
		btn_left.setOnClickListener(this);
	}

	/** 替换布局视图 */
	@Override
	public void initReplaceView()
	{
		// 获取base布局文件中的布局
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// 使用login布局文件封装view容器
		View view = View.inflate(context, R.layout.activity_show_prize, null);
		// 布局添加view
		base_frameLayout.addView(view);
		initView(view);
	}
	
	/**初始化视图*/
	private void initView(View view)
	{
		//开心摇奖
		btn_jf_showprice = (Button) getView(view, R.id.btn_jf_showprice);
		//一元摇奖
		btn_yy_showprice = (Button) getView(view, R.id.btn_yy_showprice);
		//列表
		pl_prize_list = (PullToRefreshListView) getView(view,
				R.id.pl_prize_list);
		//我要分享
		btn_show_my_prize = (Button) getView(view, R.id.btn_show_my_prize);
		
		btn_show_my_prize.setOnClickListener(this);
		
	}

	/** 添加监听方法 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_head_left:
			finish();
			break;

		case R.id.btn_show_my_prize:
			
			break;
			
		default:
			break;
		}
	}
}
