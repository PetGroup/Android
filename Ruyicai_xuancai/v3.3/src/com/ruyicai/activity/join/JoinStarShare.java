/**
 * 
 */
package com.ruyicai.activity.join;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.common.OrderPrizeDiaog;
import com.ruyicai.constant.Constants;
import com.ruyicai.dialog.LogOutDialog;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.third.share.ShareActivity;
import com.third.share.Token;
import com.third.share.Weibo;
import com.third.share.WeiboDialogListener;
import com.third.sharetorenren.Renren;
import com.third.sharetorenren.StatusDemo;

/**
 * �������
 * @author Administrator
 *
 */
public class JoinStarShare extends Activity implements HandlerMsg{

//	private CompanyInfo  companyInfo = new CompanyInfo(this);//��˾���
	private Context context;
	private ProgressDialog progressdialog;
	private RelativeLayout relativeLayout;
	private boolean isSinaTiaoZhuan = true;
	public static int iQuitFlag = 0; // �����˳�
	MyHandler handler = new MyHandler(this);//�Զ���handler
	String textStr;
    ProgressDialog pBar;
    boolean[] isOrderPrize = new boolean[8];
    RWSharedPreferences shellRW ;
    LogOutDialog logOutDialog;
    
    String token,expires_in;
    
    int returnType = 0;//1Ϊ����ҳ��ķ��ز�����0Ϊ���ظ���
    OrderPrizeDiaog orderPrizeDialog;//�������Ĺ�����
    
    RelativeLayout kaijiangdingyue,personidset;//���ý��� �������ĺ͸����ʺ�����
    Button auto_login_set;//�Զ���¼����
//    Button is_sharetorenren,is_sharetosinaweibo;//΢���˺�����
    
    RelativeLayout sharerenren,sharesina,sharetecent,sharetomsg;//�������ļ���RelativeLayout
    private Renren renren;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		shellRW = new RWSharedPreferences(JoinStarShare.this, "addInfo");
		orderPrizeDialog = new OrderPrizeDiaog(shellRW, JoinStarShare.this);
		context = this;
//		initView();
//		appc=(ApplicationContext)getApplication();
		showShareView();
		Constants.shareContent ="#����# ��@���ʣ��Ҹո�ʹ�������ֻ��ͻ��˷����˺��򷽰���������ַΪ��http://wap.ruyicai.com/w/clientContrallor/clientDown.jspx����������ɣ���Ʊ����Ͷ����ʱʱ�У�";
	}

	/**
	 * ��ת������ҳ��
	 */
	private void showShareView(){
		setContentView(R.layout.joinsharelayout);
		renren = new Renren(this);
		returnType = 1;
		sharesina = (RelativeLayout)findViewById(R.id.tableRow_sharetosina);
		sharetecent = (RelativeLayout)findViewById(R.id.tableRow_sharetotecent);
		sharerenren = (RelativeLayout)findViewById(R.id.tableRow_sharetorenren);
		sharetomsg = (RelativeLayout)findViewById(R.id.tableRow_sharetomsg);
		sharesina.setOnClickListener(moreActivityListener);
		sharetecent.setOnClickListener(moreActivityListener);
		sharerenren.setOnClickListener(moreActivityListener);
		sharetomsg.setOnClickListener(moreActivityListener);
	}
	
	OnClickListener moreActivityListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tableRow_sharetosina:
				oauthOrShare();
				break;
			case R.id.tableRow_sharetorenren:
				StatusDemo.publishStatusOneClick(JoinStarShare.this, renren);
				break;
			case R.id.tableRow_sharetomsg:
				shareToMsg();
				break;
//			case R.id.isSharetoSina:
//				isSinaTiaoZhuan = false;
//				if(shellRW.getStringValue("Failed to handle callback; interface not implemented, callback:android.view.View$PerformClick@41452b08").equals("")){
//					oauth();;
//				}else{
//					shellRW.putStringValue("token", "");
//					is_sharetosinaweibo.setBackgroundResource(R.drawable.off);
//				}
//				break;
//			case R.id.isSharetoRenren:
//				break;
			}
		}
	};
	private boolean is_auto_login;//��sharedpreference�л�ȡ�û����Զ���¼����
	
	

	
	private  void	shareToMsg(){
		Uri smsToUri = Uri.parse("smsto:");// ��ϵ�˵�ַ
		Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,smsToUri);
		mIntent.putExtra("sms_body", Constants.shareContent);// ���ŵ�����
		startActivity(mIntent);
	}
	
	
	private void oauthOrShare(){
		token = shellRW.getStringValue("token");
		expires_in = shellRW.getStringValue("expires_in");
		if(token.equals("")){
			oauth();
		}else{
			isSinaTiaoZhuan = true;
			initAccessToken(token, expires_in);
		}
	}
	
	private void oauth(){

		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		// Oauth2.0
		// ��ʽ��Ȩ��֤��ʽ
		weibo.setRedirectUrl(Constants.CONSUMER_URL);// �˴��ص�ҳ����Ӧ���滻Ϊ��appkey��Ӧ��Ӧ�ûص�ҳ
		// ��Ӧ��Ӧ�ûص�ҳ���ڿ����ߵ�½����΢������ƽ̨֮��
		// �����ҵ�Ӧ��--Ӧ������--Ӧ����Ϣ--�߼���Ϣ--��Ȩ����--Ӧ�ûص�ҳ�������úͲ鿴��
		// Ӧ�ûص�ҳ����Ϊ��
		weibo.authorize(JoinStarShare.this, new AuthDialogListener());
	}

	
 

    /**
     * �л�view
     * 
     */
	public void switchView(View view){
		setContentView(view);
	}

	/**
	 *  �������ӿ�
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			return progressdialog;
		}
		}
		return null;
	}
	/**
	 * ��õ�ǰ����context
	 */
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
  
 
	public void dismissDialog() {
		// TODO Auto-generated method stub
		progressdialog.dismiss();
	}
	 /**
     *  ��ʾ�������ӿ�
     */
	public void showDialog() {
		// TODO Auto-generated method stub
		showDialog(0);
	}
    /**
     * �����봦����
     */
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		
	}

	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#errorCode_000000()
	 */
	@Override
	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ����һ��activity���ص�ǰactivityִ�еķ���
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (renren != null) {
			renren.authorizeCallback(requestCode, resultCode, data);
		}
		}
 

	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			PublicMethod.myOutLog("token111", "zhiqiande"+shellRW.getStringValue("token"));
			PublicMethod.myOutLog("onComplete", "12131321321321");
			String token = values.getString("access_token");
			PublicMethod.myOutLog("token", token);
			String expires_in = values.getString("expires_in");
			shellRW.putStringValue("token", token);
			shellRW.putStringValue("expires_in", expires_in);
//			is_sharetosinaweibo.setBackgroundResource(R.drawable.on);
			initAccessToken(token,expires_in);
		}


		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}
	}
	
	private void initAccessToken(String token,String expires_in){
		Token accessToken = new Token(token, Weibo.getAppSecret());
		accessToken.setExpiresIn(expires_in);
		Weibo.getInstance().setAccessToken(accessToken);
		share2weibo(Constants.shareContent);
		if(isSinaTiaoZhuan){
			Intent intent = new Intent();
			intent.setClass(JoinStarShare.this, ShareActivity.class);
			startActivity(intent);
		}
	}
	 private void share2weibo(String content)  {
	        Weibo weibo = Weibo.getInstance();
	        weibo.share2weibo(this, weibo.getAccessToken().getToken(), weibo.getAccessToken().getSecret(), content, "");
	    }
}
