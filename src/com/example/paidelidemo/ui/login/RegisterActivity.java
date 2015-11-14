package com.example.paidelidemo.ui.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.entity.request.GetVolifareCodeRequest;
import com.example.paidelidemo.entity.request.RegisterRequest;
import com.example.paidelidemo.entity.request.RegisterRequest.Parameter;
import com.example.paidelidemo.utils.AppManager;
import com.example.paidelidemo.utils.EdittextUtil;
import com.example.paidelidemo.utils.EmojiUtils;
import com.example.paidelidemo.utils.HttpConnectTool;
import com.example.paidelidemo.utils.HttpConnectTool.ConnectListener;
import com.example.paidelidemo.utils.ToastUtils;
import com.google.gson.Gson;

/**
 * �û�ע�����
 * 
 * @author xiehaifeng
 */
public class RegisterActivity extends HeadBaseActivity {
	private Context context;
	private TextView register_nextstep_btn, register_getverification_code_btn,
			user_agreement_text, register_clear_up_btn;
	private EditText register_phone_number_edittext,
			register_verification_code_edittext,
			register_enter_password_edittext,
			register_enter_password_again_edittext;
	private Message msg;
	private String phoneNumber, password, again_Password;
	private static final int REGISTER_SUCCESS = 0;
	private static final int REGISTER_FAIL = 1;
	private static final int UPLOADING_FAIL = 2;
	private static final int PHONE_NUMBER_ERROR = 3;
	private static final int PASSWORD_ERROR = 4;
	private static final int PASSWORD_AGAIN_ERROR = 5;
	private static final int PHONE_NUMBER_NULL = 6;
	private static final int VERIFICATION_CODE_NULL = 7;
	private static final int PASSWORD_NULL = 8;
	private static final int PASSWORD_AGAIN_NULL = 9;
	private static final int PASSWORD_NOT_SAME = 10;
	private static final int PASSWORD_SHORT = 11;
	private static final int CODE_ERROR = 12;
	private static final int CODE_LOSE = 13;
	private static final int CODE_OK = 14;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CODE_OK:
				ToastUtils.makeText(context, "��֤���ѷ���", 0).show();
				break;
			case CODE_ERROR:
				ToastUtils.makeText(context, "��֤�����", 0).show();
				break;
			case CODE_LOSE:
				ToastUtils.makeText(context, "��Ч����֤��", 0).show();
				break;
			case UPLOADING_FAIL:
				ToastUtils.makeText(context, "���ʷ�������ʱ", 0).show();
				break;
			case REGISTER_SUCCESS:
				intentActivity(context, InvitationCodeActivity.class, null);
				finish();
				break;
			case REGISTER_FAIL:
				ToastUtils.makeText(context, "ע��ʧ�ܣ�", 0).show();
				break;
			case PHONE_NUMBER_ERROR:
				ToastUtils.makeText(context, "�ֻ�������\n����������", 0).show();
				break;
			case PHONE_NUMBER_NULL:
				ToastUtils.makeText(context, "�������ֻ���", 0).show();
				break;
			case VERIFICATION_CODE_NULL:
				ToastUtils.makeText(context, "��������֤��", 0).show();
				break;
			case PASSWORD_ERROR:
				ToastUtils.makeText(context, "�����ʽ����ȷ������������", 0).show();
				break;
			case PASSWORD_AGAIN_ERROR:
				ToastUtils.makeText(context, "�ٴ��������벻��ȷ������������", 0).show();
				break;
			case PASSWORD_NULL:
				ToastUtils.makeText(context, "����������", 0).show();
				break;
			case PASSWORD_AGAIN_NULL:
				ToastUtils.makeText(context, "���ٴ���������", 0).show();
				break;
			case PASSWORD_NOT_SAME:
				ToastUtils.makeText(context, "��������������벻һ�£�����������", 0).show();

