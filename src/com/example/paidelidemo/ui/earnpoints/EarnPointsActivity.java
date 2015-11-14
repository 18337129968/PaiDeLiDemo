package com.example.paidelidemo.ui.earnpoints;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.earnpoints.FragmentViewPagerAdapter.OnExtraPageChangeListener;
import com.example.paidelidemo.utils.AppManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ׬ȡ������
 * 
 * @author Administrator
 * 
 */
public class EarnPointsActivity extends EarnPointsFM {
	private Context context;
	@ViewInject(R.id.viewPager)
	private ViewPager viewPager;
	@ViewInject(R.id.scoreboard)
	private Button scoreBoard;
	@ViewInject(R.id.recommended_list)
	private Button recommendedList;
	@ViewInject(R.id.integral_wall_list)
	private Button integralWallList;

	private String[] images = {
			"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
			"http://img2.3lian.com/2014/f2/37/d/40.jpg",
			"http://d.3987.com/sqmy_131219/001.jpg",
			"http://img2.3lian.com/2014/f2/37/d/39.jpg",
			"http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
			"http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg" };

	public static List<PosterList> posterLists = new ArrayList<PosterList>();

	/** ���湹�췽�� */
	public EarnPointsActivity() {
		AppManager.getInstance().addActivity(this);
		context = EarnPointsActivity.this;
	}

	/** ��ӱ����� */
	@Override
	public void appHead(View view) {
		top_text.setText("׬ȡ�ı�");
		btn_left.setOnClickListener(this);
	}

	/** �滻������ͼ */
	@Override
	public void initReplaceView() {
		// ��ȡbase�����ļ��еĲ���
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// ʹ��login�����ļ���װview����
		View view = View.inflate(context, R.layout.earnpoints, null);
		// �������view
		base_frameLayout.addView(view);

		posterLists.clear();
		for (int i = 0; i < images.length; i++) {
			PosterList posterList = new PosterList();
			posterList.setPosterImgUrl(images[i]);
			posterList.setPosterPrice(120);
			posterList.setPosterTitle("����֮����ë���޷�");
			posterLists.add(posterList);
		}

		initLayoutView();
	}

	/** ��ʼ������ */
	private void initLayoutView() {
		ViewUtils.inject(this);
		scoreBoard.setOnClickListener(this);
		recommendedList.setOnClickListener(this);
		integralWallList.setOnClickListener(this);
		scoreBoard.setSelected(true);

		IntegralistFragement integralistFragement = new IntegralistFragement(
				context, "1");

		IntegralistFragement integralistFragmentd = new IntegralistFragement(
				context, "2");

		IntegralWallActivity integralwall = new IntegralWallActivity(context,
				null);
		
		List<Fragment> listView = new ArrayList<Fragment>();
		
		listView.add(integralistFragement);
		listView.add(integralistFragmentd);
		listView.add(integralwall);
		
		FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(
				getSupportFragmentManager(), viewPager, listView);

		viewPager.setCurrentItem(0);
		

		adapter.setOnExtraPageChangeListener(new OnExtraPageChangeListener() {

			@Override
			public void onExtraPageScrolled(int i, float v, int i2) {
				// TODO Auto-generated method stub
				super.onExtraPageScrolled(i, v, i2);
			}

			@Override
			public void onExtraPageSelected(int i) {
				switch (i) {
				case 0:
					scoreBoard.setSelected(true);
					recommendedList.setSelected(false);
					integralWallList.setSelected(false);
					break;
				case 1:
					scoreBoard.setSelected(false);
					recommendedList.setSelected(true);
					integralWallList.setSelected(false);
					break;
				case 2:
					scoreBoard.setSelected(false);
					recommendedList.setSelected(false);
					integralWallList.setSelected(true);
					break;
				default:
					break;
				}
				super.onExtraPageSelected(i);
			}

			@Override
			public void onExtraPageScrollStateChanged(int i) {
				// TODO Auto-generated method stub
				super.onExtraPageScrollStateChanged(i);
			}

		});
		
	}

	/** ��Ӽ������� */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_head_left:
			finish();
			break;
		case R.id.scoreboard://�ıҰ�
			viewPager.setCurrentItem(0);
			break;
		case R.id.recommended_list://�Ƽ���
			viewPager.setCurrentItem(1);
			break;
		case R.id.integral_wall_list://app��
			viewPager.setCurrentItem(2);
			break;
		default:
			break;
		}

	}
}
