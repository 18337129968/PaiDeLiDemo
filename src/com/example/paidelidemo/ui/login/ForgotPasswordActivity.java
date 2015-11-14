package com.example.paidelidemo.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.entity.request.ForgotPasswordRequest;
import com.example.paidelidemo.entity.request.ForgotPasswordRequest.Parameter;
import com.example.paidelidemo.entity.request.GetVolifareCodeRequest;
import com.example.paidelidemo.utils.AppManager;
import com.example.paidelidemo.utils.DialogUtils;
import com.example.paidelidemo.utils.EdittextUtil;
import com.example.paidelidemo.utils.EmojiUtils;
import com.example.paidelidemo.utils.HttpConnectTool;
import com.example.paidelidemo.utils.HttpConnectTool.ConnectListener;
import com.example.paidelidemo.utils.ToastUtils;
import com.google.gson.Gson;

/**
 * �����������
 * 
 * @author xiehaifeng
 */
public class ForgotPasswordActivity extends HeadBaseActivity {
	private Context context;
	String new_pwd, again_pwd, phoneNumber;
	// GetVerificationCodeRequest getVerificationRequest;
	private TextView fgpw_getverification_code_btn, fgpw_done_btn,
			fgpw_clear_up_btn;
	private EditText fgpw_phone_number_edittext,
			fgpw_verification_code_edittext, fgpw_enter_password_edittext,
			fgpw_enter_password_again_edittext;
	private Dialog dialog;

	/** ���湹�췽�� */
	public ForgotPasswordActivity() {
		context = ForgotPasswordActivity.this;
	}

	/** ���ͷ���� */
	@Override
	public void appHead(View view) {
		btn_right.setVisibility(View.INVISIBLE);
		top_text.setText(R.string.forgot_pwd_text);
		btn_left.setOnClickListener(this);
	}

	/** ��forgot���ּ��������ܲ����� */
	@Override
	public void initReplaceView() {
		// �洢�����˳���Activity
		AppManager.getInstance().addActivity(this);
		FrameLayout base_content = (FrameLayout) findViewById(R.id.layout_frame);
		View view = View.inflate(context, R.layout.activity_forgot_pwd, null);
		base_content.addView(view);
		initLayoutView();
	}

	/** ��ʼ�������ļ� */
	private void initLayoutView() {
		// ��ȡ��֤�밴ť
		fgpw_getverification_code_btn = (TextView) findViewById(R.id.fgpw_getverification_code_btn);
		// ��ɰ�ť
		fgpw_done_btn = (TextView) findViewById(R.id.fgpw_done_btn);
		// �ֻ���
		fgpw_phone_number_edittext = (EditText) findViewById(R.id.fgpw_phone_number_edittext);
		// ��֤��
		fgpw_verification_code_edittext = (EditText) findViewById(R.id.fgpw_verification_code_edittext);
		// ����������
		fgpw_enter_password_edittext = (EditText) findViewById(R.id.fgpw_enter_password_edittext);
		// �ٴ�����������
		fgpw_enter_password_again_edittext = (EditText) findViewById(R.id.fgpw_enter_password_again_edittext);
		// �����ť
		fgpw_clear_up_btn = (TextView) findViewById(R.id.fgpw_clear_up_btn);
		// ����
		fgpw_getverification_code_btn.setOnClickListener(this);
		fgpw_clear_up_btn.setOnClickListener(this);
		fgpw_done_btn.setOnClickListener(this);
		// ��⹤��
		EdittextUtil.edittextForPhone(fgpw_phone_number_edittext);
		EdittextUtil.edittextForPhone(fgpw_verification_code_edittext);
		EdittextUtil.edittextForPhone(fgpw_enter_password_edittext);
		EdittextUtil.edittextForPhone(fgpw_enter_password_again_edittext);
		// �ֻ��༭��ʵʱ����
		fgpw_phone_number_edittext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(s)) {
					fgpw_clear_up_btn.setVisibility(View.INVISIBLE);
					fgpw_clear_up_btn.setClickable(false);
				} else {
					fgpw_clear_up_btn.setVisibility(View.VISIBLE);
					fgpw_clear_up_btn.setClickable(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_head_left:// ���ذ�ť
			finish();
			break;
		case R.id.fgpw_getverification_code_btn:// ��ȡ��֤�밴ť
			getVolifareCode();
			break;
		case R.id.fgpw_clear_up_btn:// �����ť
			fgpw_phone_number_edittext.setText("");
			break;
		case R.id.fgpw_done_btn:// ��ɰ�ť
			initForgotpwd();
			break;
		default:
			break;
		}

	}