				break;
			case PASSWORD_SHORT:
				ToastUtils
						.makeText(RegisterActivity.this, "��������������\n����������", 0)
						.show();
				break;
			default:
				break;
			}
		}
	};

	/** ���췽�� */
	public RegisterActivity() {
		context = RegisterActivity.this;
	}

	/** ���ͷ���� */
	@Override
	public void appHead(View view) {
		btn_right.setVisibility(View.GONE);
		top_text.setText(R.string.register_text);
		btn_left.setOnClickListener(this);
	}

	/** ��register���ּ��������ܲ����� */
	@Override
	public void initReplaceView() {
		// �洢�����˳���Activity
		AppManager.getInstance().addActivity(this);
		FrameLayout base_content = (FrameLayout) findViewById(R.id.layout_frame);
		View view = View.inflate(context, R.layout.activity_register, null);
		base_content.addView(view);
		initLayoutView();
	}

	/** ��ʼ�������ļ� */
	private void initLayoutView() {
		// ��һ����ť
		register_nextstep_btn = (TextView) findViewById(R.id.register_nextstep_btn);
		// ��ȡ��֤��
		register_getverification_code_btn = (TextView) findViewById(R.id.register_getverification_code_btn);
		// �û�Э��
		user_agreement_text = (TextView) findViewById(R.id.user_agreement_text);
		// �û���
		register_phone_number_edittext = (EditText) findViewById(R.id.register_phone_number_edittext);
		// ��֤��
		register_verification_code_edittext = (EditText) findViewById(R.id.register_verification_code_edittext);
		// ��������
		register_enter_password_edittext = (EditText) findViewById(R.id.register_enter_password_edittext);
		// �ٴ���������
		register_enter_password_again_edittext = (EditText) findViewById(R.id.register_enter_password_again_edittext);
		// ����û�
		register_clear_up_btn = (TextView) findViewById(R.id.register_clear_up_btn);

		user_agreement_text.setOnClickListener(this);
		register_getverification_code_btn.setOnClickListener(this);
		register_nextstep_btn.setOnClickListener(this);
		register_clear_up_btn.setOnClickListener(this);

		EdittextUtil.edittextForPhone(register_phone_number_edittext);
		EdittextUtil.edittextForPhone(register_verification_code_edittext);
		EdittextUtil.edittextForPhone(register_enter_password_edittext);
		EdittextUtil.edittextForPhone(register_enter_password_again_edittext);
		// ʵʱ����ֻ��ű༭��
		register_phone_number_edittext
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

						if (TextUtils.isEmpty(s)) {
							register_clear_up_btn.setVisibility(View.INVISIBLE);
							register_clear_up_btn.setClickable(false);
						} else {
							register_clear_up_btn.setVisibility(View.VISIBLE);
							register_clear_up_btn.setClickable(true);
						}

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_head_left:// ����
			this.finish();
			break;
		case R.id.user_agreement_text:// �û�ע��Э��

			break;
		case R.id.register_clear_up_btn:// ����û���
			register_phone_number_edittext.setText("");
			break;
		case R.id.register_getverification_code_btn:// ��ȡ��֤��
			getVolifareCode();
			break;
		case R.id.register_nextstep_btn:// ��һ����ť
			initRegister();
			break;
		default:
			break;
		}

	}

	/** �ж�ע����Ϣ */
	private void initRegister() {
		msg = Message.obtain();
		String verification_code = register_verification_code_edittext
				.getText().toString();
		// ȥ������
		password = EmojiUtils.filterEmoji(register_enter_password_edittext
				.getText().toString());
		again_Password = EmojiUtils
				.filterEmoji(register_enter_password_again_edittext.getText()
						.toString());
		if (TextUtils.isEmpty(verification_code)) {
			msg.what = VERIFICATION_CODE_NULL;
			handler.sendMessage(msg);
			return;
		}
		if (verification_code.length() < 4) {
			ToastUtils.makeText(context, "������4λ��Ч��֤��", 0).show();
			return;
		}
		if (password.length() < 6) {
			msg.what = PASSWORD_SHORT;
			handler.sendMessage(msg);
			return;
		}
		if (TextUtils.isEmpty(again_Password)) {
			msg.what = PASSWORD_AGAIN_NULL;
			handler.sendMessage(msg);
			return;
		}
		if (!password.equals(again_Password)) {
			msg.what = PASSWORD_NOT_SAME;
			handler.sendMessage(msg);
			return;
		} else {
			// ��ȡע���û�����ʵ��
			RegisterRequest registerRequest = new RegisterRequest();
			Parameter parameter = registerRequest.p;
			parameter.code = register_verification_code_edittext.getText()
					.toString();
			parameter.mobile = phoneNumber;
			parameter.pwd = password;
			// ʵ��Jsonת��
			Gson gson = new Gson();
			String requestJson = gson.toJson(registerRequest);
			// http������Json���󷵻�Json����
			HttpConnectTool.update(requestJson, context, new ConnectListener() {

				@Override
				public void onConnectSuccess(String result) {
					if (!TextUtils.isEmpty(result)) {
						/*
						 * Gson gson = new Gson(); RegisterResult jsonObject =
						 * gson.fromJson( result, RegisterResult.class);
						 * com.jishijiyu
						 * .takeadvantage.entity.result.RegisterResult.Parameter
						 * parameter2 = jsonObject.p; switch (parameter2.type)
						 * {}
						 */
						msg.what = REGISTER_SUCCESS;
						handler.sendMessage(msg);
						return;
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
					// ���ʳ�ʱ
					ToastUtils.makeText(context, "���ʷ�������ʱ", 0).show();
				}
			});

		}
	}

	/** ��ȡ��֤�뷽�� */
	private void getVolifareCode() {
		msg = Message.obtain();
		phoneNumber = register_phone_number_edittext.getText().toString();
		if (TextUtils.isEmpty(phoneNumber)) {
			msg.what = PHONE_NUMBER_NULL;
			handler.sendMessage(msg);
			return;
		}
		if (phoneNumber.length() < 11 || !isPhone(phoneNumber)) {
			msg.what = PHONE_NUMBER_ERROR;
			handler.sendMessage(msg);
			return;
		} else {
			// ��ȡ��֤������ʵ��
			GetVolifareCodeRequest getVolifareCodeRequest = new GetVolifareCodeRequest();
			getVolifareCodeRequest.p.mobile = phoneNumber;
			getVolifareCodeRequest.p.type = "1";
			Gson gson = new Gson();
			// ͨ��gson��ʵ�����ת����Json�ַ���{"c":"1019","p":{"mobile":"18337129968","type":"1"}}
			String requestJson = gson.toJson(getVolifareCodeRequest);

			// ����Http�������ӹ�����
			HttpConnectTool.update(requestJson, context, new ConnectListener() {
				/** ���������ʳɹ� */
				@Override
				public void onConnectSuccess(String result) {
					if (!TextUtils.isEmpty(result)) {
						// Gson gson = new Gson();
						// ͨ��gesoת��JsonΪ���󣬻��ʵ�����
						// GetVerificationCodeResult jsonobj =
						// gson.fromJson(result,GetVerificationCodeResult.class);
						// Parameter parameter = jsonobj.p;
						// switch (parameter.code) {}
						TimerCount timerCount = new TimerCount(59000, 9);
						timerCount.start();
						ToastUtils.makeText(context, "��֤���ѷ���", 0).show();
					} else {
						ToastUtils.makeText(context, "��֤�뷢��ʧ��", 0).show();
					}

				}

				@Override
				public void onConnectStart() {
					// ��ʼ��������
				}

				@Override
				public void onConnectFailed(String error, String request) {
					// ���ʳ�ʱ
					ToastUtils.makeText(context, "���ʷ�������ʱ", 0).show();
				}
			});

		}

	}

	/** �ж��Ƿ����ֻ������ʽ */
	public static boolean isPhone(String phoneNumber) {
		// ƥ��������ʽ����1��ʼ
		Pattern pattern = Pattern.compile("^1[3578]\\d{9}$");
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	/** ��ȡ��֤�밴ť����ʱЧ��,�̳е���ʱ�� */
	class TimerCount extends CountDownTimer {

		public TimerCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			register_getverification_code_btn
					.setBackgroundResource(R.drawable.btn_reget_code);
			register_getverification_code_btn.setTextColor(Color
					.parseColor("#DBDBDB"));
			register_getverification_code_btn.setEnabled(false);
			register_getverification_code_btn.setText(millisUntilFinished
					/ 1000 + "�����»�ȡ");
		}

		@Override
		public void onFinish() {
			register_getverification_code_btn
					.setBackgroundResource(R.drawable.btn_getcode);
			register_getverification_code_btn.setTextColor(Color
					.parseColor("#e6bab2"));
			register_getverification_code_btn.setEnabled(true);
			register_getverification_code_btn.setText("��ȡ��֤��");
		}

	}
}
