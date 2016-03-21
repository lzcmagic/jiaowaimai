package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Fa_Login extends Activity
{
	private Button register,login;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fa00_login);
		register = (Button) findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), FAa_Register.class);
				startActivity(intent);
			}
		});
		
		login=(Button) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
			}
		});
	}
	
}
