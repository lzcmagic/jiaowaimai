package com.lzc.jiaowaimai.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.view.RoundedImageView;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class F_XinXi extends Activity
{
	/** 记录按下返回键的时间 */
	private long exitTime = 0;

	private RoundedImageView tx_image;
	private TextView username, userphone;
	private LinearLayout InfoLayout;

	/** 余额 */
	private TextView tv_JinE;
	/** 红包个数 */
	private TextView tv_HongBao;
	/** 积分 */
	private TextView tv_JiFen;

	private List<String> hongbaoInfo;

	private LinearLayout hongbaolayout, addresslayout, CollectLayout, FoodLayout, jifenlayout, pinfenlayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f00_xinxi);
	}

	/** 动态注册广播 */
	private class MyBroadCast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			System.out.println("收到广播");
			tx_image.setImageResource(R.drawable.ic_launcher);
		}

	}

	@Override
	protected void onDestroy()
	{
		unregisterReceiver(new MyBroadCast());
		super.onDestroy();
	}

	@Override
	protected void onResume()
	{
		initViews();
		IntentFilter filter = new IntentFilter();
		filter.addAction("please_change_head_image");
		registerReceiver(new MyBroadCast(), filter);
		super.onResume();
	}

	private void initViews()
	{
		InfoLayout = (LinearLayout) findViewById(R.id.f00_layout);
		InfoLayout.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();

				if (ApplWork.CurrentUser != null )
				{
					intent.setClass(getApplicationContext(), Fb_UserInfo.class);
				}
				else
				{
					intent.setClass(getApplicationContext(), Fa_Login.class);
				}
				startActivity(intent);
			}
		});

		tx_image = (RoundedImageView) findViewById(R.id.iv_touxiang);
		username = (TextView) findViewById(R.id.tv_username);
		userphone = (TextView) findViewById(R.id.tv_phone);

		if (ApplWork.CurrentUser == null )
		{
			tx_image.setClickable(false);
			tx_image.setBackgroundResource(R.drawable.ic_launcher);
			username.setText("登录/注册");
			userphone.setText("");
		}
		else
		{
			tx_image.setClickable(true);
			tx_image.setImageBitmap(ApplWork.CurrentUser.getUserpic());
			username.setText(ApplWork.CurrentUser.getUsername());
			userphone.setText(ApplWork.CurrentUser.getPhone());
		}

		tv_JinE = (TextView) findViewById(R.id.tv_jine);
		tv_HongBao = (TextView) findViewById(R.id.tv_hongbao);
		tv_JiFen = (TextView) findViewById(R.id.tv_jifen);
		if (ApplWork.CurrentUser != null )
		{
			hongbaoInfo = new ArrayList<String>();
			tv_JinE.setText(String.valueOf(ApplWork.CurrentUser.getBalance()));
			Cursor cursor = SQLiteDao.query(F_XinXi.this, "redpackage_info", ApplWork.CurrentUser.getPhone());
			if (cursor.moveToFirst() )
			{
				do
				{
					String redpackageid = cursor.getString(cursor.getColumnIndex("redpackageid"));
					int amount = cursor.getInt(cursor.getColumnIndex("amount"));
					String type = cursor.getString(cursor.getColumnIndex("type"));
					String starttime = cursor.getString(cursor.getColumnIndex("starttime"));
					String endtime = cursor.getString(cursor.getColumnIndex("endtime"));
					hongbaoInfo.add(redpackageid + "," + String.valueOf(amount) + "," + type + "," + starttime
							+ "," + endtime);
				} while (cursor.moveToNext());
			}
			tv_HongBao.setText(String.valueOf(hongbaoInfo.size()));
		}
		else
		{
			tv_HongBao.setText("0");
			tv_JiFen.setText("0");
			tv_JinE.setText("0.00");
		}

		hongbaolayout = (LinearLayout) findViewById(R.id.hongbaolayout);
		hongbaolayout.setOnClickListener(new HongbaoLayoutOnclick());

		addresslayout = (LinearLayout) findViewById(R.id.addresslayout);
		addresslayout.setOnClickListener(new AddressLayoutOnclik());

		CollectLayout = (LinearLayout) findViewById(R.id.CollectLayout);
		CollectLayout.setOnClickListener(new CollectOnClick());

		FoodLayout = (LinearLayout) findViewById(R.id.FoodLayout);
		FoodLayout.setOnClickListener(new FoodOnClick());

		jifenlayout = (LinearLayout) findViewById(R.id.jifenlayout);
		jifenlayout.setOnClickListener(new JifenLayout());

		pinfenlayout = (LinearLayout) findViewById(R.id.pinfenlayout);
		pinfenlayout.setOnClickListener(new PinfenOnclick());
	}

	/** 评分 */
	private class PinfenOnclick implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (ApplWork.CurrentUser != null )
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, L_PingfenInfo.class);
				startActivity(intent);
			}
			else
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, Fa_Login.class);
				startActivity(intent);

			}
		}

	}

	/** 积分商城 */
	private class JifenLayout implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (ApplWork.CurrentUser != null )
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, K_JifenShop.class);
				startActivity(intent);

			}
			else
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, Fa_Login.class);
				startActivity(intent);

			}
		}

	}

	/** 美食相册 */
	private class FoodOnClick implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (ApplWork.CurrentUser != null )
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, J_FoodInfo.class);
				startActivity(intent);
			}
			else
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, Fa_Login.class);
				startActivity(intent);

			}
		}

	}

	/** 我的收藏信息栏 */
	private class CollectOnClick implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (ApplWork.CurrentUser != null )
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, I_Collect.class);
				startActivity(intent);
			}
			else
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, Fa_Login.class);
				startActivity(intent);
			}
		}

	}

	/** 地址信息栏 */
	private class AddressLayoutOnclik implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (ApplWork.CurrentUser != null )
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, H_Address.class);
				startActivity(intent);
			}
			else
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, Fa_Login.class);
				startActivity(intent);
			}
		}

	}

	/** 红包的点击事件 */
	private class HongbaoLayoutOnclick implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (ApplWork.CurrentUser != null )
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, G_Hongbao.class);
				intent.putExtra("hongbaoinfo", (Serializable) hongbaoInfo);
				startActivity(intent);
			}
			else
			{
				Intent intent = new Intent();
				intent.setClass(F_XinXi.this, Fa_Login.class);
				startActivity(intent);
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			if ((System.currentTimeMillis() - exitTime) > 2000 )
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}
			else
			{
				finish();
				System.exit(0);
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
