package com.lzc.jiaowaimai.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lzc.jiaowaimai.activity.bean.CollectRes;
import com.lzc.jiaowaimai.activity.bean.CurrentUser;

import android.app.Application;

public class ApplWork extends Application
{

	/** ��ǰ��¼�û� */
	public static CurrentUser CurrentUser;

	/** �������� */
	public static List<Map<String, String>> OrderMealList = new ArrayList<Map<String, String>>();

	/** �����ܽ�� */
	public static List<Integer> OrderMealMoneyList = new ArrayList<Integer>();

	/** �ղز͹� */
	public static Map<String, CollectRes> ResMap = new HashMap<String, CollectRes>();

	/** �绰���� */
	public static String Phone;

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

}
