package com.example.paidelidemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关工具类
 * 
 * @author xiehaifeng
 */
public class InternetUtils {

	/** 常规构造方法 */
	public InternetUtils() {
		throw new UnsupportedOperationException("无法被初始化:cannot be instantiated");
	}

	/** 判断网络是否连接 */
	public static boolean isConnected(Context context) {
		// 网络连接管理者方法
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取当前网络连接工作对象
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (null != connectivityManager && info != null) {
			if (info.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}
}
