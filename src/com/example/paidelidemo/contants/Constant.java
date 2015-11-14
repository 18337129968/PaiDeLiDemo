package com.example.paidelidemo.contants;

/**
 * 常量类
 * 
 * @author xiehaifeng
 */
public class Constant {
	
	public static final String DESCRIPTOR = "com.umeng.share";
	public static final String ACCOUNT_REMOVED = "account_removed";
	public static final String DYNAMICACTION = "0"; // 动态广播的Action字符串
	
	/** 登陆 */
	public static final int LOGIN_TO_HOME_CODE = 13;

	/** 用户注册 */
	public static final String USER_REGISTER_CODE = "1001";

	/** 用户登录 */
	public static final String USER_LOGIN_CODE = "1002";

	/** 获取验证码 */
	public static final String GET_VERIFICATION_CODE = "1019";

	/** 忘记密码请求 */
	public static final String FORGOTPASSWORD_REQUEST_CODE = "1020";

	/** 请求输入邀请码 */
	public static final String INVITATION_CODE_REQUEST = "1024";
	
	/** 任务列表 */
	public static final String TASK_LIST = "1027";
	

	/** 存储欢迎界面引导页状态的xml名字 */
	public static final String GUIDE_NAME = "guidePage";

	/** 登录缓存头像名称 */
	public static final String HEAD_PIC_FILE_NAME = "head_pic.jpg";

	/** 用户信息文件名 */
	public static final String USER_INFO_FILE_NAME = "UserInfo";

	/** 连接服务器的HttpURL 备用服务端主程地址http://192.168.8.151:8080/AppServer/action.a */
	public static final String CONNECT_URL = "http://192.168.0.151:8080/AppServer/action.a";
	
	/** 连接服务器的HttpURL 备用服务端主程地址http://api.openweathermap.org/data/2.5/weather?q=China,Beijing */
	public static final String CONNECT_URL0 = "http://api.openweathermap.org/data/2.5/weather?q=China,Beijing";
	
	/** 连接服务器的HttpURL 备用服务端主程地址http://www.baidu.com */
	public static final String CONNECT_URL1 = "http://www.baidu.com";

	/** 用户是否报名参加摇奖 */
	public static final String GETENTER_FOR_STATE = "GetEnterForState";

	/** 获取系统时间 */
	public static long time;

	/** 时间广播 */
	public static String ernieBegintime = "1";
	/** 时间广播 */
	public static String ernieEndtime = "2";
	/** 时间广播 */
	public static String joinBegintime = "3";
	/** 时间广播 */
	public static String joinEndtime = "4";

}
