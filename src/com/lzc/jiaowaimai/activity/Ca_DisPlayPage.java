package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;

public class Ca_DisPlayPage extends FragmentActivity
{
	private ViewPager mViewPager;

	private List<Fragment> listFrament;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ca_display);
		initViewPager();
	}

	private void initViewPager()
	{
		listFrament = new ArrayList<Fragment>();
		listFrament.add(new Cb_ShangPin());
		listFrament.add(new Cd_PinJia());
		listFrament.add(new Ce_ShangJia());
		mViewPager = (ViewPager) findViewById(R.id.ca_viewpager);
		mViewPager.setAdapter(new PagerFramentAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new Ca_PagerChangeListener());
	}

	private class Ca_PagerChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{

		}

		@Override
		public void onPageSelected(int position)
		{

		}

		@Override
		public void onPageScrollStateChanged(int state)
		{

		}

	}

	/** ·ÖÒ³µÄfragment */
	class PagerFramentAdapter extends FragmentPagerAdapter
	{

		public PagerFramentAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			return listFrament.get(position);
		}

		@Override
		public int getCount()
		{
			return listFrament.size();
		}

	}
}
