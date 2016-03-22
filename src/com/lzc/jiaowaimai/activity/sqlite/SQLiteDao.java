package com.lzc.jiaowaimai.activity.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDao
{

	/**
	 * 查找用户
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

	/** 插入 新用户数据 */
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

	/** 删除 */
	public static void delete()
	{

	}

	/** 修改 */
	public static void update()
	{

	}
}
