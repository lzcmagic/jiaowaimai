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
	/** ��¼���·��ؼ���ʱ�� */
	private long exitTime = 0;
	private ViewPager mViewPager;
	private LinearLayout mLayout;
	private int lastPosition = 0;

	/** �Ƿ�ر��̵߳ı�־λ��Ĭ��Ϊfalse:���ر� */
	private boolean isStop;

	/**
	 * �������ʾ��ͼƬ����
	 */
	private List<ImageView> mImageViews;

	/**
	 * ����UI�߳��е�ͼƬ�Զ��ֲ�����
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
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
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
	 * �������У�������Ѽ���������棬ɳ��С�ԣ����˼�������С�棬һ��ˮ��̯�����𵰸�꣬�����壬�ϵ»���
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

	/** ��ʼ���������� */
	private void initOtherViews()
	{

	}

	/** ��ʼ��spinner������ */
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

				// �ѵ�ǰѡ�еĵ���л���, ����������ϢҲ�л�
				// ����ǰһ����Ϊ���ɵ��״̬
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

		// ����Ĭ��ѡ�еĵ�.
		lastPosition = 0;
		mLayout.getChildAt(lastPosition).setEnabled(true);

		// ��ViewPager����ΪĬ��ѡ��Integer.MAX_VALUE / 2;
		int currentPosition = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % mImageViews.size();
		mViewPager.setCurrentItem(currentPosition);
	}

	/** ��ʼ������ */
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
		// ��ʼ��ͼƬ���ϵ�����
		for (int i = 0; i < images.length; i++)
		{
			iv = new ImageView(this);
			iv.setBackgroundResource(images[i]);
			mImageViews.add(iv);

			// ÿѭ��һ����Ҫ��LinearLayoutһ�����View����
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
		 * ����Integer���͵�ֵ, ����ΪViewPager���ܳ�����ʹ��.
		 */
		@Override
		public int getCount()
		{
			return Integer.MAX_VALUE;
		}

		/**
		 * �ж��Ƿ�ʹ�û���, ������ص���true, ʹ�û���. ��ȥ����instantiateItem��������һ���µĶ���
		 */
		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		/**
		 * ��ʼ��һ����Ŀ position ���ǵ�ǰ��Ҫ������Ŀ������
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
		 * ����һ����Ŀ position ���ǵ�ǰ��Ҫ�����ٵ���Ŀ������
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			mViewPager.removeView(mImageViews.get(position % mImageViews.size()));
		}

	}

}
