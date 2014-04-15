package com.palmdream.RuyicaiAndroid.wxapi;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.util.RuyiGuessUtil;
import com.ruyicai.constant.Constants;
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

public class WXEntryActivity extends Activity implements IWXAPIEventHandler,View.OnClickListener {
	private TextView ruyipackage_title;
	private String sharemsg;
	private EditText sharecontent;
	private Button commit ,cancel;
	private IWXAPI api;
	private RWSharedPreferences rw;
	private String sharestyle;
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	
	
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
		toShareDirectly();
	}
	
	private void toShareDirectly(){
		rw=new RWSharedPreferences(this, "shareweixin");
		sharestyle=rw.getStringValue("weixin_pengyou");
		if("toweixin".equals(sharestyle)){
			String mSharePictureName=getIntent().getStringExtra("mSharePictureName");
			
			WXTextObject textObj = new WXTextObject();
			textObj.text = sharemsg;

			WXImageObject imgObj = new WXImageObject();
			imgObj.setImagePath(mSharePictureName);
			
			WXMediaMessage msg = new WXMediaMessage();
			msg.mediaObject = imgObj;
			msg.description = "ssdd";
			msg.title = "sdfs";
			
			Bitmap bmp = BitmapFactory.decodeFile(mSharePictureName);
			if(bmp!=null){
//				Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 300, 400, true);
//				bmp.recycle();
				msg.thumbData = getBitmapBytes(bmp, false);
			}

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = String.valueOf(System.currentTimeMillis());
			req.message = msg;
			api.sendReq(req);
		}else if("topengyouquan".equals(sharestyle)){
			
		}
		WXEntryActivity.this.finish();
	}
	
	private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.RGB_565);
	        Canvas localCanvas = new Canvas(localBitmap);
	        int i;
	        int j;
	        if (bitmap.getHeight() > bitmap.getWidth()) {
	            i = bitmap.getWidth();
	            j = bitmap.getWidth();
	        } else {
	            i = bitmap.getHeight();
	            j = bitmap.getHeight();
	        }
	        while (true) {
	            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,50
	, 50), null);
	            if (paramBoolean)
	                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
	            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
	            localBitmap.recycle();
	            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
	            try {
	                localByteArrayOutputStream.close();
	                return arrayOfByte;
	            } catch (Exception e) {
	            }
	            i = bitmap.getHeight();
	            j = bitmap.getHeight();
        }
	    }
	
	private void init() {
		rw=new RWSharedPreferences(this, "shareweixin");
		sharestyle=rw.getStringValue("weixin_pengyou");
		ruyipackage_title=(TextView) this.findViewById(R.id.ruyipackage_title);
		if("toweixin".equals(sharestyle)){
			ruyipackage_title.setText("微信分享");
		}
	    if("topengyouquan".equals(sharestyle)){
			ruyipackage_title.setText("分享到朋友圈");
		}
		
		sharecontent = (EditText) findViewById(R.id.sharecontent);
		sharecontent.setText(sharemsg);
		commit = (Button) findViewById(R.id.share);
		cancel = (Button) findViewById(R.id.btn_return);
		commit.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	
	private void getShareContent(){
		sharemsg=getIntent().getStringExtra("sharecontent");
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
			sharetoweixin();
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
		result = "分享成功";
		finish();
		break;
	case BaseResp.ErrCode.ERR_USER_CANCEL:
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
