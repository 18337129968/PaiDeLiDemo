package com.example.paidelidemo.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 * Activity管理类，用于管理程序退出
 * 
 * @author xiehaifeng
 */
public class AppManager extends Application {
	/**
	 * 运用list集合来保存每一个activity，是关键
	 * */
	private List<Activity> mList;
	/**
	 * 静态初始化对象，为了实现每次使用该类时不创建新的对象
	 * */
	private static AppManager appManager;

	/**
	 * 首先创建一个构造方法
	 * */
	private AppManager() {
	}

	/**
	 * 单列实例化，考虑线程同步,一次只允许一个访问
	 * */
	public synchronized static AppManager getInstance() {
		if (appManager == null) {
			appManager = new AppManager();
		}
		return appManager;
	}

	/** 添加activity */
	public void addActivity(Activity activity) {
		if (mList == null) {
			mList = new LinkedList<Activity>();
		}
		mList.add(activity);
	}

	/** 关闭每一个list内的activity */
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

	/** 系统内存不足时，调用此方法，杀死进程 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		// 强制进行垃圾回收
		System.gc();
	}

}
