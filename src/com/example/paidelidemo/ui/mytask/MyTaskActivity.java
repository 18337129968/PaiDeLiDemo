package com.example.paidelidemo.ui.mytask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.text.format.Time;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.entity.request.RequestTaskList;
import com.example.paidelidemo.entity.request.RequestTaskList.TaskSonData;
import com.example.paidelidemo.ui.earnpoints.EarnPointsActivity;
import com.example.paidelidemo.ui.earnpoints.MyAdapter;
import com.example.paidelidemo.ui.earnpoints.PosterList;
import com.example.paidelidemo.ui.earnpoints.ViewHolder;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;
import com.example.paidelidemo.utils.HttpConnectTool;
import com.example.paidelidemo.utils.ToastUtils;
import com.example.paidelidemo.utils.HttpConnectTool.ConnectListener;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的任务类
 * 
 * @author xiehaifeng
 * 
 */
public class MyTaskActivity extends HeadBaseActivity
{
	private ListView taskListview;
	
	private Context context;
	private String userId, tokenId, activityId, taskid;
	private Gson gson;
	private RequestTaskList requestTaskList;
	private MyAdapter<PosterList> myAdapter;
	public static List<PosterList> posterLists = new ArrayList<PosterList>();
	
	/** 常规构造方法 */
	public MyTaskActivity()
	{
		AppManager.getInstance().addActivity(MyTaskActivity.this);
		context = MyTaskActivity.this;
	}

	/** 添加标题栏 */
	@Override
	public void appHead(View view)
	{
		top_text.setText("我的任务");
		btn_left.setOnClickListener(this);
	}

	/** 替换布局视图 */
	@Override
	public void initReplaceView()
	{
		// 获取base布局文件中的布局
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// 使用login布局文件封装view容器
		View view = View.inflate(context, R.layout.my_task_list, null);
		// 布局添加view
		base_frameLayout.addView(view);
		taskListview = (ListView)findViewById(R.id.my_news_listview);
		
		userId = "13333333333";
		tokenId = "100";

	}

	@Override
	protected void onResume()
	{
		super.onResume();

		posterLists.clear();
		for (int i = 0; i < 7; i++) {
			PosterList posterList = new PosterList();
			posterList.setPosterImgUrl(i+"");
			posterList.setPosterPrice(120);
			posterList.setPosterTitle("海澜之家羊毛羽绒服");
			posterLists.add(posterList);
		}

		taskList();
	}

	/** 任务列表 */
	private void taskList()
	{

		requestTaskList = new RequestTaskList();
		requestTaskList.setC(Constant.TASK_LIST);
		TaskSonData data = new TaskSonData();
		data.setTokenId(tokenId);
		data.setUserId(userId);
		requestTaskList.setP(data);
		gson = new Gson();
		String requestJson = gson.toJson(requestTaskList);

		HttpConnectTool.update(requestJson, context, new ConnectListener()
		{

			@Override
			public void onConnectSuccess(String result)
			{
				/**
				 * TaskListResult listResult = gson.fromJson(result,
				 * TaskListResult.class);
				 */
				
				final List<PosterList> list = MyTaskActivity.posterLists;
				myAdapter = new MyAdapter<PosterList>(context, list,
						R.layout.mytask_item)
				{

					@Override
					public void convert(ViewHolder helper, int position,
							PosterList item)
					{
						helper.setImageResource(R.id.activity_imageview,
								R.drawable.activity);
						
						helper.setText(R.id.mytask_name,
								list.get(position).posterTitle);
						
						helper.setText(R.id.mytask_count,
								list.get(position).getPosterPrice()+"");
						
						helper.setText(R.id.exercise_time, "(送锁活动"
								+ GetTime(Calendar.getInstance()
										.getTimeInMillis())
								+ " ~ "
								+ GetTime(Calendar.getInstance()
										.getTimeInMillis()) + ")");
						
						helper.setText(R.id.to_invite, "前往");
						
						helper.getView(R.id.to_invite).setBackgroundResource(
								R.drawable.come_btn);
						
					}

				};
				taskListview.setAdapter(myAdapter);
			}

			@Override
			public void onConnectStart()
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onConnectFailed(String error, String request)
			{
				if (error != null)
				{
					ToastUtils.makeText(context, error, 0).show();
				}
				finish();
			}
		});

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

		default:
			break;
		}
	}

	public static String GetTime(long cc_time)
	{
		String str_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		str_time = sdf.format(cc_time);
		return str_time;
	}
}
