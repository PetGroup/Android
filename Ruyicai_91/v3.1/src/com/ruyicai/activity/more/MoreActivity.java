/**
 * 
 */
package com.ruyicai.activity.more;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.OrderPrizeDiaog;
import com.ruyicai.activity.home.HomeActivity;
import com.ruyicai.activity.home.MainGroup;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.dialog.LogOutDialog;
import com.ruyicai.dialog.MyDialogListener;
import com.ruyicai.dialog.UpdateDialog;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.interfaces.ReturnPage;
import com.ruyicai.net.newtransaction.CancleAutoLoginInterface;
import com.ruyicai.net.newtransaction.SoftwareUpdateInterface;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.ShellRWConstants;
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
public class MoreActivity extends Activity implements ReturnPage,HandlerMsg, MyDialogListener{
	private List<Map<String, Object>> list;/* �б�������������Դ */
	private TextView text;
	private static final String IICON = "IICON";
	private static final String CONTENT = "CONTENT";
	private static final String TEL = "PHONE";
	private final static String TITLE = "TITLE"; /* ���� */
//	private CompanyInfo  companyInfo = new CompanyInfo(this);//��˾���
	private Context context;
	private ProgressDialog progressdialog;
	private RelativeLayout relativeLayout;
	private String isLogined;//��sharePreference�л�ȡ��sessionid��Ӧ��ֵ
	/**
	 * ���˷����Ƿ���ת
	 */
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
		shellRW = new RWSharedPreferences(MoreActivity.this, "addInfo");
		orderPrizeDialog = new OrderPrizeDiaog(shellRW, MoreActivity.this);
		context = this;
//		initView();
		showMoreListView();
//		appc=(ApplicationContext)getApplication();
	}


	/**
	 *  �����ࡱѡ���б�
	 */
	public  void showMoreListView() {
		returnType = 0;
		setContentView(R.layout.ruyihelper_listview);
		relativeLayout = (RelativeLayout) findViewById(R.id.ruyihelper_listview_relative);
		relativeLayout.setVisibility(RelativeLayout.GONE);
		// ����Դ
		list = getListForMoreAdapter();

		ListView listview = (ListView) findViewById(R.id.ruyihelper_listview_ruyihelper_id);

		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,R.layout.ruyihelper_listview_icon_item, new String[] {
						TITLE, IICON ,TEL,CONTENT }, new int[] { R.id.ruyihelper_icon_text,R.id.ruyihelper_iicon,R.id.ruyihelper_icon_layout_text1,
				                                   R.id.ruyihelper_icon_layout_text2 });

		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);

		/* �б�ĵ����ı��� */
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				textStr = text.getText().toString();
//				relativeLayout.setVisibility(RelativeLayout.VISIBLE);
//				iQuitFlag = 10;//��ǰ��������һ��
				onClickListener(textStr);
			}

		};
		listview.setOnItemClickListener(clickListener);

	}
	/**
	 * ��ת������ҳ��
	 */
	private void showShareView(){
		setContentView(R.layout.ruyicai_share);
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
			case R.id.tableRow_sharetotecent:
				
				break;
			case R.id.tableRow_sharetorenren:
				StatusDemo.publishStatusOneClick(MoreActivity.this, renren);
				break;
			case R.id.tableRow_sharetomsg:
				shareToMsg();
				break;
			case R.id.tableRow_kaijiangdingyue:
				orderPrizeDialog.orderPrizeDialog().show();
				break;
			case R.id.auto_login_set_checkbox:
				 boolean  is_auto_login = shellRW.getBooleanValue("auto_login");
				if(isLogined.equals("")||isLogined.equals("null")){
					Toast.makeText(MoreActivity.this, "���ȵ�¼��", Toast.LENGTH_SHORT).show();
				}else{
					if(is_auto_login){
						cancleAutoLogin();
					}else{
						Toast.makeText(MoreActivity.this, "�Զ���¼ֻ���ڵ�¼ʱ����!", Toast.LENGTH_SHORT).show();
					}
				}
				
				break;
//			case R.id.isSharetoSina:
//				isSinaTiaoZhuan = false;
//				if(shellRW.getStringValue("token").equals("")){
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
		String CONSUMER_KEY = "2806185439";// �滻Ϊ�����ߵ�appkey������"1646212960";
		String CONSUMER_SECRET = "f9acd48bfcb796da21f55015a2752ede";// �滻Ϊ�����ߵ�appkey����
		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
		// Oauth2.0
		// ��ʽ��Ȩ��֤��ʽ
		weibo.setRedirectUrl("http://soft.sj.91.com/91lottery/index.shtml");// �˴��ص�ҳ����Ӧ���滻Ϊ��appkey��Ӧ��Ӧ�ûص�ҳ
		// ��Ӧ��Ӧ�ûص�ҳ���ڿ����ߵ�½����΢������ƽ̨֮��
		// �����ҵ�Ӧ��--Ӧ������--Ӧ����Ϣ--�߼���Ϣ--��Ȩ����--Ӧ�ûص�ҳ�������úͲ鿴��
		// Ӧ�ûص�ҳ����Ϊ��
		weibo.authorize(MoreActivity.this, new AuthDialogListener());
	}

	/**
	 * �б���ʵ�ַ���
	 * @param str
	 */
	public void onClickListener(String str){
		/* ������ */
		if (getString(R.string.menu_checkupdate).equals(str)) {
			if (HomeActivity.softwareErrorCode.equals("true")) {
				MainUpdate update = new MainUpdate(MoreActivity.this,new Handler(),HomeActivity.softwareurl, HomeActivity.softwaremessageStr, HomeActivity.softwaretitle);
				update.showDialog();
				update.createMyDialog();
			}else if(HomeActivity.softwareErrorCode.equals("not")){
				isUpdateNet();
			}else{
				Toast.makeText(this, "��ǰ�Ѿ������°汾��", Toast.LENGTH_SHORT).show();
			}
		}
		/* �ͷ� */
		if (getString(R.string.phone_kefu_title).equals(str)) {
			   phoneKefu();  
		}
		/* ���� */
		if (getString(R.string.share).equals(str)) {
//			switchView(systemSet.showView());
			showShareView();
		}
		/* ��Ҫ����*/
		if (getString(R.string.menu_feedback).equals(str)) {
			Intent intent1 = new Intent(MoreActivity.this, FeedBack.class);
			startActivity(intent1);		}
		/* �û����� */
		if (getString(R.string.menu_help).equals(str)) {
			Intent intent2 = new Intent(MoreActivity.this, HelpCenter.class);
			startActivity(intent2);
		}
		/* ������Ȩ*/
		if (getString(R.string.menu_about).equals(str)) {
			Intent intent3 = new Intent(MoreActivity.this, CompanyInfo.class);
			startActivity(intent3);
		}
		/*�������*/
		if(getString(R.string.settings).equals(str)){
			showSettingView();
		}
		/*�������*/
		if(getString(R.string.ruyihelper_about).equals(str)){
			Intent intent = new Intent(MoreActivity.this, CompanyInfo.class);
			Bundle bundle = new Bundle();
			bundle.putString(CompanyInfo.TITLE,getString(R.string.ruyihelper_about));
			bundle.putString(CompanyInfo.URL,"ruyihelper_about.html");
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
  
    /**
     * ����ͷ��绰
     */
	private void phoneKefu() {
		Intent intent =new Intent();  
		    intent.setAction("android.intent.action.CALL");  
		    intent.setData(Uri.parse("tel:"+getString(R.string.phone_kefu)));  
		    //������ͼ��������������绰���Ź���  
		    startActivity(intent);
	}
	/**
	 * �������View
	 */
	private void showSettingView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.applicationsetting);
		returnType = 2;
		kaijiangdingyue = (RelativeLayout)findViewById(R.id.tableRow_kaijiangdingyue);
		kaijiangdingyue.setOnClickListener(moreActivityListener);
		auto_login_set = (Button)findViewById(R.id.auto_login_set_checkbox);
//		is_sharetorenren = (Button)findViewById(R.id.isSharetoRenren);
//		is_sharetosinaweibo = (Button)findViewById(R.id.isSharetoSina);
//		initIsSharetosinaweiboBtn();
//		is_sharetorenren.setOnClickListener(moreActivityListener);
//        is_sharetosinaweibo.setOnClickListener(moreActivityListener);
		initAutoLoginSet();
	}
	/**
	 * ��ʼ�� �Զ���¼�ؼ�����
	 */
	private  void  initAutoLoginSet(){
		   isLogined = shellRW.getStringValue("sessionid");
		  boolean  is_auto_login = shellRW.getBooleanValue(ShellRWConstants.AUTO_LOGIN);
          if(isLogined.equals("")||isLogined.equals("null")){
        	  auto_login_set.setBackgroundResource(R.drawable.off);
          }else{
        	  if(is_auto_login){
        		  auto_login_set.setBackgroundResource(R.drawable.on);
        	  }else{
        		  auto_login_set.setBackgroundResource(R.drawable.off);
        	  }
        	 
          }
          auto_login_set.setOnClickListener(moreActivityListener);
         
	}
