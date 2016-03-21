package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Aa_Title extends Fragment
{
	private RelativeLayout mLayout;
	private TextView mTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.a10_title, container, false);
		mLayout = (RelativeLayout) view.findViewById(R.id.layout_back);
		mLayout.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getActivity().finish();
			}
		});
		mTextView = (TextView) view.findViewById(R.id.tv_title);
		Activity activity = getActivity();
		if (activity.getClass() == C_WaiMai.class )
		{
			mTextView.setText("����");
			mLayout.setVisibility(View.GONE);
		}
		if (activity.getClass() == D_DingDan.class )
		{
			mTextView.setText("����");
			mLayout.setVisibility(View.GONE);
		}
		if (activity.getClass() == E_FaXian.class )
		{
			mTextView.setText("����");
			mLayout.setVisibility(View.GONE);
		}
		if (activity.getClass() == F_XinXi.class )
		{
			mTextView.setText("�ҵ���Ϣ");
			mLayout.setVisibility(View.GONE);
		}
		if (activity.getClass() == Fa_Login.class )
		{
			mTextView.setText("��¼");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == Fb_UserInfo.class )
		{
			mTextView.setText("��Ϣ");
			mLayout.setVisibility(View.GONE);
		}
		return view;
	}

}
