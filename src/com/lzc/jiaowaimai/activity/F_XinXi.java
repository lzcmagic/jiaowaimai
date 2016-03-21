package com.lzc.jiaowaimai.activity;

import java.io.File;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.framework.ApplWork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class F_XinXi extends Activity
{
	/** 记录按下返回键的时间 */
	private long exitTime = 0;

	private ImageView tx_image;
	private TextView username, userphone;

	private static final int SELECT_PICTURE = 1;
	private static final int SELECT_CAMER = 0;
	File mOutputFile;

	private LinearLayout InfoLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f00_xinxi);
		initViews();
	}

	private void initViews()
	{
		InfoLayout = (LinearLayout) findViewById(R.id.f00_layout);
		InfoLayout.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();

				if (ApplWork.CurrentUserInfo != null && !ApplWork.CurrentUserInfo.isEmpty() )
				{
					intent.setClass(getApplicationContext(), Fb_UserInfo.class);
				}
				else
				{
					intent.setClass(getApplicationContext(), Fa_Login.class);
				}
				startActivity(intent);
			}
		});

		tx_image = (ImageView) findViewById(R.id.iv_touxiang);
		if (ApplWork.CurrentUserInfo == null )
		{
			tx_image.setClickable(false);
		}
		tx_image.setOnClickListener(new ImageViewOnclickListener());
		username = (TextView) findViewById(R.id.tv_username);
		userphone = (TextView) findViewById(R.id.tv_phone);

	}

	private class ImageViewOnclickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			showImageDialog();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			if ((System.currentTimeMillis() - exitTime) > 2000 )
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}
			else
			{
				finish();
				System.exit(0);
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void showImageDialog()
	{
		final String[] items =
		{
			"拍照",
			"从相册选取",
			"取消"
		};
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请选择");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setCancelable(false);
		builder.setItems(items, new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				switch (which)
				{
					case SELECT_PICTURE:
					{

						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.addCategory(Intent.CATEGORY_OPENABLE);
						intent.setType("image/*");
						intent.putExtra("return-data", true);
						startActivityForResult(intent, SELECT_PICTURE);
						break;
					}
					case SELECT_CAMER:
					{
						String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
						mOutputFile = new File(sdPath, System.currentTimeMillis() + ".tmp");
						Uri uri = Uri.fromFile(mOutputFile);
						System.out.println("uri" + uri);
						Intent newIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
						startActivityForResult(newIntent, SELECT_CAMER);
						break;
					}
					case 2:
					{
						builder.create().dismiss();
						break;
					}
					default:
						break;
				}
			}
		});
		builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == SELECT_CAMER )
		{
			// 拍照
			if (resultCode == RESULT_CANCELED )
			{
				Toast.makeText(F_XinXi.this, "拍照失败", Toast.LENGTH_SHORT).show();
				return;
			}
			// 拍照完了之后，就把这个裁剪一下
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(Uri.fromFile(mOutputFile), "image/*");
			// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 150);
			intent.putExtra("outputY", 150);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(mOutputFile.getAbsoluteFile() + "tmp")));
			startActivityForResult(intent, 2);
		}
		if (requestCode == 2 )
		{
			// 拍照完了之后的裁剪
			if (resultCode == RESULT_CANCELED )
			{
				Toast.makeText(F_XinXi.this, "拍照裁剪失败", Toast.LENGTH_SHORT).show();
				return;
			}
			Bitmap bm = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath() + "tmp");
			tx_image.setImageBitmap(bm);
		}
		if (requestCode == SELECT_PICTURE )
		{
			String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			mOutputFile = new File(sdPath, System.currentTimeMillis() + ".tmp");
			Uri uri = data.getData();
			System.out.println("uri1" + uri);
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
			intent.putExtra("crop", true);
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 150);
			intent.putExtra("outputY", 150);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mOutputFile));
			startActivityForResult(intent, 4);
		}
		if (requestCode == 4 )
		{
			// 相册完了之后的裁剪
			if (resultCode == RESULT_CANCELED )
			{
				Toast.makeText(F_XinXi.this, "获取图片失败", Toast.LENGTH_SHORT).show();
				return;
			}
			// 获取相机返回的数据，并转换为图片格式
			Bitmap bitmap = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath());
			tx_image.setImageBitmap(bitmap);
		}
	}

}
