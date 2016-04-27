package com.lzc.jiaowaimai.activity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class J_FoodInfo extends Activity
{
	private GridView mGridView;
	private List<Map<String, Bitmap>> foodlist;

	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.j00_foodcollect);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		getData();
		mGridView = (GridView) findViewById(R.id.foodcollect);
		mButton = (Button) findViewById(R.id.insert_image);
		mGridView.setAdapter(new BaseAdapter()
		{
			private Bitmap bitmap;
			private String time;

			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				Viewholder hold = null;
				if (convertView == null )
				{
					hold = new Viewholder();
					convertView = LayoutInflater.from(J_FoodInfo.this).inflate(R.layout.ji00_listitem, null);
					hold.imageView = (ImageView) convertView.findViewById(R.id.foodimage);
					hold.textView = (TextView) convertView.findViewById(R.id.foodtime);
					convertView.setTag(hold);
				}
				else
				{
					hold = (Viewholder) convertView.getTag();
				}
				Iterator<Entry<String, Bitmap>> iterator = foodlist.get(position).entrySet().iterator();

				while (iterator.hasNext())
				{
					Entry<String, Bitmap> entry = iterator.next();
					time = entry.getKey();
					bitmap = entry.getValue();
				}
				hold.imageView.setImageBitmap(bitmap);
				hold.textView.setText(time);
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
				return foodlist.size();
			}
		});

		mGridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent();
				intent.setClass(J_FoodInfo.this, Ja_FoodImage.class);
				Iterator<Entry<String, Bitmap>> iterator = foodlist.get(position).entrySet().iterator();
				Bitmap bitmap = null;
				while (iterator.hasNext())
				{
					Entry<String, Bitmap> entry = iterator.next();
					bitmap = entry.getValue();
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.PNG, 100, baos);
				intent.putExtra("image", baos.toByteArray());
				startActivity(intent);
			}
		});

		mButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(J_FoodInfo.this, Jb_TakePhoto.class);
				startActivity(intent);
			}
		});

		super.onResume();
	}

	private void getData()
	{
		foodlist = new ArrayList<Map<String, Bitmap>>();
		Cursor cursor = SQLiteDao.query(J_FoodInfo.this, "food_album", ApplWork.CurrentUser.getPhone());
		if (cursor.moveToFirst() )
		{
			do
			{
				Map<String, Bitmap> map = new HashMap<String, Bitmap>();
				byte[] bs = cursor.getBlob(cursor.getColumnIndex("image"));
				Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
				String time = cursor.getString(cursor.getColumnIndex("time"));
				map.put(time, bitmap);
				foodlist.add(map);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed() )
		{
			cursor.close();
		}
	}

	class Viewholder
	{
		ImageView imageView;
		TextView textView;

	}
}
