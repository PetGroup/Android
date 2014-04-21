package com.palmdream.RuyicaiAndroid.wxapi;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.util.RuyiGuessUtil;
import com.ruyicai.activity.buy.jlk3.JiLinK3;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.ImageUtil;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler,View.OnClickListener {
	private TextView ruyipackage_title;
	private String sharemsg;
	private EditText sharecontent;
	private Button commit ,cancel;
	private IWXAPI api;
	private RWSharedPreferences rw;
	private String sharestyle;
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	private ImageView bitmapView; 
	private Bitmap bitmap;
	private ProgressDialog progressdialog;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.tencentshare);
    	getShareContent();
    	init();
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
		api.registerApp(Constants.APP_ID);
		api.handleIntent(getIntent(), this);
		
	}
	
	private void toShareDirectly(){
		progressdialog=PublicMethod.creageProgressDialog(WXEntryActivity.this);
		rw=new RWSharedPreferences(this, "shareweixin");
		sharestyle=rw.getStringValue("weixin_pengyou");
		if("toweixin".equals(sharestyle)){

//			url = "http://www.baidu.com//";
//			WXWebpageObject imgObj=new WXWebpageObject();
//			imgObj.webpageUrl = url;
//
//			WXMediaMessage msg = new WXMediaMessage(imgObj);
//			msg.description = sharemsg;
//			msg.title = "如意彩票";
//			
//			if(bitmap!=null){
//				msg.thumbData = ImageUtil.bmpToByteArray(bitmap, false);
//			}	
//			SendMessageToWX.Req req = new SendMessageToWX.Req();
//			req.transaction = System.currentTimeMillis()+"webpage";
//			req.message = msg;
//			req.scene = SendMessageToWX.Req.WXSceneSession;
//			api.sendReq(req);
			setWxTextMessage(false);
		}else if("topengyouquan".equals(sharestyle)){
			int wxSdkVersion = api.getWXAppSupportAPI();
			if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
				if(api!=null){
					setWxTextMessage(true);
				}
			} else {
				Toast.makeText(WXEntryActivity.this, "不支持分享到朋友圈", Toast.LENGTH_LONG).show();
			}
		}
		WXEntryActivity.this.finish();
	}
	
	private void setWxTextMessage(boolean flag){
		WXWebpageObject imgObj=new WXWebpageObject();
		imgObj.webpageUrl = url;

		WXMediaMessage msg = new WXMediaMessage(imgObj);
		msg.description = sharecontent.getText().toString();
		msg.title = "如意彩票";
		
		if(bitmap!=null){
			msg.thumbData = ImageUtil.bmpToByteArray(bitmap, false);
		}	
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = System.currentTimeMillis()+"webpage";
		req.message = msg;
		req.scene=flag?SendMessageToWX.Req.WXSceneTimeline: SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
		
		
//		WXTextObject textObj = new WXTextObject();  
//        textObj.text = sharemsg;
//
//        WXMediaMessage msg = new WXMediaMessage();  
//        msg.mediaObject = textObj;  
//        msg.description = sharemsg;  
//          
//        SendMessageToWX.Req req = new SendMessageToWX.Req();  
//        req.transaction = String.valueOf(System.currentTimeMillis());  
//        req.message = msg;  
//        req.scene=flag?SendMessageToWX.Req.WXSceneTimeline: SendMessageToWX.Req.WXSceneSession;
//        api.sendReq(req);
	}
	
	private void init() {
		rw = new RWSharedPreferences(this, "shareweixin");
		sharestyle = rw.getStringValue("weixin_pengyou");
		ruyipackage_title = (TextView) this
				.findViewById(R.id.ruyipackage_title);
		bitmapView = (ImageView) this
				.findViewById(R.id.bitmapView);
		if ("toweixin".equals(sharestyle)) {
			ruyipackage_title.setText("微信分享");
		}
		if ("topengyouquan".equals(sharestyle)) {
			ruyipackage_title.setText("分享到朋友圈");
		}
		
		String mSharePictureName=getIntent().getStringExtra("mSharePictureName");
		if(mSharePictureName!=null&&!"".equals(mSharePictureName)){
			Bitmap bmp = BitmapFactory.decodeFile(mSharePictureName);
			if(bmp!=null){
				bitmap = Bitmap.createScaledBitmap(bmp, 150, 200, true);
			}
		}else{
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		}
		bitmapView.setImageBitmap(bitmap);

		sharecontent = (EditText) findViewById(R.id.sharecontent);
		sharecontent.setText(sharemsg);
		commit = (Button) findViewById(R.id.share);
		cancel = (Button) findViewById(R.id.btn_return);
		commit.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	private void getShareContent(){
		sharemsg=getIntent().getStringExtra("sharecontent");
		url=getIntent().getStringExtra("url");
		if(url==null||"".equals(url)){
			url="http://ruyicai.com";
		}
	}
	

	@Override
	public void onClick(View v) {
       switch (v.getId()) {
	case R.id.share:
		toShare();
		WXEntryActivity.this.finish();
		break;
	case R.id.btn_return:
		WXEntryActivity.this.finish();
		break;
	default:
		break;
	}		
	}

	private void toShare() {
		if(api.isWXAppInstalled()){
//			sharetoweixin();
			toShareDirectly();
		}else{
			 Toast.makeText(this, "请先安装微信客户端",Toast.LENGTH_LONG).show();
			 Uri uri = Uri.parse("http://weixin.qq.com/m");  
			 Intent it = new Intent(Intent.ACTION_VIEW, uri);  
			 startActivity(it);
		}
	}
	
   private void sharetoweixin(){
	   if("toweixin".equals(sharestyle) && api!=null){
		   WXTextObject textObj = new WXTextObject();  
	        textObj.text = sharemsg;

	        WXMediaMessage msg = new WXMediaMessage();  
	        msg.mediaObject = textObj;  
	        msg.description = sharemsg;  
	          
	        SendMessageToWX.Req req = new SendMessageToWX.Req();  
	        req.transaction = String.valueOf(System.currentTimeMillis());  
	        req.message = msg;  
	        api.sendReq(req);
		}
	   
	   if("topengyouquan".equals(sharestyle) && api!=null){
		   int wxSdkVersion = api.getWXAppSupportAPI();
			if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
				if(api!=null){
			        WXTextObject textObj = new WXTextObject();  
			        textObj.text = sharemsg;

			        WXMediaMessage msg = new WXMediaMessage();  
			        msg.mediaObject = textObj;  
			        msg.description = sharemsg;  
			          
			        SendMessageToWX.Req req = new SendMessageToWX.Req();  
			        req.transaction = String.valueOf(System.currentTimeMillis());  
			        req.message = msg;  
			        req.scene=SendMessageToWX.Req.WXSceneTimeline;
			        api.sendReq(req);
				}
			} else {
				Toast.makeText(WXEntryActivity.this, "不支持分享到朋友圈", Toast.LENGTH_LONG).show();
			}
	   }
	}



@Override
public void onReq(BaseReq arg0) {
}



@Override
public void onResp(BaseResp resp) {
	String result = "";
	
	switch (resp.errCode) {
	case BaseResp.ErrCode.ERR_OK:
		PublicMethod.closeProgressDialog(progressdialog);
		result = "分享成功";
		finish();
		break;
	case BaseResp.ErrCode.ERR_USER_CANCEL:
		PublicMethod.closeProgressDialog(progressdialog);
		result = "取消分享";
		finish();
		break;
	default:
		break;
	}
	
	Toast.makeText(this, result, Toast.LENGTH_LONG).show();			
}

@Override
protected void onNewIntent(Intent intent) {
	super.onNewIntent(intent);
	setIntent(intent);
    api.handleIntent(intent, this);
}
}
