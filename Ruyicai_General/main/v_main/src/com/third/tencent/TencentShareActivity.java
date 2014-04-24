package com.third.tencent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.palmdream.RuyicaiAndroid.wxapi.WXEntryActivity;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.PublicMethod;
//import com.tencent.weibo.api.TAPI;
//import com.tencent.weibo.oauthv1.OAuthV1;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

public class TencentShareActivity extends Activity implements HttpCallback{
	EditText sharecontent;
	Button commit;
	Button cannel;
//	OAuthV1 oAuthV1;
	String tencentsharecontent = "";
	String userno;
	String mSharePictureName;
	private ImageView bitmapView;
	private ProgressDialog progressdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tencentshare);
		init();
		getInfo();
	}

	protected void init() {
		sharecontent = (EditText) findViewById(R.id.sharecontent);
		bitmapView = (ImageView) findViewById(R.id.bitmapView);
		commit = (Button) findViewById(R.id.share);
		cannel = (Button) findViewById(R.id.btn_return);
		commit.setOnClickListener(click);
		cannel.setOnClickListener(click);

	}
	
	private void auth(long appid, String app_secket) {
		final Context context = this.getApplicationContext();
		// 注册当前应用的appid和appkeysec，并指定一个OnAuthListener
		// OnAuthListener在授权过程中实施监听
		AuthHelper.register(this, appid, app_secket, new OnAuthListener() {

			// 如果当前设备没有安装腾讯微博客户端，走这里
			@Override
			public void onWeiBoNotInstalled() {
//				Toast.makeText(TencentShareActivity.this, "onWeiBoNotInstalled",
//						1000).show();
				AuthHelper.unregister(TencentShareActivity.this);
				Intent i = new Intent(TencentShareActivity.this, Authorize.class);
				startActivity(i);
			}

			// 如果当前设备没安装指定版本的微博客户端，走这里
			@Override
			public void onWeiboVersionMisMatch() {
//				Toast.makeText(TencentShareActivity.this, "onWeiboVersionMisMatch",
//						1000).show();
				AuthHelper.unregister(TencentShareActivity.this);
				Intent i = new Intent(TencentShareActivity.this, Authorize.class);
				startActivity(i);
			}

			// 如果授权失败，走这里
			@Override
			public void onAuthFail(int result, String err) {
//				Toast.makeText(TencentShareActivity.this, "result : " + result,
//						1000).show();
				AuthHelper.unregister(TencentShareActivity.this);
			}

			// 授权成功，走这里
			// 授权成功后，所有的授权信息是存放在WeiboToken对象里面的，可以根据具体的使用场景，将授权信息存放到自己期望的位置，
			// 在这里，存放到了applicationcontext中
			@Override
			public void onAuthPassed(String name, WeiboToken token) {
				Toast.makeText(TencentShareActivity.this, "passed", 1000).show();
				//
				Util.saveSharePersistent(context, "ACCESS_TOKEN",
						token.accessToken);
				Util.saveSharePersistent(context, "EXPIRES_IN",
						String.valueOf(token.expiresIn));
				Util.saveSharePersistent(context, "OPEN_ID", token.openID);
				Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
				Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig()
						.getProperty("APP_KEY"));
				Util.saveSharePersistent(context, "AUTHORIZETIME",
						String.valueOf(System.currentTimeMillis() / 1000l));
				AuthHelper.unregister(TencentShareActivity.this);
			}
		});

		AuthHelper.auth(this, "");
	}

	OnClickListener click = new OnClickListener() {
		String response;
//		TAPI tAPI;

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.share:
				toShare();
				break;
			case R.id.btn_return:
				finish();
			}

		}
	};

	public void onBackPressed() {
		finish();
	}
	
	private Bitmap thumbBmp;
	private String tencent_token;
	private WeiboAPI weiboAPI;// 微博相关API
	
	private void toShare(){
		tencent_token = Util.getSharePersistent(getApplicationContext(),
				"ACCESS_TOKEN");
		if (tencent_token == null || "".equals(tencent_token)) {
			long appid = Constants.kAppKey1;
			String app_secket =Constants.kAppSecret;
			auth(appid, app_secket);
		}else{
			progressdialog=PublicMethod.creageProgressDialog(TencentShareActivity.this);
			AccountModel account = new AccountModel(tencent_token);
			weiboAPI = new WeiboAPI(account);
			weiboAPI.addPic(TencentShareActivity.this, sharecontent.getText().toString(), "json", 0,
					0, thumbBmp, 0, 0, this, null,
					BaseVO.TYPE_JSON);
		}
	}

	public void getInfo() {
		Intent intent = getIntent();
		if (intent != null) {
			tencentsharecontent = intent.getStringExtra("tencent");
			mSharePictureName = intent.getStringExtra("bitmap");
			if(mSharePictureName!=null&&!"".equals(mSharePictureName)){
				Bitmap bmp = BitmapFactory.decodeFile(mSharePictureName);
				if(bmp!=null){
					thumbBmp = Bitmap.createScaledBitmap(bmp, 300, 400, true);
				}else{
					thumbBmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
				}
			}else{
				thumbBmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
			}
			bitmapView.setImageBitmap(thumbBmp);
			sharecontent.setText(tencentsharecontent);
		}
	}

	@Override
	public void onResult(Object object) {
		{
			if (object != null) {
				PublicMethod.closeProgressDialog(progressdialog);
				ModelResult result = (ModelResult) object;
				if (result.isExpires()) {
					Toast.makeText(TencentShareActivity.this,
							result.getError_message(), Toast.LENGTH_SHORT)
							.show();
				} else {
					if (result.isSuccess()) {
						Toast.makeText(TencentShareActivity.this, "分享成功", 4000)
								.show();
						finish();
					} else {
						Toast.makeText(TencentShareActivity.this,
								((ModelResult) object).getError_message(), 4000).show();
					}
				}

			}else{
				Toast.makeText(TencentShareActivity.this, "发送失败", 4000).show();
				finish();
				PublicMethod.closeProgressDialog(progressdialog);
			}

		}

	}
}
