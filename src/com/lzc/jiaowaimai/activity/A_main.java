package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class A_main extends TabActivity implements OnCheckedChangeListener
{

	private TabHost mTabHost;
	private RadioGroup mRadioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.a00_main);
		mTabHost = getTabHost();
		mTabHost.setup();

		// 第一个界面
		mTabHost.addTab(
				mTabHost.newTabSpec("tab1").setIndicator("1").setContent(new Intent(this, C_WaiMai.class)));

		// 第二个界面
		mTabHost.addTab(
				mTabHost.newTabSpec("tab2").setIndicator("2").setContent(new Intent(this, D_DingDan.class)));

		// 第三个界面
		mTabHost.addTab(
				mTabHost.newTabSpec("tab3").setIndicator("3").setContent(new Intent(this, E_FaXian.class)));

		// 第三个界面
		mTabHost.addTab(
				mTabHost.newTabSpec("tab4").setIndicator("4").setContent(new Intent(this, F_XinXi.class)));

		mRadioGroup = (RadioGroup) findViewById(R.id.main_button);
		mRadioGroup.setOnCheckedChangeListener(this);
		mRadioGroup.check(R.id.rbb1);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{

		switch (checkedId)
		{
			case R.id.rbb1:
			{
				mTabHost.setCurrentTabByTag("tab1");
				break;
			}
			case R.id.rbb2:
			{
				mTabHost.setCurrentTabByTag("tab2");
				break;
			}
			case R.id.rbb3:
			{
				mTabHost.setCurrentTabByTag("tab3");
				break;
			}
			case R.id.rbb4:
			{
				mTabHost.setCurrentTabByTag("tab4");
				break;
			}
			default:
				break;
		}

	}
}
