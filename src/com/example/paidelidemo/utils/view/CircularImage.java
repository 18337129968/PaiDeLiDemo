package com.example.paidelidemo.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/** ��Բ���� */
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

	/** ʵ�ּ̳���ĳ��󷽷�����Բ�� */
	@Override
	public Bitmap createMask() {
		// ��ȡͼƬ�Ŀ��
		int x = getWidth();
		int y = getHeight();
		// ����32λ���ص�ͼ��
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap srcBitmap = Bitmap.createBitmap(x, y, localConfig);
		// ��������
		Canvas localCanvas = new Canvas(srcBitmap);
		// �������ʣ����û��ʿ��Ϊ1
		Paint localPaint = new Paint(1);
		// ���û�����ɫ
		localPaint.setColor(Color.WHITE);
		// ͨ��RectF������ָ����Բ�ε����о��Σ���������������Բ��
		RectF localRectF = new RectF(0.0F, 0.0F, x, y);
		// ͨ���ھ������滭��Բ
		localCanvas.drawOval(localRectF, localPaint);
		return srcBitmap;
	}
}