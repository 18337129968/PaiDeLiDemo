package com.example.paidelidemo.ui.login;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paidelidemo.R;
import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.entity.request.LoginUser;
import com.example.paidelidemo.entity.request.LoginUser.Parameter;
import com.example.paidelidemo.ui.home.HomeActivity;
import com.example.paidelidemo.utils.APKUpData;
import com.example.paidelidemo.utils.AppManager;
import com.example.paidelidemo.utils.EdittextUtil;
import com.example.paidelidemo.utils.EmojiUtils;
import com.example.paidelidemo.utils.HttpConnectTool;
import com.example.paidelidemo.utils.HttpConnectTool.ConnectListener;
import com.example.paidelidemo.utils.SPUtils;
import com.example.paidelidemo.utils.ToastUtils;
import com.example.paidelidemo.utils.view.CircularImage;
import com.google.gson.Gson;

/**
 * ��¼����
 * 
 * @author xiehaifeng
 */
public class LoginActivity extends HeadBaseActivity
{
	private Context context;
	private CircularImage cover_user_photo;;
	private TextView forgot_password_text, register_text, login_btn;
	private EditText edittext_phone_number, edittext_password;
	private ImageView remember1, autoLogin;
	private Bitmap bitmap;
	private SharedPreferences sp_name, sp_pwd;
	private String pwd, name;
	private String isMemory, isAutoLogin;
	boolean flag = false, flag2 = false;
	private String FILE = "saveUserPwd";
	private Message msg;
	private static final int CHECK_SUCCESS = 0;
	private static final int CHECK_FAIL = 1;
	private static final int PHONE_NUMBER_ERROR = 2;
	private static final int PHONE_NUMBER_NULL = 3;
	private static final int PASSWORD_NULL = 4;
	private static final int CONNECT_FAIL = 6;
	public static File file;
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case CHECK_SUCCESS:
				ToastUtils.makeText(context, "��¼�ɹ���", 0).show();
				remember();
				autoLogin();
				if (sp_name == null)
				{
					sp_name = getSharedPreferences("UserPhone",
							Context.MODE_PRIVATE);
				}
				Editor ed = sp_name.edit();
				ed.putString("phoneNumber", name);
				ed.commit();
				intentActivity(context, HomeActivity.class, null);
				finish();
				break;
			case CHECK_FAIL:
				ToastUtils.makeText(LoginActivity.this, "�û�������������", 0).show();
				break;
			case PHONE_NUMBER_ERROR:
				ToastUtils.makeText(LoginActivity.this, "��������ȷ�ֻ�����", 0).show();
				break;
			case PHONE_NUMBER_NULL:
				ToastUtils.makeText(LoginActivity.this, "�������ֻ���", 0).show();
				break;
			case PASSWORD_NULL:
				ToastUtils.makeText(LoginActivity.this, "����������", 0).show();
				break;
			case CONNECT_FAIL:
				ToastUtils.makeText(LoginActivity.this, "���ʷ�����ʧ��", 0).show();
				break;
			default:
				break;
			}
		};
	};

	public LoginActivity()
	{
		context = LoginActivity.this;
	}

	/** ���ͷ���� */
	@Override
	public void appHead(View view)
	{
		top_text.setText(R.string.user_login_title);
		btn_left.setVisibility(View.INVISIBLE);
		btn_right.setVisibility(View.INVISIBLE);
	}

	/** ��login���ּ��������ܲ����� */
	@Override
	public void initReplaceView()
	{
		// �洢�����˳���Activity
		AppManager.getInstance().addActivity(this);
		// ��ȡbase�����ļ��еĲ���
		FrameLayout base_frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
		// ʹ��login�����ļ���װview����
		View view = View.inflate(context, R.layout.activity_login, null);
		// �������view
		base_frameLayout.addView(view);
		initLayoutView();
	}

	/** ��ʼ����½view */
	private void initLayoutView()
	{
		// ��������
		forgot_password_text = (TextView) findViewById(R.id.forgot_password_text);
		// ע��
		register_text = (TextView) findViewById(R.id.register_text);
		// �û���
		edittext_phone_number = (EditText) findViewById(R.id.edittext_phone_number);
		// ����
		edittext_password = (EditText) findViewById(R.id.edittext_password);
		// ��ס����
		remember1 = (ImageView) findViewById(R.id.remember_pwd_image);
		// �Զ���¼
		autoLogin = (ImageView) findViewById(R.id.auto_login_image);
		// ��¼
		login_btn = (TextView) findViewById(R.id.login_btn);
		// �û�ͼ��
		cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
		/* ���ü��� */
		register_text.setOnClickListener(this);
		remember1.setOnClickListener(this);
		autoLogin.setOnClickListener(this);
		login_btn.setOnClickListener(this);
		forgot_password_text.setOnClickListener(this);
		EdittextUtil.edittextForPhone(edittext_phone_number);
		setLoginView();
	}

	/** ����view��ͼ���� */
	private void setLoginView()
	{
		new APKUpData(context).checkUpData(false);
		// ��ȡfiles�ľ���·��
		file = getFilesDir();
		// LogUtils.i("LoginAvtivity().setLoginView().file=" + file);
		bitmap = BitmapFactory.decodeFile(file + Constant.HEAD_PIC_FILE_NAME);
		if (bitmap == null)
		{
			// mapΪ������Ĭ��ͼ��
			bitmap = BitmapFactory
					.decodeResource(getResources(), R.drawable.pr);
			// ��image����mapͼ��
			cover_user_photo.setImageBitmap(bitmap);
		} else
		{
			cover_user_photo.setImageBitmap(bitmap);
		}

		// xml�洢�ļ��ļ�����UserPhone����ȡ��phoneNumber��ֵ
		sp_name = getSharedPreferences("UserPhone", Context.MODE_PRIVATE);
		name = sp_name.getString("phoneNumber", "");
		edittext_phone_number.setText(name);
		// xml�洢�ļ��ļ�����saveUserPwd����ȡ��isMemory��ֵ,û�еĻ�ΪNo
		sp_pwd = this.getSharedPreferences("saveUserPwd", Context.MODE_PRIVATE);
		isMemory = sp_pwd.getString("isMemory", "No");
		if (isMemory.equals("Yes"))
		{
			pwd = sp_pwd.getString("PassWord", "");
			edittext_password.setText(pwd);
			remember1.setBackgroundResource(R.drawable.remember_pwd);
			// ��ʶ
			flag = true;
		} else
		{
			remember1.setBackgroundResource(R.drawable.no_remember_pwd);
		}

		isAutoLogin = sp_pwd.getString("isAutoLogin", "No");
		if (isAutoLogin.equals("Yes"))
		{
			pwd = sp_pwd.getString("PassWord", "");
			edittext_password.setText(pwd);
			autoLogin.setBackgroundResource(R.drawable.remember_pwd);
			// ��ʶ
			flag2 = true;
		} else
		{
			autoLogin.setBackgroundResource(R.drawable.no_remember_pwd);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.register_text:// ע��
			intentActivity(context, RegisterActivity.class, null);
			break;
		case R.id.login_btn:// ��¼

			msg = Message.obtain();
			msg.what = CHECK_SUCCESS;
			handler.sendMessage(msg);

			// checkUserInfo();

			break;
		case R.id.remember_pwd_image:// ��ס����
			flag = changeBackground(remember1, flag);
			if (flag2)
			{
				flag2 = changeBackground(autoLogin, flag2);
			}
			break;
		case R.id.auto_login_image:// �Զ���¼
			flag2 = changeBackground(autoLogin, flag2);
			if (!flag)
			{
				flag = changeBackground(remember1, flag);
			}
			break;
		case R.id.forgot_password_text:// ��������
			intentActivity(context, ForgotPasswordActivity.class, null);
			break;
		default:
			break;
		}
	}

	/** ��ס���� */
	private void remember()
	{
		if (flag)
		{
			if (sp_pwd == null)
			{
				sp_pwd = getSharedPreferences(FILE, MODE_PRIVATE);
			}
			Editor editor = sp_pwd.edit();
			editor.putString("PassWord", pwd);
			editor.putString("isMemory", "Yes");
			editor.commit();
		} else
		{
			if (sp_pwd == null)
			{
				sp_pwd = getSharedPreferences(FILE, MODE_PRIVATE);
			}
			Editor editor = sp_pwd.edit();
			editor.putString("isMemory", "No");
			editor.commit();
		}

	}

	/** �Զ���¼ */
	private void autoLogin()
	{
		if (flag2)
		{
			if (sp_pwd == null)
			{
				sp_pwd = getSharedPreferences(FILE, MODE_PRIVATE);
			}
			Editor editor = sp_pwd.edit();
			editor.putString("isAutoLogin", "Yes");
			editor.commit();
		} else
		{
			if (sp_pwd == null)
			{
				sp_pwd = getSharedPreferences(FILE, MODE_PRIVATE);
			}
			Editor editor = sp_pwd.edit();
			editor.putString("isAutoLogin", "No");
			editor.commit();
		}
	}

	/**
	 * ��������
	 * 
	 * @return
	 */
	private boolean changeBackground(ImageView imageView, boolean flag)
	{
		if (flag)
		{
			imageView.setBackgroundResource(R.drawable.no_remember_pwd);
			return flag = false;
		} else
		{
			imageView.setBackgroundResource(R.drawable.remember_pwd);
			return flag = true;
		}

	}

	/** ����û���Ϣ */
	private void checkUserInfo()
	{
		// ������Ϣ��
		msg = Message.obtain();
		name = edittext_phone_number.getText().toString();
		pwd = EmojiUtils.filterEmoji(edittext_password.getText().toString());
		// �û�ʵ��
		LoginUser user = new LoginUser();
		Parameter parameter = user.p;
		parameter.mobile = name;
		parameter.pwd = pwd;
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd))
		{
			ToastUtils.makeText(context, "�ֻ��Ż����벻��Ϊ��", 0).show();
			return;
		}
		if (!RegisterActivity.isPhone(name) || name.length() < 11)
		{
			msg.what = PHONE_NUMBER_ERROR;
			handler.sendMessage(msg);
			return;
		}
		if (pwd.length() < 6)
		{
			ToastUtils.makeText(context, "���볤�Ȳ���̫��", 0).show();
			return;
		} else
		{
			login_btn.setEnabled(false);
			Gson gson = new Gson();
			String requestJson = gson.toJson(user);
			// ��ʵ��ת��Json{"c":"1002","p":{"mobile":"13233333333","pwd":"qqqqqq"}}
			
			HttpConnectTool.update(requestJson, context, new ConnectListener()
			{

				@Override
				public void onConnectSuccess(String result)
				{
					if (!TextUtils.isEmpty(result))
					{
						SPUtils.put(context, Constant.USER_INFO_FILE_NAME,
								result);
						msg.what = CHECK_SUCCESS;
						handler.sendMessage(msg);
						login_btn.setEnabled(true);
						return;
					} else
					{
						ToastUtils.makeText(context, "���ӷ�����ʧ��", 0).show();
						login_btn.setEnabled(true);
					}

				}

				@Override
				public void onConnectStart()
				{

				}

				@Override
				public void onConnectFailed(String error, String request)
				{
					if (error != null)
					{
						ToastUtils.makeText(context, error, 0).show();
					}
					login_btn.setEnabled(true);
				}
			});
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			AppManager.getInstance().exit();
		}
		return super.onKeyDown(keyCode, event);
	}

}
