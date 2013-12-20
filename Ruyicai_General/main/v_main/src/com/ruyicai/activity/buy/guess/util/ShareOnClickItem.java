package com.ruyicai.activity.buy.guess.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.SharePopWindow.OnChickItem;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.RWSharedPreferences;
import com.third.share.ShareActivity;
import com.third.share.Token;
import com.third.share.Weibo;
import com.third.share.WeiboDialogListener;

public class ShareOnClickItem implements OnChickItem{
	
	private Activity mActivity = null;

	@Override
	public void onChickItem(int viewId, Activity activity) {
		this.mActivity = activity;
		switch (viewId) {
		case R.id.tosinaweibo:
			oauth();
			Log.i("yejc", "tosinaweibo");
			break;
		case R.id.totengxunweibo:
			Log.i("yejc", "totengxunweibo");
			break;
		case R.id.toweixin:
			Log.i("yejc", "toweixin");
			break;
		case R.id.topengyouquan:
			Log.i("yejc", "topengyouquan");
			break;
		case R.id.tocancel:
			Log.i("yejc", "tocancel");
			break;
		}
	}
	
	private void oauth() {
		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(Constants.CONSUMER_KEY,
				Constants.CONSUMER_SECRET);
		// Oauth2.0
		// 隐式授权认证方式
		weibo.setRedirectUrl(Constants.CONSUMER_URL);// 此处回调页内容应该替换为与appkey对应的应用回调页
		// 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
		// 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
		// 应用回调页不可为空
		weibo.authorize(mActivity, new AuthDialogListener());
	}

	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			RWSharedPreferences shellRW = new RWSharedPreferences(mActivity,
					"addInfo");
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			shellRW.putStringValue("token", token);
			shellRW.putStringValue("expires_in", expires_in);
			initAccessToken(token, expires_in);
		}

		@Override
		public void onCancel() {
			Toast.makeText(mActivity, "Auth cancel", Toast.LENGTH_LONG).show();
		}
	}

	private void initAccessToken(String token, String expires_in) {
		Token accessToken = new Token(token, Weibo.getAppSecret());
		accessToken.setExpiresIn(expires_in);
		Weibo weibo = Weibo.getInstance();
		weibo.setAccessToken(accessToken);
		weibo.share2weibo(mActivity, weibo.getAccessToken().getToken(), weibo
				.getAccessToken().getSecret(), "", "");
	}

}
