package com.example.paidelidemo.ui.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paidelidemo.R;
import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.ui.earncoins.EarnCoinsActivity;
import com.example.paidelidemo.ui.earnpoints.EarnPointsActivity;
import com.example.paidelidemo.ui.exchangemore.ExchangeMoreActivity;
import com.example.paidelidemo.ui.happyernie.HappyErnieActivity;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.ui.merchantaccount.MerchantAccountActivity;
import com.example.paidelidemo.ui.myfriend.MyFriendActivity;
import com.example.paidelidemo.ui.myinformation.MyInformationActivity;
import com.example.paidelidemo.ui.mytask.MyTaskActivity;
import com.example.paidelidemo.ui.onermbernie.OneRmbErnieActivity;
import com.example.paidelidemo.ui.recommentfrends.FriendRequestActivity;
import com.example.paidelidemo.ui.shineprize.ShinePrizeActivity;
import com.example.paidelidemo.utils.AppManager;
import com.example.paidelidemo.utils.LogUtils;
import com.example.paidelidemo.utils.SPUtils;
import com.example.paidelidemo.utils.view.MyAni;
import com.example.paidelidemo.utils.view.convenientbanner.CBViewHolderCreator;
import com.example.paidelidemo.utils.view.convenientbanner.ConvenientBanner;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 首页
 * 
 * @author xiehaiheng
 */

@ContentView(value = R.layout.activity_home)
public class HomeActivity extends Activity implements OnClickListener
{
	public static Context context;
	private long mExitTime;
	// xUtils完全注解方式进行UI绑定
	/** 未读的消息数 */
	@ViewInject(R.id.unread_msg_number)
	private TextView unread_msg_number;
	/** 个人中心布局 */
	@ViewInject(R.id.s9)
	private RelativeLayout s1;
	/** 兑换商城布局 */
	@ViewInject(R.id.s1)
	private RelativeLayout s2;
	/** 商家管理布局 */
	@ViewInject(R.id.s10)
	private RelativeLayout s3;
	/** 我的任务布局 */
	@ViewInject(R.id.s4)
	private RelativeLayout s4;
	/** 赚取拍币布局 */
	@ViewInject(R.id.s3)
	private RelativeLayout s5;
	/** 赚取金币布局 */
	@ViewInject(R.id.s2)
	private RelativeLayout s6;
	/** 开心摇奖布局 */
	@ViewInject(R.id.s5)
	private RelativeLayout s7;
	/** 一元摇奖布局 */
	@ViewInject(R.id.s6)
	private RelativeLayout s8;
	/** 用户晒奖布局 */
	@ViewInject(R.id.s7)
	private RelativeLayout s9;
	/** 我的好友布局 */
	@ViewInject(R.id.s8)
	private RelativeLayout s10;
	/** 我的好友布局 */
	@ViewInject(R.id.iv_s8_2)
	private RelativeLayout iv_s8_2;
	/** 推荐好友 */
	@ViewInject(R.id.s11)
	private RelativeLayout s11;
	/** 通用广告布局 */
	@SuppressWarnings("rawtypes")
	@ViewInject(R.id.convenientBanner)
	private ConvenientBanner convenientBanner;
	/** 标题栏左按钮 */
	@ViewInject(R.id.btn_top_left)
	private Button btn_top_left;
	/** 标题栏右按钮 */
	@ViewInject(R.id.btn_top_right)
	private Button btn_top_right;
	/** 标题栏文本框 */
	@ViewInject(R.id.top_text)
	private TextView top_text;
	/** 右按钮新消息数 */
	@ViewInject(R.id.tv_news_number)
	private TextView tv_news_number;
	/** 个人中心图 */
	@ViewInject(R.id.iv_s9)
	private ImageView iv_s1;
	/** 兑换商城图 */
	@ViewInject(R.id.iv_s1)
	private ImageView iv_s2;
	/** 商家管理图 */
	@ViewInject(R.id.iv_s10)
	private ImageView iv_s3;
	/** 我的任务图 */
	@ViewInject(R.id.iv_s4)
	private ImageView iv_s4;
	/** 赚取积分图 */
	@ViewInject(R.id.iv_s3)
	private ImageView iv_s5;
	/** 赚取金币图 */
	@ViewInject(R.id.iv_s2)
	private ImageView iv_s6;
	/** 开心摇奖图 */
	@ViewInject(R.id.iv_s5)
	private ImageView iv_s7;
	/** 一元摇奖图 */
	@ViewInject(R.id.iv_s6)
	private ImageView iv_s8;
	/** 用户晒奖图 */
	@ViewInject(R.id.iv_s7)
	private ImageView iv_s9;
	/** 我的好友 */
	@ViewInject(R.id.iv_s8)
	private ImageView iv_s10;
	/** 推荐好友 */
	@ViewInject(R.id.iv_s11)
	private ImageView iv_s11;
	/** 我的拍币数量 */
	@ViewInject(R.id.tv_integral_balance_count)
	private TextView tv_integral_balance_count;
	/** 今日拍币数量 */
	@ViewInject(R.id.tv_integral_today_count)
	private TextView tv_integral_today_count;
	/** 我的金币数量 */
	@ViewInject(R.id.tv_integral_total_count)
	private TextView tv_integral_total_count;
	/** 个人中心文本 */
	@ViewInject(R.id.tv_s9)
	private TextView tv_s1;
	/** 兑换商城文本 */
	@ViewInject(R.id.tv_s1)
	private TextView tv_s2;
	/** 商家管理文本 */
	@ViewInject(R.id.tv_s10)
	private TextView tv_s3;
	/** 我的任务文本 */
	@ViewInject(R.id.tv_s4)
	private TextView tv_s4;
	/** 赚取积分文本 */
	@ViewInject(R.id.tv_s3)
	private TextView tv_s5;
	/** 赚钱金币文本 */
	@ViewInject(R.id.tv_s2)
	private TextView tv_s6;
	/** 开心摇奖文本 */
	@ViewInject(R.id.tv_s5)
	private TextView tv_s7;
	/** 一元摇奖文本 */
	@ViewInject(R.id.tv_s6)
	private TextView tv_s8;
	/** 用户晒奖文本 */
	@ViewInject(R.id.tv_s7)
	private TextView tv_s9;
	/** 我的好友文本 */
	@ViewInject(R.id.tv_s8)
	private TextView tv_s10;
	/** 推荐好友 */
	@ViewInject(R.id.tv_s11)
	private TextView tv_s11;
	/** 个人中心 */
	@ViewInject(R.id.img_s9)
	private ImageView img_s1;
	/** 兑换商城 */
	@ViewInject(R.id.img_s1)
	private ImageView img_s2;
	/** 商家管理 */
	@ViewInject(R.id.img_s10)
	private ImageView img_s3;
	/** 我的任务 */
	@ViewInject(R.id.img_s4)
	private ImageView img_s4;
	/** 赚取积分 */
	@ViewInject(R.id.img_s3)
	private ImageView img_s5;
	/** 赚取金币 */
	@ViewInject(R.id.img_s2)
	private ImageView img_s6;
	/** 开心摇奖 */
	@ViewInject(R.id.img_s5)
	private ImageView img_s7;
	/** 一元摇奖 */
	@ViewInject(R.id.img_s6)
	private ImageView img_s8;
	/** 用户晒奖 */
	@ViewInject(R.id.img_s7)
	private ImageView img_s9;
	/** 我的好友 */
	@ViewInject(R.id.img_s8)
	private ImageView img_s10;
	/** 推荐好友 */
	@ViewInject(R.id.img_s11)
	private ImageView img_s11;

