package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.bean.CollectRes;
import com.lzc.jiaowaimai.activity.view.RoundedImageView;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class I_Collect extends Activity
{
	private ListView mListView;

	private CollectAdapter mAdapter;

	private RelativeLayout CF01;

	private List<CollectRes> CollectResList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.collect);
		mListView = (ListView) findViewById(R.id.iQ10);
		CF01 = (RelativeLayout) findViewById(R.id.cf01);
	}

	@Override
	protected void onStart()
	{
		getData();
		super.onStart();
	}

	private void getData()
	{
		CollectResList = new ArrayList<CollectRes>();
		Iterator<Entry<String, CollectRes>> iterator = ApplWork.ResMap.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<String, CollectRes> entry = iterator.next();
			CollectRes res = entry.getValue();
			CollectResList.add(res);
		}

	}

	@Override
	protected void onResume()
	{
		mAdapter = new CollectAdapter(I_Collect.this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				showDeleteDialog(position);
				return true;
			}
		});
		if (CollectResList.size() > 0 )
		{
			mListView.setVisibility(View.VISIBLE);
			CF01.setVisibility(View.GONE);
		}
		else
		{
			mListView.setVisibility(View.GONE);
			CF01.setVisibility(View.VISIBLE);
		}
		super.onResume();
	}

	protected void showDeleteDialog(final int position)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(I_Collect.this);
		builder.setTitle("提示：");
		builder.setMessage("确认删除吗？");
		builder.setPositiveButton("确定", new OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				CollectResList.remove(position);
				mAdapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private class CollectAdapter extends BaseAdapter
	{

		LayoutInflater mInflater;

		public CollectAdapter(Context contex)
		{
			this.mInflater = LayoutInflater.from(contex);
		}

		@Override
		public int getCount()
		{
			return CollectResList.size();
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
			ViewHolder holder = null;
			if (convertView == null )
			{
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.i_collect_list, null);
				holder.tx_image = (RoundedImageView) convertView.findViewById(R.id.IR01);
				holder.name_text = (TextView) convertView.findViewById(R.id.IL04);
				holder.qisongjia_text = (TextView) convertView.findViewById(R.id.IL02);
				holder.peisongfei_text = (TextView) convertView.findViewById(R.id.IL03);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tx_image.setImageBitmap(CollectResList.get(position).getLogo());
			holder.name_text.setText(CollectResList.get(position).getName());
			holder.qisongjia_text.setText("¥" + CollectResList.get(position).getQisong());
			holder.peisongfei_text.setText("¥" + CollectResList.get(position).getPeisong() + "配送费");
			return convertView;
		}

	}

	class ViewHolder
	{
		/** 商户头像 */
		RoundedImageView tx_image;
		/** 商户名字 */
		TextView name_text;
		/** 商户起送价 */
		TextView qisongjia_text;
		/** 商户配送费 */
		TextView peisongfei_text;
	}
}
