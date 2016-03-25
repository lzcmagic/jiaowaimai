package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
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
	boolean IsPayExist;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fbd00_change_info);
		phone = getIntent().getStringExtra("phone");
		IsPayExist = getIntent().getBooleanExtra("IsPayExist", false);
		initViews();
	}

	private void initViews()
	{
		ymmEdit = (EditText) this.findViewById(R.id.fbd_yuanmima);
		fmmEdit = (EditText) this.findViewById(R.id.fbd_xinmima);
		smmEdit = (EditText) this.findViewById(R.id.fbd_secondmima);
		confirmButton = (Button) this.findViewById(R.id.fbd_button);
		if (IsPayExist )
		{
			ymmEdit.setVisibility(View.VISIBLE);
			fmmEdit.setVisibility(View.VISIBLE);
			smmEdit.setVisibility(View.VISIBLE);
		}
		else
		{
			ymmEdit.setVisibility(View.VISIBLE);
			ymmEdit.setHint("������֧������");
			ymmEdit.setHintTextColor(Color.parseColor("#c0c0c0"));
			fmmEdit.setVisibility(View.GONE);
			smmEdit.setVisibility(View.GONE);
		}
		confirmButton.setOnClickListener(new PayButtonOnclickListener(IsPayExist));

	}

	/** ֧����ť�ĵ���¼� */
	private class PayButtonOnclickListener implements OnClickListener
	{

		boolean IsPayExist1;

		public PayButtonOnclickListener(boolean IsPayExist)
		{
			this.IsPayExist1 = IsPayExist;
		}

		@Override
		public void onClick(View v)
		{
			if (IsPayExist1 )
			{
				if (checkOriginPwdIsRight() )
				{
					if (checkPasswordIsConsistent() )
					{
						SQLiteDao.update(FBd_Update_Info.this, phone, "paypassword",
								smmEdit.getText().toString());
						ApplWork.CurrentUser.setPaypassword(smmEdit.getText().toString());
						MyToast.show("�޸�֧������ɹ���", FBd_Update_Info.this);
						FBd_Update_Info.this.finish();
					}
					else
					{
						MyToast.show("�����������벻һ�£�", FBd_Update_Info.this);
					}
				}
				else
				{
					MyToast.show("ԭ�������벻��ȷ��", FBd_Update_Info.this);
				}
			}
			else
			{
				SQLiteDao.update(FBd_Update_Info.this, phone, "paypassword", ymmEdit.getText().toString());
				ApplWork.CurrentUser.setPaypassword(ymmEdit.getText().toString());
				MyToast.show("֧���������óɹ���", FBd_Update_Info.this);
				FBd_Update_Info.this.finish();
			}
		}

	}

	/** ���ԭ�����Ƿ���ȷ */
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

	/** ��������Ƿ�����һ�� */
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
