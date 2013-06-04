package com.ruyicai.activity.home;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.CheckWireless;
import com.ruyicai.dialog.ShowConnectionDialog;
import com.ruyicai.dialog.ShowNoConnectionDialog;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.SoftwareUpdateInterface;
import com.ruyicai.service.AlarmService_Service;
import com.ruyicai.util.ClockThread;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.ShellRWSharesPreferences;

/**
 * @author Administrator
 *
 */
public class HomeActivity extends Activity{
	
	private PendingIntent mAlarmSender;
	long firstTime = SystemClock.elapsedRealtime();
	private static final String TAG = "HomePage";
	public static String channel_id = "C10319";
	public static String channel = Constants.COOP_ID;
	// ����������
	private String softwareErrorCode = "";
	private String softwareurl = "";
	private String softwaremessageStr = "";
	private String softwaretitle = "";
	private ShellRWSharesPreferences shellRW;
	private StartTask mStartTask;
	private boolean b = false;
	private ImageView imageview;
	int iShowStatus = 0;
	public boolean isHint = false;
	// ������Ϣ
	public Handler mHandler = new Handler();
	//����������ȡ�����´θ��µ����� 

	// ʵ������ļ�⣬���û����������ʾһ���Ի�����ʾ�����������á�
	CheckWireless checkWireless;	
	private ProgressDialog pBar;
	private Handler handler = new Handler();
	private JSONObject obj;
	private String softwareUpdateInfo;	
	
	
	Bitmap resizedBitmap = null;
	private String notificationMark;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //ȫ����ʾ
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home_activity);
		getMachineInfo();// ��ȡ�ֻ���Ϣ
		clearLastLoginInfo();// ����ϴε�¼��Ϣ
		// ��ȡ����������
		shellRW = new ShellRWSharesPreferences(this, "addInfo");
		String channel_id = shellRW.getUserLoginInfo("channel_id");
		String channel = shellRW.getUserLoginInfo("channel");
		if (channel_id == null || channel == null) {
			shellRW.setUserLoginInfo("channel_id", this.channel_id);
			shellRW.setUserLoginInfo("channel", this.channel);
		} else {
			this.channel_id = channel_id;
			this.channel = channel;
		}
		//����ʱʱ�ʣ�11ѡ5��ѡ��ť��ࡣ
		DisplayMetrics metric = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metric);
		Constants.SCREEN_WIDTH = getWindowManager().getDefaultDisplay().getWidth();
		  if(Constants.SCREEN_WIDTH==240){
		    	Constants.PADDING = 20;
		    }else if(Constants.SCREEN_WIDTH==320){
//		    	Log.v("padding", "dopadding");
		    	Constants.PADDING = 27;
		    }else  if(Constants.SCREEN_WIDTH==480){
		    	Constants.PADDING = 40;
		 }
		//��ȡ����  
	   Intent intent1 = getIntent();
	   notificationMark = intent1.getStringExtra(Constants.NOTIFICATION_MARKS);  
	   
		// ��ʼ��imageview
		imageview = (ImageView) this.findViewById(R.id.cp1);
		// ����ͼƬ
		Resources r = this.getResources();
		InputStream is = r.openRawResource(R.drawable.cp1);

		BitmapDrawable bmpDraw = new BitmapDrawable(is);
		Bitmap iBackgrounBitmap = bmpDraw.getBitmap();

		// ��ͼ
		Matrix matrix = new Matrix();

		float iScreenWidth = PublicMethod.getDisplayWidth(this);
		float iScreenHeight = PublicMethod.getDisplayHeight(this);
		float w = iScreenWidth / iBackgrounBitmap.getWidth();
		float h = iScreenHeight / iBackgrounBitmap.getHeight();
		matrix.postScale(w, h);
		resizedBitmap = Bitmap.createBitmap(iBackgrounBitmap, 0, 0,
				iBackgrounBitmap.getWidth(), iBackgrounBitmap.getHeight(),
				matrix, true);
		imageview.setImageBitmap(resizedBitmap);

		checkWireless = new CheckWireless(this);
		// �ͻ�����������˳���� 1->3->5->6->4
		// ��ʼ��handler
		// �ͻ�����������˳���� 1->3->5->6->4

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch (msg.what) {
				case 1: {
					iShowStatus = 3;
					checkWirelessNetwork();
					break;
				}
				case 2: {
					try {
						showalert();
					} catch (Exception e) {
						e.printStackTrace();// ��ʾ��ʾ��û�����磬�û��Ƿ���� �� 2010/7/2 �³�
					}
					break;
				}
				case 3: {
					Message mg = Message.obtain();
					mg.what = 6;
					mHandler.sendMessage(mg);
					break;
				}
				case 4: {
					Intent in = new Intent(HomeActivity.this, MainGroup.class);
//					Log.v("", "191notificationMark="+notificationMark);
					in.putExtra(Constants.NOTIFICATION_MARKS, notificationMark);
					startActivity(in);
					HomeActivity.this.finish();
					break;
				}
				case 5: {
					checkCurrentVersion();// ����Ƿ�Ҫ����
					break;
				}
				case 6:

					mStartTask = new StartTask();
					mStartTask.execute();
					break;
				case 7:
					Toast.makeText(HomeActivity.this, "�����쳣��", Toast.LENGTH_SHORT).show();
					break;
				case 8:
					Toast.makeText(HomeActivity.this, "�쳣��", Toast.LENGTH_SHORT).show();
					break;
				case 9:
					update(softwareurl, softwaremessageStr, softwaretitle);
					break;
				}
				
			}
		};
		initBitmap();//��ʼ��С��
		Message mg = Message.obtain();
		mg.what = 1;
		mHandler.sendMessage(mg);
	}
    private void startAlerm(int second){
     	mAlarmSender = PendingIntent.getService(HomeActivity.this,0, new Intent(HomeActivity.this, AlarmService_Service.class), 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, second);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),  mAlarmSender);
