package com.example.paidelidemo.entity.request;

import com.example.paidelidemo.contants.Constant;

/**
 * 用户登录请求
 * 
 * @author xiehaifeng
 */
public class LoginUser {
	public String c = Constant.USER_LOGIN_CODE;
	public Parameter p;

	/** 常规构造方法 */
	public LoginUser() {
		p = new Parameter();
	}

	public class Parameter {
		public String pwd;
		public String mobile;
	}
}
