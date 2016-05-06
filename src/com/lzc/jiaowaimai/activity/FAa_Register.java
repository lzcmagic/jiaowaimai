package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class FAa_Register extends Activity
{
	private EditText phone, yanzhengma, password, passwordagain;
	private Button yanzhengButton, registerButton;
	private CheckBox check;
	private int randomText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.faa00_register);
		initViews();
	}

	private void initViews()
	{
		phone = (EditText) findViewById(R.id.OaX01);
		phone.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				checkPhone(s);
			}
		});
		yanzhengma = (EditText) findViewById(R.id.OaX10);
		password = (EditText) findViewById(R.id.OaX02);
		passwordagain = (EditText) findViewById(R.id.OaX03);
		passwordagain.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if (!checkPassIsConsistent() )
				{
					MyToast.show("密码输入不一致", getApplicationContext());
				}
			}
		});

		yanzhengButton = (Button) findViewById(R.id.OaB11);
		yanzhengButton.setOnClickListener(new YanZhenButtonOnclickListener());
		registerButton = (Button) findViewById(R.id.OaB09);
		registerButton.setOnClickListener(new RegisterOnclickListener());
		check = (CheckBox) findViewById(R.id.OaK06);
	}

	/** 检查密码输入是否一致 */
	protected boolean checkPassIsConsistent()
	{
		if (password.getText().toString().equals(passwordagain.getText().toString()) )
		{
			return true;
		}
		return false;
	}

	/** 注册按钮事件 */
	private class RegisterOnclickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (String.valueOf(randomText).equals(yanzhengma.getText().toString())
					&& checkPassIsConsistent() )
			{
				if (check.isChecked() )
				{
					SQLiteDao.insert(getApplicationContext(), phone.getText().toString(),
							password.getText().toString());

					MyToast.show("注册成功！", getApplicationContext());
					FAa_Register.this.finish();
				}
				else
				{
					MyToast.show("未同意用户注册协议", getApplicationContext());
				}

			}
			else
			{
				MyToast.show("验证码不正确/密码输入不一致", getApplicationContext());
			}
		}

	}

	/** 验证码点击事件 */
	private class YanZhenButtonOnclickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			CountDownTimer timer = new CountDownTimer(30000, 1000)
			{

				@Override
				public void onTick(long millisUntilFinished)
				{
					yanzhengButton.setBackgroundColor(Color.parseColor("#c0c0c0"));
					yanzhengButton.setEnabled(false);
					yanzhengButton.setClickable(false);
					yanzhengButton.setText(String.valueOf(millisUntilFinished / 1000));

				}

				@Override
				public void onFinish()
				{
					yanzhengButton.setBackgroundColor(Color.parseColor("#008DE1"));
					yanzhengButton.setEnabled(true);
					yanzhengButton.setClickable(true);
					yanzhengButton.setText("获取验证码");
				}
			};
			timer.start();
			showYanZhenDialog(true);
		}

	}

	/** 检查手机号是否被可用 */
	private void checkPhone(Editable s)
	{
		String reg = "[1][358]\\d{9}";
		String phone = s.toString();
		Cursor cursor = SQLiteDao.query(getApplicationContext(), "person_info", phone);
		if (cursor.moveToFirst() )
		{
			MyToast.show("手机号已注册", getApplicationContext());
		}
		else
		{
			if (!"".equals(phone) && phone.matches(reg) )
			{
				yanzhengButton.setBackgroundColor(Color.parseColor("#008DE1"));
				yanzhengButton.setEnabled(true);
				yanzhengButton.setClickable(true);
			}
			else
			{
				yanzhengButton.setBackgroundColor(Color.parseColor("#c0c0c0"));
				yanzhengButton.setEnabled(false);
				yanzhengButton.setClickable(false);
			}
		}

		if (!cursor.isClosed() )
		{
			cursor.close();
		}

	}

	public void showYanZhenDialog(boolean flag)
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(FAa_Register.this);
		builder.setTitle("验证码");
		randomText = (int) ((Math.random() * 9 + 1) * 100000);
		builder.setMessage(String.valueOf(randomText));
		if (flag )
		{
			builder.create().show();
			CountDownTimer timer = new CountDownTimer(30000, 1000)
			{

				@Override
				public void onTick(long millisUntilFinished)
				{

				}

				@Override
				public void onFinish()
				{
					builder.create().dismiss();
				}

			};
			timer.start();
		}
		else
		{
			builder.create().dismiss();
		}
	}
}
