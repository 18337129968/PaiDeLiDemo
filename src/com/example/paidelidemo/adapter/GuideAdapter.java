/**
 * ����ҳ������
 */
package com.example.paidelidemo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * ������
 * 
 * @author xiehaifeng
 */
public class GuideAdapter extends PagerAdapter {
	private Context context;
	public ImageView imageView;
	private int[] images_id;

	public GuideAdapter(Context context, ImageView imageView, int[] images_id) {
		this.context = context;
		this.imageView = imageView;
		this.images_id = images_id;
	}

	@Override
	public int getCount() {
		/* ���ؿ��õ�view������ */
		return images_id.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		/* �ж�ҳ���Ƿ��ָ����key���������key������instantiateItem(ViewGroup, int)���� */
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// ɾ��ָ��λ�õ�ҳ�棻�����������view������ɾ��view��Ȼ����ֻ��֤��finishUpdate(ViewGroup)����ʱ����ɡ�
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// ��ָ����λ�ô���ҳ�棻�������������view����������У�Ȼ����ֻ��֤��finishUpdate(ViewGroup)����ʱ�����
		imageView = new ImageView(context);
		// ��ģ���
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setBackgroundResource(images_id[position]);
		container.addView(imageView);
		return imageView;
	}

}
