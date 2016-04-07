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
				mWebView.goBack();// ������һҳ��
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	private void init()
	{
		// ����WebViewĬ��ʹ�õ�������ϵͳĬ�����������ҳ����Ϊ��ʹ��ҳ��WebView��
		WebViewClient client = new WebViewClient();
		mWebView.setWebViewClient(client);

		// ����֧��javascript
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);

		// ����ʹ�û���
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		// WebView����web��Դ
		mWebView.loadUrl("http://www.taobao.com");

	}
}
