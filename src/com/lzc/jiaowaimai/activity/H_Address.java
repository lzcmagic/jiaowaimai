package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.activity.view.ListViewCompat;
import com.lzc.jiaowaimai.activity.view.SlideView;
import com.lzc.jiaowaimai.activity.view.SlideView.OnSlideListener;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class H_Address extends Activity
{

	private List<MessageItem> AddressInfos;

	private ListViewCompat mListView;

	private SlideAdapter adapter;

	private SlideView mLastSlideViewWithStatusOn;

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
		mListView = (ListViewCompat) findViewById(R.id.hq_list);
		adapter = new SlideAdapter();
		mListView.setAdapter(adapter);
		super.onResume();
	}

	/** 数据适配器 */
	private class SlideAdapter extends BaseAdapter
	{

		private LayoutInflater mInflater;

		SlideAdapter()
		{
			super();
			mInflater = getLayoutInflater();
		}

		@Override
		public int getCount()
		{
			return AddressInfos.size();
		}

		@Override
		public Object getItem(int position)
		{
			return AddressInfos.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			SlideView slideView = (SlideView) convertView;
			if (slideView == null )
			{
				View itemView = mInflater.inflate(R.layout.ha00_listitem, null);

				slideView = new SlideView(H_Address.this);
				slideView.setContentView(itemView);

				holder = new ViewHolder(slideView);
				slideView.setOnSlideListener(new OnSlideListener()
				{

					@Override
					public void onSlide(View view, int status)
					{
						if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view )
						{
							mLastSlideViewWithStatusOn.shrink();
						}

						if (status == SLIDE_STATUS_ON )
						{
							mLastSlideViewWithStatusOn = (SlideView) view;
						}
					}
				});
				slideView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) slideView.getTag();
			}
			final MessageItem item = AddressInfos.get(position);
			item.slideView = slideView;
			item.slideView.shrink();

			if (ApplWork.CurrentUser.getUsername() != null )
			{
				holder.name.setText(ApplWork.CurrentUser.getUsername());
			}
			holder.phone.setText(ApplWork.CurrentUser.getPhone());
			holder.address.setText(item.address);
			holder.deleteHolder.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					AddressInfos.remove(position);
					adapter.notifyDataSetChanged();
					SQLiteDao.delete(H_Address.this, item.addressid);
					MyToast.show("删除成功！", H_Address.this);
				}
			});

			return slideView;
		}

	}

	private void getData()
	{
		AddressInfos = new ArrayList<H_Address.MessageItem>();
		Cursor cursor = SQLiteDao.query(H_Address.this, "address_info", ApplWork.CurrentUser.getPhone());
		if (cursor.moveToFirst() )
		{
			do
			{
				MessageItem item = new MessageItem();
				String addressid = cursor.getString(cursor.getColumnIndex("addressid"));
				String province = cursor.getString(cursor.getColumnIndex("province"));
				String city = cursor.getString(cursor.getColumnIndex("city"));
				String country = cursor.getString(cursor.getColumnIndex("country"));
				String street = cursor.getString(cursor.getColumnIndex("street"));
				item.addressid = addressid;
				item.address = province + city + country + street;
				AddressInfos.add(item);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed() )
		{
			cursor.close();
		}
	}

	public class MessageItem
	{
		public String addressid;
		public String address;
		public SlideView slideView;
	}

	private static class ViewHolder
	{
		public TextView name;
		public TextView phone;
		public TextView address;
		public ViewGroup deleteHolder;

		ViewHolder(View view)
		{
			name = (TextView) view.findViewById(R.id.ha_name);
			phone = (TextView) view.findViewById(R.id.ha_phone);
			address = (TextView) view.findViewById(R.id.ha_address);
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
		}
	}

}
