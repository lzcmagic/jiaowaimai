package com.lzc.jiaowaimai.activity.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDao
{
	Context context;
	SQLiteDatabase db = null;

	public SQLiteDao(Context context)
	{
		this.context = context;
		LocalSQLite sqLite = new LocalSQLite(context, LocalSQLite.BD_NAME, null, LocalSQLite.VERSION);
		db = sqLite.getReadableDatabase();
		// db = sqLite.getWritableDatabase();
	}

	public SQLiteDao()
	{
		super();
	}

	/** ���� */
	public static void query()
	{

	}

	/***/
	public static void insert()
	{

	}

	/** ɾ�� */
	public static void delete()
	{

	}

	/** �޸� */
	public static void update()
	{

	}
}
