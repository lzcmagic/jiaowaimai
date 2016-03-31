package com.lzc.jiaowaimai.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

public class Ha_ListItem extends Activity
{
	private TextView phone, address;
	private WheelView provincewheel, citywheel, countrywheel;
	/** 省级数据 */
	private List<String> provincedata;
	/** 市级数据 */
	private Map<String, List<String>> citydata;

	private Map<String, List<String>> countrydata;
	/** 省级菜单是否滑动 默认false 不滑动 */
	private boolean ProvinceIsScroll = false;

	/** 市级菜单是否滑动 默认false 不滑动 */
	private boolean CityIsScroll = false;

	private TextView name;

	private EditText street;

	private Button insert;

	private String provinceaddress;
	private String cityaddress;
	private String countryaddress;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hi00_item);
	}

	@Override
	protected void onStart()
	{
		initData();
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		initView();
		super.onResume();
	}

	private void initView()
	{
		name = (TextView) findViewById(R.id.hi_name);
		name.setText(ApplWork.CurrentUser.getUsername());
		street = (EditText) findViewById(R.id.hi_street);
		street.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				// 当前选中的省级数据位置
				int i = provincewheel.getCurrentItem();
				provinceaddress = provincedata.get(i);

				// 市级数据的位置
				int j = citywheel.getCurrentItem();
				cityaddress = citydata.get(provinceaddress).get(j);

				// 区县级位置
				int k = countrywheel.getCurrentItem();
				countryaddress = countrydata.get(cityaddress).get(k);

				address.setText(provinceaddress + cityaddress + countryaddress + s.toString());
			}
		});
		phone = (TextView) findViewById(R.id.hi_phone);
		phone.setText(ApplWork.CurrentUser.getPhone());
		address = (TextView) findViewById(R.id.hi_address);

		insert = (Button) findViewById(R.id.hi_insert);
		insert.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				SQLiteDao.insertAddress(Ha_ListItem.this, provinceaddress, cityaddress, countryaddress,
						street.getText().toString(), phone.getText().toString());
				MyToast.show("保存成功", Ha_ListItem.this);
				Ha_ListItem.this.finish();
			}
		});

		provincewheel = (WheelView) findViewById(R.id.province);
		citywheel = (WheelView) findViewById(R.id.city);
		countrywheel = (WheelView) findViewById(R.id.country);

		provincewheel.setViewAdapter(new ProvinceWheelAdapter(this));
		provincewheel.addChangingListener(new OnWheelChangedListener()
		{

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				if (!ProvinceIsScroll )
				{
					updateCities(citywheel, citydata, newValue);
				}
			}
		});

		provincewheel.addScrollingListener(new OnWheelScrollListener()
		{

			@Override
			public void onScrollingStarted(WheelView wheel)
			{
				ProvinceIsScroll = true;
			}

			@Override
			public void onScrollingFinished(WheelView wheel)
			{
				ProvinceIsScroll = false;
				updateCities(citywheel, citydata, provincewheel.getCurrentItem());
			}
		});
		provincewheel.setCurrentItem(1);

	}

	private void initData()
	{
		try
		{
			InputStream stream = this.getAssets().open("a.json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				buffer.append(line);
			}
			try
			{
				// 去除省级数据
				JSONObject object = new JSONObject(buffer.toString());
				JSONArray array = object.optJSONArray("p");
				provincedata = new ArrayList<String>();
				for (int i = 0; i < array.length(); i++)
				{
					provincedata.add(array.optString(i));
				}

				// 去除市级数据
				JSONObject object2 = object.optJSONObject("c");
				citydata = new HashMap<String, List<String>>();
				for (int i = 0; i < provincedata.size(); i++)
				{
					List<String> coutrylist = new ArrayList<String>();
					for (int j = 0; j < object2.optJSONArray(provincedata.get(i)).length(); j++)
					{
						coutrylist.add(object2.optJSONArray(provincedata.get(i)).optString(j));
					}
					citydata.put(provincedata.get(i), coutrylist);
				}

				// 取出区级数据
				JSONObject object3 = object.optJSONObject("a");
				countrydata = new HashMap<String, List<String>>();

				for (int i = 0; i < provincedata.size(); i++)
				{
					String provincestr = provincedata.get(i);
					for (int j = 0; j < citydata.get(provincestr).size(); j++)
					{
						String citystr = citydata.get(provincestr).get(j);
						String countrykey = provincestr + "-" + citystr;
						JSONArray arraycountry = object3.optJSONArray(countrykey);
						List<String> listcountry = new ArrayList<String>();
						for (int k = 0; k < arraycountry.length(); k++)
						{
							String countryname = arraycountry.optString(k);
							listcountry.add(countryname);
							System.out.println("循环中的countryname" + countryname);
						}
						countrydata.put(citystr, listcountry);
					}
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/** 刷新city数据 */
	protected void updateCities(WheelView city2, Map<String, List<String>> citydata2, int newValue)
	{
		CityWheelAdapter adapter = new CityWheelAdapter(Ha_ListItem.this,
				citydata2.get(provincedata.get(newValue)));
		city2.setViewAdapter(adapter);
		city2.setCurrentItem(citydata2.get(provincedata.get(newValue)).size() / 2);

		city2.addChangingListener(new OnWheelChangedListener()
		{

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				if (!CityIsScroll )
				{
					updateCountries(countrywheel,
							countrydata.get(citydata.get(provincedata.get(provincewheel.getCurrentItem()))
									.get(citywheel.getCurrentItem())),
							newValue);

				}

			}
		});

		city2.addScrollingListener(new OnWheelScrollListener()
		{

			@Override
			public void onScrollingStarted(WheelView wheel)
			{
				CityIsScroll = true;
			}

			@Override
			public void onScrollingFinished(WheelView wheel)
			{
				CityIsScroll = false;
				updateCountries(countrywheel,
						countrydata.get(citydata.get(provincedata.get(provincewheel.getCurrentItem()))
								.get(citywheel.getCurrentItem())),
						citywheel.getCurrentItem());
			}
		});
	}

	protected void updateCountries(WheelView country, List<String> list, int currentItem)
	{
		CountryAdapter adapter = new CountryAdapter(Ha_ListItem.this, list);
		country.setViewAdapter(adapter);
		country.setCurrentItem(list.size() / 2);
	}

	private class CountryAdapter extends AbstractWheelTextAdapter
	{
		private List<String> CountryList;

		protected CountryAdapter(Context context, List<String> list)
		{
			super(context, R.layout.hai00_countryitem, NO_RESOURCE);
			this.setItemTextResource(R.id.country_name);
			this.CountryList = list;
		}

		@Override
		public int getItemsCount()
		{
			return CountryList.size();
		}

		@Override
		protected CharSequence getItemText(int index)
		{
			return CountryList.get(index);
		}

	}

	/** 市级数据适配器 */
	private class CityWheelAdapter extends AbstractWheelTextAdapter
	{
		private List<String> citylist;

		protected CityWheelAdapter(Context context, List<String> citylist)
		{
			super(context, R.layout.hai00_cityitem, NO_RESOURCE);
			this.setItemTextResource(R.id.city_name);
			this.citylist = citylist;
		}

		@Override
		public int getItemsCount()
		{
			return citylist.size();
		}

		@Override
		protected CharSequence getItemText(int index)
		{
			return citylist.get(index);
		}

	}

	/** 省级数据适配器 */
	private class ProvinceWheelAdapter extends AbstractWheelTextAdapter
	{

		protected ProvinceWheelAdapter(Context context)
		{
			super(context, R.layout.hai00_provincetext, NO_RESOURCE);
			this.setItemTextResource(R.id.province_name);
		}

		@Override
		public int getItemsCount()
		{
			return provincedata.size();
		}

		@Override
		protected CharSequence getItemText(int index)
		{
			return provincedata.get(index);
		}

	}
}
