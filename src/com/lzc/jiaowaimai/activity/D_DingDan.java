package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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

	private List<ViewBean> OrderedList = new ArrayList<D_DingDan.ViewBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getData();
	}

	private void getData()
	{
		if (ApplWork.CurrentUser != null )
		{
			Cursor cursor = SQLiteDao.query(D_DingDan.this, "ordermeal_info",
					ApplWork.CurrentUser.getPhone());
			OrderedList = new ArrayList<ViewBean>();
			if (cursor.moveToFirst() )
			{
				do
				{
					ViewBean bean = new ViewBean();
					String restaurantname = cursor.getString(cursor.getColumnIndex("restaurantname"));
					bean.setRestaurantname(restaurantname);
					String mealname = cursor.getString(cursor.getColumnIndex("mealname"));
					bean.setMealname(mealname);
					String mealnum = cursor.getString(cursor.getColumnIndex("mealnum"));
					bean.setMealnum(mealnum);
					int mealmoney = cursor.getInt(cursor.getColumnIndex("mealmoney"));
					bean.setMealmoney(String.valueOf(mealmoney));
					OrderedList.add(bean);
				} while (cursor.moveToNext());
			}

			if (!cursor.isClosed() )
			{
				cursor.close();
			}
			setContentView(R.layout.d00_dingdan);
			initView();
		}
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

		public MyListAdapter(Context mContext)
		{
			this.mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount()
		{
			return OrderedList.size();
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
		public View getView(int position, View convertView, android.view.ViewGroup parent)
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
				hold.content = (TextView) convertView.findViewById(R.id.di03);
				hold.againButton = (Button) findViewById(R.id.di06);
				convertView.setTag(hold);
			}
			else
			{
				hold = (ViewHoilder) convertView.getTag();
			}
			hold.nameText.setText(OrderedList.get(position).getRestaurantname());
			hold.numText.setText(OrderedList.get(position).getMealnum());
			hold.moneyText.setText(String.valueOf(Integer.parseInt(OrderedList.get(position).getMealmoney())
					* Integer.parseInt(OrderedList.get(position).getMealnum())));
			hold.content.setText(OrderedList.get(position).getMealname());
			return convertView;
		}

	}

	class ViewHoilder
	{
		public ImageView txImage;
		public TextView nameText;
		public TextView numText;
		public TextView moneyText;
		public TextView content;
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

	class ViewBean
	{
		String restaurantname;
		String mealname;
		String mealnum;
		String mealmoney;

		public String getRestaurantname()
		{
			return restaurantname;
		}

		public void setRestaurantname(String restaurantname)
		{
			this.restaurantname = restaurantname;
		}

		public String getMealname()
		{
			return mealname;
		}

		public void setMealname(String mealname)
		{
			this.mealname = mealname;
		}

		public String getMealnum()
		{
			return mealnum;
		}

		public void setMealnum(String mealnum)
		{
			this.mealnum = mealnum;
		}

		public String getMealmoney()
		{
			return mealmoney;
		}

		public void setMealmoney(String mealmoney)
		{
			this.mealmoney = mealmoney;
		}

	}

}
