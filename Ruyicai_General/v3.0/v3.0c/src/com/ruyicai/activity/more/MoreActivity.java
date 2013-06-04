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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.home.HomeActivity;
import com.ruyicai.activity.more.share.ShareActivity;
import com.ruyicai.activity.more.share.Token;
import com.ruyicai.activity.more.share.Weibo;
import com.ruyicai.activity.more.share.WeiboDialogListener;
import com.ruyicai.activity.more.sharetorenren.Renren;
import com.ruyicai.activity.more.sharetorenren.StatusDemo;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.dialog.LogOutDialog;
import com.ruyicai.dialog.MyDialogListener;
import com.ruyicai.dialog.UpdateDialog;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.interfaces.ReturnPage;
import com.ruyicai.net.newtransaction.SoftwareUpdateInterface;
import com.ruyicai.service.PrizeNotificationService;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;

/**
 * �������
 * @author Administrator
 *
 */
public class MoreActivity extends Activity implements ReturnPage,HandlerMsg, MyDialogListener{
	private List<Map<String, Object>> list;/* �б�������������Դ */
	private TextView text;
	private static final String IICON = "IICON";
	private final static String TITLE = "TITLE"; /* ���� */
//	private CompanyInfo  companyInfo = new CompanyInfo(this);//��˾���
	private Context context;
	private ProgressDialog progressdialog;
	private RelativeLayout relativeLayout;
	public static int iQuitFlag = 0; // �����˳�
	MyHandler handler = new MyHandler(this);//�Զ���handler
	String textStr;
    ProgressDialog pBar;
    boolean[] isOrderPrize = new boolean[8];
    RWSharedPreferences shellRW ;
    LogOutDialog logOutDialog;
    
    int returnType = 0;//1Ϊ����ҳ��ķ��ز�����0Ϊ���ظ���
    
    RelativeLayout sharerenren,sharesina,sharetecent,sharetomsg;
    private Renren renren;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shellRW = new RWSharedPreferences(MoreActivity.this, "addInfo");
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
						TITLE, IICON }, new int[] { R.id.ruyihelper_icon_text,R.id.ruyihelper_iicon });

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
		sharesina.setOnClickListener(shareListener);
		sharetecent.setOnClickListener(shareListener);
		sharerenren.setOnClickListener(shareListener);
		sharetomsg.setOnClickListener(shareListener);
	}
	
	OnClickListener shareListener = new OnClickListener() {
		
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
			}
		}
	};
	
	
	private  void	shareToMsg(){
		Uri smsToUri = Uri.parse("smsto:");// ��ϵ�˵�ַ
		Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,smsToUri);
		mIntent.putExtra("sms_body", Constants.shareContent);// ���ŵ�����
		startActivity(mIntent);
	}
	
	private static final String CONSUMER_KEY = "2143826468";// �滻Ϊ�����ߵ�appkey������"1646212960";
	private static final String CONSUMER_SECRET = "f3199c4912660f1bcbdee7cfc37c636e";// �滻Ϊ�����ߵ�appkey����
	private void oauthOrShare(){
		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
		// Oauth2.0
		// ��ʽ��Ȩ��֤��ʽ
		weibo.setRedirectUrl("http://wap.ruyicai.com/w/client/download.jspx");// �˴��ص�ҳ����Ӧ���滻Ϊ��appkey��Ӧ��Ӧ�ûص�ҳ
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
				Toast.makeText(this, "��ǰ�Ѿ������°汾��", Toast.LENGTH_SHORT);
			}
		}
		/* �������� */
		if (getString(R.string.menu_orderprize).equals(str)) {
			orderPrizeDialog().show();
 		}
		/* ���� */
		if ("����".equals(str)) {
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
		/* ע����½*/
		if (getString(R.string.menu_userexit).equals(str)) {
            logOutDialog = LogOutDialog.createDialog(this);
			logOutDialog.setOnClik(this);
		}
		/*�л��û�*/
		if(getString(R.string.more_updateuser).equals(str)){
			Intent intent = new Intent(this, UserLogin.class);
			startActivity(intent);
		}
	}

	/**
	 *  ��á����ࡱ�б�������������Դ
	 * @return
	 */
	protected List<Map<String, Object>> getListForMoreAdapter() {

		String[] titles = {
				getString(R.string.menu_checkupdate),
				getString(R.string.menu_orderprize),
                "����"				,
				getString(R.string.menu_feedback),
				getString(R.string.menu_help),
				getString(R.string.menu_about),
				getString(R.string.menu_userexit),
				getString(R.string.more_updateuser)
				};
		int it = R.drawable.xiangyou;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles[i]);
			map.put(IICON, it);

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
	
	
	AlertDialog orderPrizeDialog(){
		getOrderPrize();
		return new AlertDialog.Builder(MoreActivity.this)
         .setTitle("��������")
         .setMultiChoiceItems(R.array.lotttery_list,
        		 isOrderPrize,
                 new DialogInterface.OnMultiChoiceClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton,boolean isChecked) {
                         /* User clicked on a check box do some stuff */
                     }
                 })
         .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 /* User clicked Yes so do some stuff */
            	Toast.makeText(MoreActivity.this, "�����ɹ�", Toast.LENGTH_SHORT).show();
            	 saveOrderPrize();
            	 startPrizeService();//���ȷ���Ϳ���Service
             }
         }).create();
	}
	/**
	 * ���涩������
	 */
	private void saveOrderPrize(){
		 for(int i=0;i<isOrderPrize.length;i++){
    		 shellRW.putBooleanValue(Constants.orderPrize[i], isOrderPrize[i]);
    	 }
	}
	
	/**
	 * ��ȡ��������
	 */
	private void getOrderPrize(){
		 for(int i=0;i<isOrderPrize.length;i++){
			 isOrderPrize[i] = shellRW.getBooleanValue(Constants.orderPrize[i]);
    	 }
	}
	
	private void startPrizeService(){
		   Intent prizeIntent = new Intent( this, PrizeNotificationService.class);
		   startService(prizeIntent);
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
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			Token accessToken = new Token(token, Weibo.getAppSecret());
			accessToken.setExpiresIn(expires_in);
			Weibo.getInstance().setAccessToken(accessToken);
			share2weibo(Constants.shareContent);
			Intent intent = new Intent();
			intent.setClass(MoreActivity.this, ShareActivity.class);
			startActivity(intent);
		}


		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}
	}
	
	 private void share2weibo(String content)  {
	        Weibo weibo = Weibo.getInstance();
	        weibo.share2weibo(this, weibo.getAccessToken().getToken(), weibo.getAccessToken().getSecret(), content, "");
	    }
	
}
