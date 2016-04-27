package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.LocalSQLite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class C_WaiMai extends Activity
{
	/** 记录按下返回键的时间 */
	private long exitTime = 0;
	private ViewPager mViewPager;
	private LinearLayout mLayout;
	private int lastPosition = 0;

	private List<IndexShow> sqlList = new ArrayList<IndexShow>();

	private IndexAdapter indexAdapter;

	/** 是否关闭线程的标志位，默认为false:不关闭 */
	private boolean isStop;

	/**
	 * 广告条显示的图片集合
	 */
	private List<ImageView> mImageViews;

	/** 按种类排序的Spinner */
	private Spinner typeSpinner;
	/** 按热度排序的Spinner */
	private Spinner hotSpinner;
	/** 种类的集合 */
	private List<String> typeList;
	/** 热度的集合 */
	private List<String> hotList;

	private Map<String, String> map;

	/** 参观的ListView */
	private ListView mListView;

	/** 地址信息显示的text */
	private TextView c00_title;

	/** 百度地图定位相关 */
	public LocationClient mLocationClient = null;

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
		c00_title = (TextView) findViewById(R.id.c00_title);
		mLocationClient = new LocationClient(getApplicationContext());
		LocationClientOption option = new LocationClientOption();
		// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationDescribe(true);
		// 可选，设置是否需要地址信息，默认不需要
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		// 声明LocationClient类
		// 注册监听函数
		mLocationClient.registerLocationListener(new MyLocationListener());
		mLocationClient.start();
		initOtherViews();
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		mLocationClient.stop();
		super.onPause();
	}

	/** 百度地图定位接口 */
	private class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation location)
		{
			String str = location.getAddrStr().replaceFirst("中国", "");
			String strDescribe = location.getLocationDescribe();
			c00_title.setText(str + ":" + strDescribe);
		}

	}

	/**
	 * 外卖调研：北京烤鸭，兰州拉面，沙县小吃，黄焖鸡，重庆小面，一号水果摊，梦甜蛋糕店，粤仔湾，肯德基，
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

	/** 初始化spinner的数据 */
	private void initSpinnerDate()
	{
		typeList = new ArrayList<String>();
		typeList.add("分类");
		typeList.add("快餐");
		typeList.add("正餐");
		typeList.add("小吃");
		typeList.add("水果");
		typeList.add("甜品");

		hotList = new ArrayList<String>();
		map = new HashMap<String, String>();
		hotList.add("排序");
		hotList.add("销量最高");
		map.put("销量最高", "sales");
		hotList.add("评分最高");
		map.put("评分最高", "score");
		hotList.add("起送价最低");
		map.put("起送价最低", "startrunmoney");
		hotList.add("配送速度最快");
		map.put("配送速度最快", "runspeed");
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

	/** 初始化其他界面 */
	private void initOtherViews()
	{
		// 种类
		typeSpinner = (Spinner) findViewById(R.id.s_type);
		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, typeList);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(typeAdapter);
		// typeSpinner.setSelected(false);
		typeSpinner.setOnItemSelectedListener(new TypeOnItemClickListener());

		// 排序
		hotSpinner = (Spinner) findViewById(R.id.s_hot);
		ArrayAdapter<String> hotAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				hotList);
		hotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		hotSpinner.setAdapter(hotAdapter);
		hotSpinner.setOnItemSelectedListener(new SortOnItemClickListener());

		// 餐馆的ListView
		mListView = (ListView) findViewById(R.id.lv_index);
		indexAdapter = new IndexAdapter(getApplicationContext());
		mListView.setAdapter(indexAdapter);

		mListView.setOnItemClickListener(new ListViewItemClickListener());
	}

	/** adapter的数据 */
	private void initSqlDataForSpinner(String typeText, String hotText)
	{

		LocalSQLite localSQLite = new LocalSQLite(getApplicationContext(), LocalSQLite.BD_NAME, null,
				LocalSQLite.VERSION);
		SQLiteDatabase data = localSQLite.getReadableDatabase();
		Cursor cursor = null;
		if (typeText.equals("分类") )
		{
			if (hotText.equals("排序") )
			{
				cursor = data.rawQuery("select * from restaurant_info", null);
			}
			else
			{
				if (hotText.equals("销量最高") || hotText.equals("评分最高") )
				{
					cursor = data.rawQuery(
							"select * from restaurant_info order by " + map.get(hotText) + " desc", null);
				}
				else
				{
					cursor = data.rawQuery(
							"select * from restaurant_info order by " + map.get(hotText) + " asc", null);
				}
			}
		}
		else
		{
			if (hotText.equals("排序") )
			{
				cursor = data.rawQuery("select * from restaurant_info where restype=" + "'" + typeText + "'",
						null);
			}
			else
			{
				if (hotText.equals("销量最高") || hotText.equals("评分最高") )
				{
					cursor = data.rawQuery("select * from restaurant_info where restype=" + "'" + typeText
							+ "'" + " order by " + map.get(hotText) + " desc", null);
				}
				else
				{
					cursor = data.rawQuery("select * from restaurant_info where restype=" + "'" + typeText
							+ "'" + " order by " + map.get(hotText) + " asc", null);
				}
			}

		}
		if (cursor.moveToFirst() )
		{
			do
			{
				IndexShow show = new IndexShow();
				String resid = cursor.getString(cursor.getColumnIndex("restaurantid"));
				show.setResid(resid);
				byte[] bs = cursor.getBlob(cursor.getColumnIndex("canguanpic"));
				// 将获得的byte数组转换成bitmap
				Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length, null);
				show.setBitmap(bitmap);
				String resname = cursor.getString(cursor.getColumnIndex("resname"));
				show.setResname(resname);
				int startrunmoney = cursor.getInt(cursor.getColumnIndex("startrunmoney"));
				show.setStartrunmoney(startrunmoney);
				String sales = cursor.getString(cursor.getColumnIndex("sales"));
				show.setSales(sales);
				int respeisong = cursor.getInt(cursor.getColumnIndex("respeisong"));
				show.setRespeisong(respeisong);
				String restype = cursor.getString(cursor.getColumnIndex("restype"));
				show.setRestype(restype);
				String score = cursor.getString(cursor.getColumnIndex("score"));
				show.setScore(score);
				String runspeed = cursor.getString(cursor.getColumnIndex("runspeed"));
				show.setRunspeed(runspeed);
				String resaddressid = cursor.getString(cursor.getColumnIndex("resaddressid"));
				show.setResaddressid(resaddressid);
				sqlList.add(show);
			} while (cursor.moveToNext());
		}
		cursor.close();

	}

	/** listView点击事件 */
	private class ListViewItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), Ca_DisPlayPage.class);
			intent.putExtra("currentres_info", sqlList.get(position).toString());
			Bitmap bitmap = sqlList.get(position).getBitmap();
			intent.putExtra("currentres_bitmap", bitmap);
			startActivity(intent);
		}

	}

	class ViewHolder
	{
		/** 商户头像 */
		ImageView tx_image;
		/** 商户名字 */
		TextView name_text;
		/** 商户起送价 */
		TextView qisongjia_text;
		/** 商户配送费 */
		TextView peisongfei_text;
		/** 月销售单数 */
		TextView num_text;
		/** 商户评分 */
		TextView fenshu_text;
		/** 展示评分的RatingBar */
		RatingBar mRatingBar;
		/** 评分 */
		TextView score;
	}

	/** 主页ListView的适配器 */
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
			return sqlList.size();
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
				holder.fenshu_text = (TextView) convertView.findViewById(R.id.tv_fenshu);
				holder.mRatingBar = (RatingBar) convertView.findViewById(R.id.ratingBar11);
				holder.score = (TextView) convertView.findViewById(R.id.tv_fenshu);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tx_image.setImageBitmap(sqlList.get(position).getBitmap());
			holder.name_text.setText(sqlList.get(position).getResname());
			holder.qisongjia_text.setText(String.valueOf(sqlList.get(position).getStartrunmoney()));
			holder.peisongfei_text
					.setText("¥" + String.valueOf(sqlList.get(position).getRespeisong()) + "配送费");
			holder.num_text.setText(sqlList.get(position).getSales());
			holder.fenshu_text.setText(sqlList.get(position).getScore());
			holder.mRatingBar.setRating(Float.parseFloat(sqlList.get(position).getScore()));
			holder.score.setText(String.valueOf(sqlList.get(position).getScore()));
			return convertView;
		}

	}

	/** 按热度排序 */
	private class SortOnItemClickListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{
			sqlList.clear();
			initSqlDataForSpinner(typeSpinner.getSelectedItem().toString(),
					parent.getSelectedItem().toString());
			indexAdapter.notifyDataSetChanged();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{

		}

	}

	/** 按种类筛选 */
	private class TypeOnItemClickListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{

			sqlList.clear();
			initSqlDataForSpinner(parent.getSelectedItem().toString(),
					hotSpinner.getSelectedItem().toString());
			indexAdapter.notifyDataSetChanged();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{

		}

	}

	/** PagerAdapter的适配器 */
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
					// TODO:放网页
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

	class IndexShow
	{
		/** 餐馆主键 */
		String resid;
		/** 餐馆头像 */
		Bitmap bitmap;
		/** 餐馆名称 */
		String resname;
		/** 餐馆起步价 */
		int startrunmoney;
		/** 餐馆配送费 */
		int respeisong;
		/** 餐馆销售量 */
		String sales;
		/** 餐馆种类 */
		String restype;
		/** 餐馆评分 */
		String score;
		/** 餐馆送餐速度 */
		String runspeed;
		/** 餐馆地址id */
		String resaddressid;

		public String getRunspeed()
		{
			return runspeed;
		}

		public void setRunspeed(String runspeed)
		{
			this.runspeed = runspeed;
		}

		public Bitmap getBitmap()
		{
			return bitmap;
		}

		public void setBitmap(Bitmap bitmap)
		{
			this.bitmap = bitmap;
		}

		public String getResname()
		{
			return resname;
		}

		public void setResname(String resname)
		{
			this.resname = resname;
		}

		public int getStartrunmoney()
		{
			return startrunmoney;
		}

		public String getResaddressid()
		{
			return resaddressid;
		}

		public void setResaddressid(String resaddressid)
		{
			this.resaddressid = resaddressid;
		}

		public void setStartrunmoney(int startrunmoney)
		{
			this.startrunmoney = startrunmoney;
		}

		public int getRespeisong()
		{
			return respeisong;
		}

		public void setRespeisong(int respeisong)
		{
			this.respeisong = respeisong;
		}

		public String getSales()
		{
			return sales;
		}

		public void setSales(String sales)
		{
			this.sales = sales;
		}

		public String getRestype()
		{
			return restype;
		}

		public void setRestype(String restype)
		{
			this.restype = restype;
		}

		public String getScore()
		{
			return score;

		}

		public String getResid()
		{
			return resid;
		}

		public void setResid(String resid)
		{
			this.resid = resid;
		}

		public void setScore(String score)
		{
			this.score = score;
		}

		@Override
		public String toString()
		{
			return "resid=" + resid + ", resname=" + resname + ", startrunmoney=" + startrunmoney
					+ ", respeisong=" + respeisong + ", sales=" + sales + ", restype=" + restype + ", score="
					+ score + ", runspeed=" + runspeed + ", resaddressid=" + resaddressid;
		}

	}

}
