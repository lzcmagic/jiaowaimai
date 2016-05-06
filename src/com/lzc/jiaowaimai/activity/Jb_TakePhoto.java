package com.lzc.jiaowaimai.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Jb_TakePhoto extends Activity
{

	private LinearLayout layout;
	private Button btn_take, commit, cancel;
	private ImageView imagView;
	File mOutputFile;

	/** ��ȡ��Ļ�ߴ� */
	private DisplayMetrics DisplayMetrics;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jb00_takephoto);
		// ��ʼ����Ļ�ߴ�
		DisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(DisplayMetrics);
		initViews();
	}

	private void initViews()
	{
		layout = (LinearLayout) findViewById(R.id.imagelayout);
		btn_take = (Button) findViewById(R.id.btn_take);
		commit = (Button) findViewById(R.id.commit);
		cancel = (Button) findViewById(R.id.cancel);
		imagView = (ImageView) findViewById(R.id.takephoto);

		btn_take.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
				File file = new File(sdPath + "/jiaowaimaifoodpicture1/");
				if (!file.exists() )
				{
					file.mkdir();
				}
				Intent intent = new Intent();
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		if (requestCode == 1 && resultCode == RESULT_OK )
		{
			// ������ͷ�����ͼƬ��
			Bitmap bitMap = (Bitmap) data.getExtras().get("data");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitMap.compress(CompressFormat.PNG, 100, baos);
			imagView.setImageBitmap(bitMap);
			int options = 100;
			// ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			while (baos.toByteArray().length / 1024 > 100)
			{
				// ����baos�����baos
				baos.reset();
				bitMap.compress(Bitmap.CompressFormat.JPEG, options, baos);
				// ÿ�ζ�����10
				options -= 10;
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
			Bitmap bitMapNew = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
			final ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
			bitMapNew.compress(CompressFormat.PNG, 100, baos1);
			layout.setVisibility(View.VISIBLE);
			btn_take.setVisibility(View.GONE);
			commit.setOnClickListener(new OnClickListener()
			{

				@SuppressLint("SimpleDateFormat")
				@Override
				public void onClick(View v)
				{
					SQLiteDao.insertPhoto(Jb_TakePhoto.this, ApplWork.CurrentUser.getPhone(),
							new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), baos1.toByteArray());
					MyToast.show("����ɹ�", Jb_TakePhoto.this);
					Jb_TakePhoto.this.finish();
				}
			});

			cancel.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Jb_TakePhoto.this.finish();
				}
			});
		}

	}
}
