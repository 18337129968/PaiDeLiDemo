/**
 * 引导页适配器
 */
package com.example.paidelidemo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 适配器
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
		/* 返回可用的view的数量 */
		return images_id.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		/* 判断页面是否跟指定的key对象关联，key对象由instantiateItem(ViewGroup, int)返回 */
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// 删除指定位置的页面；适配器负责从view容器中删除view，然而它只保证在finishUpdate(ViewGroup)返回时才完成。
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 在指定的位置创建页面；适配器负责添加view到这个容器中，然而它只保证在finishUpdate(ViewGroup)返回时才完成
		imageView = new ImageView(context);
		// 规模风格
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setBackgroundResource(images_id[position]);
		container.addView(imageView);
		return imageView;
	}

}
