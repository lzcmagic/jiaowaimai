package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("unused")
public class Cb_ShangPin extends Fragment
{

	private ListView mListView;

	private ShangPinAdapter adapter;
	int cout;

	private Handler mHandler = new Handler()
	{
		@SuppressWarnings("static-access")
		public void handleMessage(android.os.Message msg)
		{
			View view = mListView.getChildAt(0).inflate(getContext(), R.layout.cb10_shangpin_item, null);
			TextView textView = (TextView) view.findViewById(R.id.cb_fenshu);
			textView.setText(String.valueOf(msg.getData().getInt("count")));
			System.out.println(msg.getData().getInt("position") + "----" + msg.getData().getInt("count"));
		};
	};

	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.cb00_shangpin, null);
		mListView = (ListView) view.findViewById(R.id.cb_listview);
		adapter = new ShangPinAdapter(getContext());
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{

				View view1 = mListView.getChildAt(position).inflate(getContext(), R.layout.cb10_shangpin_item,
						null);
				TextView textView = (TextView) view1.findViewById(R.id.cb_fenshu);
				textView.setText(String.valueOf(cout++));
				System.out.println("posi----" + "zhixingdaole");
			}
		});
		return view;
	}

	private class ShangPinAdapter extends BaseAdapter
	{
		int count = 0;
		private LayoutInflater mInflater;
		int Posi;

		public ShangPinAdapter(Context context)
		{
			super();
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			return 10;
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
		public void notifyDataSetChanged()
		{
			super.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHoler hold = null;
			if (convertView == null )
			{
				hold = new ViewHoler();
				convertView = mInflater.inflate(R.layout.cb10_shangpin_item, null);
				hold.cb_image = (ImageView) convertView.findViewById(R.id.canzhong_image);
				hold.cb_Text = (TextView) convertView.findViewById(R.id.canzhong_name);
				hold.cb_price = (TextView) convertView.findViewById(R.id.canzhong_price);
				hold.puls_button = (Button) convertView.findViewById(R.id.puls);
				hold.minus_button = (Button) convertView.findViewById(R.id.minus);
				hold.cb_fenshu = (TextView) convertView.findViewById(R.id.cb_fenshu);
				hold.cb_fenshu.setTag(position);
				hold.cb_fenshu.setText(String.valueOf(count));
				convertView.setTag(hold);
			}
			else
			{
				hold = (ViewHoler) convertView.getTag();
			}

			hold.puls_button.setOnClickListener(new OnClickListener()
			{
				@SuppressWarnings("static-access")
				@Override
				public void onClick(View v)
				{

					count++;

					View view = mListView.getChildAt(1).inflate(getContext(), R.layout.cb10_shangpin_item,
							null);
					TextView textView = (TextView) view.findViewById(R.id.cb_fenshu);
					textView.setText(String.valueOf(count));
					// Bundle bundle = new Bundle();
					// bundle.putInt("position", 12);
					// bundle.putInt("count", count);
					// Message message = new Message();
					// message.setData(bundle);
					// mHandler.sendMessage(message);
				}
			});
			return convertView;
		}

	}

	class ViewHoler
	{
		ImageView cb_image;
		TextView cb_Text;
		TextView cb_price;
		Button puls_button;
		Button minus_button;
		TextView cb_fenshu;
	}
}
