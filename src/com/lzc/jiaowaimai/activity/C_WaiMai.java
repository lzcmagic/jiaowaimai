package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

	/** �����������Spinner */
	private Spinner typeSpinner;
	/** ���ȶ������Spinner */
	private Spinner hotSpinner;
	/** ����ļ��� */
	private List<String> typeList;
	/** �ȶȵļ��� */
	private List<String> hotList;

	/** �ι۵�ListView */
	private ListView mListView;

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
		initSpinnerDate();
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

	/** ��ʼ��spinner������ */
	private void initSpinnerDate()
	{
		typeList = new ArrayList<String>();
		typeList.add("����");
		typeList.add("���");
		typeList.add("����");
		typeList.add("С����ʳ");
		typeList.add("��������");

		hotList = new ArrayList<String>();
		hotList.add("����");
		hotList.add("�������");
		hotList.add("�������");
		hotList.add("���ͼ����");
		hotList.add("�����ٶ����");
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

	/** ��ʼ���������� */
	private void initOtherViews()
	{
		// ����
		typeSpinner = (Spinner) findViewById(R.id.s_type);
		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, typeList);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(typeAdapter);
		typeSpinner.setOnItemSelectedListener(new TypeOnItemClickListener());

		// ����
		hotSpinner = (Spinner) findViewById(R.id.s_hot);
		ArrayAdapter<String> hotAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				hotList);
		hotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		hotSpinner.setAdapter(hotAdapter);
		hotSpinner.setOnItemSelectedListener(new SortOnItemClickListener());

		// �͹ݵ�ListView
		mListView = (ListView) findViewById(R.id.lv_index);
		mListView.setAdapter(new IndexAdapter(getApplicationContext()));

		mListView.setOnItemClickListener(new ListViewItemClickListener());
	}

	/** listView����¼� */
	private class ListViewItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), Ca_DisPlayPage.class);
			
			startActivity(intent);
		}

	}

	class ViewHolder
	{
		/** �̻�ͷ�� */
		ImageView tx_image;
		/** �̻����� */
		TextView name_text;
		/** �̻����ͼ� */
		TextView qisongjia_text;
		/** �̻����ͷ� */
		TextView peisongfei_text;
		/** �����۵��� */
		TextView num_text;
		/** �̻����� */
		TextView fenshu_text;
	}

	/** ��ҳListView�������� */
	private class IndexAdapter extends BaseAdapter
	{

		LayoutInflater mInflater;

		public IndexAdapter(Context contex)
		{
			this.mInflater = LayoutInflater.from(contex);
		}

		@Override
		public int getCount()
		{
			return 5;
		}

		@Override
		public Object getItem(int position)
		{
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null )
			{
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.index_list_view, null);
				holder.tx_image = (ImageView) convertView.findViewById(R.id.index_iv);
				holder.name_text = (TextView) convertView.findViewById(R.id.index_name);
				holder.qisongjia_text = (TextView) convertView.findViewById(R.id.tv_price);
				holder.peisongfei_text = (TextView) convertView.findViewById(R.id.tv_peisongfei);
				holder.num_text = (TextView) convertView.findViewById(R.id.tv_danshu);
				holder.fenshu_text = (TextView) findViewById(R.id.tv_fenshu);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

	}

	/** ���ȶ����� */
	private class SortOnItemClickListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{

		}

	}

	/** ������ɸѡ */
	private class TypeOnItemClickListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{
			MyToast.show((String) parent.getSelectedItem(), getApplicationContext());

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{

		}

	}

	/** PagerAdapter�������� */
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
