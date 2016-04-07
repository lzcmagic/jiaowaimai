package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class K_JifenShop extends Activity
{
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.k00_jifen);
		mWebView = (WebView) findViewById(R.id.wv_jifen);
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
		}

		return super.onKeyDown(keyCode, event);
	}

	private void init()
	{
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		WebViewClient client = new WebViewClient();
		mWebView.setWebViewClient(client);

		// 启用支持javascript
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);

		// 优先使用缓存
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		// WebView加载web资源
		mWebView.loadUrl("http://www.taobao.com");

	}
}
