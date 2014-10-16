package ru.guryev_av.calckasko;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class News extends Activity{
	
	WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		mWebView = (WebView)findViewById(R.id.wvMain);
		mWebView.setWebViewClient(new MyWVC());
		//включаем поддержку JavaScript
		mWebView.getSettings().setJavaScriptEnabled(true);
		//resize content to fit the screen
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.setInitialScale(130);
		//устанавливаем Zoom control
		mWebView.getSettings().setBuiltInZoomControls(true);
		//mWebView.addJavascriptInterface(new myJavaScriptInterface(this), "Android");
		//указываем страницу загрузки
		mWebView.loadUrl("http://intra.makc.ru/about/kis.php");
	}
	
	private class MyWVC extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
