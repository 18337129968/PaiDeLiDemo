package com.example.paidelidemo.ui.login;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.entity.request.InvitationCodeRequest;
import com.example.paidelidemo.entity.request.InvitationCodeRequest.Parameter;
import com.example.paidelidemo.entity.request.RegisterResult;
import com.example.paidelidemo.utils.AppManager;
import com.example.paidelidemo.utils.FileUtil;
import com.example.paidelidemo.utils.HttpConnectTool;
import com.example.paidelidemo.utils.HttpConnectTool.ConnectListener;
import com.example.paidelidemo.utils.ToastUtils;
import com.google.gson.Gson;

/**
 * �������������
 * 
 * @author xiehaifeng
 */
public class InvitationCodeActivity extends HeadBaseActivity {
	private Context context;
	TextView register_done_btn;
	View invitation_skip_text;
	Timer timer;
	TimerTask task;
	EditText invitation_code_edittext;

	/** ���湹�췽�� */
	public InvitationCodeActivity() {
		context = InvitationCodeActivity.this;
	}

	/** ���ͷ���� */
	@Override
	public void appHead(View view) {
		top_text.setText(R.string.register_text);
		btn_right.setVisibility(View.INVISIBLE);
		btn_left.setOnClickListener(this);
	}

	/** ��invitation_code���ּ��������ܲ����� */
	@Override
	public void initReplaceView() {
		// �洢�����˳���Activity
		AppManager.getInstance().addActivity(this);
		FrameLayout base_content = (FrameLayout) findViewById(R.id.layout_frame);
		View view = View.inflate(context, R.layout.activity_invitation_code,
				null);
		base_content.addView(view);
		initLayoutView();
	}

	/** ��ʼ�������ļ� */
	private void initLayoutView() {
		// ���������벢���ע��
		invitation_skip_text = findViewById(R.id.invitation_skip_text);
		// ���ע��
		register_done_btn = (TextView) findViewById(R.id.register_done_btn);
		// �����������
		invitation_code_edittext = (EditText) findViewById(R.id.invitation_code_edittext);
		// ���ü���
		invitation_skip_text.setOnClickListener(this);
		register_done_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_head_left:
			finish();
			break;
		case R.id.invitation_skip_text:
			ToastUtils.makeText(context, "ע��ɹ�", 0).show();
			intentActivity(context, LoginActivity.class, null);
			finish();
			break;
		case R.id.register_done_btn:
			String invitation_code = invitation_code_edittext.getText()
					.toString();
			if (TextUtils.isEmpty(invitation_code)) {
				ToastUtils.makeText(context, "������������", 0).show();
			} else {
				Gson gson = new Gson();
				String userJson = FileUtil.readFile(context,
						Constant.USER_INFO_FILE_NAME);
				if (TextUtils.isEmpty(userJson)) {
					ToastUtils.makeText(context, "�û���Ϣ�ļ�������", 0).show();
					return;
				}
				RegisterResult userEntity = gson.fromJson(userJson,
						RegisterResult.class);
				InvitationCodeRequest invitationcodeRequest = new InvitationCodeRequest();
				Parameter parameter = invitationcodeRequest.p;
				parameter.inviteCode = invitation_code_edittext.getText()
						.toString();
				parameter.userId = String.valueOf(userEntity.p.user.id);
				String requestJson = gson.toJson(invitationcodeRequest);

				HttpConnectTool.update(requestJson, context,
						new ConnectListener() {

							@Override
							public void onConnectSuccess(String result) {
								if (!TextUtils.isEmpty(result)) {
									finish();
								} else {
									ToastUtils.makeText(context, "���ӷ�������ʱ", 0)
											.show();
								}
							}

							@Override
							public void onConnectStart() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onConnectFailed(String error,
									String request) {
								// ���ʳ�ʱ
								ToastUtils.makeText(context, "���ʷ�������ʱ", 0)
										.show();
							}
						});

			}
			break;
		default:
			break;
		}
	}

}
