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
 * ������ ������
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
		// ǿ��ת������Ϊ�˷�ֹandroid��ÿ����Ļ���л���������Activity
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
	 * ʵ���������󷽷� top��������ʵ��
	 * 
	 * @author zhaobin
	 * @param
	 * @return void
	 * @throws
	 */
	public abstract void appHead(View view);

	/**
	 * ���湦��ʵ��
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
	 *            ��ǰactivity
	 * @param classs
	 *            Ҫ��ת����activity
	 * @param value
	 *            ���ݵ�ֵ
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

	// ���ȴ����¼���ȡ��������¼�
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// ����ƶ��¼�Ϊ����
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			// ��ȡ��ǰView�Ľ���View The current View with focus
			View view = getCurrentFocus();
			// �������淽������Boolenֵ
			if (isHideInput(view, ev)) {
				HideSoftInput(view.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// �ж��Ƿ���Ҫ����
	public boolean isHideInput(View v, MotionEvent ev) {
		// view��Ϊ�ղ���view����Ϊedittext
		if (v != null && (v instanceof EditText)) {
			// ������������
			int[] l = { 0, 0 };
			// ��ȡview�������ڵ�λ��
			/* �����ͼ�Ĵ��ڵ�������㡣���������Ĳ���������һ�����顣�������غ�,���������x��y��λ�� */
			v.getLocationInWindow(l);
			// System.out.println(Arrays.toString(l));
			// l[0]Ϊ��ȡ�Ŀؼ�X����
			// l[1]ΪY����
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			// ��ȡ���������ڿؼ���x���꣬С��x+�ؼ����***
			// ����ؼ������򷵻�false
			if (ev.getX() > left && ev.getX() < right && ev.getY() > top
					&& ev.getY() < bottom) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	// ���������
	private void HideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(token,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
