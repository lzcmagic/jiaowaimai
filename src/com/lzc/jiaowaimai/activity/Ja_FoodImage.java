package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Ja_FoodImage extends Activity
{
	private ImageView mImage;

	private ProgressBar progressbar;

	private Bitmap bitmap;

	private Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			if (msg.what == 1 )
			{
				mImage.setVisibility(View.VISIBLE);
				progressbar.setVisibility(View.GONE);
				mImage.setImageBitmap(bitmap);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ja00_big_food);
		byte[] array = getIntent().getByteArrayExtra("image");
		bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
		progressbar = (ProgressBar) findViewById(R.id.image_pro);
		mImage = (ImageView) findViewById(R.id.big_image);
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					Thread.sleep(1000);
					handler.sendEmptyMessage(1);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.finish();
		return super.onTouchEvent(event);
	}
}
