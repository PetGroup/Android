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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.OrderPrizeDiaog;
import com.ruyicai.activity.home.HomeActivity;
import com.ruyicai.activity.introduce.PhotoActivity;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.dialog.LogOutDialog;
import com.ruyicai.dialog.MyDialogListener;
import com.ruyicai.dialog.UpdateDialog;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.interfaces.ReturnPage;
import com.ruyicai.net.newtransaction.CancleAutoLoginInterface;
import com.ruyicai.net.newtransaction.SoftwareUpdateInterface;
import com.ruyicai.util.CallServicePhoneConfirm;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.third.share.ShareActivity;
import com.third.share.Token;
import com.third.share.Utility;
import com.third.share.Weibo;
import com.third.share.WeiboDialogListener;
import com.third.share.WeiboParameters;
import com.third.sharetorenren.AccessTokenManager;
import com.third.sharetorenren.Renren;
import com.third.sharetorenren.StatusDemo;
import com.umeng.analytics.MobclickAgent;


/**
 * 更多界面
 * @author Administrator
 *
 */
public class MoreActivity extends Activity implements ReturnPage,HandlerMsg, MyDialogListener{
	private List<Map<String, Object>> list;/* 列表适配器的数据源 */
	private TextView text;
	private static final String IICON = "IICON";
	private static final String CONTENT = "CONTENT";
	private static final String TEL = "PHONE";
	private final static String TITLE = "TITLE"; /* 标题 */
//	private CompanyInfo  companyInfo = new CompanyInfo(this);//公司简介
	private Context context;
	private ProgressDialog progressdialog;
	private RelativeLayout relativeLayout;
	private String isLogined;//从sharePreference中获取的sessionid对应的值
	/**
	 * 新浪分享是否跳转
	 */
	private boolean isSinaTiaoZhuan = true;
	public static int iQuitFlag = 0; // 代表退出
	MyHandler handler = new MyHandler(this);//自定义handler
	String textStr;
    ProgressDialog pBar;
    boolean[] isOrderPrize = new boolean[8];
    RWSharedPreferences shellRW ;
    LogOutDialog logOutDialog;
    
    String token,expires_in;
    
    int returnType = 0;//1为分享页面的返回参数，0为本地更多
    OrderPrizeDiaog orderPrizeDialog;//开奖订阅公共类
    
    RelativeLayout kaijiangdingyue,personidset,weibobangding,caizhongSetting;//设置界面 开奖订阅和个人帐号设置//彩种设置
    Button auto_login_set;//自动登录设置
    Button is_sharetorenren,is_sharetosinaweibo;//微博账号设置
    
