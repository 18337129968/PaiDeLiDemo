package com.example.paidelidemo.ui.myfriend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.entity.request.LoginUserResult.User;
import com.example.paidelidemo.ui.earnpoints.widget.PullToRefreshBase;
import com.example.paidelidemo.ui.earnpoints.widget.PullToRefreshListView;
import com.example.paidelidemo.ui.myfriend.ReceiveContents.ContactInfo;
import com.example.paidelidemo.ui.myfriend.util.CharacterParser;
import com.example.paidelidemo.ui.myfriend.util.PinyinComparator;
import com.example.paidelidemo.ui.myfriend.util.SortAdapter;
import com.example.paidelidemo.ui.myfriend.util.SortModel;
import com.example.paidelidemo.ui.myfriend.view.SideBar;
import com.example.paidelidemo.ui.myfriend.view.SideBar.OnTouchingLetterChangedListener;

/**
 * 
 * 好友
 * 
 * @author zhaobin
 */
@SuppressLint("NewApi")
public class FriendFragmentActivity extends Fragment
{

	Context context;
	private static final String TAG = "FriendFragmentActivity";
	private SideBar sideBar = null;
	private PullToRefreshListView listView = null;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	private SortAdapter adapter;
	private LinearLayout newFridenLayout = null;
	private LinearLayout addFridenLayout = null;
	private LinearLayout merchant_code_layout = null;
	private TextView unreadMsgView = null;
	private int page = 1;
	private int pageSize = 9;
	public static final int type = 0;

	private List<ContactInfo> list;

	public FriendFragmentActivity(Context context)
	{
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		new QueryData().execute();
		View view = inflater.inflate(R.layout.my_friend, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		// 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
		if (savedInstanceState != null
				&& savedInstanceState.getBoolean("isConflict", false))
			return;
		init();
	}

	public void init()
	{
		SourceDateList = new ArrayList<SortModel>();
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		this.sideBar = (SideBar) getActivity().findViewById(R.id.sidrbar);
		this.listView = (PullToRefreshListView) getActivity().findViewById(
				R.id.friend_list);

		this.newFridenLayout = (LinearLayout) getActivity().findViewById(
				R.id.new_layout);
		this.addFridenLayout = (LinearLayout) getActivity().findViewById(
				R.id.add_layout);
		this.merchant_code_layout = (LinearLayout) getActivity().findViewById(
				R.id.merchant_code_layout);
		this.unreadMsgView = (TextView) getActivity().findViewById(
				R.id.myfriend_unread_msg_number);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener()
		{

			@Override
			public void onTouchingLetterChanged(String s)
			{
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1)
				{
					listView.getRefreshableView().setSelection(position);
				}

			}
		});

		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{

				page = 1;
			}
		});
		
		listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView)
			{
				page = 1;
				getFriendList();
				listView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView)
			{
				page++;
				getFriendList();
				listView.onRefreshComplete();
			}
		});
		this.newFridenLayout.setOnClickListener(clickListener);
		this.addFridenLayout.setOnClickListener(clickListener);
		this.merchant_code_layout.setOnClickListener(clickListener);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		refresh();
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private void getFriendList()
	{

		// 重要部分
		for (int i = 0; i < list.size(); i++)
		{

			SortModel sortModel = new SortModel();
			sortModel.setObject(list.get(i));
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(list.get(i)
					.getContactName());
			
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]"))
			{
				sortModel.setSortLetters(sortString.toUpperCase());
			} else
			{
				sortModel.setSortLetters("#");
			}

			SourceDateList.add(sortModel);
		}
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);

		if (adapter == null)
		{
			adapter = new SortAdapter(getActivity(), SourceDateList, type);

			listView.setAdapter(adapter);
		} else
		{
			adapter.refresh(SourceDateList);
		}
	}

	private OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = null;
			switch (v.getId())
			{
			case R.id.new_layout:
				page = 1;

				break;
			case R.id.add_layout:
				page = 1;

				break;
			case R.id.merchant_code_layout:
				page = 1;

				break;
			}
		}
	};

	// 刷新ui
	public void refresh()
	{

	}

	/**
	 * 获取联系人列表，并过滤掉黑名单和排序
	 */
	private User getContact()
	{
		return null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		if (((MyFriendActivity) getActivity()).isConflict)
		{
			outState.putBoolean("isConflict", true);
		} else if (((MyFriendActivity) getActivity())
				.getCurrentAccountRemoved())
		{
			outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
		}

	}

	@Override
	public void onDestroy()
	{

		super.onDestroy();
	}

	private class QueryData extends AsyncTask<Void, Integer, Void>
	{
		ProgressDialog progressDialog = new ProgressDialog(context);

		@Override
		protected Void doInBackground(Void... params)
		{
			list = new ReceiveContents(context).getContact();
			return null;
		}

		@Override
		protected void onPreExecute()
		{
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result)
		{
			progressDialog.dismiss();
			getFriendList();
			super.onPostExecute(result);
		}
	}
}
