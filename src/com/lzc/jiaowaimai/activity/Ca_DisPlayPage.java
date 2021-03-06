package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.bean.CollectRes;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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

	// 店名，起送价，配送费，配餐速度
	private TextView cal01, ca_qisong, ca_peisong, ca_runspeed;

	private ImageView dingdanImage, touxaingImage, collectImage;

	private String[] array = null;
	private Bitmap bitmap;
	public String resid;
	public String resname;

	private TextView jiesuan;
	/** 记录按下次数，奇数次为收藏，偶数次为未收藏 */
	private int collectNum;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ca_display);
		array = getIntent().getStringExtra("currentres_info").split(",");
		System.out.println(getIntent().getStringExtra("currentres_info"));
		resname = array[1].split("=")[1];
		bitmap = getIntent().getParcelableExtra("currentres_bitmap");
		initViews();
	}

	@Override
	protected void onResume()
	{
		jiesuan = (TextView) findViewById(R.id.ca_jiesuan);
		int moneySum = 0;
		for (int money : ApplWork.OrderMealMoneyList)
		{
			moneySum = moneySum + money;
		}
		jiesuan.setText("总计：" + String.valueOf(moneySum) + "元");
		super.onResume();
	}

	@SuppressWarnings("deprecation")
	private void initViews()
	{
		resid = array[0].split("=")[1];
		touxaingImage = (ImageView) findViewById(R.id.cap02);
		touxaingImage.setImageBitmap(bitmap);
		collectImage = (ImageView) findViewById(R.id.collectImage);
		if (ApplWork.ResMap.containsKey(resid) )
		{
			collectNum = 1;
			collectImage.setImageResource(R.drawable.collectt);
		}

		final String[] newArray = array[1].split("=");
		cal01 = (TextView) findViewById(R.id.cal01);
		cal01.setText(newArray[1]);

		ca_qisong = (TextView) findViewById(R.id.ca_qisong);
		ca_qisong.setText(array[2].split("=")[1] + "元起送");

		ca_peisong = (TextView) findViewById(R.id.ca_peisong);
		ca_peisong.setText(array[3].split("=")[1] + "元配送费");

		ca_runspeed = (TextView) findViewById(R.id.ca_runspeed);
		ca_runspeed.setText(array[7].split("=")[1] + "分钟内送达");

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

		dingdanImage = (ImageView) findViewById(R.id.ca_image);
		dingdanImage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showDialog();
			}
		});

		mViewPager.setAdapter(new PagerFramentAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new Ca_PagerChangeListener());

		collectImage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				collectNum++;
				if (ApplWork.CurrentUser != null )
				{
					System.out.println("点击了收藏");
					CollectRes res = new CollectRes();
					res.setLogo(bitmap);
					res.setName(newArray[1]);
					res.setPeisong(array[3].split("=")[1]);
					res.setQisong(array[2].split("=")[1]);
					res.setResid(resid);
					if (collectNum != 0 && collectNum % 2 != 0 )
					{
						// 已收藏状态->存储数据并更换图片
						ApplWork.ResMap.put(resid, res);
						collectImage.setImageResource(R.drawable.collectt);
						MyToast.show("收藏成功", Ca_DisPlayPage.this);
					}
					else
					{
						ApplWork.ResMap.remove(resid);
						collectImage.setImageResource(R.drawable.collect);
						MyToast.show("取消收藏", Ca_DisPlayPage.this);
					}
				}
				else
				{
					MyToast.show("请先登录！", Ca_DisPlayPage.this);
				}

			}
		});

	}

	private void showDialog()
	{

		// dialog参数设置
		// 先得到构造器
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("已选择"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < ApplWork.OrderMealList.size(); i++)
		{
			Map<String, String> map = ApplWork.OrderMealList.get(i);
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext())
			{
				Entry<String, String> entry = iterator.next();
				String name = entry.getKey();
				String num = entry.getValue();
				list.add(name + "\t\t\t" + num + "份");
			}

		}
		candans = list.toArray(new String[list.size()]);
		builder.setItems(candans, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// dialog.dismiss();
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
