/**
 * 
 */
package com.example.paidelidemo.entity.request;

import com.example.paidelidemo.contants.Constant;

/**
 * �û�ע������ʵ����
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
