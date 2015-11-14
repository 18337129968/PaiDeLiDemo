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
 * 用户注册界面
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
				ToastUtils.makeText(context, "验证码已发送", 0).show();
				break;
			case CODE_ERROR:
				ToastUtils.makeText(context, "验证码错误", 0).show();
				break;
			case CODE_LOSE:
				ToastUtils.makeText(context, "无效的验证码", 0).show();
				break;
			case UPLOADING_FAIL:
				ToastUtils.makeText(context, "访问服务器超时", 0).show();
				break;
			case REGISTER_SUCCESS:
				intentActivity(context, InvitationCodeActivity.class, null);
				finish();
				break;
			case REGISTER_FAIL:
				ToastUtils.makeText(context, "注册失败！", 0).show();
				break;
			case PHONE_NUMBER_ERROR:
				ToastUtils.makeText(context, "手机号有误\n请重新输入", 0).show();
				break;
			case PHONE_NUMBER_NULL:
				ToastUtils.makeText(context, "请输入手机号", 0).show();
				break;
			case VERIFICATION_CODE_NULL:
				ToastUtils.makeText(context, "请输入验证码", 0).show();
				break;
			case PASSWORD_ERROR:
				ToastUtils.makeText(context, "密码格式不正确，请重新输入", 0).show();
				break;
			case PASSWORD_AGAIN_ERROR:
				ToastUtils.makeText(context, "再次输入密码不正确，请重新输入", 0).show();
				break;
			case PASSWORD_NULL:
				ToastUtils.makeText(context, "请输入密码", 0).show();
				break;
			case PASSWORD_AGAIN_NULL:
				ToastUtils.makeText(context, "请再次输入密码", 0).show();
				break;
			case PASSWORD_NOT_SAME:
				ToastUtils.makeText(context, "您输入的两次密码不一致，请重新输入", 0).show();

				break;
			case PASSWORD_SHORT:
				ToastUtils
						.makeText(RegisterActivity.this, "您输入的密码过短\n请重新输入", 0)
						.show();
				break;
			default:
				break;
			}
		}
	};

	/** 构造方法 */
	public RegisterActivity() {
		context = RegisterActivity.this;
	}

	/** 添加头标题 */
	@Override
	public void appHead(View view) {
		btn_right.setVisibility(View.GONE);
		top_text.setText(R.string.register_text);
		btn_left.setOnClickListener(this);
	}

	/** 把register布局加入基础框架布局中 */
	@Override
	public void initReplaceView() {
		// 存储即将退出的Activity
		AppManager.getInstance().addActivity(this);
		FrameLayout base_content = (FrameLayout) findViewById(R.id.layout_frame);
		View view = View.inflate(context, R.layout.activity_register, null);
		base_content.addView(view);
		initLayoutView();
	}

	/** 初始化布局文件 */
	private void initLayoutView() {
		// 下一步按钮
		register_nextstep_btn = (TextView) findViewById(R.id.register_nextstep_btn);
		// 获取验证码
		register_getverification_code_btn = (TextView) findViewById(R.id.register_getverification_code_btn);
		// 用户协议
		user_agreement_text = (TextView) findViewById(R.id.user_agreement_text);
		// 用户名
		register_phone_number_edittext = (EditText) findViewById(R.id.register_phone_number_edittext);
		// 验证码
		register_verification_code_edittext = (EditText) findViewById(R.id.register_verification_code_edittext);
		// 输入密码
		register_enter_password_edittext = (EditText) findViewById(R.id.register_enter_password_edittext);
		// 再次输入密码
		register_enter_password_again_edittext = (EditText) findViewById(R.id.register_enter_password_again_edittext);
		// 清除用户
		register_clear_up_btn = (TextView) findViewById(R.id.register_clear_up_btn);

		user_agreement_text.setOnClickListener(this);
		register_getverification_code_btn.setOnClickListener(this);
		register_nextstep_btn.setOnClickListener(this);
		register_clear_up_btn.setOnClickListener(this);

		EdittextUtil.edittextForPhone(register_phone_number_edittext);
		EdittextUtil.edittextForPhone(register_verification_code_edittext);
		EdittextUtil.edittextForPhone(register_enter_password_edittext);
		EdittextUtil.edittextForPhone(register_enter_password_again_edittext);
		// 实时监测手机号编辑框
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
		case R.id.btn_head_left:// 返回
			this.finish();
			break;
		case R.id.user_agreement_text:// 用户注册协议

			break;
		case R.id.register_clear_up_btn:// 清除用户名
			register_phone_number_edittext.setText("");
			break;
		case R.id.register_getverification_code_btn:// 获取验证码
			getVolifareCode();
			break;
		case R.id.register_nextstep_btn:// 下一步按钮
			initRegister();
			break;
		default:
			break;
		}

	}

	/** 判断注册信息 */
	private void initRegister() {
		msg = Message.obtain();
		String verification_code = register_verification_code_edittext
				.getText().toString();
		// 去除表情
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
			ToastUtils.makeText(context, "请输入4位有效验证码", 0).show();
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
			// 获取注册用户请求实体
			RegisterRequest registerRequest = new RegisterRequest();
			Parameter parameter = registerRequest.p;
			parameter.code = register_verification_code_edittext.getText()
					.toString();
			parameter.mobile = phoneNumber;
			parameter.pwd = password;
			// 实体Json转换
			Gson gson = new Gson();
			String requestJson = gson.toJson(registerRequest);
			// http工具类Json请求返回Json解析
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
						ToastUtils.makeText(context, "连接服务器超时", 0).show();
					}

				}

				@Override
				public void onConnectStart() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onConnectFailed(String error, String request) {
					// 访问超时
					ToastUtils.makeText(context, "访问服务器超时", 0).show();
				}
			});

		}
	}

	/** 获取验证码方法 */
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
			// 获取验证码请求实体
			GetVolifareCodeRequest getVolifareCodeRequest = new GetVolifareCodeRequest();
			getVolifareCodeRequest.p.mobile = phoneNumber;
			getVolifareCodeRequest.p.type = "1";
			Gson gson = new Gson();
			// 通过gson把实体对象转换成Json字符串{"c":"1019","p":{"mobile":"18337129968","type":"1"}}
			String requestJson = gson.toJson(getVolifareCodeRequest);

			// 调用Http访问链接工具类
			HttpConnectTool.update(requestJson, context, new ConnectListener() {
				/** 服务器访问成功 */
				@Override
				public void onConnectSuccess(String result) {
					if (!TextUtils.isEmpty(result)) {
						// Gson gson = new Gson();
						// 通过geso转换Json为对象，获得实体对象
						// GetVerificationCodeResult jsonobj =
						// gson.fromJson(result,GetVerificationCodeResult.class);
						// Parameter parameter = jsonobj.p;
						// switch (parameter.code) {}
						TimerCount timerCount = new TimerCount(59000, 9);
						timerCount.start();
						ToastUtils.makeText(context, "验证码已发送", 0).show();
					} else {
						ToastUtils.makeText(context, "验证码发送失败", 0).show();
					}

				}

				@Override
				public void onConnectStart() {
					// 开始访问链接
				}

				@Override
				public void onConnectFailed(String error, String request) {
					// 访问超时
					ToastUtils.makeText(context, "访问服务器超时", 0).show();
				}
			});

		}

	}

	/** 判断是否是手机号码格式 */
	public static boolean isPhone(String phoneNumber) {
		// 匹配正则表达式，从1开始
		Pattern pattern = Pattern.compile("^1[3578]\\d{9}$");
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	/** 获取验证码按钮倒计时效果,继承倒计时类 */
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
					/ 1000 + "秒重新获取");
		}

		@Override
		public void onFinish() {
			register_getverification_code_btn
					.setBackgroundResource(R.drawable.btn_getcode);
			register_getverification_code_btn.setTextColor(Color
					.parseColor("#e6bab2"));
			register_getverification_code_btn.setEnabled(true);
			register_getverification_code_btn.setText("获取验证码");
		}

	}
}
