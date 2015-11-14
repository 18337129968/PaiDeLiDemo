package com.example.paidelidemo.ui.shineprize;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.paidelidemo.R;
import com.example.paidelidemo.ui.earnpoints.widget.PullToRefreshListView;
import com.example.paidelidemo.ui.login.HeadBaseActivity;
import com.example.paidelidemo.utils.AppManager;

/**
 * �û�ɹ����
 * 
 * @author xiehaifeng
 * 
 */
public class ShinePrizeActivity extends HeadBaseActivity
{
	private Context context;
	private Button btn_jf_showprice;
	private Button btn_yy_showprice;
	private Button btn_show_my_prize;
	private PullToRefreshListView pl_prize_list;
	/** ���湹�췽�� */
	public ShinePrizeActivity()
	{
		AppManager.getInstance().addActivity(ShinePrizeActivity.this);
		context = ShinePrizeActivity.this;
	}

	/** ��ӱ����� */
	@Override
	public void appHead(View view)
	{
		top_text.setText("�û�ɹ��");
		btn_left.setOnClickListener(this);
	}

	/** �滻������ͼ */
	@Override
	public void initReplaceView()
	{
		// ��ȡbase�����ļ��еĲ���
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// ʹ��login�����ļ���װview����
		View view = View.inflate(context, R.layout.activity_show_prize, null);
		// �������view
		base_frameLayout.addView(view);
		initView(view);
	}
	
	/**��ʼ����ͼ*/
	private void initView(View view)
	{
		//����ҡ��
		btn_jf_showprice = (Button) getView(view, R.id.btn_jf_showprice);
		//һԪҡ��
		btn_yy_showprice = (Button) getView(view, R.id.btn_yy_showprice);
		//�б�
		pl_prize_list = (PullToRefreshListView) getView(view,
				R.id.pl_prize_list);
		//��Ҫ����
		btn_show_my_prize = (Button) getView(view, R.id.btn_show_my_prize);
		
		btn_show_my_prize.setOnClickListener(this);
		
	}

	/** ��Ӽ������� */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_head_left:
			finish();
			break;

		case R.id.btn_show_my_prize:
			
			break;
			
		default:
			break;
		}
	}
}
