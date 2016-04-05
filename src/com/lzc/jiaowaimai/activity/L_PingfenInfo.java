package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class L_PingfenInfo extends Activity
{
	private RatingBar bar;
	private TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.l00_pingfen);
		bar = (RatingBar) findViewById(R.id.ratingBar1);
		textview = (TextView) findViewById(R.id.ratingtext);
		bar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
		{

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{
				textview.setText(String.valueOf(rating));
			}
		});
	}
}
