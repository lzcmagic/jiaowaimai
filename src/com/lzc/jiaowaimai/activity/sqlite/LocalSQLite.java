package com.lzc.jiaowaimai.activity.sqlite;

import java.io.ByteArrayOutputStream;

import com.lzc.jiaowaimai.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/** 本地数据表 */
public class LocalSQLite extends SQLiteOpenHelper
{
	public static final int VERSION = 10;
	public static final String BD_NAME = "jiaowaimai.db";

	private Context mContext;

	public LocalSQLite(Context context, String name, CursorFactory factory, int version)
	{
		super(context, BD_NAME, null, VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(
				"create table person_info (userid text primary key,username text,password text,paypassword text,phone text,userpic bolb,balance integer);");
		db.execSQL(
				"create table address_info(addressid text primary key,province text,city text,country text,street text,phone text);");
		db.execSQL(
				"create table redpackage_info(redpackageid text primary key,amount integer,type text,phone text,starttime text,endtime text);");
		db.execSQL(
				"create table ordermeal_info(mealid text primary key,restaurantname text,mealname text,mealnum integer,mealmoney integer,restaurantid text);");
		db.execSQL(
				"create table restaurant_info(restaurantid text primary key,resname text,resphone text,canguanpic bolb,restype text,respeisong integer,runspeed text,startrunmoney integer,sales text,score text,resaddressid text,menuid text);");
		db.execSQL(
				"create table menu_info (menuid text primary key,type1 text,type2 text,type3 text,type4 text,type5 text,type6 text,type7 text,type8 text,type9 text,type10 text,type11 text,type12 text,type13 text,type14 text,type15 text,type16 text,type17 text,type18 text,type19 text,type20 text);");
		db.execSQL(
				"create table resaddress_info(resaddressid text primary key,resprovince text,rescity text,rescountry text,resstreet text);");
		initTable_Person_Info(db, mContext);
		initTable_Address_Info(db, mContext);
		initTable_Redpackage_Info(db, mContext);
		initTable_Ordermeal_Info(db, mContext);
		initTable_Restaurant_Info(db, mContext);
		initTable_Menu_Info(db, mContext);
		initTable_Resaddress_Info(db, mContext);
	}

	/**
	 * 初始化餐馆地址表的信息
	 * 
	 * @param context
	 * @param db
	 */
	private void initTable_Resaddress_Info(SQLiteDatabase db, Context context)
	{
		ContentValues values = new ContentValues();
		values.put("restaurantid", "10000");
		values.put("resphone", "13915873770");
		values.put("resname", "北京烤鸭");
		Drawable drawable = context.getResources().getDrawable(R.drawable.bjky);
		values.put("canguanpic", getPicture(drawable));
		values.put("restype", "正餐");
		values.put("runspeed", "30");
		values.put("startrunmoney", "15");
		values.put("sales", "0");
		values.put("score", "0");
		values.put("resaddressid", "20000");
		values.put("respeisong", "5");
		values.put("menuid", "30000");
		db.insert("restaurant_info", null, values);

		ContentValues values1 = new ContentValues();
		values1.put("restaurantid", "10001");
		values1.put("resphone", "13915873771");
		values1.put("resname", "兰州拉面");
		Drawable drawable1 = context.getResources().getDrawable(R.drawable.lzlm);
		values1.put("canguanpic", getPicture(drawable1));
		values1.put("restype", "快餐");
		values1.put("runspeed", "40");
		values1.put("startrunmoney", "20");
		values1.put("sales", "0");
		values1.put("score", "0");
		values1.put("respeisong", "3");
		values1.put("resaddressid", "20001");
		values1.put("menuid", "30001");
		db.insert("restaurant_info", null, values1);

		ContentValues values2 = new ContentValues();
		values2.put("restaurantid", "10002");
		values2.put("resphone", "13915873772");
		values2.put("resname", "沙县小吃");
		Drawable drawable2 = context.getResources().getDrawable(R.drawable.sxxc);
		values2.put("canguanpic", getPicture(drawable2));
		values2.put("restype", "小吃");
		values2.put("runspeed", "30");
		values2.put("startrunmoney", "30");
		values2.put("sales", "0");
		values2.put("respeisong", "3");
		values2.put("score", "0");
		values2.put("resaddressid", "20002");
		values2.put("menuid", "30002");
		db.insert("restaurant_info", null, values2);

		ContentValues values3 = new ContentValues();
		values3.put("restaurantid", "10003");
		values3.put("resphone", "13915873773");
		values3.put("resname", "黄焖鸡");
		Drawable drawable3 = context.getResources().getDrawable(R.drawable.hmj);
		values3.put("canguanpic", getPicture(drawable3));
		values3.put("restype", "快餐");
		values3.put("runspeed", "30");
		values3.put("startrunmoney", "20");
		values3.put("sales", "0");
		values3.put("score", "0");
		values3.put("respeisong", "2");
		values3.put("resaddressid", "20003");
		values3.put("menuid", "30003");
		db.insert("restaurant_info", null, values3);

		ContentValues values4 = new ContentValues();
		values4.put("restaurantid", "10004");
		values4.put("resphone", "13915873774");
		values4.put("resname", "重庆小面");
		Drawable drawable4 = context.getResources().getDrawable(R.drawable.cqxm);
		values4.put("canguanpic", getPicture(drawable4));
		values4.put("restype", "快餐");
		values4.put("runspeed", "40");
		values4.put("startrunmoney", "30");
		values4.put("sales", "0");
		values4.put("respeisong", "6");
		values4.put("score", "0");
		values4.put("resaddressid", "20004");
		values4.put("menuid", "30004");
		db.insert("restaurant_info", null, values4);

		ContentValues values5 = new ContentValues();
		values5.put("restaurantid", "10005");
		values5.put("resphone", "13915873775");
		values5.put("resname", "一号水果摊");
		Drawable drawable5 = context.getResources().getDrawable(R.drawable.yhsgt);
		values5.put("canguanpic", getPicture(drawable5));
		values5.put("restype", "水果");
		values5.put("runspeed", "40");
		values5.put("startrunmoney", "50");
		values5.put("sales", "0");
		values5.put("respeisong", "3");
		values5.put("score", "5");
		values5.put("resaddressid", "20005");
		values5.put("menuid", "30005");
		db.insert("restaurant_info", null, values5);

		ContentValues values6 = new ContentValues();
		values6.put("restaurantid", "10006");
		values6.put("resphone", "13915873776");
		values6.put("resname", "梦甜蛋糕");
		Drawable drawable6 = context.getResources().getDrawable(R.drawable.mtdg);
		values6.put("canguanpic", getPicture(drawable6));
		values6.put("restype", "甜品");
		values6.put("runspeed", "50");
		values6.put("startrunmoney", "60");
		values6.put("sales", "0");
		values6.put("score", "0");
		values6.put("respeisong", "6");
		values6.put("resaddressid", "20006");
		values6.put("menuid", "30006");
		db.insert("restaurant_info", null, values6);

		ContentValues values7 = new ContentValues();
		values7.put("restaurantid", "10007");
		values7.put("resphone", "13915873777");
		values7.put("resname", "粤仔湾");
		Drawable drawable7 = context.getResources().getDrawable(R.drawable.yzg);
		values7.put("canguanpic", getPicture(drawable7));
		values7.put("restype", "正餐");
		values7.put("runspeed", "40");
		values7.put("startrunmoney", "100");
		values7.put("sales", "0");
		values7.put("respeisong", "8");
		values7.put("score", "0");
		values7.put("resaddressid", "20007");
		values7.put("menuid", "30007");
		db.insert("restaurant_info", null, values7);

		ContentValues values8 = new ContentValues();
		values8.put("restaurantid", "10008");
		values8.put("resphone", "13915873778");
		values8.put("resname", "肯打鸡");
		Drawable drawable8 = context.getResources().getDrawable(R.drawable.kdj);
		values8.put("canguanpic", getPicture(drawable8));
		values8.put("restype", "小吃");
		values8.put("runspeed", "30");
		values8.put("startrunmoney", "50");
		values8.put("sales", "0");
		values8.put("score", "0");
		values8.put("respeisong", "10");
		values8.put("resaddressid", "20008");
		values8.put("menuid", "30008");
		db.insert("restaurant_info", null, values8);

	}

	/**
	 * 将Drawable转换成可以用来存储的Byte[]类型
	 * 
	 * @param drawable
	 * @return
	 */
	private byte[] getPicture(Drawable drawable)
	{
		if (drawable == null )
		{
			return null;
		}
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 初始化菜单表的信息
	 * 
	 * @param mContext2
	 * @param db
	 */
	private void initTable_Menu_Info(SQLiteDatabase db, Context mContext2)
	{

	}

	/**
	 * 初始化餐馆表的信息
	 * 
	 * @param mContext2
	 * @param db
	 */
	private void initTable_Restaurant_Info(SQLiteDatabase db, Context mContext2)
	{

	}

	/**
	 * 初始化订单表的信息
	 * 
	 * @param mContext2
	 * @param db
	 */
	private void initTable_Ordermeal_Info(SQLiteDatabase db, Context mContext2)
	{

	}

	/**
	 * 初始化红包表的信息
	 * 
	 * @param mContext2
	 * @param db
	 */
	private void initTable_Redpackage_Info(SQLiteDatabase db, Context mContext2)
	{
		ContentValues values = new ContentValues();
		values.put("redpackageid", "500");
		values.put("amount", "100");
		values.put("type", "管理员红包");
		values.put("starttime", "1970-01-01");
		values.put("endtime", "2100-12-31");
		values.put("phone", "13915873770");
		db.insert("redpackage_info", null, values);

		ContentValues values1 = new ContentValues();
		values1.put("redpackageid", "501");
		values1.put("amount", "500");
		values1.put("type", "管理员红包");
		values1.put("starttime", "1970-01-01");
		values1.put("endtime", "2100-12-31");
		values1.put("phone", "13915873770");
		db.insert("redpackage_info", null, values1);
	}

	/**
	 * 初始化用户地址表的信息
	 * 
	 * @param mContext2
	 * @param db
	 */
	private void initTable_Address_Info(SQLiteDatabase db, Context context)
	{
		ContentValues values = new ContentValues();
		values.put("addressid", "500");
		values.put("province", "江苏省");
		values.put("city", "常州市");
		values.put("country", "溧阳县");
		values.put("street", "社渚镇农贸市场北楼");
		values.put("phone", "13915873770");
		db.insert("address_info", null, values);
	}

	/**
	 * 初始化用户表的信息
	 * 
	 * @param mContext2
	 * @param db
	 */
	private void initTable_Person_Info(SQLiteDatabase db, Context context)
	{
		ContentValues values = new ContentValues();
		values.put("userid", "100");
		values.put("username", "admin");
		values.put("password", "admin");
		values.put("paypassword", "123456");
		Drawable drawable = context.getResources().getDrawable(R.drawable.admin);
		values.put("phone", "13915873770");
		values.put("userpic", getPicture(drawable));
		values.put("balance", "10000");
		db.insert("person_info", null, values);
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

}
