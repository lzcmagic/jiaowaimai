package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;
import com.lzc.jiaowaimai.activity.sqlite.SQLiteDao;
import com.lzc.jiaowaimai.activity.utils.MyToast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class Cd_PinJia extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.cd00_pinjia, null);
		final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.cd_ratingbar);
		final TextView textView = (TextView) view.findViewById(R.id.cd_ratingtext);
		Button button = (Button) view.findViewById(R.id.cd_button);

		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
		{

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{
				textView.setText("得分：" + String.valueOf(rating));
			}
		});
		button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Ca_DisPlayPage parentActivity = (Ca_DisPlayPage) getActivity();
				SQLiteDao.updateResScore(getActivity().getApplicationContext(), parentActivity.resid, "score",
						String.valueOf(ratingBar.getRating()));
				MyToast.show("评价成功！", getActivity().getApplicationContext());
			}
		});
		return view;
	}
}
