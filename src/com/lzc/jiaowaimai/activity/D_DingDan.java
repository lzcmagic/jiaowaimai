package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
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

	private class MyListAdapter extends BaseAdapter
	{
		private Context mContext;

		public MyListAdapter(Context mContext)
		{
			super();
			this.mContext = mContext;
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.d10_list_item, null);
			if (convertView != null )
			{
				ImageView imageView = (ImageView) findViewById(R.id.di01);
				TextView textView = (TextView) findViewById(R.id.di02);
				ListView listView = (ListView) findViewById(R.id.di03);
				TextView textView2 = (TextView) findViewById(R.id.di04);
				TextView textView3 = (TextView) findViewById(R.id.di05);
				Button button = (Button) findViewById(R.id.di06);
			}

			return convertView;
		}

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
