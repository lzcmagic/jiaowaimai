package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class FBd_Update_Info extends Activity
{
	private EditText ymmEdit, fmmEdit, smmEdit;
	private Button confirmButton;
	private String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fbd00_change_info);
		phone = getIntent().getStringExtra("phone");
		initViews();
	}

	private void initViews()
	{
		ymmEdit = (EditText) this.findViewById(R.id.fbd_yuanmima);
		fmmEdit = (EditText) this.findViewById(R.id.fbd_xinmima);
		smmEdit = (EditText) this.findViewById(R.id.fbd_secondmima);
		confirmButton = (Button) this.findViewById(R.id.fbd_button);
		confirmButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (checkOriginPwdIsRight() )
				{
					if (checkPasswordIsConsistent() )
					{
						SQLiteDao.update(FBd_Update_Info.this, phone, "paypassword",
								smmEdit.getText().toString());
						ApplWork.CurrentUser.setPaypassword(smmEdit.getText().toString());
						MyToast.show("修改支付密码成功！", FBd_Update_Info.this);
					}
					else
					{
						MyToast.show("两次密码输入不一致！", FBd_Update_Info.this);
					}
				}
				else
				{
					MyToast.show("原密码输入不正确！", FBd_Update_Info.this);
				}
			}
		});
	}

	/** 检查原密码是否正确 */
	protected boolean checkOriginPwdIsRight()
	{
		Cursor cursor = SQLiteDao.query(FBd_Update_Info.this, "person_info", phone);
		if (cursor.moveToFirst() )
		{
			String pwd = cursor.getString(cursor.getColumnIndex("paypassword"));
			if (pwd.equals(ymmEdit.getText().toString()) )
			{
				return true;
			}
		}
		return false;
	}

	/** 检查密码是否输入一致 */
	protected boolean checkPasswordIsConsistent()
	{
		if (fmmEdit.getText().toString().equals(smmEdit.getText().toString()) )
		{
			return true;
		}
		else
		{
			return false;
		}

	}

}
