package com.example.paidelidemo.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/** 画圆形类 */
public class CircularImage extends MaskedImage {
	public CircularImage(Context paramContext) {
		super(paramContext);
	}

	public CircularImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CircularImage(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	/** 实现继承类的抽象方法，画圆形 */
	@Override
	public Bitmap createMask() {
		// 获取图片的宽高
		int x = getWidth();
		int y = getHeight();
		// 创建32位像素的图像
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap srcBitmap = Bitmap.createBitmap(x, y, localConfig);
		// 创建画布
		Canvas localCanvas = new Canvas(srcBitmap);
		// 创建画笔，设置画笔宽度为1
		Paint localPaint = new Paint(1);
		// 设置画笔颜色
		localPaint.setColor(Color.WHITE);
		// 通过RectF对象来指定椭圆形的外切矩形，并依此来绘制椭圆。
		RectF localRectF = new RectF(0.0F, 0.0F, x, y);
		// 通过在矩形里面画椭圆
		localCanvas.drawOval(localRectF, localPaint);
		return srcBitmap;
	}
}