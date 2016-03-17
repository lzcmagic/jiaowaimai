package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Ca_DisPlayPage extends FragmentActivity
{
	private ViewPager mViewPager;

	private List<Fragment> listFrament;

	private String[] candans;
	private TextView ca_shangpin;
	private TextView ca_pinjia;
	private TextView ca_shangjia;

	private ImageView mImage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ca_display);
		initViews();
	}

	@SuppressWarnings("deprecation")
	private void initViews()
	{
		listFrament = new ArrayList<Fragment>();
		listFrament.add(new Cb_ShangPin());
		listFrament.add(new Cd_PinJia());
		listFrament.add(new Ce_ShangJia());
		mViewPager = (ViewPager) findViewById(R.id.ca_viewpager);

		ca_shangpin = (TextView) findViewById(R.id.ca_shangpin);
		ca_shangpin.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mViewPager.setCurrentItem(0);
			}
		});
		ca_pinjia = (TextView) findViewById(R.id.ca_pinjia);
		ca_pinjia.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mViewPager.setCurrentItem(1);
			}
		});
		ca_shangjia = (TextView) findViewById(R.id.ca_shangjia);
		ca_shangjia.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mViewPager.setCurrentItem(2);
			}
		});

		mImage = (ImageView) findViewById(R.id.ca_image);
		mImage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showDialog();
			}
		});

		mViewPager.setAdapter(new PagerFramentAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new Ca_PagerChangeListener());
	}

	private void showDialog()
	{

		// dialog参数设置
		// 先得到构造器
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容
		builder.setIcon(R.drawable.ic_launcher);// 设置图标，图片id即可
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setItems(candans, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

			}
		});
		builder.create().show();
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
			switch (position)
			{
				case 0:
				{
					ca_shangpin.setBackgroundResource(R.drawable.m_select);
					ca_pinjia.setBackgroundColor(Color.parseColor("#ffffff"));
					ca_shangjia.setBackgroundColor(Color.parseColor("#ffffff"));
					break;
				}
				case 1:
				{
					ca_shangpin.setBackgroundColor(Color.parseColor("#ffffff"));
					ca_pinjia.setBackgroundResource(R.drawable.m_select);
					ca_shangjia.setBackgroundColor(Color.parseColor("#ffffff"));
					break;
				}
				case 2:
				{
					ca_shangpin.setBackgroundColor(Color.parseColor("#ffffff"));
					ca_pinjia.setBackgroundColor(Color.parseColor("#ffffff"));
					ca_shangjia.setBackgroundResource(R.drawable.m_select);
					break;
				}
				default:
					break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int state)
		{

		}

	}

	/** 分页的fragment */
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