//     	long firstTime = SystemClock.elapsedRealtime();
//
//        // Schedule the alarm!
//        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
//        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime, second*1000, mAlarmSender);
    }
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void checkWirelessNetwork() {
		shellRW = new ShellRWSharesPreferences(this, "addInfo");
		if (!checkWireless.getConnectGPRS() && !checkWireless.getConnectWIFI()) {
			ShowNoConnectionDialog showNoConnectionDialog = new ShowNoConnectionDialog(this, this);
			showNoConnectionDialog.showNoConnectionDialog();
		} else {
			try {
				if (shellRW.getUserLoginInfo("noHint") != null) {
					if (shellRW.getUserLoginInfo("noHint").equals("false")) {
						ShowConnectionDialog showConnectionDialog = new ShowConnectionDialog(this, this, shellRW);
						showConnectionDialog.showConnectionDialog(this);
					} else {
						Message mg = Message.obtain();
						mg.what = 3;
						mHandler.sendMessage(mg);
					}
				} else {
					ShowConnectionDialog showConnectionDialog = new ShowConnectionDialog(this, this, shellRW);
					showConnectionDialog.showConnectionDialog(this);
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * �����쳣��ʾ��
	 */
	public void showalert() {
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ʾ")
				.setMessage("��������쳣").setPositiveButton("����",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								shellRW = new ShellRWSharesPreferences(
										HomeActivity.this, "addInfo");
								for (int i = 0; i < 7; i++) {
									shellRW.setUserLoginInfo("information" + i,"");
								}
								Intent in = new Intent(HomeActivity.this,MainGroup.class);
//								Log.v("", "283notificationMark="+notificationMark);
								in.putExtra(Constants.NOTIFICATION_MARKS, notificationMark);
								startActivity(in);
								HomeActivity.this.finish();
							}

						}).setNegativeButton("�˳�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								HomeActivity.this.finish();
							}
						});

		dialog.show();
	}

	/**
	 * �˺��������ѷϳ�
	 */
	public void checkCurrentVersion() {
		Message msg = new Message();
		msg.what = 6;
		mHandler.sendMessage(msg);
	}
	
   
	
	private boolean initProxySetting() {
        ConnectivityManager ConnMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = ConnMgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //comment for XT800, it work well without the proxy settings
                //under wifi env
//                mProxyHost = android.net.Proxy.getHost(mContext);
//                mProxyPort = android.net.Proxy.getPort(mContext);
                Constants.mProxyPort = 0;
                Constants.mProxyHost = null;
            } else {
            	Constants.mProxyHost = android.net.Proxy.getDefaultHost();
            	Constants.mProxyPort = android.net.Proxy.getDefaultPort();
            }
            return (!TextUtils.isEmpty(Constants.mProxyHost) && Constants.mProxyPort != 0);
        }
        return false;
    }
	
	/** 
	 * ��������,�����Ҫ��Ϣ,�ж��������
	 * 
	 */
	public void initSoftwareStartInfomationFromServer() {
		
		Constants.isProxyConnect = initProxySetting();//��ʼ����������
		String intevalSeconds = "1200000";
		// �ж��Ƿ�Ҫ�ϴ�ͳ����Ϣ��������
		SharedPreferences sharedPreferences = getSharedPreferences(Constants.APPNAME, MODE_PRIVATE);
		
		int gameSumCount = sharedPreferences.getInt(Constants.GAME_CLICK_SUM, 0); // ��Ϸ����+n , Ĭ��ֵΪ0
		boolean isUpdateStatInfo = false;// �Ƿ��ϴ���ͳ����Ϣ
		JSONObject statJsonObject = null;
		if (gameSumCount >= Constants.STAT_INFO_CACHE_NUM) {
			// �û������Ϸ������,ֵ���ϴ�һ��ͳ����Ϣ
			statJsonObject = new JSONObject();
			try {
				for (int i = 0; i < 12; i++) {
				statJsonObject.put(String.valueOf(i), sharedPreferences.getInt(Constants.GAME_CLASS + i, 0));// ��Ϸ����+n ,
				}
				isUpdateStatInfo = true;
			} catch (JSONException e) {
				statJsonObject = null;
			}

		}

		softwareUpdateInfo = SoftwareUpdateInterface.getInstance().softwareupdate(statJsonObject);
		try {
			obj = new JSONObject(softwareUpdateInfo);
			PublicConst.MESSAGE = obj.getString("broadcastmessage");
			if (isUpdateStatInfo == true) {
				// �ϴ���ͳ����Ϣ,�����ｫͳ����Ϣ���
				SharedPreferences.Editor editor = getSharedPreferences(Constants.APPNAME, MODE_PRIVATE).edit();
				for (int i = 0; i < 12; i++) {
				editor.putInt(Constants.GAME_CLASS + i, 0);
				}
				editor.putInt(Constants.GAME_CLICK_SUM, 0);
				editor.commit();
			}
			intevalSeconds = obj.getString("noticetime");
			softwareErrorCode = obj.getString("errorCode");
			if (softwareErrorCode.equals("true")) {
				// ��Ҫ����,������������ֶ�
				softwareurl = obj.getString("updateurl");
				softwaretitle = obj.getString("title");
				softwaremessageStr = obj.getString("message");
			}
			Constants.NEWS = obj.getString("news");
			Constants.currentLotnoInfo = obj.getJSONObject("currentBatchCode");// ��ȡ������ں���Ϣ
			ClockThread clock = new ClockThread();//���������������ʱ�߳�
			clock.startThread();
		} catch (JSONException e) {
			e.printStackTrace();
			ClockThread clock = new ClockThread();//���������������ʱ�߳�
		}

	   if(intevalSeconds!=null){
//		   Log.v("","343intevalSeconds="+intevalSeconds);
		   startAlerm(Integer.valueOf(intevalSeconds));//��ʼ����Alerm���� 
	   }
		flag = false;
	}

	/**
	 * 
	 * �����Զ������°汾
	 */
	public void update(final String url, String message, String title) {
		Dialog dialog = new AlertDialog.Builder(HomeActivity.this).setTitle(
				"���°汾����").setMessage(message)// ��������
				.setCancelable(false).setNegativeButton("ȷ��",// ����ȷ����ť
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
									pBar = new ProgressDialog(HomeActivity.this);
									pBar.setTitle("��������");
									pBar.setMessage("���Ժ�");
									pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// ���÷��Ϊ��������
									pBar.setIndeterminate(false);
									pBar.incrementProgressBy(1); // ���Ӻͼ��ٽ���
									downFile(url);
								} else {
									Toast.makeText(HomeActivity.this,"��δ����SD���������SD��֮���ٸ���",Toast.LENGTH_SHORT).show();
									Message mg = new Message();
									mg.what = 4;
									mHandler.sendMessage(mg);
								}
							}
						}).setPositiveButton("ȡ��",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Message mg = new Message();
								mg.what = 4;
								mHandler.sendMessage(mg);
							}
						}).create();
		
		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ļ�
	 * @param url
	 */
	void downFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					double onePiece = length / 100;
					int percent = 1;
					if (is != null) {
						File file = new File(Environment.getExternalStorageDirectory(),"RuyicaiAndroid_update.apk");
						fileOutputStream = new FileOutputStream(file);
						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							// baos.write(buf, 0, ch);
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {
								if (count >= (onePiece * percent)) {
									percent = percent + 1;
									pBar.incrementProgressBy(1);
								}
							}
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {

				} catch (IOException e) {

				}
			}
		}.start();
	}

	void down() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();
			}
		});
	}

	void update() {
		finish();

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(
				"/sdcard/RuyicaiAndroid_update.apk")),
				"application/vnd.android.package-archive");
		startActivity(intent);

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return false;
		}
		return false;
	}

	/**
	 * ��ȡ�ֻ��豸��Ϣ
	 */
	public void getMachineInfo() {

		TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
		Constants.IMEI = telephonyManager.getDeviceId();
		Constants.IMSI = telephonyManager.getSubscriberId();

		Constants.MACHINE_ID = Build.MODEL;

	}

	/**
	 * ����ϴεĵ�¼��Ϣ
	 */
	public void clearLastLoginInfo() {
		shellRW = new ShellRWSharesPreferences(this, "addInfo");
		shellRW.setUserLoginInfo("sessionid", "");
		shellRW.setUserLoginInfo("userno", "");
	}

	private boolean flag = true;
	
	private class StartTask extends AsyncTask<String, Void, Integer> {
		protected Integer doInBackground(String... params) {

			Thread t = new Thread(new Runnable() {
				public void run() {
					initSoftwareStartInfomationFromServer();
				}
			});
			try {
				// 6 - 500ms
				t.start();
				int counter = 0;
				while(flag){
					Thread.sleep(500);
					counter += 500;
					if(counter >= 5000){
						flag = false;
					}
				}
			} catch (Exception e) {

			}

			return 0;
		}

		protected void onPostExecute(Integer result) {
			if (softwareErrorCode.equals("true")) {
				Message message = Message.obtain();
				message.what = 9;
				mHandler.sendMessage(message);
				// ��Ҫ����
				return;
			} else {
				Bundle b = new Bundle();
				Intent in = new Intent(HomeActivity.this, MainGroup.class);
//				Log.v("", "550notificationMark="+notificationMark);
				in.putExtra(Constants.NOTIFICATION_MARKS, notificationMark);
				in.putExtras(b);
				startActivity(in);
				HomeActivity.this.finish();
			}
		}
	}
	/**
	 * ��ʼ��С��ͼƬ
	 * 
	 */
	public void initBitmap(){
		Resources res = this.getResources();

		Constants.grey = new BitmapDrawable(res.openRawResource(R.drawable.grey)).getBitmap();
		Constants.red = new BitmapDrawable(res.openRawResource(R.drawable.red)).getBitmap();
		Constants.blue = new BitmapDrawable(res.openRawResource(R.drawable.blue)).getBitmap();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		resizedBitmap.recycle();
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
	
}
