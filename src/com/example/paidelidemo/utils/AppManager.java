package com.example.paidelidemo.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 * Activity�����࣬���ڹ�������˳�
 * 
 * @author xiehaifeng
 */
public class AppManager extends Application {
	/**
	 * ����list����������ÿһ��activity���ǹؼ�
	 * */
	private List<Activity> mList;
	/**
	 * ��̬��ʼ������Ϊ��ʵ��ÿ��ʹ�ø���ʱ�������µĶ���
	 * */
	private static AppManager appManager;

	/**
	 * ���ȴ���һ�����췽��
	 * */
	private AppManager() {
	}

	/**
	 * ����ʵ�����������߳�ͬ��,һ��ֻ����һ������
	 * */
	public synchronized static AppManager getInstance() {
		if (appManager == null) {
			appManager = new AppManager();
		}
		return appManager;
	}

	/** ���activity */
	public void addActivity(Activity activity) {
		if (mList == null) {
			mList = new LinkedList<Activity>();
		}
		mList.add(activity);
	}

	/** �ر�ÿһ��list�ڵ�activity */
	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null) {
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/** ϵͳ�ڴ治��ʱ�����ô˷�����ɱ������ */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		// ǿ�ƽ�����������
		System.gc();
	}

}
