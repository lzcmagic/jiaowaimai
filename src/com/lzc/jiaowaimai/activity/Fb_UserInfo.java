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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Fb_UserInfo extends Activity
{
	private ImageView mImageView;
	private TextView username, phone;
	private LinearLayout txLayout, xmLayout, dhLayout, loginLayout, payLayout;

	private static final int SELECT_PICTURE = 1;
	private static final int SELECT_CAMER = 0;
	File mOutputFile;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fb00_user_info);
		initViews();
	}

	/** 初始化 */
	private void initViews()
	{
		mImageView = (ImageView) findViewById(R.id.iv_image);
		if (ApplWork.CurrentUser.getUserpic() != null )
		{
			mImageView.setImageBitmap(ApplWork.CurrentUser.getUserpic());
		}
		else
		{
			mImageView.setBackgroundResource(R.drawable.ic_launcher);
		}
		mImageView.setOnClickListener(new ImageViewOnclickListener());

		username = (TextView) findViewById(R.id.fb00_username);
		if (ApplWork.CurrentUser.getUsername() != null )
		{
			username.setText(ApplWork.CurrentUser.getUsername());
		}
		else
		{
			username.setText("修改");
		}

		phone = (TextView) findViewById(R.id.fb00_phone);
		if (ApplWork.CurrentUser.getPhone() != null )
		{
			phone.setText(ApplWork.CurrentUser.getPhone());
		}
		else
		{
			phone.setText("修改");
		}

		xmLayout = (LinearLayout) findViewById(R.id.tx_username);
		dhLayout = (LinearLayout) findViewById(R.id.tx_phone);
		loginLayout = (LinearLayout) findViewById(R.id.tx_change_password);
		payLayout = (LinearLayout) findViewById(R.id.tx_pay_password);
	}

	private class ImageViewOnclickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			showImageDialog();
		}

	}

	/** 显示拍照对话框 */
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
				Toast.makeText(Fb_UserInfo.this, "拍照失败", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(Fb_UserInfo.this, "拍照裁剪失败", Toast.LENGTH_SHORT).show();
				return;
			}
			Bitmap bm = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath() + "tmp");
			mImageView.setImageBitmap(bm);
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
				Toast.makeText(Fb_UserInfo.this, "获取图片失败", Toast.LENGTH_SHORT).show();
				return;
			}
			// 获取相机返回的数据，并转换为图片格式
			Bitmap bitmap = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath());
			mImageView.setImageBitmap(bitmap);
		}
	}
}
