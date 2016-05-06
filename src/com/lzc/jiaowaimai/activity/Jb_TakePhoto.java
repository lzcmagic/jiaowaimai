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

	/** 获取屏幕尺寸 */
	private DisplayMetrics DisplayMetrics;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jb00_takephoto);
		// 初始化屏幕尺寸
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
			// 从摄像头获得了图片了
			Bitmap bitMap = (Bitmap) data.getExtras().get("data");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitMap.compress(CompressFormat.PNG, 100, baos);
			imagView.setImageBitmap(bitMap);
			int options = 100;
			// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			while (baos.toByteArray().length / 1024 > 100)
			{
				// 重置baos即清空baos
				baos.reset();
				bitMap.compress(Bitmap.CompressFormat.JPEG, options, baos);
				// 每次都减少10
				options -= 10;
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			Bitmap bitMapNew = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
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
					MyToast.show("保存成功", Jb_TakePhoto.this);
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
