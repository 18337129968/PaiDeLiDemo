package com.example.paidelidemo.entity.request;

import com.example.paidelidemo.contants.Constant;

/**
 * 获取验证码请求
 * 
 * @author xiehaifeng
 * 
 */
public class GetVolifareCodeRequest {
	public String c = Constant.GET_VERIFICATION_CODE;
	public Parameter p;

	/** 构造方法 */
	public GetVolifareCodeRequest() {
		this.p = new Parameter();
	}

	public class Parameter {
		public String mobile;
		public String type;
	}
}
