package com.example.paidelidemo.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.earnpoints.ImageLoaderUtil;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.view.convenientbanner.CBPageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Sai on 15/8/4.
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
	private ImageView imageView;

	@Override
	public View createView(Context context) {
		imageView = new ImageView(context);
		//imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		return imageView;
	}

	@Override
	public void UpdateUI(Context context, final int position, String data) {
		imageView.setImageResource(R.drawable.loading);
		ImageLoaderUtil.loadImage(data, imageView,context);
		
		//ImageLoader.getInstance().displayImage(data, imageView);//可以不用init
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(view.getContext(),
						"点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT)
						.show();
				HeadBaseActivity.intentActivity(HomeActivity.context,
						BannerDesActivity.class, "http://baidu.com/");
			}
		});
	}
}
