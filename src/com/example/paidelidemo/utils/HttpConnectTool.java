package com.example.paidelidemo.utils;

import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.widget.Toast;

import com.example.paidelidemo.contants.Constant;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 访问网络请求工具类返回Json字符串进行解析
 * 
 * @author Administrator
 */
public class HttpConnectTool {
	private static String path = Constant.CONNECT_URL1;
	public static final int HTTP_CONNECT_START = 0;
	public static final int HTTP_CONNECT_SUCCESS = 1;
	public static final int HTTP_CONNECT_FAILED = 2;
	// xUtils工具类
	static HttpUtils httpUtils;

	/** 常规构造方法 */
	public HttpConnectTool() {

	}

	/** 访问服务器，requestJson向服务器请求Json，服务器连接监听 */
	public static void update(final String requestJson, Context context,
			final ConnectListener connectListener) {
		update(requestJson, true, context, connectListener);
	}

	/** 多态方法 */
	public static void update(String requestJson, boolean showDialog,
			final Context context, final ConnectListener connectListener) {
		boolean isConnected = InternetUtils.isConnected(context);
		if (!isConnected) {
			Toast.makeText(context, "网络断开连接，请检查", 0).show();
			connectListener.onConnectFailed(null, null);
			return;
		}
		StringEntity entity;
		try {
			entity = new StringEntity(requestJson, "utf-8");
		} catch (Exception e) {
			ToastUtils.makeText(context, "请求参数失败!", ToastUtils.LENGTH_SHORT);
			return;
		}

		httpUtils = new HttpUtils();
		// 设置当前请求的缓存时间
		httpUtils.configCurrentHttpCacheExpiry(0);
		// 设置默认请求的缓存时间
		httpUtils.configDefaultHttpCacheExpiry(0);
		// 设置线程数
		httpUtils.configRequestThreadPoolSize(10);
		// 设置超时时间限制
		httpUtils.configTimeout(10 * 1000);
		httpUtils.configSoTimeout(20 * 1000);
		RequestParams params = new RequestParams();
		params.setBodyEntity(entity);

		httpUtils.send(HttpMethod.POST, path, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// 访问超时
						connectListener.onConnectFailed("访问服务器失败", null);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// 正在访问中
						super.onLoading(total, current, isUploading);
					}

					@Override
					public void onStart() {
						// 开始访问链接
						connectListener.onConnectStart();
						super.onStart();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// 访问成功返回结果Json字符串
						connectListener.onConnectSuccess(arg0.result);
					}

				});

	}

	public interface ConnectListener {
		public void onConnectStart();

		public void onConnectSuccess(String result);

		public void onConnectFailed(String error, String request);
	}
}