	private float startX;
	private float endX;
	private float moveX;
	private int count;
	private int size;
	private MyAni myAni;
	private ArrayList<RelativeLayout> vList;
	private List<String> networkImages;
	private String[] images =
	{
			"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
			"http://img2.3lian.com/2014/f2/37/d/40.jpg",
			"http://d.3987.com/sqmy_131219/001.jpg",
			"http://img2.3lian.com/2014/f2/37/d/39.jpg",
			"http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
			"http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg" };
	public static MediaPlayer player;
	public static AudioManager audioManager;
	private int maxVolume;

	/** 常规构造 */
	public HomeActivity()
	{
		AppManager.getInstance().addActivity(this);
		context = HomeActivity.this;
	}

	/** onCreat方法 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		ViewUtils.inject(this);
		initDial();
		super.onCreate(savedInstanceState);
		// 获取媒体服务
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		// 获取最大音量
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		initHead();
		getUserInfor();
		initImageLoader();
		loadingBanner();
	}

	/** 初始化刻度盘 */
	private void initDial()
	{
		myAni = new MyAni(context);
		iv_s1.setOnClickListener(this);
		iv_s2.setOnClickListener(this);
		iv_s3.setOnClickListener(this);
		iv_s4.setOnClickListener(this);
		iv_s5.setOnClickListener(this);
		iv_s6.setOnClickListener(this);
		iv_s7.setOnClickListener(this);
		iv_s8.setOnClickListener(this);
		iv_s9.setOnClickListener(this);
		iv_s10.setOnClickListener(this);
		iv_s11.setOnClickListener(this);
		count = 0;
		myAni.myAnimation(s1, 0f, -60f, 1f, 0.75f);
		myAni.myAnimation(s2, 0f, -45f, 1f, 0.75f);
		myAni.myAnimation(s3, 0f, -30f, 1f, 0.81f);
		myAni.myAnimation(s11, 0f, -15f, 1f, 0.9f);
		myAni.myAnimation(s4, 0f, 0f, 1f, 1f);
		myAni.myAnimation(s5, 0f, 15f, 1f, 0.85f);
		myAni.myAnimation(s6, 0f, 30f, 1f, 0.75f);
		myAni.myAnimation(s7, 0f, 45f, 1f, 0.6515f);
		myAni.myAnimation(s8, 0f, 60f, 1f, 0.6515f);
		myAni.myAnimation(s9, 0f, 60f, 1f, 0.729f);
		myAni.myAnimation(s10, 0f, 60f, 1f, 0.729f);

		vList = new ArrayList<RelativeLayout>();
		vList.add(s1);
		vList.add(s2);
		vList.add(s3);
		vList.add(s11);
		vList.add(s4);
		vList.add(s5);
		vList.add(s6);
		vList.add(s7);
		vList.add(s8);
		vList.add(s9);
		vList.add(s10);
		size = vList.size();
	}

