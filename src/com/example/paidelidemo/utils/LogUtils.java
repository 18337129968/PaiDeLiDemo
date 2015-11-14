package com.example.paidelidemo.utils;

import android.util.Log;

/**
 * Log��ʾ��
 * 
 * @author xiehaifeng
 */
public class LogUtils {

	/** ���湹�췽�� */
	public LogUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	// �Ƿ���Ҫ��ӡbug��������application��onCreate���������ʼ��
	public static boolean isDebug = true;
	private static final String TAG = "LogUtils";

	// �����ĸ���Ĭ��tag�ĺ���
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}

	// �����Ǵ����Զ���tag�ĺ���
	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}
}
