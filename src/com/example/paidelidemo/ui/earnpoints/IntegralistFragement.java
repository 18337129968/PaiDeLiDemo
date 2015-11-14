package com.example.paidelidemo.ui.earnpoints;

import io.vov.vitamio.demo.VideoViewBuffer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.earnpoints.widget.PullToRefreshBase;
import com.example.paidelidemo.ui.earnpoints.widget.PullToRefreshGridView;

/**
 * 拍币榜fragement类
 * 
 * @author xiehaifeng
 * 
 */
public class IntegralistFragement extends Fragment implements
		IEarnPointsRequest {
	private Context context;
	private String type;
	private PullToRefreshGridView gridview;
	private MyAdapter<PosterList> adapter = null;
	private int mPage;
	private boolean flag1 = true, flag2 = true;
	List<PosterList> mList = new ArrayList<PosterList>();

	/** 常规构造 */
	public IntegralistFragement() {
	}

	/** 带参数构造 */
	public IntegralistFragement(Context context, String type) {
		this.context = context;
		this.type = type;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.earn_points_gridview, null);

		gridview = (PullToRefreshGridView) view.findViewById(R.id.gridview);

		gridview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				mPage = 0;
				intergraNewList(mPage, true);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				mPage = mPage +1;
				intergraNewList(mPage, false);
			}
		});
		return view;
	}

	@Override
	public void request() {
		intergraNewList(0, true);
		if (type.equals("1")) {
			flag1 = false;
		} else if (type.equals("2")) {
			flag2 = false;
		}
	}

	@Override
	public boolean isFirst() {
		if (type.equals("1")) {
			return flag1;
		} else if (type.equals("2")) {
			return flag2;
		} else {
			return true;
		}
	}
	
	public void intergraNewList(int page, final boolean isUpOrDown) {
		// 广告列表请求

		final List<PosterList> list = EarnPointsActivity.posterLists;

		if (list != null && list.size() > 0) {

			if (adapter == null) {

				mList.addAll(list);

				adapter = new MyAdapter<PosterList>(context, list,
						R.layout.earnpoints_item) {

					@Override
					public void convert(ViewHolder helper, int position,
							PosterList item) {

						helper.setText(R.id.earn_points_title,
								list.get(position).posterTitle);

						String s = ""+list.get(position).posterPrice;

						helper.setText(R.id.earn_points_number, "拍币  " + s);

						helper.setImageBitmap(R.id.earn_points_picture,
								list.get(position).posterImgUrl);

					}
				};

				gridview.setAdapter(adapter);

			} else {
				if (isUpOrDown) {
					mList.clear();
					adapter.upData(list);
					mList.addAll(list);
				} else {
					adapter.AddData(list);
					mList.addAll(list);
				}
				gridview.onRefreshComplete();
				adapter.notifyDataSetChanged();
			}
			mItemClick();
		} else {
			gridview.onRefreshComplete();
		}
	}
	
	public void mItemClick() {

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(context, "您点击了"+position, 0).show();
				EarnPointsFM.intentActivity(context, VideoViewBuffer.class, position);
			}
		});
	}
}