	/**
	 * 向左滑动
	 */
	protected void leftShift()
	{
		AnimationSet aniSet = new AnimationSet(true);
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get(count % size), -60f, -300f, 0.729f, 0.6515f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 1) % size), -45f, -60f, 0.81f, 0.729f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 2) % size), -30f, -45f, 0.81f, 0.75f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 3) % size), -15f, -30f, 0.9f, 0.81f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 4) % size), 0f, -15f, 1f, 0.9f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 5) % size), 15f, 0f, 0.9f, 1f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 6) % size), 30f, 15f, 0.81f, 0.9f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 7) % size), 45f, 30f, 0.75f, 0.81f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 8) % size), 60f, 45f, 0.6515f, 0.729f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 9) % size), 75f, 60f, 0.5f, 0.6515f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 10) % size), 90f, 75f, 0.5f, 0.5f));
		aniSet.start();
		vList.get((count + 3) % size).isClickable();
		count++;
		exchangeCentreTitle();
	}

	/**
	 * 向右滑动
	 */
	protected void rightShift()
	{
		AnimationSet aniSet = new AnimationSet(true);
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get(count % size), -60f, -45f, 0.729f, 0.75f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 1) % size), -45f, -30f, 0.75f, 0.81f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 2) % size), -30f, -15f, 0.81f, 0.9f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 3) % size), -15f, 0f, 0.9f, 1f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 4) % size), 0f, 15f, 1f, 0.9f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 5) % size), 15f, 30f, 0.9f, 0.79f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 6) % size), 30f, 45f, 0.79f, 0.70f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 7) % size), 45f, 60f, 0.70f, 0.6515f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 8) % size), 60f, 75f, 0.6515f, 0.6515f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 9) % size), 75f, 90f, 0.6515f, 0.6515f));
		aniSet.addAnimation((Animation) myAni.myAnimation(
				vList.get((count + 10) % size), 90f, 270f, 0.6515f, 0.6515f));
		aniSet.start();
		count = count + 10;
		exchangeCentreTitle();
	}

	/** 滑动信息修改 */
	private void exchangeCentreTitle()
	{
		if (player != null)
		{
			if (player.isPlaying())
			{
				player.stop();
			}
			player.release();
		}
		// 创建媒体
		player = MediaPlayer.create(context, R.raw.tone);
		player.start();
		switch ((count + 3) % size)
		{
		case 0:// 兑换商城
				// tv_centre_title.setText("处 理 任 务");
				// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s2.setVisibility(View.INVISIBLE);
			iv_s2.setVisibility(View.VISIBLE);

			tv_s1.setVisibility(View.VISIBLE);
			iv_s1.setVisibility(View.INVISIBLE);

			tv_s3.setVisibility(View.VISIBLE);
			iv_s3.setVisibility(View.INVISIBLE);
			break;
		case 1:// 商家管理
				// tv_centre_title.setText("我 要 赚 钱");
				// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s3.setVisibility(View.INVISIBLE);
			iv_s3.setVisibility(View.VISIBLE);

			tv_s2.setVisibility(View.VISIBLE);
			iv_s2.setVisibility(View.INVISIBLE);

			tv_s11.setVisibility(View.VISIBLE);
			iv_s11.setVisibility(View.INVISIBLE);

			break;
		case 2:
			// tv_centre_title.setText(getResources().getString("推荐好友"
			// R.string.earn_integral));
			// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s11.setVisibility(View.INVISIBLE);
			iv_s11.setVisibility(View.VISIBLE);

			tv_s3.setVisibility(View.VISIBLE);
			iv_s3.setVisibility(View.INVISIBLE);
			
			tv_s4.setVisibility(View.VISIBLE);
			iv_s4.setVisibility(View.INVISIBLE);
			break;
		case 3:
			// checkText();我的任务
			// tv_count_down.setVisibility(View.VISIBLE);
			tv_s4.setVisibility(View.INVISIBLE);
			iv_s4.setVisibility(View.VISIBLE);

			tv_s11.setVisibility(View.VISIBLE);
			iv_s11.setVisibility(View.INVISIBLE);

			tv_s5.setVisibility(View.VISIBLE);
			iv_s5.setVisibility(View.INVISIBLE);
			break;
		case 4:
			// tv_centre_title.setText("我 要 晒 奖");赚取积分
			// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s5.setVisibility(View.INVISIBLE);
			iv_s5.setVisibility(View.VISIBLE);

			tv_s4.setVisibility(View.VISIBLE);
			iv_s4.setVisibility(View.INVISIBLE);
			tv_s6.setVisibility(View.VISIBLE);
			iv_s6.setVisibility(View.INVISIBLE);
			break;
		case 5:
			// tv_centre_title.setText("进 入 邀 请");赚取金币
			// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s6.setVisibility(View.INVISIBLE);
			iv_s6.setVisibility(View.VISIBLE);

			tv_s5.setVisibility(View.VISIBLE);
			iv_s5.setVisibility(View.INVISIBLE);
			tv_s7.setVisibility(View.VISIBLE);
			iv_s7.setVisibility(View.INVISIBLE);
			break;
		case 6:
			// tv_centre_title.setText("我 的 账 号");开心摇奖
			// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s7.setVisibility(View.INVISIBLE);
			iv_s7.setVisibility(View.VISIBLE);
			tv_s6.setVisibility(View.VISIBLE);
			iv_s6.setVisibility(View.INVISIBLE);
			tv_s8.setVisibility(View.VISIBLE);
			iv_s8.setVisibility(View.INVISIBLE);

			break;
		case 7:
			// tv_centre_title.setText("一元拍");一元摇奖
			// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s8.setVisibility(View.INVISIBLE);
			iv_s8.setVisibility(View.VISIBLE);

			tv_s7.setVisibility(View.VISIBLE);
			iv_s7.setVisibility(View.INVISIBLE);

			tv_s9.setVisibility(View.VISIBLE);
			iv_s9.setVisibility(View.INVISIBLE);
			break;
		case 8:
			// tv_centre_title.setText(getResources()用户晒奖
			// .getString(R.string.earn_gold));
			// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s9.setVisibility(View.INVISIBLE);
			iv_s9.setVisibility(View.VISIBLE);

			tv_s8.setVisibility(View.VISIBLE);
			iv_s8.setVisibility(View.INVISIBLE);

			tv_s10.setVisibility(View.VISIBLE);
			iv_s10.setVisibility(View.INVISIBLE);
			iv_s8_2.setVisibility(View.INVISIBLE);
			break;
		case 9:
			// tv_centre_title.setText(getResources().getString(
			// R.string.earn_integral));我的好友
			// tv_count_down.setVisibility(View.INVISIBLE);
			tv_s10.setVisibility(View.INVISIBLE);
			iv_s10.setVisibility(View.VISIBLE);

			iv_s8_2.setVisibility(View.VISIBLE);

			tv_s9.setVisibility(View.VISIBLE);
			iv_s9.setVisibility(View.INVISIBLE);
			tv_s1.setVisibility(View.VISIBLE);
			iv_s1.setVisibility(View.INVISIBLE);

			break;
		case 10:// 个人中心
			tv_s1.setVisibility(View.INVISIBLE);
			iv_s1.setVisibility(View.VISIBLE);

			tv_s2.setVisibility(View.VISIBLE);
			iv_s2.setVisibility(View.INVISIBLE);

			tv_s10.setVisibility(View.VISIBLE);
			iv_s10.setVisibility(View.INVISIBLE);

			iv_s8_2.setVisibility(View.VISIBLE);

			break;
		default:
			break;
		}

	}

	/** 调度触摸事件 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{

		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			endX = event.getX();
			moveX = endX - startX;
			break;
		default:
			break;
		}
		if (moveX > 50)
		{
			rightShift();
		} else if (moveX < -50)
		{
			leftShift();
		} else
		{
			return false;
		}
		startX = 0;
		endX = 0;
		moveX = 0;
		return super.onTouchEvent(event);
	}

	/** 获取用户信息 */
	private void getUserInfor()
	{
		String userInfo = (String) SPUtils.get(context,
				Constant.USER_INFO_FILE_NAME, "");
		if (!TextUtils.isEmpty(userInfo))
		{
			LogUtils.i(userInfo + "userInfo");
			// Gson gson = new Gson();
			// LoginUserResult userResult = gson.fromJson(userInfo,
			// LoginUserResult.class);
		}

	}

	/** 加载广告 */
	@SuppressWarnings("unchecked")
	private void loadingBanner()
	{
		networkImages = Arrays.asList(images);
		convenientBanner.setPages(
				new CBViewHolderCreator<NetworkImageHolderView>()
				{
					@Override
					public NetworkImageHolderView createHolder()
					{
						return new NetworkImageHolderView();
					}
				}, networkImages).setPageIndicator(new int[]
		{ R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused });
	}

	/** 初始化标题 */
	private void initHead()
	{
		top_text.setText(R.string.app_name);
		btn_top_left.setVisibility(View.VISIBLE);
		btn_top_right.setVisibility(View.VISIBLE);
		btn_top_left.setOnClickListener(this);
		btn_top_right.setOnClickListener(this);
	}

	/** 初始化imageloader */
	private void initImageLoader()
	{

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.loading)
				.showImageOnFail(R.drawable.loading).cacheInMemory(true)
				.cacheOnDisk(true).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_top_left:
			// 签到
			HeadBaseActivity
					.intentActivity(context, SignInActivity.class, null);
			break;
		case R.id.btn_top_right:
			// 消息
			HeadBaseActivity.intentActivity(context, MyMessageActivity.class,
					null);
			break;
		case R.id.iv_s1:
			// 兑换商城
			HeadBaseActivity.intentActivity(context,
					ExchangeMoreActivity.class, null);
			break;
		case R.id.iv_s2:
			// 赚取金币
			HeadBaseActivity.intentActivity(context, EarnCoinsActivity.class,
					null);
			break;
		case R.id.iv_s3:
			// 赚取积分
			HeadBaseActivity.intentActivity(context, EarnPointsActivity.class,
					null);
			break;
		case R.id.iv_s4:
			// 我的任务
			HeadBaseActivity
					.intentActivity(context, MyTaskActivity.class, null);
			break;
		case R.id.iv_s5:
			// 开心摇奖
			HeadBaseActivity.intentActivity(context, HappyErnieActivity.class,
					null);
			break;
		case R.id.iv_s6:
			// 一元摇奖
			HeadBaseActivity.intentActivity(context, OneRmbErnieActivity.class,
					null);
			break;
		case R.id.iv_s7:
			// 用户晒奖
			HeadBaseActivity.intentActivity(context, ShinePrizeActivity.class,
					null);
			break;
		case R.id.iv_s8:
			// 我的好友
			HeadBaseActivity.intentActivity(context, MyFriendActivity.class,
					null);
			break;
		case R.id.iv_s9:
			// 个人中心
			HeadBaseActivity.intentActivity(context,
					MyInformationActivity.class, null);
			break;
		case R.id.iv_s10:
			// 商家管理
			HeadBaseActivity.intentActivity(context,
					MerchantAccountActivity.class, null);
			break;
		case R.id.iv_s11:
			// 好友推荐
			HeadBaseActivity.intentActivity(context,
					FriendRequestActivity.class, null);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		convenientBanner.startTurning(3000);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		convenientBanner.stopTurning();
	}

	/** 获取当前音量 */
	private int getMediaVolume()
	{
		return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	/** 设置当前音量 */
	private void setMediaVolume(int volume)
	{
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume,
				AudioManager.FLAG_PLAY_SOUND);
	}

	/** 增加音量 */
	private void addMediaVolume(int current)
	{
		current = current + 2;
		if (current >= maxVolume)
		{
			current = maxVolume;
		}
		setMediaVolume(current);

	}

	private void cutMediaVolume(int current)
	{
		current = current - 2;
		if (current <= 0)
		{
			current = 0;
		}
		setMediaVolume(current);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		switch (keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			if ((System.currentTimeMillis() - mExitTime) > 2000)
			{
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else
			{
				AppManager.getInstance().exit();
			}
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			addMediaVolume(getMediaVolume());
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			cutMediaVolume(getMediaVolume());

			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
