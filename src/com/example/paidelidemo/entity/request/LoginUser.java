package com.example.paidelidemo.entity.request;

import com.example.paidelidemo.contants.Constant;

/**
 * �û���¼����
 * 
 * @author xiehaifeng
 */
public class LoginUser {
	public String c = Constant.USER_LOGIN_CODE;
	public Parameter p;

	/** ���湹�췽�� */
	public LoginUser() {
		p = new Parameter();
	}

	public class Parameter {
		public String pwd;
		public String mobile;
	}
}
