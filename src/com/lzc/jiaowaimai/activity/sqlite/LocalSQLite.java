package com.lzc.jiaowaimai.activity.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/** 本地数据表 */
public class LocalSQLite extends SQLiteOpenHelper
{
	public static final int VERSION = 3;
	public static final String BD_NAME = "jiaowaimai.db";

	public LocalSQLite(Context context, String name, CursorFactory factory, int version)
	{
		super(context, BD_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(
				"create table person_info (userid text primary key,username text,password text,userpic bolb,balance integer,addressid text,redpackageid text);");
		db.execSQL(
				"create table address_info(addressid text primary key,province text,city text,country text,street text);");
		db.execSQL(
				"create table redpackage_info(redpackageid text primary key,amount integer,type text,starttime text,endtiem text);");
		db.execSQL(
				"create table ordermeal_info(mealid text primary key,restaurantname text,mealname text,mealnum integer,mealmoney integer,restaurantid text);");
		db.execSQL(
				"create table restaurant_info(restaurantid text primary key,resphone text,restype text,runspeed text,startrunmoney integer,sales text,score text,resaddressid text,menuid text);");
		db.execSQL(
				"create table menu_info (menuid text primary key,type1 text,type2 text,type3 text,type4 text,type5 text,type6 text,type7 text,type8 text,type9 text,type10 text,type11 text,type12 text,type13 text,type14 text,type15 text,type16 text,type17 text,type18 text,type19 text,type20 text);");
		db.execSQL(
				"create table resaddress_info(resaddressid text primary key,resprovince text,rescity text,rescountry text,resstreet text);");
		initTable_Person_Info();
		initTable_Address_Info();
		initTable_Redpackage_Info();
		initTable_Ordermeal_Info();
		initTable_Restaurant_Info();
		initTable_Menu_Info();
		initTable_Resaddress_Info();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		System.out.println("oldVersion:" + oldVersion + "newVersion:" + newVersion);
		db.execSQL("drop table if exists resaddress_info");
		db.execSQL("drop table if exists menu_info");
		db.execSQL("drop table if exists restaurant_info");
		db.execSQL("drop table if exists ordermeal_info");
		db.execSQL("drop table if exists redpackage_info");
		db.execSQL("drop table if exists address_info");
		db.execSQL("drop table if exists person_info");
		onCreate(db);
	}

	/** 初始化餐馆地址表的信息 */
	private void initTable_Resaddress_Info()
	{

	}

	/** 初始化菜单表的信息 */
	private void initTable_Menu_Info()
	{

	}

	/** 初始化餐馆表的信息 */
	private void initTable_Restaurant_Info()
	{

	}

	/** 初始化订单表的信息 */
	private void initTable_Ordermeal_Info()
	{

	}

	/** 初始化红包表的信息 */
	private void initTable_Redpackage_Info()
	{

	}

	/** 初始化用户地址表的信息 */
	private void initTable_Address_Info()
	{

	}

	/** 初始化用户表的信息 */
	private void initTable_Person_Info()
	{

	}

}
