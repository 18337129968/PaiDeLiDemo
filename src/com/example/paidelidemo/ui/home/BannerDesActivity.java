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
 * ����ViewPager��������
 * 
 * @author xiehaifeng
 * 
 */
public class BannerDesActivity extends HeadBaseActivity {
	private Context context;
	private ProgressWebView webView;
	private RelativeLayout layout;

	/** ���湹�췽�� */
	public BannerDesActivity() {
		AppManager.getInstance().addActivity(this);
		context = BannerDesActivity.this;
	}

	/** ���ͷ���� */
	@Override
	public void appHead(View view) {
		layout = (RelativeLayout) view;
		btn_right.setText(getResources().getString(R.string.ernie_top));
		btn_left.setOnClickListener(this);
	}

	/** ��ʼ���滻View */
	@Override
	public void initReplaceView() {
		// ��ȡbase�����ļ��еĲ���
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// ʹ��login�����ļ���װview����
		View view = View.inflate(context, R.layout.activity_banner_des, null);
		// �������view
		base_frameLayout.addView(view);
		initLayoutView();
	}

	/** ��ʼ��view */
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
		// ��ȡwebView��settsȻ�����ÿ�֧��JavaScript
		webSettings.setJavaScriptEnabled(true);
		// ֧�ֶ������
		webSettings.setSupportMultipleWindows(true);
		// ���õ���λ
		webSettings.setGeolocationEnabled(true);
		/*
		 * String localPath = this.getApplicationContext() .getDir("location",
		 * Context.MODE_PRIVATE).getPath(); // ���ö�λ�����ݿ�·��
		 * webSettings.setGeolocationDatabasePath(localPath);
		 */
		// ���ÿ�����
		webSettings.setBuiltInZoomControls(true);
		// �Ƿ�������ʾ����ͼƬ
		// webSettings.setBlockNetworkImage(false);
		// �����Ƿ�֧�ֱ佹
		webSettings.setSupportZoom(true);
		// ֧���Զ�����ͼƬ
		webSettings.setLoadsImagesAutomatically(true);
		// ��ͼƬ����ʱ��webwiew��С
		webSettings.setUseWideViewPort(true);
		// ʹ���Ƽ�����
		webSettings.setLoadWithOverviewMode(true);
		// ���ÿ��Է����ļ�
		webSettings.setAllowFileAccess(true);
		// ��webwiew����requestFoucusʱ���ýڵ�
		webSettings.setNeedInitialFocus(true);
		// ��������DoM����
		webSettings.setDomStorageEnabled(true);
		// Ӧ�ÿ��������ݿ�
		webSettings.setDatabaseEnabled(true);
		/*
		 * String dbPath = this.getApplicationContext() .getDir("database",
		 * Context.MODE_PRIVATE).getPath(); webSettings.setDatabasePath(dbPath);
		 */
		// ���ü��ز��
		webSettings.setPluginsEnabled(true);
		webSettings.setPluginState(PluginState.ON);
		// ����Ĭ�ϱ����ʽ
		// webSettings.setDefaultTextEncodingName("utf-8");
		// ����Ӧ�ÿ����л���
		webSettings.setAppCacheEnabled(true);
		// ���û���ģʽLOAD_CACHE_ELSE_NETWORKģʽ�£������Ƿ������磬ֻҪ�����л��棬��ʹ�û��档����û�л���ʱ�Ŵ������ϻ�ȡ��
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		/*
		 * String appCacheDir = this.getApplicationContext() .getDir("cache",
		 * Context.MODE_PRIVATE).getPath();
		 * webSettings.setAppCachePath(appCacheDir);
		 */
		// ���ô��ڲ���
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		// ֧��ͨ��js���´���
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		// webView.setDrawingCacheEnabled(true);
		// ���þ۽�
		// webView.requestFocus();
		// ���ù������ڲ�����
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		// ����activity�󶨵�java_js���js����ȥ
		webView.addJavascriptInterface(this, "Android");

		MyWebViewClient client = new MyWebViewClient();
		client.shouldOverrideUrlLoading(webView, linkUrl);
		/*
		 * ʹ��WebChromeClient ���Բ���Javascript dialogs��js�ű��Ի���, favicons������ղصı�־��,
		 * titles�����⣩, �� progress����������.
		 * 
		 * ������˼���HTML�Ļ���ֻ��Ҫ��WebViewClient����
		 */
		webView.setWebViewClient(new MyWebViewClient());
		webView.setDownloadListener(new MyWebViewDownLoadListener());
		/*
		 * webView.setWebChromeClient(new WebChromeClient() { CustomViewCallback
		 * customViewCallback;
		 * 
		 * // ����ȫ����ʱ��
		 * 
		 * @Override public void onShowCustomView(View view, CustomViewCallback
		 * callback) {
		 * LogUtils.i("onShowCustomView_____________________________");
		 * 
		 * ((Activity) context) .requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * // ��ֵ��callback customViewCallback = callback; // ����webView����
		 * webView.setVisibility(View.GONE); // ����video����֮�����Ƶ�ŵ�������ȥ FrameLayout
		 * video = (FrameLayout) findViewById(R.id.video); // ��video�ŵ���ǰ��ͼ��
		 * video.addView(view); // ������ʾ ((Activity) context)
		 * .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		 * // ����ȫ�� setFullScreen(); }
		 * 
		 * // �˳�ȫ����ʱ��
		 * 
		 * @Override public void onHideCustomView() { if (customViewCallback !=
		 * null) { // ���ص� customViewCallback.onCustomViewHidden(); } //
		 * �û���ǰ����ѡ����
		 * setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER); //
		 * �˳�ȫ�� quitFullScreen(); // ����WebView�ɼ�
		 * webView.setVisibility(View.VISIBLE); }
		 * 
		 * });
		 */

	}

	/** �����ļ� */
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

	/** �������� */
	class MyWebViewClient extends WebViewClient {
		// shouldOverrideUrlLoading �����µ������ڵ�ǰWebView�д�
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	/** �������л� */
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

	/** ����ȫ�� */
	private void setFullScreen() {
		// ����ȫ����������ԣ���ȡ��ǰ����Ļ״̬��Ȼ������ȫ��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ȫ���µ�״̬�룺1098974464
		// �����µ�״̬��1098973440
	}

	/** �˳�ȫ�� */
	private void quitFullScreen() {
		// ������ǰ��Ļ״̬�Ĳ�������ȡ
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
