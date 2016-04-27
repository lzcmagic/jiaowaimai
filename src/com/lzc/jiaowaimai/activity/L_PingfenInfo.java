package com.lzc.jiaowaimai.activity;

import java.util.ArrayList;
import java.util.List;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.utils.MyToast;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class L_PingfenInfo extends Activity
{
	private RatingBar bar;
	private TextView textview;

	private Button mButton;

	private ListView mListView;

	private List<String> pinfenList;

	private PinfenAdapter pFAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.l00_pingfen);
		pinfenList = new ArrayList<String>();
		bar = (RatingBar) findViewById(R.id.ratingBar1);
		textview = (TextView) findViewById(R.id.ratingtext);
		mButton = (Button) findViewById(R.id.pingjiaBtn);
		mListView = (ListView) findViewById(R.id.pingfenlist);
		pFAdapter = new PinfenAdapter();
		mListView.setAdapter(pFAdapter);
		bar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
		{

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{
				textview.setText(String.valueOf(rating));
			}
		});

		mButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (textview.getText().toString() != null && !"".equals(textview.getText().toString()) )
				{
					String score = textview.getText().toString();
					pinfenList.add(score);
					pFAdapter.notifyDataSetChanged();
				}
				else
				{
					MyToast.show("请选择评分", L_PingfenInfo.this);
				}
			}
		});
	}

	/** 评分适配器 */
	private class PinfenAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return pinfenList.size();
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
			TextView textView1 = null;
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			textView1 = new TextView(L_PingfenInfo.this);
			textView1.setTextSize(16);
			textView1.setPadding(30, 6, 16, 6);
			textView1.setBackgroundColor(Color.parseColor("#99cc33"));
			textView1.setTextColor(Color.parseColor("#ffffff"));
			textView1.setLayoutParams(params);
			textView1.setText("得分：\t\t" + pinfenList.get(position) + "分");
			return textView1;
		}

	}
}
