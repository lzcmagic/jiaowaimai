package com.lzc.jiaowaimai.activity;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Jb_TakePhoto extends Activity
{

	private LinearLayout layout;
	private Button btn_take, commit, cancel;
	private ImageView imagView;
	File mOutputFile;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jb00_takephoto);
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
				boolean fileIsExist = false;
				String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
				File file = new File(sdPath + "/jiaowaimaifoodpicture/");
				if (!file.exists() )
				{
					fileIsExist = file.mkdir();
				}
				if (fileIsExist )
				{
					mOutputFile = new File(file, System.currentTimeMillis() + ".jpg");
					Uri uri = Uri.fromFile(mOutputFile);
					System.out.println("uri" + uri);
					Intent newIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(newIntent, 1);
				}
				else
				{
					mOutputFile = new File(sdPath, System.currentTimeMillis() + ".jpg");
					Uri uri = Uri.fromFile(mOutputFile);
					System.out.println("uri" + uri);
					Intent newIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(newIntent, 1);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1 )
		{

			// 拍照
			if (resultCode == RESULT_CANCELED )
			{
				Toast.makeText(Jb_TakePhoto.this, "拍照失败", Toast.LENGTH_SHORT).show();
				return;
			}
			// 拍照完了之后，就把这个裁剪一下
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(Uri.fromFile(mOutputFile), "image/*");
			// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 16);
			intent.putExtra("aspectY", 11);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 160);
			intent.putExtra("outputY", 110);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(mOutputFile.getAbsoluteFile() + "tmp")));
			startActivityForResult(intent, 2);

		}
		if (requestCode == 2 )
		{
			// 拍照完了之后的裁剪
			if (resultCode == RESULT_CANCELED )
			{
				Toast.makeText(Jb_TakePhoto.this, "拍照裁剪失败", Toast.LENGTH_SHORT).show();
				return;
			}
			BitmapFactory.Options options = new BitmapFactory.Options();
			// 缩放
			options.inSampleSize = 2;
			Bitmap bm = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath() + "tmp", options);
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.PNG, 100, baos);
			layout.setVisibility(View.VISIBLE);
			btn_take.setVisibility(View.GONE);
			imagView.setImageBitmap(bm);
			commit.setOnClickListener(new OnClickListener()
			{

				@SuppressLint("SimpleDateFormat")
				@Override
				public void onClick(View v)
				{
					SQLiteDao.insertPhoto(Jb_TakePhoto.this, ApplWork.CurrentUser.getPhone(),
							new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), baos.toByteArray());
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
