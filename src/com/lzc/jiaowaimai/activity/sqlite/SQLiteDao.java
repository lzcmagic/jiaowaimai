package com.lzc.jiaowaimai.activity.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDao
{

	/**
	 * 查找属于这个用户的对应表的信息
	 * 
	 * @return
	 */
	public static Cursor query(Context context, String tableName, String phone)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from " + tableName + " where phone = '" + phone + "'", null);
		return cursor;
	}

	/** 查找餐馆 */
	public static Cursor queryRestuanrant(Context context, String tableName, String resId)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from " + tableName + " where restaurantid = '" + resId + "'",
				null);
		return cursor;
	}

	/** 查找菜单 */
	public static Cursor queryMenu(Context context, String resid)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from menu_info where restaurantid='" + resid + "'", null);
		return cursor;

	}

	/** 注册时插入 新用户数据 */
	public static void insert(Context context, String phone, String password)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userid", String.valueOf((int) ((Math.random() * 9 + 1) * 100)));
		values.put("phone", phone);
		values.put("password", password);
		db.insert("person_info", null, values);
	}

	public static void insertMenu(Context context, String restaurantname, String mealname, String mealnum,
			int mealmoney, String phone)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mealid", String.valueOf((int) ((Math.random() * 9 + 1) * 1000)));
		values.put("restaurantname", restaurantname);
		values.put("mealname", mealname);
		values.put("mealnum", mealnum);
		values.put("mealmoney", mealmoney);
		values.put("phone", phone);
		db.insert("ordermeal_info", null, values);
	}

	/** 插入地址 */
	public static void insertAddress(Context context, String province, String city, String country,
			String street, String phone)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("addressid", String.valueOf((int) ((Math.random() * 9 + 1) * 100)));
		values.put("province", province);
		values.put("city", city);
		values.put("country", country);
		values.put("street", street);
		values.put("phone", phone);
		db.insert("address_info", null, values);
	}

	/* 插入头像 **/
	public static void insertPhoto(Context context, String phone, String time, byte[] bs)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("albumid", String.valueOf((int) ((Math.random() * 9 + 1) * 10000)));
		values.put("image", bs);
		values.put("time", time);
		values.put("phone", phone);
		db.insert("food_album", null, values);
	}

	/** 删除 地址信息 */
	public static void delete(Context context, String addressId)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();
		String whereClause = "addressid=?";
		String[] whereArgs =
		{
			addressId
		};
		db.delete("address_info", whereClause, whereArgs);
	}

	/** 修改 用户信息 */
	public static void update(Context context, String phone, String key, String value)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(key, value);

		String whereClause = "phone=?";
		String[] whereArgs = new String[]
		{
			phone
		};
		db.update("person_info", values, whereClause, whereArgs);
	}

	public static void updateSales(Context context, String restaurantid, String key, String value)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(key, value);

		String whereClause = "restaurantid=?";
		String[] whereArgs = new String[]
		{
			restaurantid
		};
		db.update("restaurant_info", values, whereClause, whereArgs);
	}

	/** 更新菜馆信息 */
	public static void updateResScore(Context context, String restaurantid, String key, String value)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(key, value);
		String whereClause = "restaurantid=?";
		String[] whereArgs = new String[]
		{
			restaurantid
		};
		db.update("restaurant_info", values, whereClause, whereArgs);
	}

	/** 修改用户图片 */
	public static void updateUserPic(Context context, String phone, byte[] bs)
	{
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		SQLiteDatabase db = sqLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userpic", bs);
		String whereClause = "phone=?";
		String[] whereArgs = new String[]
		{
			phone
		};
		db.update("person_info", values, whereClause, whereArgs);
	}
}
