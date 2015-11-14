package com.example.paidelidemo.ui.earnpoints;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.paidelidemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 大量图片异步加载框架工具类
 * 
 * @author tml
 */
public class ImageLoaderUtil {
	// 调用图片异步加载框架
	private static DisplayImageOptions options;
	private static ImageLoader imageLoader;

	/**
	 * 不保存在本地的图片加载方法 *
	 * 
	 * @param uri
	 * @param imageview
	 */
	public static void loadImage(String uri, ImageView imageview,
			Context context) {
		imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
		loadImage(uri, imageview, false, context, null);
	}

	public void loadImage1(String uri, ImageView imageview,
			final boolean isSave, final Context mContext, final String fileName) {
		if (TextUtils.isEmpty(uri) || uri.length() < 7) {
			if (imageview != null) {
				imageview.setBackgroundResource(R.drawable.ic_launcher);
			}
			return;
		}
		initializeImageLoader();
		String str = "\\";
		if (uri.contains(str)) {
			uri = uri.replace(str, "/");
		}
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		imageLoader.displayImage(uri.substring(0, 7).equals("http://") ? uri
				: "http://" + uri, imageview, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if (isSave) {
							File file = mContext.getFilesDir();
							FileOutputStream fos;
							try {
								fos = new FileOutputStream(file + fileName);
								@SuppressWarnings("unused")
								boolean success = loadedImage.compress(
										Bitmap.CompressFormat.PNG, 100, fos);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {
					}
				});
	}

	/**
	 * 保存到本地的图片加载方法
	 * 
	 * @param uri
	 * @param imageview
	 * @param isSave
	 *            保存为true
	 * @param mContext
	 *            上下文Context
	 * @param fileName
	 *            保存的图片名字
	 * 
	 *            ImageView 加载本地图片的方法 BitmapFactory factory = new
	 *            BitmapFactory(); Bitmap bitmap =
	 *            factory.decodeFile(getFilesDir() + 图片名);
	 *            image.setImageBitmap(bitmap);
	 * 
	 */
	public static void loadImage(String uri, ImageView imageview,
			final boolean isSave, final Context mContext, final String fileName) {
		if (TextUtils.isEmpty(uri) || uri.length() < 7) {
			if (imageview != null) {
				imageview.setBackgroundResource(R.drawable.ic_launcher);
			}
			return;
		}

		if (isSave) {
			File file = new File(mContext.getFilesDir() + fileName);
			if (file.exists()) {
				new BitmapFactory();
				Bitmap bitmap = BitmapFactory.decodeFile(mContext.getFilesDir()
						+ fileName);
				imageview.setImageBitmap(bitmap);
				return;
			}
		}

		initializeImageLoader();

		String str = "\\";

		if (uri.contains(str)) {
			uri = uri.replace(str, "/");
		}

		uri = uri.contains("http://") ? uri : "http://" + uri;
		if (imageLoader == null) {
			imageLoader = ImageLoader.getInstance();
			// 必须初始化
			imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		}
		imageLoader.displayImage(uri, imageview, options,
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if (isSave) {
							File file = mContext.getFilesDir();
							FileOutputStream fos;
							try {
								fos = new FileOutputStream(file + fileName);
								@SuppressWarnings("unused")
								boolean success = loadedImage.compress(
										Bitmap.CompressFormat.JPEG, 100, fos);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {

					}
				});
	}

	public static void initializeImageLoader() {
		if (options == null) {
			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.loading)
					.showImageForEmptyUri(R.drawable.loading)
					.showImageOnFail(R.drawable.loading)
					.cacheInMemory(true).cacheOnDisk(true)
					.considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
		}
	}

}
