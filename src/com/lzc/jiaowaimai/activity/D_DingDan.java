package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class D_DingDan extends Activity
{
	/** 记录按下返回键的时间 */
	private long exitTime = 0;

	/** 订单的ListView */
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d00_dingdan);
		initView();
	}

	private void initView()
	{
		mListView = (ListView) findViewById(R.id.DQ01);
		MyListAdapter adapter = new MyListAdapter(getApplicationContext());
		mListView.setAdapter(adapter);

	}

	/** 自定义的Adapter */
	private class MyListAdapter extends BaseAdapter
	{
		private LayoutInflater mInflater;
		
		private ListView contentListView;

		public MyListAdapter(Context mContext)
		{
			this.mInflater = LayoutInflater.from(mContext);
			contentListView=(ListView) findViewById(R.id.di03);
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
			ViewHoilder hold = null;
			if (convertView == null )
			{
				hold = new ViewHoilder();
				convertView = mInflater.inflate(R.layout.d10_list_item, null);
				hold.txImage = (ImageView) convertView.findViewById(R.id.di01);
				hold.nameText = (TextView) convertView.findViewById(R.id.di02);
				hold.numText = (TextView) convertView.findViewById(R.id.di04);
				hold.moneyText = (TextView) convertView.findViewById(R.id.di05);
				hold.contentList = (ListView) convertView.findViewById(R.id.di03);
				hold.againButton = (Button) findViewById(R.id.di06);
				convertView.setTag(hold);
			}
			else
			{
				hold = (ViewHoilder) convertView.getTag();
			}

			return convertView;
		}

	}

	class ViewHoilder
	{
		public ImageView txImage;
		public TextView nameText;
		public TextView numText;
		public TextView moneyText;
		public ListView contentList;
		public Button againButton;
	}

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

}
