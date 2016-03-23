package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class FBb_Update_Info extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fbb00_change_info);
	}

}
