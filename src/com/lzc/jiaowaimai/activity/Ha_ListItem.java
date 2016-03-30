package com.lzc.jiaowaimai.activity;

import java.io.IOException;
import java.io.InputStream;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import kankan.wheel.widget.WheelView;

public class Ha_ListItem extends Activity
{
	private TextView phone;
	private EditText name;
	private WheelView province, city, country;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hi00_item);
		try
		{
			InputStream stream = this.getAssets().open("a.json");
			System.out.println("======" + stream.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		name = (EditText) findViewById(R.id.hi_name);
		phone = (TextView) findViewById(R.id.hi_phone);
		phone.setText(ApplWork.CurrentUser.getPhone());
		province = (WheelView) findViewById(R.id.province);
		// province.setViewAdapter(new ArrayWheelAdapter<String>(this, items));

		city = (WheelView) findViewById(R.id.city);
		country = (WheelView) findViewById(R.id.country);
	}
}
