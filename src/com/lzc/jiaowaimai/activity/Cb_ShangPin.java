package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Cb_ShangPin extends Fragment
{

	private ListView mListView;

	private ShangPinAdapter adapter;
	int cout;
	String resid;
	String resname;
	private Context mContext;
	private List<MenuBean> menuList;

	@Override
	public void onAttach(Context context)
	{
		this.mContext = context;
		Ca_DisPlayPage parentActivity = (Ca_DisPlayPage) getActivity();
		this.resid = parentActivity.resid;
		this.resname = parentActivity.resname;
		super.onAttach(context);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Cursor cursor = SQLiteDao.queryMenu(mContext, resid);
		menuList = new ArrayList<MenuBean>();
		if (cursor.moveToFirst() )
		{
			do
			{
				MenuBean bean = new MenuBean();
				String menuid = cursor.getString(cursor.getColumnIndex("menuid"));
				bean.setMenuid(menuid);
				String name = cursor.getString(cursor.getColumnIndex("name"));
				bean.setName(name);
				String price = cursor.getString(cursor.getColumnIndex("price"));
				bean.setPrice(price);
				byte[] bs = cursor.getBlob(cursor.getColumnIndex("image"));
				bean.setImage(bs);
				String resid = cursor.getString(cursor.getColumnIndex("restaurantid"));
				bean.setRestaurantid(resid);
				menuList.add(bean);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed() )
		{
			cursor.close();
		}
	}

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
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				byte[] bs = menuList.get(position).getImage();
				Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length, options);
				Intent intent = new Intent();
				intent.setClass(mContext, Cba_OrderDetels.class);
				intent.putExtra("resname", resname);
				intent.putExtra("info", menuList.get(position).toString());
				intent.putExtra("menuimage", bitmap);
				startActivity(intent);
			}
		});
		return view;
	}

	private class ShangPinAdapter extends BaseAdapter
	{
		private LayoutInflater mInflater;

		public ShangPinAdapter(Context context)
		{
			super();
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			return menuList.size();
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
				convertView.setTag(hold);
			}
			else
			{
				hold = (ViewHoler) convertView.getTag();
			}

			byte[] bs = menuList.get(position).getImage();
			hold.cb_image.setImageBitmap(BitmapFactory.decodeByteArray(bs, 0, bs.length));
			hold.cb_Text.setText(menuList.get(position).getName());
			hold.cb_price.setText("¥" + menuList.get(position).getPrice());
			return convertView;
		}

	}

	class ViewHoler
	{
		ImageView cb_image;
		TextView cb_Text;
		TextView cb_price;
	}

	class MenuBean
	{
		String menuid;
		String name;
		String price;
		byte[] image;
		String restaurantid;

		public String getMenuid()
		{
			return menuid;
		}

		public void setMenuid(String menuid)
		{
			this.menuid = menuid;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getPrice()
		{
			return price;
		}

		public void setPrice(String price)
		{
			this.price = price;
		}

		public byte[] getImage()
		{
			return image;
		}

		public void setImage(byte[] image)
		{
			this.image = image;
		}

		public String getRestaurantid()
		{
			return restaurantid;
		}

		public void setRestaurantid(String restaurantid)
		{
			this.restaurantid = restaurantid;
		}

		@Override
		public String toString()
		{
			return "menuid=" + menuid + ", name=" + name + ", price=" + price + ", restaurantid="
					+ restaurantid;
		}

	}
}
