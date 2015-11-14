package com.example.paidelidemo.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;

import com.example.paidelidemo.R;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class ShareDirectUtil {
	public UMSocialService mController;
	public Activity activity;
	public Context context;
	private String shareUrl = null;

	public ShareDirectUtil(Activity activity, Context context) {
		this.activity = activity;
		this.context = context;
		addQQQZonePlatform();
		addWXPlatform();
		setShareData("�ĵ�����������Ӯ��", null);

	}

	public ShareDirectUtil(Activity activity, Context context, String messages,
			String url) {
		this.activity = activity;
		this.context = context;
		shareUrl = url;
		addQQQZonePlatform();
		addWXPlatform();
		setShareData(messages, null);

	}

	private void setShareData(String msg, Object object) {
		// ����������Activity��������³�Ա����
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		// ���÷�������
		mController.setShareContent(msg);
		// ���÷���ͼƬ, ����2ΪͼƬ��url��ַ
		if (object == null) {
			mController.setShareMedia(new UMImage(context,
					R.drawable.ic_launcher));
		}
		if (object instanceof Bitmap) {
			mController.setShareMedia(new UMImage(context, (Bitmap) object));
		} else if (object instanceof Integer) {
			mController.setShareMedia(new UMImage(context, (Integer) object));
		} else if (object instanceof File) {
			mController.setShareMedia(new UMImage(context, (File) object));
		} else if (object instanceof String) {
			mController.setShareMedia(new UMImage(context, object.toString()));
		} else if (object instanceof byte[]) {
			mController.setShareMedia(new UMImage(context, (byte[]) object));
		}

	}

	public void openShare() {
		mController.openShare(activity, false);
	}

	/**
	 * ����postShare������ת������༭ҳ��Ȼ���ٷ���</br> [ע��]<li>
	 * �������ˣ����꣬���ˣ���Ѷ΢����ת������༭ҳ������ƽֱ̨����ת����Ӧ�Ŀͻ���
	 */
	public void postShare(boolean isDisplay) {

		ShareTool shareBoard = new ShareTool(activity);
		shareBoard.initView(isDisplay);
		shareBoard.showAtLocation(activity.getWindow().getDecorView(),
				Gravity.BOTTOM, 0, 0);

	}

	public void postShare() {

		ShareTool shareBoard = new ShareTool(activity);
		shareBoard.initView(true);
		shareBoard.showAtLocation(activity.getWindow().getDecorView(),
				Gravity.BOTTOM, 0, 0);

	}

	/**
	 * @�������� : ���΢��ƽ̨����
	 * @return
	 */
	private void addWXPlatform() {
		// ע�⣺��΢����Ȩ��ʱ�򣬱��봫��appSecret
		// wx967daebe835fbeac������΢�ſ���ƽ̨ע��Ӧ�õ�AppID, ������Ҫ�滻����ע���AppID
		// String appId = "wx967daebe835fbeac";
		// String appSecret = "5bb696d9ccd75a38c8a0bfe0675559b3";
		// ��˾��
		// String appId = "wx4d979f58d5966865";
		// String appSecret = "5d5cdafa1d67683abea1cbbcd2abe31b";
		// sunaoyang��
		String appId = "wx1f42a9c6643f6201";
		String appSecret = "41063fbaecbda8ed02ac7b941c4a72bb";
		// ���΢��ƽ̨
		UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
		wxHandler.addToSocialSDK();

		// ֧��΢������Ȧ
		UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId,
				appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	/**
	 * @�������� : ���QQƽ̨֧�� QQ��������ݣ� �����������ͣ� �����������֡�ͼƬ�����֡���Ƶ. ����˵�� : title, summary,
	 *       image url�б�����������һ��, targetUrl��������,��ҳ��ַ������"http://"��ͷ . title :
	 *       Ҫ������� summary : Ҫ��������ָ��� image url : ͼƬ��ַ [������������������дһ��] targetUrl:
	 *       �û�����÷���ʱ��ת����Ŀ���ַ [����] ( ������д��Ĭ������Ϊ������ҳ )
	 * @return
	 */
	private void addQQQZonePlatform() {
		String appId = "100424468";
		String appKey = "c7394704798a158208a74ab60104f0ba";
		// ���QQ֧��, ��������QQ�������ݵ�target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, appId,
				appKey);
		qqSsoHandler.setTargetUrl(shareUrl);
		qqSsoHandler.addToSocialSDK();

		// ���QZoneƽ̨
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId,
				appKey);
		qZoneSsoHandler.setTargetUrl(shareUrl);
		qZoneSsoHandler.addToSocialSDK();
	}

}
