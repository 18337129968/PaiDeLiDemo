package com.example.paidelidemo.ui.earnpoints;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.utils.AppManager;

/**
 * 标题框架 抽象类
 * 
 * @author xiehaifeng
 */
public abstract class EarnPointsFM extends FragmentActivity implements
		OnClickListener {
	public Context context = null;
	public TextView top_text = null;
	public Button btn_right = null;
	public Button btn_left = null;
	public RelativeLayout layout = null;
	public FrameLayout btn_right2 = null;
	public TextView btn_right_num = null;
	public static final String intentKey = "value";

	public EarnPointsFM() {
		super();
		AppManager.getInstance().addActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 强制转换竖屏为了防止android中每次屏幕的切换都会重启Activity
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_headbase);
		context = EarnPointsFM.this;
		initView();
	}

	private void initView() {
		top_text = (TextView) findViewById(R.id.top_text);
		btn_right = (Button) findViewById(R.id.btn_top_right);
		btn_left = (Button) findViewById(R.id.btn_head_left);
		layout = (RelativeLayout) findViewById(R.id.head);
		btn_right2 = (FrameLayout) findViewById(R.id.btn_top_right2);
		btn_right_num = (TextView) findViewById(R.id.num);
		appHead(layout);
		initReplaceView();

	}

	public View getView(View view, int id) {
		return view.findViewById(id);
	}

	/**
	 * 实现两个抽象方法 top顶部工能实现
	 * 
	 * @author zhaobin
	 * @param
	 * @return void
	 * @throws
	 */
	public abstract void appHead(View view);

	/**
	 * 界面功能实现
	 * 
	 * @author zhaobin
	 * @param
	 * @return void
	 * @throws
	 */
	public abstract void initReplaceView();

	/**
	 * @author zhaobin
	 * @param context
	 *            当前activity
	 * @param classs
	 *            要跳转到的activity
	 * @param value
	 *            传递的值
	 * @return void
	 * @throws
	 */
	public static void intentActivity(Context context, Class<?> classs,
			Serializable value) {
		Intent intent = new Intent(context, classs);
		if (value != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(intentKey, value);
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	// 调度触摸事件获取触摸点击事件
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// 如果移动事件为向下
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			// 获取当前View的焦点View The current View with focus
			View view = getCurrentFocus();
			// 调用下面方法返回Boolen值
			if (isHideInput(view, ev)) {
				HideSoftInput(view.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// 判定是否需要隐藏
	public boolean isHideInput(View v, MotionEvent ev) {
		// view不为空并且view类型为edittext
		if (v != null && (v instanceof EditText)) {
			// 创建数组容器
			int[] l = { 0, 0 };
			// 获取view窗口所在的位置
			/* 这个视图的窗口的坐标计算。两个整数的参数必须是一个数组。方法返回后,该数组包含x和y的位置 */
			v.getLocationInWindow(l);
			// System.out.println(Arrays.toString(l));
			// l[0]为获取的控件X坐标
			// l[1]为Y坐标
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			// 获取点击焦点大于控件的x坐标，小于x+控件宽度***
			// 点击控件内区域返回false
			if (ev.getX() > left && ev.getX() < right && ev.getY() > top
					&& ev.getY() < bottom) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	// 隐藏软键盘
	private void HideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(token,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
