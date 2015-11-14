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
 * ͼ��ģ���Լ�ʵ�ֻ�ͼ��
 * 
 * @author Administrator
 */
public abstract class MaskedImage extends ImageView {
	/** ��ɫ��Ⱦ�� */
	private static final Xfermode MASK_XFERMODE;
	private Bitmap srcBitmap;
	private Paint paint;

	static {
		/** ȡ������ƽ�������ʾ�²�mode,DSTĿ��ͼ�����ڱ���ͼ��SRCԴͼ�������»���ǰ��ͼ */
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		/** ʹ��ͼ��ϳɵ�16��Porter-Duff���������һ��������Paint��������е�Canvasͼ����н����� */
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

	// �Զ�ִ�д˷���
	@Override
	protected void onDraw(Canvas canvas) {
		// ��ȡͼ����ΪDSTĿ��ͼ�����ڱ���ͼ
		Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
		try {
			if (this.paint == null) {
				// ��������
				this.paint = new Paint();
				// �Ƿ������ֽ�ͼ������
				this.paint.setFilterBitmap(false);
				// ��xfermode��Ⱦ����װ��pain������
				this.paint.setXfermode(MASK_XFERMODE);
			}
			/** ��ȡͼƬ��� */
			int f1 = getWidth();
			int f2 = getHeight();
			// �洢�����ز㷵�ذ汾��
			int i = canvas.saveLayer(0.0F, 0.0F, f1, f2, null, 31);
			// ��Drawable���û�����С
			localDrawable.setBounds(0, 0, f1, f2);
			// ���ϻ���
			localDrawable.draw(canvas);
			// ���ͼ��Ϊ�ջ��߱�����
			if ((this.srcBitmap == null) || (this.srcBitmap.isRecycled())) {
				// ���ó��󷽷�����Bitmap����srcBitmap,SRCԴͼ�������»���ǰ��ͼ
				this.srcBitmap = createMask();
			}
			// ���û�ͼ������ʹDSTĿ��ͼ�����»��ϵ�SRCԴͼ����Mode.DST_INģʽ��ͼ
			canvas.drawBitmap(this.srcBitmap, 0.0F, 0.0F, this.paint);
			// ����汾��
			canvas.restoreToCount(i);
		} catch (Exception localException) {
			StringBuilder localStringBuilder = new StringBuilder()
					.append("Attempting to draw with recycled bitmap.");
			System.out.println("localStringBuilder==" + localStringBuilder);
		}
	}
}