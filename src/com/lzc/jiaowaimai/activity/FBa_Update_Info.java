package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class FBa_Update_Info extends Activity
{
	private EditText mEditText;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fba00_change_info);
		initViews();
	}

	private void initViews()
	{
		mEditText = (EditText) findViewById(R.id.fba00_name);
		mButton = (Button) findViewById(R.id.fba_button);
		mButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				String username = mEditText.getText().toString();
				SQLiteDao.update(FBa_Update_Info.this, getIntent().getStringExtra("phone"), "username",
						username);
				ApplWork.CurrentUser.setUsername(username);
				MyToast.show("用户名修改成功", FBa_Update_Info.this);
				FBa_Update_Info.this.finish();
			}
		});
	}

}
