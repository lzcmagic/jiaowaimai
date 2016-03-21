package com.lzc.jiaowaimai.framework;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.graphics.Bitmap;

public class ApplWork extends Application
{

	/** 当前登录用户 */
	public static List<CurrentUser> CurrentUserInfo=new ArrayList<ApplWork.CurrentUser>();

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	class CurrentUser
	{
		String userid;
		Bitmap userpic;
		String username;
		String password;
		String phone;
		int balance;
		String addressid;
		String redpackageid;
	}
}
