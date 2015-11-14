package com.example.paidelidemo.ui.myfriend.util;

import java.util.List;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.myfriend.ReceiveContents.ContactInfo;

import android.content.Context;
import android.renderscript.Type;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter implements SectionIndexer
{
	private List<SortModel> list = null;
	private Context mContext;
	private int type;
	private ViewHolder viewHolder = null;

	public SortAdapter(Context mContext, List<SortModel> list, int type)
	{
		this.mContext = mContext;
		this.list = list;
		this.type = type;
	}

	@Override
	public int getCount()
	{
		return this.list != null ? list.size() : 0;
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2)
	{
		final SortModel mContent = list.get(position);
		if (view == null)
		{
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.my_friend_item, null);
			viewHolder.imageView = (ImageView) view.findViewById(R.id.img);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);

			view.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tvTitle.setText(((ContactInfo)list.get(position).getObject()).getContactName());
		return view;
	}

	@Override
	public Object[] getSections()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int section)
	{
		for (int i = 0; i < getCount(); i++)
		{
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section)
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position)
	{
		return list.get(position).getSortLetters().charAt(0);
	}

	final static class ViewHolder
	{
		ImageView imageView;
		TextView tvLetter;
		TextView tvTitle;
		Button btn_yq;
		ImageView img_success;
	}

	public void refresh(List<SortModel> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}