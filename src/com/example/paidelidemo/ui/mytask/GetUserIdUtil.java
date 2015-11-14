package com.example.paidelidemo.ui.mytask;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;

import com.example.paidelidemo.contants.Constant;
import com.example.paidelidemo.entity.request.LoginUserResult;
import com.example.paidelidemo.entity.request.UserNotice;
import com.example.paidelidemo.utils.SPUtils;
import com.google.gson.Gson;

/**
 * ��ȡUserId
 * 
 * @author tml
 * 
 *         ʧ�ܷ���null ��Ҫ�Լ�����
 */
public class GetUserIdUtil {
	public static String getUserId(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		String userId = result.p.user.id + "13333339968";
		if (TextUtils.isEmpty(userId)) {
			return null;
		}
		return userId;
	}

	// �������
	public static int goldNum(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		int goldNum = result.p.user.goldNum;
		return goldNum;

	}

	// ����������
	public static int inviteUserNum(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		int inviteUserNum = result.p.user.inviteUserNum;
		return inviteUserNum;

	}

	// ��������
	public static int goldLockNum(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		int goldLockNum = result.p.user.goldLockNum;
		return goldLockNum;

	}

	// ��������
	public static int silverLockNum(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		int silverLockNum = result.p.user.silverLockNum;
		return silverLockNum;

	}

	// ͭ������
	public static int copperLockNum(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		int copperLockNum = result.p.user.copperLockNum;
		return copperLockNum;

	}

	// ������
	public static String invitationCode(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		String invitationCode = result.p.user.inviteCode;
		return invitationCode;

	}

	// �̼�״̬
	public static int merchantState(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		int merchantState = result.p.user.type;
		return merchantState;
	}
		// ��ȡ�ı�����
		public static int getSorce(Context mContext) {
			LoginUserResult result = getLogin(mContext);
			int Sorce = result.p.user.score;
			return Sorce;

	}

	/**
	 * @param mContext
	 * @return
	 */
	public static void getEnterForState(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		if (result.p.enroll == null) {
			SPUtils.put(mContext, Constant.GETENTER_FOR_STATE, false);
		} else {
			SPUtils.put(mContext, Constant.GETENTER_FOR_STATE, true);
		}
	}

	public static String getUserMobile(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		String userMobile = result.p.user.mobile + "";
		if (TextUtils.isEmpty(userMobile)) {
			return null;
		}
		return userMobile;
	}

	public static String getErnieId(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		String ernieId = result.p.ernie.id + "";
		if (TextUtils.isEmpty(ernieId)) {
			return null;
		}
		return ernieId;
	}

	/**
	 * ��ȡtokenId
	 * 
	 * @param mContext
	 * @return tokenId
	 */
	public static String getTokenId(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		String tokenId = result.p.tokenId;
		if (TextUtils.isEmpty(tokenId)) {
			return null;
		}
		return tokenId;
	}

	/**
	 * ������Ϣ�б�
	 * 
	 * @param mContext
	 * @param list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void saveList(Context mContext,  ArrayList list) {
		Gson gson = new Gson();
		String string = (String) SPUtils.get(mContext,
				Constant.USER_INFO_FILE_NAME, "");
		LoginUserResult result = gson.fromJson(string, LoginUserResult.class);
		result.p.notReadNoticeList = list;
		string = gson.toJson(result);
		SPUtils.put(mContext, Constant.USER_INFO_FILE_NAME, string);
	}

	/**
	 * ��ȡ��Ϣ�б�
	 * 
	 * @param mContext
	 * @return ��Ϣ�б�
	 */
	public static ArrayList<UserNotice> getList(Context mContext) {
		LoginUserResult result = getLogin(mContext);
		return result.p.notReadNoticeList;
	}

	/**
	 * ��¼��Ϣ
	 * 
	 * @param mContext
	 * @return ��Ϣ�б�
	 */
	public static LoginUserResult getLogin(Context mContext) {
		Gson gson = new Gson();
		String string = (String) SPUtils.get(mContext,
				Constant.USER_INFO_FILE_NAME, "");
		LoginUserResult result = gson.fromJson(string, LoginUserResult.class);
		return result;
	}

	/**
	 * �����¼��Ϣ
	 * 
	 * @param mContext
	 * @param LoginUserResult
	 */
	public static void saveLoginUserResult(Context mContext,
			LoginUserResult loginUserResult) {
		Gson gson = new Gson();
		String string = (String) SPUtils.get(mContext,
				Constant.USER_INFO_FILE_NAME, "");
		string = gson.toJson(loginUserResult);
		SPUtils.put(mContext, Constant.USER_INFO_FILE_NAME, string);
	}
}
