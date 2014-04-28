package com.ruyicai.activity.buy.guess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;

/**
 * 竞猜创建扎堆成功分享页面
 * @author wangw
 *
 */
public class RuyiGuessShareActivity extends RoboActivity implements OnClickListener {

	private static final String SMS_CONTENT = "古人云：“彩票是通往成功的必经之路” 快快参与如意竞猜，不用充值一样搞定500万。。。。http://wap.ruyicai.com/w/";
	private static final int SHOWCONTACTS_CODE=1001;
	private static final int SEND_SMS_RESULT_CODE=1002;
	/*号码*/
	private static final String NUMBER = "number";

	@InjectView(R.id.share_wx)
	private Button mShare_wx;

	@InjectView(R.id.share_sms)
	private Button mShare_sms;
	/*用户当前选择的联系人名称*/
	private String mCurrUserName;
	/*多个号码选择*/
	private AlertDialog mNumberDialog;
	private ListView mNumberList;
	/*数据源*/
	private List<Map<String,String>> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_guess_share);
		mData = new ArrayList<Map<String,String>>();
		initViews();
	}

	/**
	 * 初始化组件
	 */
	private void initViews() {
		mShare_sms.setOnClickListener(this);
		mShare_wx.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.share_sms:	//短信分享
			showSystemContacts();
			break;

		case R.id.share_wx:	//微信分享
			break;
		}
	}


	/**
	 * 显示系统通讯录
	 */
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

	/**
	 * 读取指定联系人的通信信息
	 * @param contactData
	 */
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
			sendSMS(mData.get(0).get(NUMBER));
		}
	}

	/**
	 *	发送短信
	 * @param number
	 */
	private void sendSMS(String number){
		Uri uri = Uri.parse("smsto:"+number);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.putExtra("address", number);
		intent.putExtra("sms_body", SMS_CONTENT);
		intent.setType("vnd.android-dir/mms-sms");
		startActivity(intent);
	}

	/**
	 * 弹出多个号码选择框
	 */
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
				sendSMS(number);
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

}
