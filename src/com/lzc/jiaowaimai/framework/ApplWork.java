package com.lzc.jiaowaimai.framework;

import com.lzc.jiaowaimai.activity.bean.CurrentUser;

import android.app.Application;

public class ApplWork extends Application
{

	/** ��ǰ��¼�û� */
	public static CurrentUser CurrentUser;

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

}
