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

	/** 当前登录用户 */
	public static CurrentUser CurrentUser;

	/** 订单详情 */
	public static List<Map<String, String>> OrderMealList = new ArrayList<Map<String, String>>();

	/** 订单总金额 */
	public static List<Integer> OrderMealMoneyList = new ArrayList<Integer>();

	/** 收藏餐馆 */
	public static Map<String, CollectRes> ResMap = new HashMap<String, CollectRes>();

	/** 电话号码 */
	public static String Phone;

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

}
