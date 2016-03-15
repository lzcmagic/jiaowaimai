package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class C_WaiMai extends Activity
{
	/** 记录按下返回键的时间 */
	private long exitTime = 0;
	private ViewPager mViewPager;
	private LinearLayout mLayout;
	private int lastPosition = 0;

	/** 是否关闭线程的标志位，默认为false:不关闭 */
	private boolean isStop;

	/**
	 * 广告条显示的图片集合
	 */
	private List<ImageView> mImageViews;

	/**
	 * 处理UI线程中的图片自动轮播问题
	 */
	private Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			if (msg.what == 1 )
			{
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			}

		};
	};

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

	@Override
	protected void onStop()
	{
		isStop = true;
		super.onDestroy();
	}

	@Override
	protected void onResume()
	{
		new SQLiteDao(getApplicationContext());
		super.onResume();
	}

	/**
	 * 外卖调研：北京烤鸭，兰州拉面，沙县小吃，黄焖鸡，重庆小面，一号水果摊，梦甜蛋糕店，粤仔湾，肯德基，
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c00_waimai);
		initDate();
		initViewPagerView();
		Thread thread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (!isStop)
				{
					try
					{
						Thread.sleep(3000);
						handler.sendEmptyMessage(1);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();

		initOtherViews();
	}

	/** 初始化其他界面 */
	private void initOtherViews()
	{

	}

	/** 初始化spinner的数据 */
	private void initDate()
	{

	}

	@SuppressWarnings("deprecation")
	private void initViewPagerView()
	{
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mLayout = (LinearLayout) findViewById(R.id.ll_pagerview);
		initData();
		mViewPager.setAdapter(new MyPagerAdapter());
		mViewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
 
			@Override
			public void onPageSelected(int position)
			{
				int newPoition = position % mImageViews.size();

				// 把当前选中的点给切换了, 还有描述信息也切换
				// 设置前一个点为不可点击状态
				mLayout.getChildAt(lastPosition).setEnabled(false);
				mLayout.getChildAt(newPoition).setEnabled(true);
				lastPosition = newPoition;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{

			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});

		// 设置默认选中的点.
		lastPosition = 0;
		mLayout.getChildAt(lastPosition).setEnabled(true);

		// 把ViewPager设置为默认选中Integer.MAX_VALUE / 2;
		int currentPosition = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % mImageViews.size();
		mViewPager.setCurrentItem(currentPosition);
	}

	/** 初始化数据 */
	private void initData()
	{
		int[] images = new int[]
		{
			R.drawable.a,
			R.drawable.b,
			R.drawable.c,
			R.drawable.d
		};
		mImageViews = new ArrayList<ImageView>();
		ImageView iv;
		View v;
		LayoutParams params;
		// 初始化图片集合的内容
		for (int i = 0; i < images.length; i++)
		{
			iv = new ImageView(this);
			iv.setBackgroundResource(images[i]);
			mImageViews.add(iv);

			// 每循环一次需要向LinearLayout一个点的View对象
			v = new View(this);
			v.setBackgroundResource(R.drawable.point_bg);
			params = new LayoutParams(6, 6);
			if (i != 0 )
			{
				params.leftMargin = 10;
			}
			v.setLayoutParams(params);
			v.setEnabled(false);
			mLayout.addView(v);
		}
	}

	private class MyPagerAdapter extends PagerAdapter
	{

		ImageView imageView = null;

		/**
		 * 返回Integer类型的值, 会作为ViewPager的总长度来使用.
		 */
		@Override
		public int getCount()
		{
			return Integer.MAX_VALUE;
		}

		/**
		 * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
		 */
		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		/**
		 * 初始化一个条目 position 就是当前需要加载条目的索引
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			final int newPosition = position % mImageViews.size();
			imageView = new ImageView(C_WaiMai.this);
			imageView = mImageViews.get(newPosition);
			imageView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Toast.makeText(getApplicationContext(), "" + newPosition + "aa", Toast.LENGTH_SHORT)
							.show();
				}
			});
			mViewPager.addView(imageView);
			return imageView;
		}

		/**
		 * 销毁一个条目 position 就是当前需要被销毁的条目的索引
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			mViewPager.removeView(mImageViews.get(position % mImageViews.size()));
		}

	}

}
