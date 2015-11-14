package com.example.paidelidemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.paidelidemo.R;

/**
 * �Զ���dialog��
 * 
 * @author xiehaifeng
 */
public class DialogUtils {
	private static AlertDialog alertDialog;
	private static View view;

	/*
	 * private static boolean flag; private static EditText deit_home_code;
	 */

	/** ���湹�췽�� */
	public DialogUtils() {

	}

	public static AlertDialog showDialog(Context context, CharSequence msg,
			int btnName[], OnClickListener clickListener, boolean isImg) {
		if (isImg) {
			view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
		} else {
			switch (btnName.length) {
			case 1:
				view = LayoutInflater.from(context).inflate(R.layout.dialog1,
						null);
				break;
			case 2:
				view = LayoutInflater.from(context).inflate(R.layout.dialog2,
						null);
				break;
			default:
				break;
			}
		}
		// ��ʾ������Ϣ
		TextView message = (TextView) view.findViewById(R.id.dialog_text);
		// ��ʾ���Ұ�ť
		Button left = (Button) view.findViewById(R.id.left);
		Button right = (Button) view.findViewById(R.id.right);
		// ����������Ϣ
		message.setText(msg);

		// ȷ���Ի���İ�ť����
		switch (btnName.length) {
		case 1:
			left.setText(context.getResources().getString(btnName[0]));
			left.setOnClickListener(clickListener);
			break;
		case 2:
			left.setText(context.getResources().getString(btnName[0]));
			right.setText(context.getResources().getString(btnName[1]));
			left.setOnClickListener(clickListener);
			right.setOnClickListener(clickListener);
			break;
		}
		// ����һ���Ի���������ʾ
		alertDialog = new AlertDialog.Builder(context).show();
		// �Ի���������ͼ����
		alertDialog.setContentView(view);
		/*
		 * �����־�����ɾ۽��ģ����ء� FLAG_NOT_FOCUSABLE|FLAG_ALT_FOCUSABLE_IM
		 * ��2����־�������뷨������ʾ���ص�
		 */
		alertDialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		// ��ʽ��������ģʽʹ�õĴ�����ʾ�����
		alertDialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return alertDialog;
	}

}