//	private void initIsSharetosinaweiboBtn(){
//	  String token = shellRW.getStringValue("token");
//	  if(token.equals("")){
//		  is_sharetosinaweibo.setBackgroundResource(R.drawable.off);
//	  }else{
//		  is_sharetosinaweibo.setBackgroundResource(R.drawable.on);
//	  }
//	}
	private  void cancleAutoLogin(){
		pBar = UserCenterDialog.onCreateDialog(this,"ȡ���С���");
		pBar.show();
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				String userno = shellRW.getStringValue("userno");
				// TODO Auto-generated method stub
				String cancleBack = CancleAutoLoginInterface.cancelAutoLogin(userno);
				try {
					JSONObject netBack = new JSONObject(cancleBack);
					String errorCode = netBack.getString("error_code");
					String massage = netBack.getString("message");
					if(errorCode.equals("0000")){
						auto_login_set.setBackgroundResource(R.drawable.off);
						shellRW.putBooleanValue(ShellRWConstants.AUTO_LOGIN, false);
						pBar.dismiss();
					}else{
						auto_login_set.setBackgroundResource(R.drawable.on);
						shellRW.putBooleanValue(ShellRWConstants.AUTO_LOGIN, true);
						pBar.dismiss();
					}
					Toast.makeText(MoreActivity.this, massage, Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (RuntimeException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 *  ��á����ࡱ�б�������������Դ
	 * @return
	 */
	protected List<Map<String, Object>> getListForMoreAdapter() {
//		getString(R.string.menu_orderprize),
//		getString(R.string.menu_userexit),
//		getString(R.string.more_updateuser),
		String[] titles = {
				getString(R.string.phone_kefu_title),
				getString(R.string.menu_help),
				getString(R.string.menu_checkupdate),
				getString(R.string.share),
				getString(R.string.menu_feedback),
				getString(R.string.menu_about),
				getString(R.string.settings),
				getString(R.string.ruyihelper_about)
				};
		int it = R.drawable.xiangyou;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles[i]);
			map.put(IICON, it);
            if(i==0){
            	map.put(CONTENT, getString(R.string.phone_kefu_content));
            	map.put(TEL, getString(R.string.phone_kefu));
            }else{
            	map.put(CONTENT,"");
            	map.put(TEL, "");
            }
			list.add(map);

		}

		return list;
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
    /**
     * ���ص���ǰ����
     */
	public void returnMain() {
		// TODO Auto-generated method stub
		showMoreListView();
	}
    /**
     *  ȡ���������ӿ�
     */
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

	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(returnType==2){
			initAutoLoginSet();
		}
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
    /**
     * ��д�ؽ�
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
		   case 4:
	    	  if(returnType == 0){
	    		  ExitDialogFactory.createExitDialog(this);	  
	    	  }else{
	    		  showMoreListView();
	    	  }
			  break;
		}
		return false;
	}
	
	
	/**
	 * ��������°汾
	 * 
	 */
	public void isUpdateNet(){
		pBar = UserCenterDialog.onCreateDialog(this);
		pBar.show();
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject obj;
				try {
				obj = new JSONObject(SoftwareUpdateInterface.getInstance().softwareupdate(null));
				pBar.dismiss();
				String softwareErrorCode = obj.getString("errorCode");
				if (softwareErrorCode.equals("true")) {
					// ��Ҫ����,������������ֶ�
					final String softwareurl = obj.getString("updateurl");
					final String softwaretitle = obj.getString("title");
					final String softwaremessageStr = obj.getString("message");
					handler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							MainUpdate update = new MainUpdate(MoreActivity.this,new Handler(),softwareurl, softwaremessageStr, softwaretitle);
							update.showDialog();
							update.createMyDialog();
						}
					});
				}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	class MainUpdate extends UpdateDialog{

		public MainUpdate(Activity activity, Handler handler, String url,
				String message, String title) {
			super(activity, handler, url, message, title);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCancelButton() {
			// TODO Auto-generated method stub
			
		}


		
	}
	@Override
	public void onOkClick() {
		// TODO Auto-generated method stub
		logOutDialog.clearLastLoginInfo();
		Intent intent = new Intent("com.ruyicai.activity.home.MainGroup.inittop");
		sendBroadcast(intent);	
	}
	@Override
	public void onCancelClick() {
		// TODO Auto-generated method stub
		
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
			intent.setClass(MoreActivity.this, ShareActivity.class);
			startActivity(intent);
		}
	}
	 private void share2weibo(String content)  {
	        Weibo weibo = Weibo.getInstance();
	        weibo.share2weibo(this, weibo.getAccessToken().getToken(), weibo.getAccessToken().getSecret(), content, "");
	    }
}
