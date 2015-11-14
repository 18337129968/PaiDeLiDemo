package com.example.paidelidemo.ui.myfriend;

import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * �ҵĺ�����
 * 
 * @author л����
 * 
 */
public class MyFriendActivity extends HeadBaseActivity {
	
	@ViewInject(R.id.btn_news)
	private Button btn_news;
	@ViewInject(R.id.btn_friend)
	private Button btn_friend;
	@ViewInject(R.id.btn_spread)
	private Button btn_spread;
	
	private Context context;
	private FriendFragmentActivity friendFragmentActivity;
	private FragmentTransaction fragmentTransaction;
	// �˺��ڱ𴦵�¼
	public boolean isConflict = false;
	// �˺ű��Ƴ�
	private boolean isCurrentAccountRemoved = false;
	
	/**
	 * ��鵱ǰ�û��Ƿ�ɾ��
	 */
	public boolean getCurrentAccountRemoved() {
		return isCurrentAccountRemoved;
	}
	
	/** ���湹�췽�� */
	public MyFriendActivity() {
		AppManager.getInstance().addActivity(MyFriendActivity.this);
		context = MyFriendActivity.this;
	}

	/** ��ӱ����� */
	@Override
	public void appHead(View view) {
		top_text.setText("�ҵĺ���");
		btn_left.setOnClickListener(this);
	}

	/** �滻������ͼ */
	@Override
	public void initReplaceView() {
		// ��ȡbase�����ļ��еĲ���
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// ʹ��login�����ļ���װview����
		View view = View.inflate(context, R.layout.friend, null);
		// �������view
		base_frameLayout.addView(view);
		//ע��view���¼�
		ViewUtils.inject(this);
		initview(view);
	}
	private void initview(View view) {
		btn_news.setOnClickListener(this);
		btn_friend.setOnClickListener(this);
		btn_spread.setOnClickListener(this);
		fragmentTransaction = getFragmentManager().beginTransaction();
		friendFragmentActivity =new FriendFragmentActivity(context);
		fragmentTransaction.add(R.id.frame, friendFragmentActivity);
		fragmentTransaction.commit();
	}

	/** ��Ӽ������� */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_head_left:
			finish();
			break;

		default:
			break;
		}
	}
}
