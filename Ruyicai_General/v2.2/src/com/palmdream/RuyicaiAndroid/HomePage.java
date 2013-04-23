/**
 * Copyright 2010 PalmDream
 * All right reserved.
 * Development History:
 * Date             Author          Version            Modify
 * 2010-5-18        fqc              1.5                none
 */

package com.palmdream.RuyicaiAndroid;

import java.io.ByteArrayOutputStream;
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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.palmdream.netintface.Jsoninformation;
import com.palmdream.netintface.ParseXML;
import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

public class HomePage extends Activity {
	private ShellRWSharesPreferences shellRW;
	// ������
//	public static String channel_id = "C10144";// ����Ӧ�û�
//	public static String channel = "279";// ����Ӧ�û�
	
	public static String channel_id = "C10299";// ����a
	public static String channel = "495";

//	public static String channel_id = "C10203";// ����a
//	public static String channel = "371";
//	
//	public static String channel_id = "C10300";// ��Ȥa
//	public static String channel = "497";
//	
//	public static String channel_id = "C10119";// tompda-android
//	public static String channel = "232";
	public static String imei;
	// ������Ϣ
	public Handler mHandler = new Handler();
	// cyhx logo image
	ImageView imageview;
	MyAnimateView iAnimateView;
	// alphaͨ������
	int alpha = 255;
	// ��ʾ״̬
	// 0 - ����
	// 1 - ����
	// 2 - ��ת Activity
	int iShowStatus = 0;
	public boolean isHint = false;
	boolean b = false;

	// ʵ������ļ�⣬���û����������ʾһ���Ի�����ʾ�����������á�
	CheckWireless checkWireless;
	private ProgressDialog pBar;
	private Handler handler = new Handler();
	private int iretrytimes = 2;
	private JSONObject obj;
	private String re;
	private String type = "000001";

