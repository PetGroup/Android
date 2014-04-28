package com.ruyicai.activity.buy.guess;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.palmdream.RuyicaiAndroid.wxapi.WXEntryActivity;
import com.ruyicai.activity.buy.guess.RuyiGuessDetailActivity.AuthDialogListener;
import com.ruyicai.adapter.ShareAdapter;
import com.ruyicai.component.view.TitleBar;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.third.share.ShareActivity;
import com.third.share.Token;
import com.third.share.Weibo;
import com.third.share.WeiboDialogListener;
import com.third.tencent.TencentShareActivity;

/**
 * 竞猜创建扎堆成功分享页面
 * @author wangw
 *
 */
public class RuyiGuessShareActivity extends RoboActivity implements OnClickListener {

	private static final String SMS_CONTENT = "古人云：“彩票是通往成功的必经之路” 快快参与如意竞猜，不用充值一样搞定500万。。。。http://wap.ruyicai.com/w/";
//	private static final int SHOWCONTACTS_CODE=1001;
//	private static final int SEND_SMS_RESULT_CODE=1002;
	/*号码*/
//	private static final String NUMBER = "number";

	@InjectView(R.id.share_wx)
	private Button mShare_wx;

	@InjectView(R.id.share_sms)
	private Button mShare_sms;
	
	@InjectView(R.id.share_description_text)
	private TextView mShareTitle;

	@InjectView(R.id.buy_guess_share_layout)
	private View mParentFrameLayout;

	private String mSharePictureName;
	private RWSharedPreferences RW;
	private String token;

	private String expires_in;

	private boolean isSinaTiaoZhuan;
	
