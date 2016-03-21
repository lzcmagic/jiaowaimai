package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.utils.MyToast;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FAa_Register extends Activity
{
	private EditText phone, yanzhengma, password, passwordagin;
	private Button yanzhengButton, registerButton;
	private CheckBox check;

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
				checkPhone();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});
		yanzhengma = (EditText) findViewById(R.id.OaX10);
		password = (EditText) findViewById(R.id.OaX02);
		passwordagin = (EditText) findViewById(R.id.OaX03);

		yanzhengButton = (Button) findViewById(R.id.OaB11);
		registerButton = (Button) findViewById(R.id.OaB09);

		check = (CheckBox) findViewById(R.id.OaK06);
	}

	private void checkPhone()
	{
		String reg = "[1][358]\\d{9}";
		if (!"".equals(phone.getText().toString()) && phone.getText().toString().matches(reg) )
		{
			yanzhengButton.setBackgroundColor(Color.parseColor("#bbbbbb"));
		}
		else
		{

		}

	}
}
