package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
	private RelativeLayout insertLayout;

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

		insertLayout = (RelativeLayout) view.findViewById(R.id.layout_insert);
		Activity activity = getActivity();
		if (activity.getClass() == C_WaiMai.class )
		{
			mTextView.setText("外卖");
			mLayout.setVisibility(View.GONE);
		}
		if (activity.getClass() == D_DingDan.class )
		{
			mTextView.setText("订单");
			mLayout.setVisibility(View.GONE);
		}
		if (activity.getClass() == E_FaXian.class )
		{
			mTextView.setText("发现");
			mLayout.setVisibility(View.GONE);
		}
		if (activity.getClass() == F_XinXi.class )
		{
			mTextView.setText("我的信息");
			mLayout.setVisibility(View.GONE);
		}
		if (activity.getClass() == Fa_Login.class )
		{
			mTextView.setText("登录");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == Fb_UserInfo.class )
		{
			mTextView.setText("信息");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == FBa_Update_Info.class )
		{
			mTextView.setText("用户名");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == FBb_Update_Info.class )
		{
			mTextView.setText("手机号");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == FBc_Update_Info.class )
		{
			mTextView.setText("登录密码");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == FBd_Update_Info.class )
		{
			mTextView.setText("支付密码");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == G_Hongbao.class )
		{
			mTextView.setText("红包");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == H_Address.class )
		{
			insertLayout.setVisibility(View.VISIBLE);
			insertLayout.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent();
					intent.setClass(getActivity().getApplicationContext(), Ha_ListItem.class);
					startActivity(intent);
				}
			});
			mTextView.setText("收货地址");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == J_FoodInfo.class )
		{
			mTextView.setText("美食相册");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == L_PingfenInfo.class )
		{
			mTextView.setText("评分");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == Ca_DisPlayPage.class )
		{
			mTextView.setText("店铺");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == Cba_OrderDetels.class )
		{
			mTextView.setText("订餐");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == M_PayInsert.class )
		{
			mTextView.setText("充值");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == I_Collect.class )
		{
			mTextView.setText("收藏");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == K_JifenShop.class )
		{
			mTextView.setText("积分商城");
			mLayout.setVisibility(View.VISIBLE);
		}
		if (activity.getClass() == Jb_TakePhoto.class )
		{
			mTextView.setText("美食相册");
			mLayout.setVisibility(View.VISIBLE);
		}

		return view;
	}

}
