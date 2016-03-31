package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class H_Address extends Activity
{
	private ListView mListView;

	private List<String> AddressInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.h00_address);
	}

	@Override
	protected void onResume()
	{
		getData();
		mListView = (ListView) findViewById(R.id.HQ_List);
		mListView.setAdapter(new BaseAdapter()
		{

			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				ViewHolder holder = null;
				if (convertView == null )
				{
					holder = new ViewHolder();
					convertView = LayoutInflater.from(H_Address.this).inflate(R.layout.ha00_listitem, null);
					holder.name = (TextView) convertView.findViewById(R.id.ha_name);
					holder.phone = (TextView) convertView.findViewById(R.id.ha_phone);
					holder.address = (TextView) convertView.findViewById(R.id.ha_address);
					convertView.setTag(holder);
				}
				else
				{
					holder = (ViewHolder) convertView.getTag();
				}
				holder.name.setText(ApplWork.CurrentUser.getUsername());
				holder.phone.setText(ApplWork.CurrentUser.getPhone());
				holder.address.setText(AddressInfos.get(position));

				return convertView;
			}

			@Override
			public long getItemId(int position)
			{
				return position;
			}

			@Override
			public Object getItem(int position)
			{
				return position;
			}

			@Override
			public int getCount()
			{
				return AddressInfos.size();
			}
		});
		super.onResume();
	}

	private void getData()
	{
		AddressInfos = new ArrayList<String>();
		Cursor cursor = SQLiteDao.query(H_Address.this, "address_info", ApplWork.CurrentUser.getPhone());
		if (cursor.moveToFirst() )
		{
			do
			{
				String province = cursor.getString(cursor.getColumnIndex("province"));
				String city = cursor.getString(cursor.getColumnIndex("city"));
				String country = cursor.getString(cursor.getColumnIndex("country"));
				String street = cursor.getString(cursor.getColumnIndex("street"));
				AddressInfos.add(province + city + country + street);
			} while (cursor.moveToNext());
		}
	}

	class ViewHolder
	{
		TextView name;
		TextView phone;
		TextView address;
	}
}
