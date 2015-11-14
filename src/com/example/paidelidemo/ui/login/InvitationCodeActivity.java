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
 * 输入邀请码界面
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

	/** 常规构造方法 */
	public InvitationCodeActivity() {
		context = InvitationCodeActivity.this;
	}

	/** 添加头标题 */
	@Override
	public void appHead(View view) {
		top_text.setText(R.string.register_text);
		btn_right.setVisibility(View.INVISIBLE);
		btn_left.setOnClickListener(this);
	}

	/** 把invitation_code布局加入基础框架布局中 */
	@Override
	public void initReplaceView() {
		// 存储即将退出的Activity
		AppManager.getInstance().addActivity(this);
		FrameLayout base_content = (FrameLayout) findViewById(R.id.layout_frame);
		View view = View.inflate(context, R.layout.activity_invitation_code,
				null);
		base_content.addView(view);
		initLayoutView();
	}

	/** 初始化布局文件 */
	private void initLayoutView() {
		// 跳过邀请码并完成注册
		invitation_skip_text = findViewById(R.id.invitation_skip_text);
		// 完成注册
		register_done_btn = (TextView) findViewById(R.id.register_done_btn);
		// 邀请码输入框
		invitation_code_edittext = (EditText) findViewById(R.id.invitation_code_edittext);
		// 设置监听
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
			ToastUtils.makeText(context, "注册成功", 0).show();
			intentActivity(context, LoginActivity.class, null);
			finish();
			break;
		case R.id.register_done_btn:
			String invitation_code = invitation_code_edittext.getText()
					.toString();
			if (TextUtils.isEmpty(invitation_code)) {
				ToastUtils.makeText(context, "请输入邀请码", 0).show();
			} else {
				Gson gson = new Gson();
				String userJson = FileUtil.readFile(context,
						Constant.USER_INFO_FILE_NAME);
				if (TextUtils.isEmpty(userJson)) {
					ToastUtils.makeText(context, "用户信息文件不存在", 0).show();
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
									ToastUtils.makeText(context, "连接服务器超时", 0)
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
								// 访问超时
								ToastUtils.makeText(context, "访问服务器超时", 0)
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
