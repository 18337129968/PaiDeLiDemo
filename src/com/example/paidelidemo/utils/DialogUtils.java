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
 * 自定义dialog类
 * 
 * @author xiehaifeng
 */
public class DialogUtils {
	private static AlertDialog alertDialog;
	private static View view;

	/*
	 * private static boolean flag; private static EditText deit_home_code;
	 */

	/** 常规构造方法 */
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
		// 提示内容信息
		TextView message = (TextView) view.findViewById(R.id.dialog_text);
		// 提示左右按钮
		Button left = (Button) view.findViewById(R.id.left);
		Button right = (Button) view.findViewById(R.id.right);
		// 设置内容信息
		message.setText(msg);

		// 确定对话框的按钮个数
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
		// 创建一个对话框让它显示
		alertDialog = new AlertDialog.Builder(context).show();
		// 对话框设置视图内容
		alertDialog.setContentView(view);
		/*
		 * 清除标志：不可聚焦的，隐藏。 FLAG_NOT_FOCUSABLE|FLAG_ALT_FOCUSABLE_IM
		 * 这2个标志决定输入法窗口显示隐藏的
		 */
		alertDialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		// 显式的软输入模式使用的窗口显示软键盘
		alertDialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return alertDialog;
	}

}
