package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class F_XinXi extends Activity
{
	/** 记录按下返回键的时间 */
	private long exitTime = 0;

	private ImageView tx_image;
	private TextView username, userphone;

	private LinearLayout InfoLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f00_xinxi);

	}

	@Override
	protected void onResume()
	{
		initViews();
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

		tx_image = (ImageView) findViewById(R.id.iv_touxiang);
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
