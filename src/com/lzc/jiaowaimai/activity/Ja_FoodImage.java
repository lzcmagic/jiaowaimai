package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Ja_FoodImage extends Activity
{
	private ImageView mImage;

	private ProgressBar progressbar;

	private Bitmap bitmap;

	private DisplayMetrics metrics;

	private Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			if (msg.what == 1 )
			{
				mImage.setVisibility(View.VISIBLE);
				progressbar.setVisibility(View.GONE);

				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						metrics.widthPixels - 150, metrics.heightPixels - 400);
				params.setMargins(80, 200, 80, 200);
				mImage.setLayoutParams(params);
				mImage.setAdjustViewBounds(true);
				mImage.setScaleType(ScaleType.CENTER_CROP);
				mImage.setImageBitmap(bitmap);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// …Ë÷√∆¡ƒª≥ﬂ¥Á
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
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
