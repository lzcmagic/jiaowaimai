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
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fa_Login extends Activity
{
	private Button register, login;
	private EditText username, password;

	private NfcAdapter nfcAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fa00_login);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null )
		{
			Toast.makeText(this, "手机不支持NFC功能", Toast.LENGTH_SHORT).show();
		}
		else
		{
			if (!nfcAdapter.isEnabled() )
			{
				Toast.makeText(this, "请先开通NFC功能", Toast.LENGTH_SHORT).show();
			}
		}
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
						ApplWork.CurrentUser = new CurrentUser();
						ApplWork.CurrentUser.setPassword(password);
						String userid = cursor.getString(cursor.getColumnIndex("userid"));
						ApplWork.CurrentUser.setUserid(userid);
						String username = cursor.getString(cursor.getColumnIndex("username"));
						ApplWork.CurrentUser.setUsername(username);
						String phone = cursor.getString(cursor.getColumnIndex("phone"));
						ApplWork.CurrentUser.setPhone(phone);
						byte[] userpic = cursor.getBlob(cursor.getColumnIndex("userpic"));
						if (userpic != null )
						{
							Bitmap bitmap = BitmapFactory.decodeByteArray(userpic, 0, userpic.length, null);
							ApplWork.CurrentUser.setUserpic(bitmap);
						}
						int balance = cursor.getInt(cursor.getColumnIndex("balance"));
						ApplWork.CurrentUser.setBalance(balance);
						String paypassword = cursor.getString(cursor.getColumnIndex("paypassword"));
						ApplWork.CurrentUser.setPaypassword(paypassword);
						MyToast.show("登录成功！", getApplicationContext());
						Fa_Login.this.finish();
					}
					else
					{
						MyToast.show("密码错误", getApplicationContext());
					}

				}
				else
				{
					MyToast.show("用户名不存在！", getApplicationContext());
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

	@Override
	protected void onResume()
	{
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(this.getIntent().getAction()) )
		{
			processIntent(getIntent());
		}
		super.onResume();
	}

	private void processIntent(Intent intent)
	{
		// 取出封装在intent中的TAG
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		String tagId = this.bytesToHexString(tagFromIntent.getId());
		if (tagId.equals("ec55b080") )
		{
			System.out.println("执行了操作");
			Cursor cursor = SQLiteDao.query(Fa_Login.this, "person_info", "13915873770");
			if (cursor.moveToFirst() )
			{
				String password = cursor.getString(cursor.getColumnIndex("password"));
				if ("admin".equals(password) )
				{
					ApplWork.CurrentUser = new CurrentUser();
					ApplWork.CurrentUser.setPassword(password);
					String userid = cursor.getString(cursor.getColumnIndex("userid"));
					ApplWork.CurrentUser.setUserid(userid);
					String username = cursor.getString(cursor.getColumnIndex("username"));
					ApplWork.CurrentUser.setUsername(username);
					String phone = cursor.getString(cursor.getColumnIndex("phone"));
					ApplWork.CurrentUser.setPhone(phone);
					byte[] userpic = cursor.getBlob(cursor.getColumnIndex("userpic"));
					if (userpic != null )
					{
						Bitmap bitmap = BitmapFactory.decodeByteArray(userpic, 0, userpic.length, null);
						ApplWork.CurrentUser.setUserpic(bitmap);
					}
					int balance = cursor.getInt(cursor.getColumnIndex("balance"));
					ApplWork.CurrentUser.setBalance(balance);
					String paypassword = cursor.getString(cursor.getColumnIndex("paypassword"));
					ApplWork.CurrentUser.setPaypassword(paypassword);
					MyToast.show("登录成功！", getApplicationContext());
					Fa_Login.this.finish();
				}
				else
				{
					MyToast.show("密码错误", getApplicationContext());
				}

			}
			else
			{
				MyToast.show("用户名不存在！", getApplicationContext());
			}
			if (!cursor.isClosed() )
			{
				cursor.close();
			}

		}
	}

	/** 字符序列转换为16进制字符串 */
	private String bytesToHexString(byte[] src)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0 )
		{
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++)
		{
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}

}
