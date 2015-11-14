package com.example.paidelidemo.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 图像模具以及实现画图类
 * 
 * @author Administrator
 */
public abstract class MaskedImage extends ImageView {
	/** 颜色渲染类 */
	private static final Xfermode MASK_XFERMODE;
	private Bitmap srcBitmap;
	private Paint paint;

	static {
		/** 取两层绘制交集。显示下层mode,DST目标图像属于背景图，SRC源图像属于新画上前景图 */
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		/** 使用图像合成的16条Porter-Duff规则的任意一条来控制Paint如何与已有的Canvas图像进行交互。 */
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	public MaskedImage(Context paramContext) {
		super(paramContext);
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public abstract Bitmap createMask();

	// 自动执行此方法
	@Override
	protected void onDraw(Canvas canvas) {
		// 获取图像作为DST目标图像属于背景图
		Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
		try {
			if (this.paint == null) {
				// 创建画笔
				this.paint = new Paint();
				// 是否设置字节图过滤器
				this.paint.setFilterBitmap(false);
				// 把xfermode渲染类型装进pain画笔中
				this.paint.setXfermode(MASK_XFERMODE);
			}
			/** 获取图片宽高 */
			int f1 = getWidth();
			int f2 = getHeight();
			// 存储画布地层返回版本数
			int i = canvas.saveLayer(0.0F, 0.0F, f1, f2, null, 31);
			// 在Drawable设置画布大小
			localDrawable.setBounds(0, 0, f1, f2);
			// 画上画布
			localDrawable.draw(canvas);
			// 如果图像为空或者被回收
			if ((this.srcBitmap == null) || (this.srcBitmap.isRecycled())) {
				// 调用抽象方法返回Bitmap对象srcBitmap,SRC源图像属于新画上前景图
				this.srcBitmap = createMask();
			}
			// 调用画图方法，使DST目标图像与新画上的SRC源图像以Mode.DST_IN模式画图
			canvas.drawBitmap(this.srcBitmap, 0.0F, 0.0F, this.paint);
			// 保存版本数
			canvas.restoreToCount(i);
		} catch (Exception localException) {
			StringBuilder localStringBuilder = new StringBuilder()
					.append("Attempting to draw with recycled bitmap.");
			System.out.println("localStringBuilder==" + localStringBuilder);
		}
	}
}