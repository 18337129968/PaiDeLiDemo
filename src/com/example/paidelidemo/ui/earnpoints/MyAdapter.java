package com.example.paidelidemo.ui.earnpoints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public abstract class MyAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	protected final int mItemLayoutId;

	public MyAdapter(Context paramContext, List<T> paramList, int paramInt) {
		this.mContext = paramContext;
		this.mInflater = LayoutInflater.from(this.mContext);
		this.mDatas = paramList;
		this.mItemLayoutId = paramInt;
	}

	private ViewHolder getViewHolder(int paramInt, View paramView,
			ViewGroup paramViewGroup) {
		return ViewHolder.get(this.mContext, paramView, paramViewGroup,
				this.mItemLayoutId, paramInt);
	}

	public void AddData(List<T> paramList) {
		this.mDatas.addAll(paramList);
	}

	public abstract void convert(ViewHolder paramViewHolder, int paramInt,
			T paramT);

	public int getCount() {
		return this.mDatas.size();
	}

	public T getItem(int paramInt) {
		return this.mDatas.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ViewHolder localViewHolder = getViewHolder(paramInt, paramView,
				paramViewGroup);
		convert(localViewHolder, paramInt, getItem(paramInt));
		return localViewHolder.getConvertView();
	}

	public void refresh(List<T> paramList) {
		this.mDatas = paramList;
		notifyDataSetChanged();
	}

	public void upData(List<T> paramList) {
		//this.mDatas.clear();
		// this.mDatas.addAll(paramList);
		this.mDatas = paramList;
	}
}