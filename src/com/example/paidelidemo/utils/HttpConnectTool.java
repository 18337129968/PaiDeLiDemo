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
 * �����������󹤾��෵��Json�ַ������н���
 * 
 * @author Administrator
 */
public class HttpConnectTool {
	private static String path = Constant.CONNECT_URL1;
	public static final int HTTP_CONNECT_START = 0;
	public static final int HTTP_CONNECT_SUCCESS = 1;
	public static final int HTTP_CONNECT_FAILED = 2;
	// xUtils������
	static HttpUtils httpUtils;

	/** ���湹�췽�� */
	public HttpConnectTool() {

	}

	/** ���ʷ�������requestJson�����������Json�����������Ӽ��� */
	public static void update(final String requestJson, Context context,
			final ConnectListener connectListener) {
		update(requestJson, true, context, connectListener);
	}

	/** ��̬���� */
	public static void update(String requestJson, boolean showDialog,
			final Context context, final ConnectListener connectListener) {
		boolean isConnected = InternetUtils.isConnected(context);
		if (!isConnected) {
			Toast.makeText(context, "����Ͽ����ӣ�����", 0).show();
			connectListener.onConnectFailed(null, null);
			return;
		}
		StringEntity entity;
		try {
			entity = new StringEntity(requestJson, "utf-8");
		} catch (Exception e) {
			ToastUtils.makeText(context, "�������ʧ��!", ToastUtils.LENGTH_SHORT);
			return;
		}

		httpUtils = new HttpUtils();
		// ���õ�ǰ����Ļ���ʱ��
		httpUtils.configCurrentHttpCacheExpiry(0);
		// ����Ĭ������Ļ���ʱ��
		httpUtils.configDefaultHttpCacheExpiry(0);
		// �����߳���
		httpUtils.configRequestThreadPoolSize(10);
		// ���ó�ʱʱ������
		httpUtils.configTimeout(10 * 1000);
		httpUtils.configSoTimeout(20 * 1000);
		RequestParams params = new RequestParams();
		params.setBodyEntity(entity);

		httpUtils.send(HttpMethod.POST, path, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// ���ʳ�ʱ
						connectListener.onConnectFailed("���ʷ�����ʧ��", null);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// ���ڷ�����
						super.onLoading(total, current, isUploading);
					}

					@Override
					public void onStart() {
						// ��ʼ��������
						connectListener.onConnectStart();
						super.onStart();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// ���ʳɹ����ؽ��Json�ַ���
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
