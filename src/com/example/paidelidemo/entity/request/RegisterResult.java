package com.example.paidelidemo.entity.request;

/**
 * �û�ע�᷵��
 * 
 * @author ciehaifeng
 * @version 2015��5��29��11:09:03
 * 
 */
public class RegisterResult {
	public int c;
	public Parameter p;

	public class Parameter {
		public int type;
		public boolean isSucce;
		public user user;
		public bannerList bannerList;
		public ernie ernie;
		public long nowTime;
	}

	public class user {
		public long createTime;
		public int id;
		public String inviteCode;
		public long lastLoginTime;
		public String mobile;
		public String password;
		public int score;
		public int state;
		public int totalScore;
		public int type;
		public int vipLevel;
	}

	public class bannerList {
		public long createTime;
		public int id;
		public String imgName;
		public String imgUrl;
		public int status;
	}

	public class ernie {
		public int createPerson;
		public long createTime;
		public long ernieBegintime;
		public long ernieEndtime;
		public String ernieStatus;
		public int id;
		public long joinBegintime;
		public long joinEndtime;
		public int joinNumber;
		public int periods;
		public String releaseStatus;
	}
}
