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
	public static final int VERSION = 24;
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
				"create table ordermeal_info(mealid text primary key,restaurantname text,mealname text,mealnum text,mealmoney integer,phone text,isOrdered integer,restaurantid text);");
		db.execSQL(
				"create table restaurant_info(restaurantid text primary key,resname text,resphone text,canguanpic bolb,restype text,respeisong integer,runspeed text,startrunmoney integer,sales text,score text,resaddressid text);");
		db.execSQL(
				"create table menu_info (menuid text primary key,name text,price text,image bolb,restaurantid text);");
		db.execSQL(
				"create table resaddress_info(resaddressid text primary key,resprovince text,rescity text,rescountry text,resstreet text);");

		db.execSQL("create table food_album(albumid text primary key,image bolb,time text,phone text);");
		initTable_Person_Info(db, mContext);
		initTable_Address_Info(db, mContext);
		initTable_Redpackage_Info(db, mContext);
		initTable_Ordermeal_Info(db, mContext);
		initTable_Restaurant_Info(db, mContext);
		initTable_Menu_Info(db, mContext);
		initTable_Resaddress_Info(db, mContext);
		initTable_Food_Album(db, mContext);
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
	 * 初始化美食相册表
	 * 
	 * @param db
	 * @param mContext2
	 */
	private void initTable_Food_Album(SQLiteDatabase db, Context context)
	{
		ContentValues values = new ContentValues();
		values.put("albumid", "50000");
		Drawable drawable = context.getResources().getDrawable(R.drawable.food1);
		values.put("image", getPicture(drawable));
		values.put("time", "2016-4-1 9:14");
		values.put("phone", "13915873770");
		db.insert("food_album", null, values);

		ContentValues values1 = new ContentValues();
		values1.put("albumid", "50001");
		Drawable drawable1 = context.getResources().getDrawable(R.drawable.food2);
		values1.put("image", getPicture(drawable1));
		values1.put("time", "2016-4-2 9:14");
		values1.put("phone", "13915873770");
		db.insert("food_album", null, values1);

		ContentValues values2 = new ContentValues();
		values2.put("albumid", "50002");
		Drawable drawable2 = context.getResources().getDrawable(R.drawable.food3);
		values2.put("image", getPicture(drawable2));
		values2.put("time", "2016-4-3 9:14");
		values2.put("phone", "13915873770");
		db.insert("food_album", null, values2);

		ContentValues values3 = new ContentValues();
		values3.put("albumid", "50003");
		Drawable drawable3 = context.getResources().getDrawable(R.drawable.food4);
		values3.put("image", getPicture(drawable3));
		values3.put("time", "2016-4-4 9:14");
		values3.put("phone", "13915873770");
		db.insert("food_album", null, values3);

		ContentValues values4 = new ContentValues();
		values4.put("albumid", "50004");
		Drawable drawable4 = context.getResources().getDrawable(R.drawable.food5);
		values4.put("image", getPicture(drawable4));
		values4.put("time", "2016-4-5 9:14");
		values4.put("phone", "13915873770");
		db.insert("food_album", null, values4);

		ContentValues values5 = new ContentValues();
		values5.put("albumid", "50005");
		Drawable drawable5 = context.getResources().getDrawable(R.drawable.food6);
		values5.put("image", getPicture(drawable5));
		values5.put("time", "2016-4-6 9:14");
		values5.put("phone", "13915873770");
		db.insert("food_album", null, values5);

	}

	/**
	 * 初始化餐馆地址表的信息
	 * 
	 * @param context
	 * @param db
	 */
	private void initTable_Resaddress_Info(SQLiteDatabase db, Context context)
	{

	}

	/**
	 * 初始化菜单表的信息
	 * 
	 * @param mContext2
	 * @param db
	 */
	private void initTable_Menu_Info(SQLiteDatabase db, Context context)
	{
		ContentValues values = new ContentValues();
		values.put("menuid", "6000");
		values.put("name", "挂炉烤鸭");
		values.put("price", "60");
		Drawable drawable = context.getResources().getDrawable(R.drawable.gualu);
		values.put("image", getPicture(drawable));
		values.put("restaurantid", "10000");
		db.insert("menu_info", null, values);

		ContentValues values1 = new ContentValues();
		values1.put("menuid", "6001");
		values1.put("name", "焖炉烤鸭");
		values1.put("price", "70");
		Drawable drawable1 = context.getResources().getDrawable(R.drawable.menlu);
		values1.put("image", getPicture(drawable1));
		values1.put("restaurantid", "10000");
		db.insert("menu_info", null, values1);

		ContentValues values2 = new ContentValues();
		values2.put("menuid", "6002");
		values2.put("name", "土豆牛肉盖浇饭");
		values2.put("price", "12");
		Drawable drawable2 = context.getResources().getDrawable(R.drawable.galiniurougaijiaofan);
		values2.put("image", getPicture(drawable2));
		values2.put("restaurantid", "10001");
		db.insert("menu_info", null, values2);

		ContentValues values3 = new ContentValues();
		values3.put("menuid", "6003");
		values3.put("name", "番茄炒蛋盖浇饭");
		values3.put("price", "10");
		Drawable drawable3 = context.getResources().getDrawable(R.drawable.fanqiechaodan);
		values3.put("image", getPicture(drawable3));
		values3.put("restaurantid", "10001");
		db.insert("menu_info", null, values3);

		ContentValues values4 = new ContentValues();
		values4.put("menuid", "6004");
		values4.put("name", "青椒炒肉盖浇饭");
		values4.put("price", "12");
		Drawable drawable4 = context.getResources().getDrawable(R.drawable.qingjiaorousigaijiaofan);
		values4.put("image", getPicture(drawable4));
		values4.put("restaurantid", "10001");
		db.insert("menu_info", null, values4);

		ContentValues values5 = new ContentValues();
		values5.put("menuid", "6005");
		values5.put("name", "新疆大盘鸡");
		values5.put("price", "50");
		Drawable drawable5 = context.getResources().getDrawable(R.drawable.dapanji);
		values5.put("image", getPicture(drawable5));
		values5.put("restaurantid", "10001");
		db.insert("menu_info", null, values5);

		ContentValues values6 = new ContentValues();
		values6.put("menuid", "6006");
		values6.put("name", "手抓羊肉");
		values6.put("price", "30");
		Drawable drawable6 = context.getResources().getDrawable(R.drawable.shouzhuayangrou);
		values6.put("image", getPicture(drawable6));
		values6.put("restaurantid", "10001");
		db.insert("menu_info", null, values6);

		ContentValues values7 = new ContentValues();
		values7.put("menuid", "6007");
		values7.put("name", "牛肉粉丝汤");
		values7.put("price", "15");
		Drawable drawable7 = context.getResources().getDrawable(R.drawable.niuroufensitang);
		values7.put("image", getPicture(drawable7));
		values7.put("restaurantid", "10001");
		db.insert("menu_info", null, values7);

		ContentValues values8 = new ContentValues();
		values8.put("menuid", "6008");
		values8.put("name", "咖喱牛肉炒饭");
		values8.put("price", "12");
		Drawable drawable8 = context.getResources().getDrawable(R.drawable.niiurouchaofan);
		values8.put("image", getPicture(drawable8));
		values8.put("restaurantid", "10001");
		db.insert("menu_info", null, values8);

		ContentValues values9 = new ContentValues();
		values9.put("menuid", "6009");
		values9.put("name", "牛肉泡膜");
		values9.put("price", "8");
		Drawable drawable9 = context.getResources().getDrawable(R.drawable.paomo);
		values9.put("image", getPicture(drawable9));
		values9.put("restaurantid", "10001");
		db.insert("menu_info", null, values9);

		ContentValues values10 = new ContentValues();
		values10.put("menuid", "6010");
		values10.put("name", "牛肉拌面");
		values10.put("price", "12");
		Drawable drawable10 = context.getResources().getDrawable(R.drawable.niuroubanmian);
		values10.put("image", getPicture(drawable10));
		values10.put("restaurantid", "10001");
		db.insert("menu_info", null, values10);

		ContentValues values11 = new ContentValues();
		values11.put("menuid", "6011");
		values11.put("name", "羊肉汤");
		values11.put("price", "20");
		Drawable drawable11 = context.getResources().getDrawable(R.drawable.yangroutang);
		values11.put("image", getPicture(drawable11));
		values11.put("restaurantid", "10001");
		db.insert("menu_info", null, values11);

		ContentValues values12 = new ContentValues();
		values12.put("menuid", "6012");
		values12.put("name", "黄焖鸡");
		values12.put("price", "15");
		Drawable drawable12 = context.getResources().getDrawable(R.drawable.huangmenji);
		values12.put("image", getPicture(drawable12));
		values12.put("restaurantid", "10003");
		db.insert("menu_info", null, values12);

		ContentValues values13 = new ContentValues();
		values13.put("menuid", "6013");
		values13.put("name", "黄焖排骨");
		values13.put("price", "18");
		Drawable drawable13 = context.getResources().getDrawable(R.drawable.hangmenpaigu);
		values13.put("image", getPicture(drawable13));
		values13.put("restaurantid", "10003");
		db.insert("menu_info", null, values13);

		ContentValues values14 = new ContentValues();
		values14.put("menuid", "6014");
		values14.put("name", "红富士苹果");
		values14.put("price", "10/斤");
		Drawable drawable14 = context.getResources().getDrawable(R.drawable.pingguo);
		values14.put("image", getPicture(drawable14));
		values14.put("restaurantid", "10005");
		db.insert("menu_info", null, values14);

		ContentValues values15 = new ContentValues();
		values15.put("menuid", "6015");
		values15.put("name", "芒果");
		values15.put("price", "12/斤");
		Drawable drawable15 = context.getResources().getDrawable(R.drawable.manguo);
		values15.put("image", getPicture(drawable15));
		values15.put("restaurantid", "10005");
		db.insert("menu_info", null, values15);

		ContentValues values16 = new ContentValues();
		values16.put("menuid", "6016");
		values16.put("name", "西瓜");
		values16.put("price", "5/斤");
		Drawable drawable16 = context.getResources().getDrawable(R.drawable.xigua);
		values16.put("image", getPicture(drawable16));
		values16.put("restaurantid", "10005");
		db.insert("menu_info", null, values16);

		ContentValues values17 = new ContentValues();
		values17.put("menuid", "6017");
		values17.put("name", "草莓蛋糕");
		values17.put("price", "80/个");
		Drawable drawable17 = context.getResources().getDrawable(R.drawable.caomeidangao);
		values17.put("image", getPicture(drawable17));
		values17.put("restaurantid", "10006");
		db.insert("menu_info", null, values17);

		ContentValues values18 = new ContentValues();
		values18.put("menuid", "6018");
		values18.put("name", "芒果蛋糕");
		values18.put("price", "100/个");
		Drawable drawable18 = context.getResources().getDrawable(R.drawable.manguodangao);
		values18.put("image", getPicture(drawable18));
		values18.put("restaurantid", "10006");
		db.insert("menu_info", null, values18);

		ContentValues values19 = new ContentValues();
		values19.put("menuid", "6019");
		values19.put("name", "冰淇淋蛋糕");
		values19.put("price", "120/个");
		Drawable drawable19 = context.getResources().getDrawable(R.drawable.bingqiling);
		values19.put("image", getPicture(drawable19));
		values19.put("restaurantid", "10006");
		db.insert("menu_info", null, values19);

		ContentValues values20 = new ContentValues();
		values20.put("menuid", "6020");
		values20.put("name", "干贝排骨粥");
		values20.put("price", "68/锅");
		Drawable drawable20 = context.getResources().getDrawable(R.drawable.ganbeipaigu);
		values20.put("image", getPicture(drawable20));
		values20.put("restaurantid", "10007");
		db.insert("menu_info", null, values20);

		ContentValues values21 = new ContentValues();
		values21.put("menuid", "6021");
		values21.put("name", "虾仁砂锅粥");
		values21.put("price", "58/锅");
		Drawable drawable21 = context.getResources().getDrawable(R.drawable.xiarenzhou);
		values21.put("image", getPicture(drawable21));
		values21.put("restaurantid", "10007");
		db.insert("menu_info", null, values21);

		ContentValues values22 = new ContentValues();
		values22.put("menuid", "6022");
		values22.put("name", "蟹黄砂锅粥");
		values22.put("price", "98/锅");
		Drawable drawable22 = context.getResources().getDrawable(R.drawable.xiehuangzhou);
		values22.put("image", getPicture(drawable22));
		values22.put("restaurantid", "10007");
		db.insert("menu_info", null, values22);

		ContentValues values23 = new ContentValues();
		values23.put("menuid", "6023");
		values23.put("name", "脆皮手枪腿");
		values23.put("price", "28");
		Drawable drawable23 = context.getResources().getDrawable(R.drawable.cuipishouqiangtui);
		values23.put("image", getPicture(drawable23));
		values23.put("restaurantid", "10008");
		db.insert("menu_info", null, values23);

		ContentValues values24 = new ContentValues();
		values24.put("menuid", "6024");
		values24.put("name", "深海鳕鱼堡");
		values24.put("price", "24");
		Drawable drawable24 = context.getResources().getDrawable(R.drawable.xueyubao);
		values24.put("image", getPicture(drawable24));
		values24.put("restaurantid", "10008");
		db.insert("menu_info", null, values24);

		ContentValues values25 = new ContentValues();
		values25.put("menuid", "6025");
		values25.put("name", "芝士牛排饭");
		values25.put("price", "38");
		Drawable drawable25 = context.getResources().getDrawable(R.drawable.zhishiniuroufan);
		values25.put("image", getPicture(drawable25));
		values25.put("restaurantid", "10008");
		db.insert("menu_info", null, values25);

		ContentValues values26 = new ContentValues();
		values26.put("menuid", "60026");
		values26.put("name", "土豆牛肉盖浇饭");
		values26.put("price", "12");
		Drawable drawable26 = context.getResources().getDrawable(R.drawable.galiniurougaijiaofan);
		values26.put("image", getPicture(drawable26));
		values26.put("restaurantid", "10002");
		db.insert("menu_info", null, values26);

		ContentValues values27 = new ContentValues();
		values27.put("menuid", "60027");
		values27.put("name", "番茄炒蛋盖浇饭");
		values27.put("price", "10");
		Drawable drawable27 = context.getResources().getDrawable(R.drawable.fanqiechaodan);
		values27.put("image", getPicture(drawable27));
		values27.put("restaurantid", "10002");
		db.insert("menu_info", null, values27);

		ContentValues values28 = new ContentValues();
		values28.put("menuid", "60028");
		values28.put("name", "青椒炒肉盖浇饭");
		values28.put("price", "12");
		Drawable drawable28 = context.getResources().getDrawable(R.drawable.qingjiaorousigaijiaofan);
		values28.put("image", getPicture(drawable28));
		values28.put("restaurantid", "10002");
		db.insert("menu_info", null, values28);

		ContentValues values29 = new ContentValues();
		values29.put("menuid", "60029");
		values29.put("name", "土豆牛肉盖浇饭");
		values29.put("price", "12");
		Drawable drawable29 = context.getResources().getDrawable(R.drawable.galiniurougaijiaofan);
		values29.put("image", getPicture(drawable29));
		values29.put("restaurantid", "10004");
		db.insert("menu_info", null, values29);

		ContentValues values30 = new ContentValues();
		values30.put("menuid", "60030");
		values30.put("name", "番茄炒蛋盖浇饭");
		values30.put("price", "10");
		Drawable drawable30 = context.getResources().getDrawable(R.drawable.fanqiechaodan);
		values30.put("image", getPicture(drawable30));
		values30.put("restaurantid", "10004");
		db.insert("menu_info", null, values30);

		ContentValues values31 = new ContentValues();
		values31.put("menuid", "60031");
		values31.put("name", "青椒炒肉盖浇饭");
		values31.put("price", "12");
		Drawable drawable31 = context.getResources().getDrawable(R.drawable.qingjiaorousigaijiaofan);
		values31.put("image", getPicture(drawable31));
		values31.put("restaurantid", "10004");
		db.insert("menu_info", null, values31);
	}

	/**
	 * 初始化餐馆表的信息
	 * 
	 * @param mContext2
	 * @param db
	 */
	private void initTable_Restaurant_Info(SQLiteDatabase db, Context context)
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
		db.insert("restaurant_info", null, values8);
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
		db.execSQL("drop table if exists food_album");
		onCreate(db);
	}

}
