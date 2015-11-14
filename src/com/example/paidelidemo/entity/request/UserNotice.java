package com.example.paidelidemo.entity.request;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserNotice implements Serializable {
	public int contentId;
	public int state;
	public String title;
	public String content;
	public int userId;
	public long sendTime;
}