    RelativeLayout sharerenren,sharesina,sharetecent,sharetomsg;//分享界面的几个RelativeLayout
    private Renren renren;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shellRW = new RWSharedPreferences(MoreActivity.this, "addInfo");
		orderPrizeDialog = new OrderPrizeDiaog(shellRW, MoreActivity.this);
		context = this;
//		initView();
		showMoreListView();
//		appc=(ApplicationContext)getApplication();
		Constants.shareContent ="Hi，我刚使用了如意彩手机客户端买彩票，很方便呢！" +
				"你也试试吧，彩票随身投，大奖时时有！中奖了记的要请客啊！下载地址:http://wap.ruyicai.com/w/client/download.jspx";
	}


	/**
	 *  “更多”选项列表
	 */
	public  void showMoreListView() {
		returnType = 0;
		setContentView(R.layout.ruyihelper_listview);
		relativeLayout = (RelativeLayout) findViewById(R.id.ruyihelper_listview_relative);
		relativeLayout.setVisibility(RelativeLayout.GONE);
		// 数据源
		list = getListForMoreAdapter();

		ListView listview = (ListView) findViewById(R.id.ruyihelper_listview_ruyihelper_id);

		// 适配器
		SimpleAdapter adapter = new SimpleAdapter(this, list,R.layout.ruyihelper_listview_icon_item, new String[] {
						TITLE, IICON ,TEL,CONTENT }, new int[] { R.id.ruyihelper_icon_text,R.id.ruyihelper_iicon,R.id.ruyihelper_icon_layout_text1,
				                                   R.id.ruyihelper_icon_layout_text2 });

		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);

		/* 列表的点击后的背景 */
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				textStr = text.getText().toString();
//				relativeLayout.setVisibility(RelativeLayout.VISIBLE);
//				iQuitFlag = 10;//当前主界面下一级
				onClickListener(textStr);
			}

		};
		listview.setOnItemClickListener(clickListener);

	}
	/**
	 * 跳转到分享页面
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
				StatusDemo st=new StatusDemo();
				st.publishStatusOneClick(MoreActivity.this, renren);
				break;
			case R.id.tableRow_sharetomsg:
				shareToMsg();
				break;
			case R.id.tableRow_kaijiangdingyue:
				orderPrizeDialog.orderPrizeDialog().show();
				break;
			case R.id.tableRow_weibobangding:
				showsharesettingView();
				break;
			case R.id.auto_login_set_checkbox:
				 boolean  is_auto_login = shellRW.getBooleanValue("auto_login");
				if(isLogined.equals("")||isLogined.equals("null")){
					Toast.makeText(MoreActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
				}else{
					if(is_auto_login){
						cancleAutoLogin();
					}else{
						Toast.makeText(MoreActivity.this, "自动登录只能在登录时设置!", Toast.LENGTH_SHORT).show();
					}
				}
				
				break;
			case R.id.isSharetoSina:
				isSinaTiaoZhuan = false;
				token = shellRW.getStringValue("token");
				if(token.equals("")){
					oauth();;
				}else{
					shellRW.putStringValue("token", "");
					is_sharetosinaweibo.setBackgroundResource(R.drawable.off);
				}
				break;
			case R.id.isSharetoRenren:
				AccessTokenManager manager =new AccessTokenManager(MoreActivity.this);
				renren=new Renren(MoreActivity.this);
				if(manager.getAccessToken()==null||manager.getAccessToken().equals("")){
					StatusDemo st1 =new StatusDemo();
					st1.publishStatusOneClick(MoreActivity.this, renren);
				}else{
					renren.logout(MoreActivity.this);
					is_sharetorenren.setBackgroundResource(R.drawable.off);
				}
				break;
				case R.id.caizhong_setting://彩种设置
				Intent intent = new Intent(MoreActivity.this,CaizhongSettingActivity.class);
				startActivity(intent);
				break;
			}
		}
	};
	private boolean is_auto_login;//从sharedpreference中获取用户的自动登录设置
	
	

	
	private  void	shareToMsg(){
		Uri smsToUri = Uri.parse("smsto:");// 联系人地址
		Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,smsToUri);
		mIntent.putExtra("sms_body", Constants.shareContent);// 短信的内容
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
		// 隐式授权认证方式
		weibo.setRedirectUrl(Constants.CONSUMER_URL);// 此处回调页内容应该替换为与appkey对应的应用回调页
		// 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
		// 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
		// 应用回调页不可为空
		weibo.authorize(MoreActivity.this, new AuthDialogListener());
	}

	/**
	 * 列表点击实现方法
	 * @param str
	 */
	public void onClickListener(String str){
		/* 检测更新 */
		if (getString(R.string.menu_checkupdate).equals(str)) {
			if (HomeActivity.softwareErrorCode.equals("true")) {
				MainUpdate update = new MainUpdate(MoreActivity.this,new Handler(),HomeActivity.softwareurl, HomeActivity.softwaremessageStr, HomeActivity.softwaretitle);
				update.showDialog();
				update.createMyDialog();
			}else if(HomeActivity.softwareErrorCode.equals("not")){
				isUpdateNet();
			}else{
				Toast.makeText(this, "当前已经是最新版本！", Toast.LENGTH_SHORT).show();
			}
		}
		/* 新手指南 */
		if (getString(R.string.menu_introduce).equals(str)) {
			Intent intent4 = new Intent(MoreActivity.this, PhotoActivity.class);
			intent4.putExtra("isHelp", true);
			startActivity(intent4);
		}
		/* 客服 */
		if (getString(R.string.phone_kefu_title).equals(str)) {
			CallServicePhoneConfirm.phoneKefu(this);
		}
		/* 分享 */
		if (getString(R.string.share).equals(str)) {
//			switchView(systemSet.showView());
			showShareView();
		}
		/* 我要反馈*/
		if (getString(R.string.menu_feedback).equals(str)) {
			Intent intent1 = new Intent(MoreActivity.this, FeedBack.class);
			startActivity(intent1);		}
		/* 用户帮助 */
		if (getString(R.string.menu_help).equals(str)) {
			Intent intent2 = new Intent(MoreActivity.this, HelpCenter.class);
			startActivity(intent2);
		}
		/* 关于授权*/
		if (getString(R.string.menu_about).equals(str)) {
			Intent intent3 = new Intent(MoreActivity.this, CompanyInfo.class);
			Bundle bundle = new Bundle();
			bundle.putString(CompanyInfo.TITLE,getString(R.string.menu_about));
			bundle.putString(CompanyInfo.URL,"ruyihelper_authorizing.html");
			intent3.putExtras(bundle);
			startActivity(intent3);
		}
		/*软件设置*/
		if(getString(R.string.settings).equals(str)){
			showSettingView();
		}
		/*关于软件*/
		if(getString(R.string.ruyihelper_about).equals(str)){
			Intent intent = new Intent(MoreActivity.this, AboutSoftwareActivity.class);
			startActivity(intent);
		}
	}
	/**
	 * 软件设置View
	 */
	private void showSettingView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.applicationsetting);
		returnType = 2;
		kaijiangdingyue = (RelativeLayout)findViewById(R.id.tableRow_kaijiangdingyue);
		weibobangding=(RelativeLayout)findViewById(R.id.tableRow_weibobangding);
		weibobangding.setOnClickListener(moreActivityListener);
		kaijiangdingyue.setOnClickListener(moreActivityListener);
		auto_login_set = (Button)findViewById(R.id.auto_login_set_checkbox);
		//得到彩种信息的布局文件并设置监听
		caizhongSetting = (RelativeLayout)findViewById(R.id.caizhong_setting);
		caizhongSetting.setOnClickListener(moreActivityListener);
		
		
