package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.bean.CurrentUser;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Fa_Login extends Activity
{
	private Button register, login;
	private EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fa00_login);
		initEditViews();
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

		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Cursor cursor = SQLiteDao.query(Fa_Login.this, "person_info", username.getText().toString());
				if (cursor.moveToFirst() )
				{
					String password = cursor.getString(cursor.getColumnIndex("password"));
					if (Fa_Login.this.password.getText().toString().equals(password) )
					{
						MyToast.show("��¼�ɹ���", getApplicationContext());
						CurrentUser user = new CurrentUser();
						user.setPassword(password);
						String userid = cursor.getString(cursor.getColumnIndex("userid"));
						user.setUserid(userid);
						String username = cursor.getString(cursor.getColumnIndex("username"));
						user.setUsername(username);
						String phone = cursor.getString(cursor.getColumnIndex("phone"));
						user.setPhone(phone);
						byte[] userpic = cursor.getBlob(cursor.getColumnIndex("userpic"));
						if (userpic != null )
						{
							Bitmap bitmap = BitmapFactory.decodeByteArray(userpic, 0, userpic.length, null);
							user.setUserpic(bitmap);
						}
						int balance = cursor.getInt(cursor.getColumnIndex("balance"));
						user.setBalance(balance);
						String addressid = cursor.getString(cursor.getColumnIndex("addressid"));
						user.setAddressid(addressid);
						String redpackageid = cursor.getString(cursor.getColumnIndex("redpackageid"));
						user.setRedpackageid(redpackageid);
						ApplWork.CurrentUser = user;
						Fa_Login.this.finish();
					}
					else
					{
						MyToast.show("�������", getApplicationContext());
					}

				}
				else
				{
					MyToast.show("�û��������ڣ�", getApplicationContext());
				}
				if (!cursor.isClosed() )
				{
					cursor.close();
				}
			}
		});
	}

	private void initEditViews()
	{
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
	}

}