	// �ж�������
	private void initForgotpwd() {
		new_pwd = EmojiUtils.filterEmoji(fgpw_enter_password_edittext.getText()
				.toString());
		again_pwd = EmojiUtils.filterEmoji(fgpw_enter_password_again_edittext
				.getText().toString());
		String verification_code = fgpw_verification_code_edittext.getText()
				.toString();
		if (TextUtils.isEmpty(verification_code)) {
			ToastUtils.makeText(context, "��������֤��", 0).show();
			return;
		}
		if (verification_code.length() < 4) {
			ToastUtils.makeText(context, "������4λ��Ч��֤��", 0).show();
			return;
		}
		if (new_pwd.length() < 6) {
			ToastUtils.makeText(context, "����������������\n����������", 0).show();
			return;
		}
		if (!new_pwd.equals(again_pwd)) {
			ToastUtils.makeText(context, "��������������벻һ������������", 0).show();
			return;
		} else {
			ForgotPasswordRequest forgotpassRequest = new ForgotPasswordRequest();
			Parameter parameter = forgotpassRequest.p;
			parameter.code = fgpw_verification_code_edittext.getText()
					.toString();
			parameter.mobile = fgpw_phone_number_edittext.getText().toString();
			parameter.newPwd = new_pwd;
			Gson gson = new Gson();
			String requestJson = gson.toJson(forgotpassRequest);
			HttpConnectTool.update(requestJson, context, new ConnectListener() {

				@Override
				public void onConnectSuccess(String result) {
					if (!TextUtils.isEmpty(result)) {
						ToastUtils.makeText(context, "�����޸ĳɹ�", 0).show();
						intentActivity(context, LoginActivity.class, null);
						finish();
					} else {
						ToastUtils.makeText(context, "���ӷ�������ʱ", 0).show();
					}

				}

				@Override
				public void onConnectStart() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onConnectFailed(String error, String request) {
					ToastUtils.makeText(context, "���ӷ�������ʱ", 0).show();
				}
			});
		}

	}

	/** ��ȡ��֤�뷽�� */
	private void getVolifareCode() {
		phoneNumber = fgpw_phone_number_edittext.getText().toString();
		if (TextUtils.isEmpty(phoneNumber)) {
			ToastUtils.makeText(context, "�������ֻ���", 0).show();
			return;
		}
		if (phoneNumber.length() < 11 || !RegisterActivity.isPhone(phoneNumber)) {
			ToastUtils.makeText(context, "�ֻ�������\n����������", 0).show();
			return;
		} else {
			// ��ȡ��֤������ʵ��
			GetVolifareCodeRequest getVolifareCodeRequest = new GetVolifareCodeRequest();
			getVolifareCodeRequest.p.mobile = phoneNumber;
			getVolifareCodeRequest.p.type = "3";
			Gson gson = new Gson();
			// ��ȡGson������ת��Json�ַ���{"c":"1019","p":{"mobile":"18333785599","type":"3"}}
			String requestJson = gson.toJson(getVolifareCodeRequest);
			// ����Http�������ӹ�����
			HttpConnectTool.update(requestJson, context, new ConnectListener() {
				/** ���������ʳɹ� */
				@Override
				public void onConnectSuccess(String result) {
					// TODO Auto-generated method stub
					if (!TextUtils.isEmpty(result)) {
						// Gson gson = new Gson();
						// ͨ��gesoת��JsonΪ���󣬻��ʵ�����
						// GetVerificationCodeResult jsonobj =
						// gson.fromJson(result,GetVerificationCodeResult.class);
						// Parameter parameter = jsonobj.p;
						if (true) {
							dialog = DialogUtils.showDialog(context,
									"���ֻ���δ��ע���", new int[] { R.string.enter,
											R.string.register_text },
									new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											switch (v.getId()) {
											case R.id.left:
												dialog.dismiss();
												break;
											case R.id.right:
												intentActivity(context,
														RegisterActivity.class,
														null);
												finish();
												break;
											default:
												break;
											}

										}
									}, false);
							TimerCount timerCount = new TimerCount(59000, 9);
							timerCount.start();
							ToastUtils.makeText(context, "��֤���ѷ���", 0).show();
						}
					} else {
						ToastUtils.makeText(context, "��֤�뷢��ʧ��", 0).show();
					}
				}

				// ��ʼ��������
				@Override
				public void onConnectStart() {
					// TODO Auto-generated method stub

				}

				// ���ʳ�ʱ
				@Override
				public void onConnectFailed(String error, String request) {
					// TODO Auto-generated method stub
					ToastUtils.makeText(context, "���ʷ�������ʱ", 0).show();
				}
			});
		}
	}

	/** ��ȡ��֤�밴ť����ʱЧ��,�̳е���ʱ�� */
	class TimerCount extends CountDownTimer {

		public TimerCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			fgpw_getverification_code_btn
					.setBackgroundResource(R.drawable.btn_getcode);
			fgpw_getverification_code_btn.setTextColor(Color
					.parseColor("#e6bab2"));
			fgpw_getverification_code_btn.setEnabled(true);
			fgpw_getverification_code_btn.setText("��ȡ��֤��");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			fgpw_getverification_code_btn
					.setBackgroundResource(R.drawable.btn_reget_code);
			fgpw_getverification_code_btn.setTextColor(Color
					.parseColor("#DBDBDB"));
			fgpw_getverification_code_btn.setEnabled(false);
			fgpw_getverification_code_btn.setText(millisUntilFinished / 1000
					+ "�����»�ȡ");
		}

	}
}