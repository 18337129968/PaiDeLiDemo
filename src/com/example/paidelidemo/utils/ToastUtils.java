package com.example.paidelidemo.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paidelidemo.R;

/**
 * Toastπ§æﬂ¿‡
 * 
 * @author zhaobin
 * 
 */
public class ToastUtils {
	public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
	public static final int LENGTH_LONG = Toast.LENGTH_LONG;
	private static Toast toast = null;
	private static View view = null;

	public static synchronized Toast makeText(Context context,
			CharSequence text, int duration) {
		ViewHolder viewHolder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.toast, null);
			viewHolder = new ViewHolder();
			viewHolder.message = (TextView) view.findViewById(R.id.msg);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.message.setText(text);

		if (toast != null) {
			toast.cancel();
//			toast.setView(view);
			toast = creatToast(toast, context, duration);
		} else {
			toast = creatToast(toast, context, duration);
		}
		return toast;
	}

	private static Toast creatToast(Toast toast, Context context, int duration) {
		toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(duration);
		toast.setView(view);
		return toast;
	}

	public static Toast makeText(Context context, int resId, int duration) {
		if (toast != null) {
			toast.cancel();
			View view = LayoutInflater.from(context).inflate(R.layout.toast,
					null);
			TextView message = (TextView) view.findViewById(R.id.msg);
			message.setText(context.getResources().getString(resId));

			toast = new Toast(context);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(duration);
			toast.setView(view);
		} else {
			View view = LayoutInflater.from(context).inflate(R.layout.toast,
					null);
			TextView message = (TextView) view.findViewById(R.id.msg);
			message.setText(context.getResources().getString(resId));

			toast = new Toast(context);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(duration);
			toast.setView(view);
		}
		return toast;
	}

	public static void ToastCancel(Context context) {
		if (toast != null) {
			toast.cancel();
		}
	}
}

class ViewHolder {
	TextView message;
}
