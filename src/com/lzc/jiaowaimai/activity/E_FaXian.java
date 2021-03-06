package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class E_FaXian extends Activity
{
	/** 记录按下返回键的时间 */
	private long exitTime = 0;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.e00_faxian);
		mWebView = (WebView) findViewById(R.id.wv_faxin);
		mWebView.setBackgroundResource(R.drawable.ic_launcher);
		init();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			if (mWebView.canGoBack() )
			{
				mWebView.goBack();// 返回上一页面
				return true;
			}
			else
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
		}

		return super.onKeyDown(keyCode, event);
	}

	private void init()
	{
		// 优先使用缓存
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// 启用支持javascript
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.loadUrl("http://www.baidu.com");
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		mWebView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}
		});

	}
}
