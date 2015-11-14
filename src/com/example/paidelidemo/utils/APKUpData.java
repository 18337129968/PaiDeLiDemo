package com.example.paidelidemo.utils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

//app 更新类

public class APKUpData {
	public Context mContext;
	private String apkUrl;
	private String des;
	private String apkCode;
	private long timeEnd;

	private long timeStart;
	private boolean isShow;
	private boolean can;
	private static ProgressDialog progressDialog;
	public static Handler mHandler;
	public static final int VERSION_UNLIKENESS_CODE = 0;
	public static final int VERSION_ISNEW_CODE = 1;
	public static final int VERSION_ERROR_CODE = 2;

	public APKUpData(Context mContext) {
		this.mContext = mContext;
		timeStart = System.currentTimeMillis();
		progressDialog = new ProgressDialog(mContext);
		progressDialog.setTitle("提示信息");
		progressDialog.setMessage("正在下载中，请稍后......");
		// 设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
		progressDialog.setCancelable(false);
		// 设置ProgressDialog样式为水平的样式
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		can = true;
		if (can) {
			new LooperThread().start();
		}
	}

	public void checkUpData(boolean isShowToast) {
		isShow = isShowToast;
		if (can) {
			new MyTread().start();
		}
	}

	class LooperThread extends Thread {

		@Override
		public void run() {
			Looper.prepare();

			mHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case VERSION_UNLIKENESS_CODE:
						showUpdateDialog();
						break;
					case VERSION_ISNEW_CODE:
						if (isShow) {
							ToastUtils.makeText(mContext, "当前是最新的版本", 0).show();
						}
						break;
					case VERSION_ERROR_CODE:
						LogUtils.i("URL访问失败，请检查URL是否正确");
						break;
					default:
						break;
					}
					can = true;
				}
			};

			Looper.loop();
		}
	}

	private String getVersionName() {
		PackageManager pm = mContext.getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(
					mContext.getPackageName(), 0);
			LogUtils.i("packageInfo.versionName=" + packageInfo.versionName);

			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("发现新版本:" + apkCode);
		builder.setMessage(des);
		builder.setCancelable(false);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				download();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	protected void download() {
		String str = "\\";
		if (apkUrl.contains(str)) {
			apkUrl = apkUrl.replace(str, "/");
		}
		apkUrl = apkUrl.contains("http://") ? apkUrl : "http://" + apkUrl;

		try {
			HttpUtils http = new HttpUtils();
			http.download(apkUrl, "/sdcard/PaiDeLiDemo.apk", // 地址不能错误否则失败
					new RequestCallBack<File>() {
						@Override
						public void onStart() {
							LogUtils.i("conn...");
							progressDialog.show();
						}

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							LogUtils.i(current + "/" + total);
							// 更新ProgressDialog的进度条
							progressDialog
									.setProgress((int) (current * 100 / total));
						}

						@Override
						public void onSuccess(ResponseInfo<File> responseInfo) {
							LogUtils.i("downloaded:"
									+ responseInfo.result.getPath());
							progressDialog.dismiss();
							installApk();
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							LogUtils.i("onFailure:" + msg);
							// 使ProgressDialog框消失
							progressDialog.dismiss();
							Toast.makeText(mContext, "下载失败", 0).show();
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void installApk() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setDataAndType(
				Uri.fromFile(new File("/sdcard/PaiDeLiDemo.apk")),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
	}

	class MyTread extends Thread {

		@Override
		public void run() {
			try {
				URL url = new URL(
						"http://172.30.93.66:8080/AppService/UpData/updata.txt");

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setConnectTimeout(5000);
				conn.setDefaultUseCaches(false);
				int code = conn.getResponseCode();
				conn.connect();
				Message message = mHandler.obtainMessage();
				LogUtils.i(code + "");
				if (code == 200) {
					InputStream inputStream = conn.getInputStream();
					String json = StreamUtils.parserInputStream(inputStream);
					// {"code":"1","des":"描述","apkUrl":"apkUrl"}
					JSONObject jsonObject = new JSONObject(json);
					apkCode = jsonObject.getString("code");
					des = jsonObject.getString("des");
					apkUrl = jsonObject.getString("apkUrl");

					LogUtils.i(apkCode + "\n" + des + "\n" + apkUrl);

					if (!getVersionName().equals(apkCode)) {
						message.what = VERSION_UNLIKENESS_CODE;
					} else {
						message.what = VERSION_ISNEW_CODE;
					}
				} else {
					message.what = VERSION_ERROR_CODE;
				}
				mHandler.sendMessage(message);
				timeEnd = System.currentTimeMillis();
			} catch (Exception e) {
				LogUtils.i("访问服务器失败！");
			}

		}
	}
}