	/*用户当前选择的联系人名称*/
//	private String mCurrUserName;
	/*多个号码选择*/
//	private AlertDialog mNumberDialog;
//	private ListView mNumberList;
	/*数据源*/
//	private List<Map<String,String>> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_guess_share);
		RW = new RWSharedPreferences(this, "shareweixin");
		initViews();
		
	}

	/**
	 * 初始化组件
	 */
	private void initViews() {
		initTatleBar();
		initShareView();
		
		mShare_sms.setOnClickListener(this);
		mShare_wx.setOnClickListener(this);
	}
	
	private void initShareView(){
		mShareTitle.setText(R.string.buy_ruyi_guess_share_title);
		
		LinearLayout shareLayout = (LinearLayout)findViewById(R.id.share_layout);
		shareLayout.setBackgroundColor(getResources().getColor(R.color.white));
		GridView gridView = (GridView)findViewById(R.id.gridview);
		gridView.setNumColumns(4);
		ShareAdapter adapter = new ShareAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:	//微信
					toWeiXin();
					break;
				case 1:	//朋友圈
					toPengYouQuan();
					break;
				case 2:	//新浪微博
					oauthOrShare();
					break;
				case 3:	//腾讯微博
					tenoauth();
					break;
				}
			}
		});
	}
	
	private void initTatleBar(){
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText(R.string.buy_ruyi_guess);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.share_sms:	//短信分享
			sendSMS();
			break;

		case R.id.share_wx:	//微信分享
			toWeiXin();
			break;
		}
	}
	
	/**
	 *	发送短信
	 * @param number
	 */
	private void sendSMS(){
		//		Uri uri = Uri.parse("smsto:"+number);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//		intent.putExtra("address", number);
		intent.putExtra("sms_body", SMS_CONTENT);
		intent.setType("vnd.android-dir/mms-sms");
		startActivity(intent);
	}
	
	
	/**
	 * 对该页面截屏并保存图片
	 */
	private void saveBitmap(){
		mParentFrameLayout.buildDrawingCache();
		Bitmap bitmap1 = mParentFrameLayout.getDrawingCache();
		mSharePictureName=PublicMethod.saveBitmap(PublicMethod.matrixBitmap(bitmap1, 400, 600));
	}
	
	/**
	 * 分享到微信
	 */
	private void toWeiXin() {
		saveBitmap();
		
		RW.putStringValue("weixin_pengyou", "toweixin");
		Intent intent = new Intent(this,
				WXEntryActivity.class);
		intent.putExtra("sharecontent","参与如意竞猜赚彩金中大奖");
		intent.putExtra("mSharePictureName",mSharePictureName);
		intent.putExtra("url","http://iphone.ruyicai.com/html/share.html?shareRuyiGuess");
		startActivity(intent);
	}

	/**
	 * 分享到朋友圈
	 */
	private void toPengYouQuan() {
		saveBitmap();
		RW.putStringValue("weixin_pengyou", "topengyouquan");
		Intent intent = new Intent(this,
				WXEntryActivity.class);
		intent.putExtra("sharecontent",getResources().getString(R.string.buy_ruyi_guess_down_title));
		intent.putExtra("mSharePictureName",mSharePictureName);
		intent.putExtra("url","http://iphone.ruyicai.com/html/share.html?shareRuyiGuess");
		startActivity(intent);
	}
	
	/**
	 * 分享到新浪微博
	 */
	private void oauthOrShare() {
		
		token = RW.getStringValue("token");
		expires_in = RW.getStringValue("expires_in");
		if (token.equals("")) {
			oauth();
		} else {
			isSinaTiaoZhuan = true;
			initAccessToken(token, expires_in);
			
		}
		
	}
	
	private void initAccessToken(String token, String expires_in) {
		Token accessToken = new Token(token, Weibo.getAppSecret());
		accessToken.setExpiresIn(expires_in);
		Weibo.getInstance().setAccessToken(accessToken);
		share2weibo("参与如意竞猜赚彩金中大奖，下载Android手机客户端:"
				+"http://iphone.ruyicai.com/html/share.html?shareRuyiGuess"/* Constants.shareContent */);
		if (isSinaTiaoZhuan) {
			Intent intent = new Intent();
			intent.setClass(this, ShareActivity.class);
			startActivity(intent);
		}
	}
	
	private void share2weibo(String content) {
		Weibo weibo = Weibo.getInstance();
		weibo.share2weibo(this, weibo.getAccessToken().getToken(), weibo
				.getAccessToken().getSecret(), content, mSharePictureName);
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
		weibo.authorize(this, new AuthDialogListener());
	}

	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			RW.putStringValue("token", token);
			RW.putStringValue("expires_in", expires_in);
			initAccessToken(token, expires_in);
			
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 分享到腾讯微博
	 */
	private void tenoauth() {
		saveBitmap();
		Intent intent = new Intent(this,
				TencentShareActivity.class);
		intent.putExtra("tencent","参与如意竞猜赚彩金中大奖，下载Android手机客户端:"
				+"http://iphone.ruyicai.com/html/share.html?shareRuyiGuess");
		intent.putExtra("bitmap",mSharePictureName);
		startActivity(intent);
		
	}


	/**
	private void showSystemContacts(){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setData(ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(intent, SHOWCONTACTS_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case SHOWCONTACTS_CODE:
			if(resultCode == RESULT_OK && data != null){
				//URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
				Uri contactData = data.getData();
				readContactInfo(contactData);
			}
			break;
		}  
	}
	private void readContactInfo(Uri contactData){
		String usernumber ="";
		//ContentProvider展示数据类似一个单个数据库表
		//ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
		ContentResolver reContentResolverol = getContentResolver();  
		//查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
		Cursor cursor = managedQuery(contactData, null, null, null, null);  
		cursor.moveToFirst(); 
		//获得DATA表中的名字
		mCurrUserName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));  
		//条件为联系人ID
		String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));  
		// 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
		Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,   
				null,   
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,   
				null,   
				null);  
		mData.clear();
		while (phone.moveToNext()) {  
			usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			Map<String, String> map = new HashMap<String, String>();
			map.put(NUMBER, usernumber);
			mData.add(map);
			Log.d("share", usernumber);
		}

		if(mData.size() > 1){
			showDialog();
		}else{
//			sendSMS(mData.get(0).get(NUMBER));
		}
	}
	public void showDialog() {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog_number, null);
		mNumberDialog = new AlertDialog.Builder(this).create();
		mNumberDialog.show();
		TextView title = (TextView) v
				.findViewById(R.id.dialog_title);
		title.setText(mCurrUserName);// 联系人姓名
		mNumberList = (ListView) v.findViewById(R.id.dialog_number_list);
		mNumberList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mNumberDialog.cancel();
				String number = mData.get(position).get(NUMBER);
//				sendSMS(number);
			}
		});
		Button ok = (Button) v.findViewById(R.id.dialog_number_img_ok);
		ok.setText(R.string.cancel);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mNumberDialog.cancel();
			}
		});
		// 适配器
		SimpleAdapter adapter = new SimpleAdapter(this, mData,
				R.layout.dialog_advice_list_item, new String[] { NUMBER },
				new int[] { R.id.dialog_advice_item_text_title });

		mNumberList.setAdapter(adapter);
		mNumberDialog.getWindow().setContentView(v);
	}
	 */
}