package com.ruyicai.component;

import com.ruyicai.constant.Constants;

import roboguice.activity.RoboActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Wap Push Url 推送的链接
 * 
 * @author anfangshuo
 * 
 */
public class PushWapBrowerActivity extends RoboActivity {

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		WebView webview = new WebView(this);

		// Let's display the progress in the activity title bar, like the
		// browser app does.
		getWindow().requestFeature(Window.FEATURE_PROGRESS);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true);

		final Activity activity = this;
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different
				// scales.
				// The progress meter will automatically disappear when we reach
				// 100%
				activity.setProgress(progress * 1000);
			}
		});

		webview.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(activity, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
			}
		});

		setContentView(webview);

		String url = getIntent().getStringExtra(Constants.PUSH_PAGE_URL);

		// Simplest usage: note that an exception will NOT be thrown
		// if there is an error loading this page (see below).
		webview.loadUrl(url);
	}
}
