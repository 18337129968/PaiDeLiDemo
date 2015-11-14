package com.example.paidelidemo.ui.earnpoints;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * ΪViewPager��Ӳ��֣�Fragment�����󶨺ʹ���fragments��viewpager֮����߼���ϵ
 * 
 */
public class FragmentViewPagerAdapter extends PagerAdapter implements
		ViewPager.OnPageChangeListener {
	// ÿ��Fragment��Ӧһ��Page
	private List<Fragment> fragments;
	// FragmentManager����
	private FragmentManager fragmentManager;
	// viewPager����
	private ViewPager viewPager;
	// ��ǰpage�������л�֮ǰ��
	private int currentPageIndex = 0;
	// ViewPager�л�ҳ��ʱ�Ķ��⹦����ӽӿ�
	private OnExtraPageChangeListener onExtraPageChangeListener;

	public FragmentViewPagerAdapter(FragmentManager fragmentManager,
			ViewPager viewPager, List<Fragment> fragments) {
		this.fragments = fragments;
		this.fragmentManager = fragmentManager;
		this.viewPager = viewPager;
		this.viewPager.setAdapter(this);
		this.viewPager.setOnPageChangeListener(this);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(fragments.get(position).getView()); // �Ƴ�viewpager����֮���page����
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = fragments.get(position);
		if (!fragment.isAdded()) { // ���fragment��û��added
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(fragment, fragment.getClass().getSimpleName());
			ft.commit();
			/**
			 * ����FragmentTransaction.commit()�����ύFragmentTransaction�����
			 * ���ڽ��̵����߳��У����첽�ķ�ʽ��ִ�С� �����Ҫ����ִ������ȴ��еĲ�������Ҫ�������������ֻ�������߳��е��ã���
			 * Ҫע����ǣ����еĻص�����ص���Ϊ��������������б�ִ����ɣ����Ҫ��ϸȷ����������ĵ���λ�á�
			 */
			fragmentManager.executePendingTransactions();
		}
		if (((IEarnPointsRequest) fragment).isFirst() && position == 0) {
			((IEarnPointsRequest) fragment).request();
		}
		if (fragment.getView().getParent() == null) {
			container.addView(fragment.getView()); // Ϊviewpager���Ӳ���
		}

		return fragment.getView();
	}

	/**
	 * ��ǰpage�������л�֮ǰ��
	 * 
	 * @return
	 */
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public OnExtraPageChangeListener getOnExtraPageChangeListener() {
		return onExtraPageChangeListener;
	}

	/**
	 * ����ҳ���л����⹦�ܼ�����
	 * 
	 * @param onExtraPageChangeListener
	 */
	public void setOnExtraPageChangeListener(
			OnExtraPageChangeListener onExtraPageChangeListener) {
		this.onExtraPageChangeListener = onExtraPageChangeListener;
	}

	@Override
	public void onPageScrolled(int i, float v, int i2) {
		if (null != onExtraPageChangeListener) { // ��������˶��⹦�ܽӿ�
			onExtraPageChangeListener.onExtraPageScrolled(i, v, i2);
		}
	}

	@Override
	public void onPageSelected(int i) {
		fragments.get(currentPageIndex).onPause(); // �����л�ǰFargment��onPause()
		// fragments.get(currentPageIndex).onStop(); // �����л�ǰFargment��onStop()
		if (fragments.get(i).isAdded()) {
			// fragments.get(i).onStart(); // �����л���Fargment��onStart()
			fragments.get(i).onResume(); // �����л���Fargment��onResume()
			if (((IEarnPointsRequest) fragments.get(i)).isFirst()) {
				((IEarnPointsRequest) fragments.get(i)).request();
			}

		}
		currentPageIndex = i;

		if (null != onExtraPageChangeListener) { // ��������˶��⹦�ܽӿ�
			onExtraPageChangeListener.onExtraPageSelected(i);
		}

	}

	@Override
	public void onPageScrollStateChanged(int i) {
		if (null != onExtraPageChangeListener) { // ��������˶��⹦�ܽӿ�
			onExtraPageChangeListener.onExtraPageScrollStateChanged(i);
		}
	}

	/**
	 * page�л����⹦�ܽӿ�
	 */
	public static class OnExtraPageChangeListener {
		public void onExtraPageScrolled(int i, float v, int i2) {
		}

		public void onExtraPageSelected(int i) {
		}

		public void onExtraPageScrollStateChanged(int i) {
		}
	}

}
