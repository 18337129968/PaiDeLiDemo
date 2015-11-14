package com.example.paidelidemo.ui.earnpoints;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.earnpoints.widget.PullToRefreshBase;
import com.example.paidelidemo.ui.earnpoints.widget.PullToRefreshListView;

/**
 * App下载
 * 
 * @author baohan
 * 
 */
@SuppressLint("ValidFragment")
public class IntegralWallActivity extends Fragment implements
		IEarnPointsRequest {
	private PullToRefreshListView listView;
	private Context context;
	String type;
	private int page = 0;
	private int pageSize = 9;
	private boolean flag = true;
	private static int thread_count = 0;
	private static final int thread_total_count = 3;

	public IntegralWallActivity(Context mContext, String type) {
		this.context = mContext;
		this.type = type;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list, container, false);
		init(view);
		return view;
	}

	@SuppressLint("NewApi")
	public void init(View view) {

		listView = (PullToRefreshListView) view.findViewById(R.id.ListView1);
		listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page = 0;
				getResult();
				listView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page++;
				getResult();
				listView.onRefreshComplete();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});

	}

	private void QueryUser() {

	}

	public void getPrice(String appScore) {
	}

	private void getResult() {
	}

	// 实现下载

	public void implementsDownLoad(final String url, final String fileName,
			final Long Appsize, final String appScore) {
		new Thread() {

			public void run() {

			};
		}.start();

	}

	// 在手机上打开，并安装
	public void openFile(File file) {
		String fileName = file.getName();
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);

	}

	@Override
	public void request() {
		// TODO Auto-generated method stub
		getResult();
		flag = false;
	}

	@Override
	public boolean isFirst() {
		// TODO Auto-generated method stub
		return flag;
	}

}
