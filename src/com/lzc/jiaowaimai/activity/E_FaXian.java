package com.lzc.jiaowaimai.activity;

import com.lzc.jiaowaimai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class E_FaXian extends Activity
{
	/** ��¼���·��ؼ���ʱ�� */
	private long exitTime = 0;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
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
				mWebView.goBack();// ������һҳ��
				return true;
			}
			else
			{
				if ((System.currentTimeMillis() - exitTime) > 2000 )
				{
					Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
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
		// ����WebViewĬ��ʹ�õ�������ϵͳĬ�����������ҳ����Ϊ��ʹ��ҳ��WebView��
		WebViewClient client = new WebViewClient();
		mWebView.setWebViewClient(client);

		// ����֧��javascript
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);

		// ����ʹ�û���
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		// WebView����web��Դ
		mWebView.loadUrl("http://www.baidu.com");

	}
}
