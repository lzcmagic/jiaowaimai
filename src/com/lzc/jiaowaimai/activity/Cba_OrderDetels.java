package com.lzc.jiaowaimai.activity;

import java.util.HashMap;
import java.util.Map;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Cba_OrderDetels extends Activity
{
	private TextView cba_num, cba_name;
	private Button cba_cancel, cba_commit, cba_plus, cba_minus;
	private ImageView cba_image;
	private String[] InfoArray;
	private int count;

	private Handler hand = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			cba_num.setText(String.valueOf(msg.obj));
			if (Integer.parseInt(cba_num.getText().toString()) <= 0 )
			{
				cba_minus.setEnabled(false);
				cba_minus.setClickable(false);

			}
			else
			{
				cba_minus.setEnabled(true);
				cba_minus.setClickable(true);

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cba00_order_details);
		String info = getIntent().getStringExtra("info");
		InfoArray = info.split(",");
		System.out.println(info);
		initViews();
	}

	private void initViews()
	{
		Bitmap bitmap = getIntent().getParcelableExtra("menuimage");
		cba_num = (TextView) findViewById(R.id.cba_num);
		cba_name = (TextView) findViewById(R.id.cba_name);
		cba_name.setText(InfoArray[1].split("=")[1]);
		cba_cancel = (Button) findViewById(R.id.cba_cancel);
		cba_commit = (Button) findViewById(R.id.cba_commit);
		cba_image = (ImageView) findViewById(R.id.cba_image);
		cba_plus = (Button) findViewById(R.id.cba_plus);
		cba_minus = (Button) findViewById(R.id.cba_minus);
		cba_image.setImageBitmap(bitmap);
		cba_minus.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				count--;
				Message msg = new Message();
				msg.obj = count;
				hand.sendMessage(msg);
			}
		});

		cba_plus.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				count++;
				Message msg = new Message();
				msg.obj = count;
				hand.sendMessage(msg);
			}
		});

		cba_commit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (Integer.parseInt(cba_num.getText().toString()) != 0 )
				{
					if (ApplWork.CurrentUser != null )
					{

						SQLiteDao.insertMenu(Cba_OrderDetels.this, getIntent().getStringExtra("resname"),
								InfoArray[1].split("=")[1], cba_num.getText().toString(),
								Integer.parseInt(InfoArray[2].split("=")[1]),
								ApplWork.CurrentUser.getPhone());
						Map<String, String> map = new HashMap<String, String>();
						map.put(InfoArray[1].split("=")[1], cba_num.getText().toString());
						Ca_DisPlayPage.OrderMealList.add(map);
						int num = Integer.parseInt(cba_num.getText().toString());
						int price = Integer.parseInt(InfoArray[2].split("=")[1]);
						System.out.println("" + num + "---" + price);
						Ca_DisPlayPage.OrderMealMoneyList.add(num * price);
						Cba_OrderDetels.this.finish();
					}
					else
					{
						MyToast.show("请先登录", Cba_OrderDetels.this);
					}
				}
				else
				{
					MyToast.show("没有点餐", Cba_OrderDetels.this);
					Cba_OrderDetels.this.finish();
				}
			}
		});

		cba_cancel.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Cba_OrderDetels.this.finish();

			}
		});
	}
}
