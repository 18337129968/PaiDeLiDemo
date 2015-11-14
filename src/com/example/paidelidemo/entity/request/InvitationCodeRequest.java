package com.example.paidelidemo.entity.request;

import com.example.paidelidemo.contants.Constant;

/**
 *  ‰»Î—˚«Î¬Î«Î«Û
 * 
 * @author xiehaifeng
 * 
 */
public class InvitationCodeRequest {
	public String c = Constant.INVITATION_CODE_REQUEST;
	public Parameter p;

	public InvitationCodeRequest() {
		this.p = new Parameter();
	}

	public class Parameter {
		public String inviteCode;
		public String userId;
	}
}