//		is_sharetorenren = (Button)findViewById(R.id.isSharetoRenren);
//		is_sharetosinaweibo = (Button)findViewById(R.id.isSharetoSina);
//		initIsSharetosinaweiboBtn();
//		is_sharetorenren.setOnClickListener(moreActivityListener);
//        is_sharetosinaweibo.setOnClickListener(moreActivityListener);
		initAutoLoginSet();
	}
	
	/***分享绑定设置
	 * 
	 */
	private void showsharesettingView(){
		returnType=3;
		setContentView(R.layout.weibo_bind);
		is_sharetorenren=(Button)findViewById(R.id.isSharetoRenren);
		is_sharetosinaweibo=(Button)findViewById(R.id.isSharetoSina);
		is_sharetorenren.setBackgroundResource(R.drawable.off);
		token = shellRW.getStringValue("token");
		expires_in = shellRW.getStringValue("expires_in");
		if(token.equals("")){
			is_sharetosinaweibo.setBackgroundResource(R.drawable.off);
		}else{
			is_sharetosinaweibo.setBackgroundResource(R.drawable.on);
		}
		is_sharetosinaweibo.setOnClickListener(moreActivityListener);
		is_sharetorenren.setOnClickListener(moreActivityListener);
		AccessTokenManager manager =new AccessTokenManager(this);
		if(manager.getAccessToken()==null||manager.getAccessToken().equals("")){
			is_sharetorenren.setBackgroundResource(R.drawable.off);
		}else{
			is_sharetorenren.setBackgroundResource(R.drawable.on);
		}
	}
	/**
	 * 初始化 自动登录控件设置
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
		pBar = UserCenterDialog.onCreateDialog(this,"取消中……");
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
	 *  获得“更多”列表适配器的数据源
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
				getString(R.string.menu_introduce),
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
     * 切换view
     * 
     */
	public void switchView(View view){
		setContentView(view);
	}

	/**
	 *  网络连接框
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			progressdialog.setMessage("网络连接中...");
			progressdialog.setIndeterminate(true);
			return progressdialog;
		}
		}
		return null;
	}
	/**
	 * 获得当前界面context
	 */
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
    /**
     * 返回到当前界面
     */
	public void returnMain() {
		// TODO Auto-generated method stub
		showMoreListView();
	}
    /**
     *  取消网络连接框
     */
	public void dismissDialog() {
		// TODO Auto-generated method stub
		progressdialog.dismiss();
	}
	 /**
     *  显示网络连接框
     */
	public void showDialog() {
		// TODO Auto-generated method stub
		showDialog(0);
	}
    /**
     * 返回码处理方法
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
	 * 从上一个activity返回当前activity执行的方法
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (renren != null) {
			renren.authorizeCallback(requestCode, resultCode, data);
		}
		}
    /**
     * 重写回建
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
	 * 联网检测新版本
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
					// 需要升级,设置升级相关字段
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
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			shellRW.putStringValue("token", token);
			shellRW.putStringValue("expires_in", expires_in);
			if(returnType==3){
			is_sharetosinaweibo.setBackgroundResource(R.drawable.on);
			}
//			initAccessToken(token,expires_in);
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
	 
	 @Override 
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			MobclickAgent.onPause(this);//BY贺思明 2012-7-24
		}
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			if(returnType==3){
				AccessTokenManager manager =new AccessTokenManager(this);
				if(manager.getAccessToken()==null||manager.getAccessToken().equals("")){
					is_sharetorenren.setBackgroundResource(R.drawable.off);
				}else{
					is_sharetorenren.setBackgroundResource(R.drawable.on);
				}
			}
			super.onResume();
			MobclickAgent.onResume(this);//BY贺思明 2012-7-24
		}
}
