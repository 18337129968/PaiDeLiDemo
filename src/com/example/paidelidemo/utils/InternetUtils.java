package com.example.paidelidemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ������ع�����
 * 
 * @author xiehaifeng
 */
public class InternetUtils {

	/** ���湹�췽�� */
	public InternetUtils() {
		throw new UnsupportedOperationException("�޷�����ʼ��:cannot be instantiated");
	}

	/** �ж������Ƿ����� */
	public static boolean isConnected(Context context) {
		// �������ӹ����߷���
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ��ȡ��ǰ�������ӹ�������
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (null != connectivityManager && info != null) {
			if (info.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}
}
