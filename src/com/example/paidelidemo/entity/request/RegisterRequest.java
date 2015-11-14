/**
 * 
 */
package com.example.paidelidemo.entity.request;

import com.example.paidelidemo.contants.Constant;

/**
 * 用户注册请求实体类
 * 
 * @author xiehaifeng
 */
public class RegisterRequest {
	public String c = Constant.USER_REGISTER_CODE;
	public Parameter p;

	public RegisterRequest() {
		this.p = new Parameter();
	}

	public class Parameter {
		public String code;
		public String mobile;
		public String pwd;
	}
}
