package com.lzc.jiaowaimai.activity.utils;

import com.lzc.jiaowaimai.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast
{
	public static void show(String text, Context context)
	{
		View layout = LayoutInflater.from(context).inflate(R.layout.toast, null);
		TextView title = (TextView) layout.findViewById(R.id.tv_toast);
		title.setText(text);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

}
