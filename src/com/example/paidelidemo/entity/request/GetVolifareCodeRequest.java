package com.example.paidelidemo.entity.request;

import com.example.paidelidemo.contants.Constant;

/**
 * ��ȡ��֤������
 * 
 * @author xiehaifeng
 * 
 */
public class GetVolifareCodeRequest {
	public String c = Constant.GET_VERIFICATION_CODE;
	public Parameter p;

	/** ���췽�� */
	public GetVolifareCodeRequest() {
		this.p = new Parameter();
	}

	public class Parameter {
		public String mobile;
		public String type;
	}
}
