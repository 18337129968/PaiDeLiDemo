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
 * 赚取积分类
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

	/** 常规构造方法 */
	public EarnPointsActivity() {
		AppManager.getInstance().addActivity(this);
		context = EarnPointsActivity.this;
	}

	/** 添加标题栏 */
	@Override
	public void appHead(View view) {
		top_text.setText("赚取拍币");
		btn_left.setOnClickListener(this);
	}

	/** 替换布局视图 */
	@Override
	public void initReplaceView() {
		// 获取base布局文件中的布局
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// 使用login布局文件封装view容器
		View view = View.inflate(context, R.layout.earnpoints, null);
		// 布局添加view
		base_frameLayout.addView(view);

		posterLists.clear();
		for (int i = 0; i < images.length; i++) {
			PosterList posterList = new PosterList();
			posterList.setPosterImgUrl(images[i]);
			posterList.setPosterPrice(120);
			posterList.setPosterTitle("海澜之家羊毛羽绒服");
			posterLists.add(posterList);
		}

		initLayoutView();
	}

	/** 初始化布局 */
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

	/** 添加监听方法 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_head_left:
			finish();
			break;
		case R.id.scoreboard://拍币榜
			viewPager.setCurrentItem(0);
			break;
		case R.id.recommended_list://推荐榜
			viewPager.setCurrentItem(1);
			break;
		case R.id.integral_wall_list://app榜
			viewPager.setCurrentItem(2);
			break;
		default:
			break;
		}

	}
}
