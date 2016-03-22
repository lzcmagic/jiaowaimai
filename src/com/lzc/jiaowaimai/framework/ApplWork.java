package com.lzc.jiaowaimai.framework;

import com.lzc.jiaowaimai.activity.bean.CurrentUser;

import android.app.Application;

public class ApplWork extends Application
{

	/** 当前登录用户 */
	public static CurrentUser CurrentUser;

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

}
