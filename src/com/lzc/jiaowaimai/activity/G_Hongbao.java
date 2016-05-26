package com.lzc.jiaowaimai.activity;

import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.utils.MyToast;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class G_Hongbao extends Activity
{
	private ListView hongbaolist;
	private List<String> hongbaoinfos;
	private HongBaoListAdapter hbAdapter;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.g00_hongbao);
		hongbaoinfos = (List<String>) getIntent().getSerializableExtra("hongbaoinfo");
		hongbaolist = (ListView) findViewById(R.id.hongbaolist);
		hbAdapter = new HongBaoListAdapter(G_Hongbao.this);
		hongbaolist.setAdapter(hbAdapter);
		hongbaolist.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				showPayDialog(position);
			}
		});
	}

	/**
	 * 显示充值页面
	 */
	protected void showPayDialog(final int position)
	{
		AlertDialog.Builder buld = new AlertDialog.Builder(G_Hongbao.this);
		buld.setTitle("提示：");
		buld.setMessage("确认充值吗");
		buld.setPositiveButton("确定", new OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				ApplWork.CurrentUser.setBalance(Integer.parseInt(hongbaoinfos.get(position).split(",")[1])
						+ ApplWork.CurrentUser.getBalance());
				hongbaoinfos.remove(position);
				hbAdapter.notifyDataSetChanged();
				MyToast.show("充值成功！", G_Hongbao.this);
				dialog.dismiss();
			}
		});
		buld.setNegativeButton("取消", new OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		buld.create().show();
	}

	/** 红包Adapter */
	private class HongBaoListAdapter extends BaseAdapter
	{

		private LayoutInflater mInflater;

		public HongBaoListAdapter(Context context)
		{
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			System.out.println("size---" + hongbaoinfos.size());
			return hongbaoinfos.size();
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
				convertView = mInflater.inflate(R.layout.g10_hongbaoitem, null);
				holder.type = (TextView) convertView.findViewById(R.id.hongbaotype);
				holder.amount = (TextView) convertView.findViewById(R.id.hongbaojine);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			holder.type.setText(hongbaoinfos.get(position).split(",")[2]);
			holder.amount.setText(hongbaoinfos.get(position).split(",")[1]);
			return convertView;
		}

	}

	class ViewHolder
	{
		/** 种类 */
		TextView type;
		/** 金额 */
		TextView amount;
	}
}
