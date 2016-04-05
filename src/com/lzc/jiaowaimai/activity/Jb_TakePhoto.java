package com.lzc.jiaowaimai.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.lzc.jiaowaimai.R;

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
				String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
				mOutputFile = new File(sdPath, System.currentTimeMillis() + ".tmp");
				Uri uri = Uri.fromFile(mOutputFile);
				System.out.println("uri" + uri);
				Intent newIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(newIntent, 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1 )
		{
			Bitmap bm = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath() + "tmp");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.PNG, 100, baos);
			imagView.setImageBitmap(bm);
		}
	}
}