	private byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.homepage);
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
		// ��ȡ����
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(this.TELEPHONY_SERVICE);
		imei = tm.getDeviceId();

		// ��ʼ��imageview
		imageview = (ImageView) this.findViewById(R.id.cp1);
		// ����ͼƬ
		Resources r = this.getResources();
		InputStream is = r.openRawResource(R.drawable.cp1);

		BitmapDrawable bmpDraw = new BitmapDrawable(is);
		Bitmap iBackgrounBitmap = bmpDraw.getBitmap();

		// ��ͼ
		Matrix matrix = new Matrix();

		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		//
		// float ps1 = dm.widthPixels / iBackgrounBitmap.getWidth();
		// float ps2 = dm.heightPixels / iBackgrounBitmap.getHeight();
		float iScreenWidth = PublicMethod.getDisplayWidth(this);
		float iScreenHeight = PublicMethod.getDisplayHeight(this);
		float w = iScreenWidth / iBackgrounBitmap.getWidth();
		float h = iScreenHeight / iBackgrounBitmap.getHeight();
		matrix.postScale(w, h);
		Bitmap resizedBitmap = Bitmap.createBitmap(iBackgrounBitmap, 0, 0,
				iBackgrounBitmap.getWidth(), iBackgrounBitmap.getHeight(),
				matrix, true);

		imageview.setImageBitmap(resizedBitmap);
		iAnimateView = new MyAnimateView(this);
		// Ĭ��Ϊcmnet����
		iHttp.conMethord = iHttp.CMNET;
		iHttp.iTimeOut = 5000;
		// ����imageview��alphaͨ��
		imageview.setAlpha(alpha);

		// imageview�����߳�
		new Thread(new Runnable() {
			public void run() {
				// initApp();
				while (iShowStatus < 2) {
					try {
						if (iShowStatus == 0) {
							Thread.sleep(2700);
							iShowStatus = 1;
						} else {
							Thread.sleep(20);
						}
						if (updateApp()) {
							return;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		checkWireless = new CheckWireless(this);

		// ��ʼ��handler
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0: {
					imageview.setAlpha(alpha);
					imageview.invalidate();
					break;
				}
				case 1: {
					PublicMethod.myOutput("-----new image");
					setContentView(iAnimateView);
					iAnimateView.invalidate();
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
					PublicMethod.myOutput("----comeback");
					iAnimateView.invalidate();
					Message mg = Message.obtain();
					mg.what = 5;
					mHandler.sendMessage(mg);
					break;
				}
				case 4: {
					uvNumber();// uvͳ�Ʒ���
					userNumber();// ����ͳ��
					iHttp.iTimeOut = 20000;
					Intent in = new Intent(HomePage.this, RuyicaiAndroid.class);
					startActivity(in);
					HomePage.this.finish();
					break;
				}
				case 5: {
					// ������� ��ѯ��ǰ����İ汾��Ϣ
					 checkCurrentVersion();
//					saveInformation();
					break;
				}
				case 6:
					isUpdate(re);
					break;
				case 7:
					Toast.makeText(HomePage.this, "�����쳣��", Toast.LENGTH_SHORT)
							.show();
					break;
				case 8:
					Toast.makeText(HomePage.this, "�쳣��", Toast.LENGTH_SHORT)
							.show();
					break;

				}
			}
		};
	}

	/*
	 * UVͳ��
	 */
	public void uvNumber() {
		shellRW = new ShellRWSharesPreferences(this, "addInfo");
		shellRW.setUserLoginInfo("sessionid", "");
		BuyLotteryMainList.flag = true;// ��������
		BuyLotteryMainList.start = true;// ��������
		JoinHall.once = true;// ��������
		// uvͳ��
		new Thread(new Runnable() {

			@Override
			public void run() {
				boolean start = false;
				final Calendar c = Calendar.getInstance();
				int mYear = c.get(Calendar.YEAR); // ��ȡ��ǰ���
				int mMonth = c.get(Calendar.MONTH);// ��ȡ��ǰ�·�
				int mDay = c.get(Calendar.DAY_OF_MONTH);// ��ȡ��ǰ�·ݵ����ں���
				if (shellRW.getUserLoginInfo("mYear") == null
						|| shellRW.getUserLoginInfo("mMonth") == null
						|| shellRW.getUserLoginInfo("mDay") == null) {
					start = true;
					shellRW.setUserLoginInfo("mYear", Integer.toString(mYear));
					shellRW
							.setUserLoginInfo("mMonth", Integer
									.toString(mMonth));
					shellRW.setUserLoginInfo("mDay", Integer.toString(mDay));

				} else if (mYear > Integer.parseInt(shellRW
						.getUserLoginInfo("mYear"))) {
					start = true;
					shellRW.setUserLoginInfo("mYear", Integer.toString(mYear));
				} else if (mMonth > Integer.parseInt(shellRW
						.getUserLoginInfo("mMonth"))) {
					start = true;
					shellRW
							.setUserLoginInfo("mMonth", Integer
									.toString(mMonth));
				} else if (mDay > Integer.parseInt(shellRW
						.getUserLoginInfo("mDay"))) {
					start = true;
					shellRW.setUserLoginInfo("mDay", Integer.toString(mDay));
				}
				if (start) {
					try {
						jrtLot.setPara(100, channel_id);
						jrtLot.uvNumber(HomePage.channel_id,
										jrtLot.versionInfo);// ���Ǻ�̨
						Log.v("========uvNumber", "uvNumber");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * ����ͳ��
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	public void userNumber() {
		// ����ͳ��
		new Thread(new Runnable() {

			@Override
			public void run() {

				jrtLot.count("1", imei, HomePage.channel_id, null);
			}
		}).start();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private void checkWirelessNetwork() {
		shellRW = new ShellRWSharesPreferences(this, "addInfo");

		if (!checkWireless.getConnectGPRS() && !checkWireless.getConnectWIFI()) {
			PublicMethod.myOutput("--------------ShowNoConnectionDialog");
			ShowNoConnectionDialog showNoConnectionDialog = new ShowNoConnectionDialog(
					this, this);
			showNoConnectionDialog.showNoConnectionDialog();
		} else {

			PublicMethod.myOutput("------------noHint"
					+ shellRW.getUserLoginInfo("noHint"));
			try {
				if (shellRW.getUserLoginInfo("noHint") != null) {
					if (shellRW.getUserLoginInfo("noHint").equals("false")) {
						ShowConnectionDialog showConnectionDialog = new ShowConnectionDialog(
								this, this, shellRW);
						showConnectionDialog.showConnectionDialog(this);
					} else {
						Message mg = Message.obtain();
						mg.what = 3;
						mHandler.sendMessage(mg);
					}
				} else {
					ShowConnectionDialog showConnectionDialog = new ShowConnectionDialog(
							this, this, shellRW);

					showConnectionDialog.showConnectionDialog(this);
				}
			} catch (Exception e) {

				PublicMethod.myOutput("------exception e-----???????-");
			}

		}

	}

	// ���䴦����
	public boolean updateApp() {
		Message mg = Message.obtain();
		mg.what = 0;
		// handler.sendMessage(mg);

		alpha -= 5;
		if (alpha <= 0) {
			iShowStatus = 2;

			mg.what = 1;
		}

		if (iShowStatus == 3) {
			// mg.what=2;
			return true;
		}
		mHandler.sendMessage(mg);
		return false;
	}

	private static class MyAnimateView extends View {
		private AnimateDrawable mDrawable;
		private Bitmap iBackgrounBitmap;
		private Bitmap iProgressBarBitmap;
		private Paint p = null;

		private float iScreenWidth;
		private float iScreenHeight;
		private float iBackgroundWidth;
		private float iBackgrounHeight;
		private float iOffsetX;
		private float iOffsetY;
		private float w, h;

		private int iShowStringX = 100;
		private int iShowStringY = 390;
		private ImageView bg;

		public MyAnimateView(Context context) {
			super(context);

			iScreenWidth = PublicMethod.getDisplayWidth(context);
			iScreenHeight = PublicMethod.getDisplayHeight(context);

			bg = new ImageView(context);
			setPaint();

			Drawable dr = context.getResources().getDrawable(R.drawable.light);
			dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
			// ����ͼƬ
			Resources r = context.getResources();
			InputStream is = r.openRawResource(R.drawable.cp1);

			BitmapDrawable bmpDraw = new BitmapDrawable(is);
			iBackgrounBitmap = bmpDraw.getBitmap();
			// ������
			Resources r1 = context.getResources();
			InputStream is1 = r1.openRawResource(R.drawable.cp2);

			BitmapDrawable bmpDraw1 = new BitmapDrawable(is1);
			iProgressBarBitmap = bmpDraw1.getBitmap();

			iBackgroundWidth = iBackgrounBitmap.getWidth();
			iBackgrounHeight = iBackgrounBitmap.getHeight();
			// ���ű���
			w = iScreenWidth / iBackgrounBitmap.getWidth();
			h = iScreenHeight / iBackgrounBitmap.getHeight();

			iOffsetX = (iScreenWidth - iBackgroundWidth) / 2;
			if (iOffsetX < 0)
				iOffsetX = 0;
			iOffsetY = (iScreenHeight - iBackgrounHeight) / 2;
			if (iOffsetY < 0)
				iOffsetY = 0;

			Animation an = new TranslateAnimation(58 * w, 240 * w, 366 * h,
					366 * h);
			an.setDuration(1000);
			an.setRepeatCount(-1);
			an.initialize(10, 10, 10, 10);

			mDrawable = new AnimateDrawable(dr, an);
			an.startNow();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// ��ͼ
			Matrix matrix = new Matrix();

			matrix.postScale(w, h);
			Bitmap resizedBitmap = Bitmap.createBitmap(iBackgrounBitmap, 0, 0,
					iBackgrounBitmap.getWidth(), iBackgrounBitmap.getHeight(),
					matrix, true);
			Bitmap iProgressBarBitmapMatrix = Bitmap.createBitmap(
					iProgressBarBitmap, 0, 0, iProgressBarBitmap.getWidth(),
					iProgressBarBitmap.getHeight(), matrix, true);
			// ��ͼ
			canvas.drawBitmap(resizedBitmap, 0, 0, null);
			canvas.drawBitmap(iProgressBarBitmapMatrix, 58 * w, 365 * h, null);

			mDrawable.draw(canvas);

			canvas.drawText("���ڻ�ȡ��������...", iShowStringX * w, iShowStringY * h,
					p);

			invalidate();
		}

		private void setPaint() {
			if (p == null) {
				p = new Paint();

				p.setColor(Color.YELLOW);
				p.setTypeface(Typeface.create(Typeface.SANS_SERIF,
						Typeface.NORMAL));
				p.setTextSize(13);
			}

		}
	}

	/**
	 * �����ȡ��ʱ��Ϣ��������ѯ����ǰ�����ĺ�̨����ֵ
	 */
	public void saveInformation() {
		new Thread(new Runnable() {
			public void run() {
				Message mg = new Message();
				// mg.what=0;
				String[] info = Jsoninformation.getjsoninformation();
				PublicMethod.myOutput("---------------=====-----:" + info[1]);
				if (info[0].equalsIgnoreCase("notice")
						&& info[1].equalsIgnoreCase("ssqinfo")
						&& info[2].equalsIgnoreCase("dddinfo")
						&& info[3].equalsIgnoreCase("qlcinfo")
						&& info[4].equalsIgnoreCase("getlotno_ssq")
						&& info[5].equalsIgnoreCase("getlotno_ddd")
						&& info[6].equalsIgnoreCase("getlotno_qlc")
						&& info[7].equalsIgnoreCase("dltinfo")
						&& info[8].equalsIgnoreCase("pl3info")
						&& info[9].equalsIgnoreCase("getlotno_dlt")
						&& info[10].equalsIgnoreCase("getlotno_pl3")) {
					mg.what = 2;
				} else {
					shellRW = new ShellRWSharesPreferences(HomePage.this,
							"addInfo");
					for (int i = 0; i < info.length; i++) {
						shellRW.setUserLoginInfo("information" + i, info[i]);
					}

					PublicMethod.myOutput("-------------------shellRW"
							+ shellRW.getUserLoginInfo("information4"));
					mg.what = 4;
				}
				mHandler.sendMessage(mg);
			};
		}).start();
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
										HomePage.this, "addInfo");
								for (int i = 0; i < 7; i++) {
									shellRW.setUserLoginInfo("information" + i,
											"");
								}
								Intent in = new Intent(HomePage.this,
										RuyicaiAndroid.class);
								startActivity(in);
								HomePage.this.finish();
							}

						}).setNegativeButton("�˳�",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// ����˳���Ӧ�¼� 20100715 �³�
								HomePage.this.finish();
							}

						});
		dialog.show();
	}

	/**
	 * 
	 * @���ߣ�����
	 * @���ڣ�2011/3/8
	 * @��������
	 * @����ֵ����
	 * @���ã���ѯ��ǰ����İ汾���ͷ������ϱ��������°汾���Ƚϣ�������ذ汾�Ǿɰ汾����ʾ�û��������µģ�������ذ汾�����µľ�ʲô������ʾ
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��1.0
	 */
	public void checkCurrentVersion() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";

				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.isVersion(type);

					try {
						obj = new JSONObject(re);
						error_code = obj.getString("errorCode");

						iretrytimes = 3;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						iretrytimes--;
					}

					if (iretrytimes == 0 && iHttp.whetherChange == false) {
						iHttp.whetherChange = true;
						if (iHttp.conMethord == iHttp.CMWAP) {
							iHttp.conMethord = iHttp.CMNET;
						} else {
							iHttp.conMethord = iHttp.CMWAP;
						}
						iretrytimes = 2;
						while (iretrytimes < 3 && iretrytimes > 0) {
							re = jrtLot.isVersion(type);

							try {
								obj = new JSONObject(re);
								error_code = obj.getString("errorCode");
								iretrytimes = 3;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								iretrytimes--;
							}
						}
					}
				}
				iretrytimes = 2;
				if (error_code.equals("A030000")) {// �ɹ�
					Message msg = new Message();
					msg.what = 6;
					mHandler.sendMessage(msg);

				} else if (error_code.equals("A030001")) {// ��������
					Message msg = new Message();
					msg.what = 7;
					mHandler.sendMessage(msg);
				} else if (error_code.equals("A030500")) {// �쳣
					Message msg = new Message();
					msg.what = 8;
					mHandler.sendMessage(msg);
				}

			}

		}).start();

	}

	/**
	 * �Ƿ�����°汾
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	public void isUpdate(String re) {
		String version = "";
		String url = "";
		try {
			JSONObject obj = new JSONObject(re);
			url = obj.getString("downurl");
			version = obj.getString("currentversion");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Double.parseDouble(jrtLot.versionInfo) < Double
				.parseDouble(version)) {
			update(url);
		} else {
			saveInformation();
		}
	}

	/**
	 * 
	 * @���ߣ�����
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ������Զ������°汾
	 * @�޸����ڣ�
	 * @�汾��
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */

	public void update(final String url) {
		Dialog dialog = new AlertDialog.Builder(HomePage.this).setTitle("ϵͳ����")
				.setMessage("�����°汾������£�")// ��������
				.setCancelable(false).setPositiveButton("ȷ��",// ����ȷ����ť
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (android.os.Environment.getExternalStorageState().equals(
										android.os.Environment.MEDIA_MOUNTED)) {
								pBar = new ProgressDialog(HomePage.this);
								pBar.setTitle("��������");
								pBar.setMessage("���Ժ�");
								pBar
										.setProgressStyle(ProgressDialog.STYLE_SPINNER);
								downFile(url);
								}else{
									Toast.makeText(HomePage.this, "��δ����SD���������SD��֮���ٸ���", Toast.LENGTH_SHORT)
									.show();
								}

							}

						}).setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// �����ȡ������ť֮���˳�����
								saveInformation();
							}
						}).create();// ����
		// ��ʾ�Ի���
		dialog.show();
	}

	void downFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				// params[0]�������ӵ�url
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {

						File file = new File(Environment
								.getExternalStorageDirectory(),
								"RuyicaiAndroid_320480.apk");
						fileOutputStream = new FileOutputStream(file);

						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							// baos.write(buf, 0, ch);
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {

							}

						}

					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
					"/sdcard/RuyicaiAndroid_320480.apk")),
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

}