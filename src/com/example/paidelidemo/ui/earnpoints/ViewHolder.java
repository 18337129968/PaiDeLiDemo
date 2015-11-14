package com.example.paidelidemo.ui.earnpoints;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context mContext;
	private ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		mContext = context;
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * �õ�һ��ViewHolder����
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * ͨ���ؼ���Id��ȡ���ڵĿؼ������û�������views
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * ΪTextView�����ַ���
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		if (text != null) {
			view.setText(text);
		}
		return this;
	}

	/**
	 * ΪTextView����tag
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setTag(int viewId, String text) {
		View view = getView(viewId);
		if (text != null) {
			view.setTag(text);
		}
		return this;
	}

	/**
	 * ΪTextView����visibility
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setVisibility(int viewId, int visibility) {
		View view = getView(viewId);
		view.setVisibility(visibility);
		return this;
	}

	/**
	 * ΪImageView����ͼƬ
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId) {
		View view = getView(viewId);
		view.setBackgroundResource(drawableId);
		return this;
	}

	/**
	 * ΪImageView����ͼƬ
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageViewResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
		return this;
	}

	/**
	 * ΪImageView����ͼƬ
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * ΪImageView��������ͼƬ
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, String url) {
		ImageView view = getView(viewId);
		if (!TextUtils.isEmpty(url)) {
			ImageLoaderUtil.loadImage(url, view,mContext);
		}
		return this;
	}

	/**
	 * ΪImageView����ͼƬ
	 * 
	 * @param viewId
	 * @param clickListener
	 * @return
	 */
	public ViewHolder setOnclick(int viewId, OnClickListener listener) {
		View view = getView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	/**
	 * 
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setOnTouchListener(int viewId, OnTouchListener listener) {
		View view = getView(viewId);
		view.setOnTouchListener(listener);
		return this;
	}

	public int getPosition() {
		return mPosition;
	}

}
