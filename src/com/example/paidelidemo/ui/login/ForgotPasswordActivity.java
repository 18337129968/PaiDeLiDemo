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
 * 忘记密码界面
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

	/** 常规构造方法 */
	public ForgotPasswordActivity() {
		context = ForgotPasswordActivity.this;
	}

	/** 添加头标题 */
	@Override
	public void appHead(View view) {
		btn_right.setVisibility(View.INVISIBLE);
		top_text.setText(R.string.forgot_pwd_text);
		btn_left.setOnClickListener(this);
	}

	/** 把forgot布局加入基础框架布局中 */
	@Override
	public void initReplaceView() {
		// 存储即将退出的Activity
		AppManager.getInstance().addActivity(this);
		FrameLayout base_content = (FrameLayout) findViewById(R.id.layout_frame);
		View view = View.inflate(context, R.layout.activity_forgot_pwd, null);
		base_content.addView(view);
		initLayoutView();
	}

	/** 初始化布局文件 */
	private void initLayoutView() {
		// 获取验证码按钮
		fgpw_getverification_code_btn = (TextView) findViewById(R.id.fgpw_getverification_code_btn);
		// 完成按钮
		fgpw_done_btn = (TextView) findViewById(R.id.fgpw_done_btn);
		// 手机号
		fgpw_phone_number_edittext = (EditText) findViewById(R.id.fgpw_phone_number_edittext);
		// 验证码
		fgpw_verification_code_edittext = (EditText) findViewById(R.id.fgpw_verification_code_edittext);
		// 输入新密码
		fgpw_enter_password_edittext = (EditText) findViewById(R.id.fgpw_enter_password_edittext);
		// 再次输入新密码
		fgpw_enter_password_again_edittext = (EditText) findViewById(R.id.fgpw_enter_password_again_edittext);
		// 清除按钮
		fgpw_clear_up_btn = (TextView) findViewById(R.id.fgpw_clear_up_btn);
		// 监听
		fgpw_getverification_code_btn.setOnClickListener(this);
		fgpw_clear_up_btn.setOnClickListener(this);
		fgpw_done_btn.setOnClickListener(this);
		// 检测工具
		EdittextUtil.edittextForPhone(fgpw_phone_number_edittext);
		EdittextUtil.edittextForPhone(fgpw_verification_code_edittext);
		EdittextUtil.edittextForPhone(fgpw_enter_password_edittext);
		EdittextUtil.edittextForPhone(fgpw_enter_password_again_edittext);
		// 手机编辑框实时监听
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
		case R.id.btn_head_left:// 返回按钮
			finish();
			break;
		case R.id.fgpw_getverification_code_btn:// 获取验证码按钮
			getVolifareCode();
			break;
		case R.id.fgpw_clear_up_btn:// 清除按钮
			fgpw_phone_number_edittext.setText("");
			break;
		case R.id.fgpw_done_btn:// 完成按钮
			initForgotpwd();
			break;
		default:
			break;
		}

	}

	// 判断新密码
	private void initForgotpwd() {
		new_pwd = EmojiUtils.filterEmoji(fgpw_enter_password_edittext.getText()
				.toString());
		again_pwd = EmojiUtils.filterEmoji(fgpw_enter_password_again_edittext
				.getText().toString());
		String verification_code = fgpw_verification_code_edittext.getText()
				.toString();
		if (TextUtils.isEmpty(verification_code)) {
			ToastUtils.makeText(context, "请输入验证码", 0).show();
			return;
		}
		if (verification_code.length() < 4) {
			ToastUtils.makeText(context, "请输入4位有效验证码", 0).show();
			return;
		}
		if (new_pwd.length() < 6) {
			ToastUtils.makeText(context, "您输入的新密码过短\n请重新输入", 0).show();
			return;
		}
		if (!new_pwd.equals(again_pwd)) {
			ToastUtils.makeText(context, "您输入的两次密码不一致请重新输入", 0).show();
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
						ToastUtils.makeText(context, "密码修改成功", 0).show();
						intentActivity(context, LoginActivity.class, null);
						finish();
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
					ToastUtils.makeText(context, "连接服务器超时", 0).show();
				}
			});
		}

	}

	/** 获取验证码方法 */
	private void getVolifareCode() {
		phoneNumber = fgpw_phone_number_edittext.getText().toString();
		if (TextUtils.isEmpty(phoneNumber)) {
			ToastUtils.makeText(context, "请输入手机号", 0).show();
			return;
		}
		if (phoneNumber.length() < 11 || !RegisterActivity.isPhone(phoneNumber)) {
			ToastUtils.makeText(context, "手机号有误\n请重新输入", 0).show();
			return;
		} else {
			// 获取验证码请求实体
			GetVolifareCodeRequest getVolifareCodeRequest = new GetVolifareCodeRequest();
			getVolifareCodeRequest.p.mobile = phoneNumber;
			getVolifareCodeRequest.p.type = "3";
			Gson gson = new Gson();
			// 获取Gson将对象转换Json字符串{"c":"1019","p":{"mobile":"18333785599","type":"3"}}
			String requestJson = gson.toJson(getVolifareCodeRequest);
			// 调用Http访问链接工具类
			HttpConnectTool.update(requestJson, context, new ConnectListener() {
				/** 服务器访问成功 */
				@Override
				public void onConnectSuccess(String result) {
					// TODO Auto-generated method stub
					if (!TextUtils.isEmpty(result)) {
						// Gson gson = new Gson();
						// 通过geso转换Json为对象，获得实体对象
						// GetVerificationCodeResult jsonobj =
						// gson.fromJson(result,GetVerificationCodeResult.class);
						// Parameter parameter = jsonobj.p;
						if (true) {
							dialog = DialogUtils.showDialog(context,
									"该手机号未被注册过", new int[] { R.string.enter,
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
							ToastUtils.makeText(context, "验证码已发送", 0).show();
						}
					} else {
						ToastUtils.makeText(context, "验证码发送失败", 0).show();
					}
				}

				// 开始访问链接
				@Override
				public void onConnectStart() {
					// TODO Auto-generated method stub

				}

				// 访问超时
				@Override
				public void onConnectFailed(String error, String request) {
					// TODO Auto-generated method stub
					ToastUtils.makeText(context, "访问服务器超时", 0).show();
				}
			});
		}
	}

	/** 获取验证码按钮倒计时效果,继承倒计时类 */
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
			fgpw_getverification_code_btn.setText("获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			fgpw_getverification_code_btn
					.setBackgroundResource(R.drawable.btn_reget_code);
			fgpw_getverification_code_btn.setTextColor(Color
					.parseColor("#DBDBDB"));
			fgpw_getverification_code_btn.setEnabled(false);
			fgpw_getverification_code_btn.setText(millisUntilFinished / 1000
					+ "秒重新获取");
		}

	}
}