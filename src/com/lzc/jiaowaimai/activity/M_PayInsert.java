package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class M_PayInsert extends Activity
{
	private EditText inputEdit;
	private Button confirmBtn;

	private TextView moneyText;
	/** 充值金额 */
	protected int money;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m00_payinsert);
	}

	@Override
	protected void onResume()
	{
		initView();
		super.onResume();
	}

	private void initView()
	{
		moneyText = (TextView) findViewById(R.id.ML03);
		inputEdit = (EditText) findViewById(R.id.MX01);
		inputEdit.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				System.out.println(s.toString() + "-----" + s);
				if (s.toString().equals("12345678") )
				{
					money = 100;
					moneyText.setText("此卡内含" + String.valueOf(money) + "元");
				}
				else if (s.toString().equals("11111111") )
				{
					money = 50;
					moneyText.setText("此卡内含" + String.valueOf(money) + "元");
				}
				else if (s.toString().equals("88888888") )
				{
					money = 10000;
					moneyText.setText("此卡内含" + String.valueOf(money) + "元");
				}
				else
				{
					moneyText.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});
		confirmBtn = (Button) findViewById(R.id.MB02);
		confirmBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder buld = new AlertDialog.Builder(M_PayInsert.this);
				buld.setTitle("提示");
				buld.setMessage("确认充值吗");
				buld.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						ApplWork.CurrentUser.setBalance(money + ApplWork.CurrentUser.getBalance());
						MyToast.show("充值成功！", M_PayInsert.this);
						dialog.dismiss();
						M_PayInsert.this.finish();
					}
				});
				buld.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
				buld.create();
				buld.show();
			}
		});
	}
}
