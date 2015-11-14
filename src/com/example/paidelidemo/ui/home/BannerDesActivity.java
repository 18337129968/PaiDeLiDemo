package com.example.paidelidemo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;
import com.example.paidelidemo.utils.view.ProgressWebView;

/**
 * 单击ViewPager进入横幅类
 * 
 * @author xiehaifeng
 * 
 */
public class BannerDesActivity extends HeadBaseActivity {
	private Context context;
	private ProgressWebView webView;
	private RelativeLayout layout;

	/** 常规构造方法 */
	public BannerDesActivity() {
		AppManager.getInstance().addActivity(this);
		context = BannerDesActivity.this;
	}

	/** 添加头标题 */
	@Override
	public void appHead(View view) {
		layout = (RelativeLayout) view;
		btn_right.setText(getResources().getString(R.string.ernie_top));
		btn_left.setOnClickListener(this);
	}

	/** 初始化替换View */
	@Override
	public void initReplaceView() {
		// 获取base布局文件中的布局
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// 使用login布局文件封装view容器
		View view = View.inflate(context, R.layout.activity_banner_des, null);
		// 布局添加view
		base_frameLayout.addView(view);
		initLayoutView();
	}

	/** 初始化view */
	@SuppressWarnings("deprecation")
	private void initLayoutView() {
		String linkUrl = getIntent().getStringExtra("value");
		String str = "\\";
		if (linkUrl.contains(str)) {
			linkUrl = linkUrl.replace(str, "/");
		}
		linkUrl = linkUrl.contains("http://") ? linkUrl : "http://" + linkUrl;
		webView = (ProgressWebView) findViewById(R.id.wv);
		WebSettings webSettings = webView.getSettings();
		// 获取webView的setts然后设置可支持JavaScript
		webSettings.setJavaScriptEnabled(true);
		// 支持多个窗口
		webSettings.setSupportMultipleWindows(true);
		// 启用地理定位
		webSettings.setGeolocationEnabled(true);
		/*
		 * String localPath = this.getApplicationContext() .getDir("location",
		 * Context.MODE_PRIVATE).getPath(); // 设置定位的数据库路径
		 * webSettings.setGeolocationDatabasePath(localPath);
		 */
		// 设置可缩放
		webSettings.setBuiltInZoomControls(true);
		// 是否设置显示网格图片
		// webSettings.setBlockNetworkImage(false);
		// 设置是否支持变焦
		webSettings.setSupportZoom(true);
		// 支持自动加载图片
		webSettings.setLoadsImagesAutomatically(true);
		// 将图片调整时候webwiew大小
		webSettings.setUseWideViewPort(true);
		// 使用推荐窗口
		webSettings.setLoadWithOverviewMode(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 当webwiew调用requestFoucus时设置节点
		webSettings.setNeedInitialFocus(true);
		// 设置允许DoM缓存
		webSettings.setDomStorageEnabled(true);
		// 应用可以有数据库
		webSettings.setDatabaseEnabled(true);
		/*
		 * String dbPath = this.getApplicationContext() .getDir("database",
		 * Context.MODE_PRIVATE).getPath(); webSettings.setDatabasePath(dbPath);
		 */
		// 设置加载插件
		webSettings.setPluginsEnabled(true);
		webSettings.setPluginState(PluginState.ON);
		// 设置默认编码格式
		// webSettings.setDefaultTextEncodingName("utf-8");
		// 设置应用可以有缓存
		webSettings.setAppCacheEnabled(true);
		// 设置缓存模式LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取。
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		/*
		 * String appCacheDir = this.getApplicationContext() .getDir("cache",
		 * Context.MODE_PRIVATE).getPath();
		 * webSettings.setAppCachePath(appCacheDir);
		 */
		// 设置窗口布局
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		// 支持通过js打开新窗口
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		// webView.setDrawingCacheEnabled(true);
		// 设置聚焦
		// webView.requestFocus();
		// 设置滚动条内部覆盖
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		// 将本activity绑定到java_js这个js当中去
		webView.addJavascriptInterface(this, "Android");

		MyWebViewClient client = new MyWebViewClient();
		client.shouldOverrideUrlLoading(webView, linkUrl);
		/*
		 * 使用WebChromeClient 可以操作Javascript dialogs（js脚本对话框）, favicons（添加收藏的标志）,
		 * titles（标题）, 和 progress（进度条）.
		 * 
		 * 如果除了加载HTML的话，只需要用WebViewClient即可
		 */
		webView.setWebViewClient(new MyWebViewClient());
		webView.setDownloadListener(new MyWebViewDownLoadListener());
		/*
		 * webView.setWebChromeClient(new WebChromeClient() { CustomViewCallback
		 * customViewCallback;
		 * 
		 * // 进入全屏的时候
		 * 
		 * @Override public void onShowCustomView(View view, CustomViewCallback
		 * callback) {
		 * LogUtils.i("onShowCustomView_____________________________");
		 * 
		 * ((Activity) context) .requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * // 赋值给callback customViewCallback = callback; // 设置webView隐藏
		 * webView.setVisibility(View.GONE); // 声明video，把之后的视频放到这里面去 FrameLayout
		 * video = (FrameLayout) findViewById(R.id.video); // 将video放到当前视图中
		 * video.addView(view); // 横屏显示 ((Activity) context)
		 * .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		 * // 设置全屏 setFullScreen(); }
		 * 
		 * // 退出全屏的时候
		 * 
		 * @Override public void onHideCustomView() { if (customViewCallback !=
		 * null) { // 隐藏掉 customViewCallback.onCustomViewHidden(); } //
		 * 用户当前的首选方向
		 * setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER); //
		 * 退出全屏 quitFullScreen(); // 设置WebView可见
		 * webView.setVisibility(View.VISIBLE); }
		 * 
		 * });
		 */

	}

	/** 下载文件 */
	private class MyWebViewDownLoadListener implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String userAgent,
				String contentDisposition, String mimetype, long contentLength) {
			Uri uri = Uri.parse(url);
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(uri);
			intent.setClassName("com.android.browser",
					"com.android.browser.BrowserActivity");
			startActivity(intent);
		}

	}

	/** 加载链接 */
	class MyWebViewClient extends WebViewClient {
		// shouldOverrideUrlLoading 控制新的连接在当前WebView中打开
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	/** 横竖屏切换 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			layout.setVisibility(View.GONE);
			setFullScreen();
		}
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			layout.setVisibility(View.VISIBLE);
			quitFullScreen();
		}
		if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
		} else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
		}
	}

	/** 设置全屏 */
	private void setFullScreen() {
		// 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 全屏下的状态码：1098974464
		// 窗口下的状态吗：1098973440
	}

	/** 退出全屏 */
	private void quitFullScreen() {
		// 声明当前屏幕状态的参数并获取
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	@Override
	protected void onResume() {
		webView.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		webView.onPause();
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_head_left:
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		webView.clearCache(isFinishing());
		webView.clearCache(true);
		webView.clearHistory();
		webView.clearDisappearingChildren();
		webView.clearFocus();
		webView.clearFormData();
		webView.clearMatches();
		webView.clearSslPreferences();
		webView.removeAllViews();
		super.onDestroy();
	}

}
