package com.lzc.jiaowaimai.activity.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDao
{

	/**
	 * ������������û��Ķ�Ӧ�����Ϣ
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

	/** ע��ʱ���� ���û����� */
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

	/** ɾ�� */
	public static void delete()
	{

	}

	/** �޸� �û���Ϣ */
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

	/** �޸��û�ͼƬ */
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
