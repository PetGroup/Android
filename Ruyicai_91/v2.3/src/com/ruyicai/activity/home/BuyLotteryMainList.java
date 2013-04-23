package com.ruyicai.activity.home;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.ChooseLuckyNum;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.game.dlt.DLTzhixuan;
import com.ruyicai.activity.game.fc3d.Fc3d;
import com.ruyicai.activity.game.pl3.PL3;
import com.ruyicai.activity.game.qlc.QLC;
import com.ruyicai.activity.game.ssc.SscZhiXuan;
import com.ruyicai.activity.game.ssq.SsqActivity;
import com.ruyicai.activity.game.zc.JinQC;
import com.ruyicai.activity.game.zc.LiuCB;
import com.ruyicai.activity.game.zc.RenX9;
import com.ruyicai.activity.game.zc.ShengFC;
import com.ruyicai.activity.webbrowser.WebBrowser;
import com.ruyicai.handler.MyDialogListener;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.ShellRWSharesPreferences;

public class BuyLotteryMainList extends Activity implements MyDialogListener,
		RadioGroup.OnCheckedChangeListener {

	// ���ѿ����ʾ��Ϣ
	private String messageidflag = null;
	private Dialog dialog;
	private String messageflage = null;
	private LayoutInflater layoutinflater;
	ProgressDialog progressdialog;
	private JSONObject obj;
	String re;
	String messagetitle;        
	String messagedetail;
	String messageerrorcode;
	// ��¼�Ƿ�ɹ��ļ�����
	private IntentFilter loginSuccessFilter;
	private SuccessReceiver loginSuccessReceiver;
	private IntentFilter loginFailFilter;
	private FailReceiver loginFailReceiver;

	/* ���� */
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				Toast.makeText(BuyLotteryMainList.this, "�����쳣��",Toast.LENGTH_SHORT).show();
				break;
			case 1:
				progressdialog.dismiss();
				// initList();
				break;
			case 4:
				showmessageDialog();
				break;
			}
		}

	};

	public void onCreate(Bundle savedInstanceState) {
		RuyicaiActivityManager.getInstance().addActivity(this);
		// ��ʾ��ҳ����
		// setContentView(R.layout.mainpage);
		super.onCreate(savedInstanceState);
		// ��ʾ��ҳ����
		setContentView(R.layout.mainpage);
		// ��ʼ��imageView��ť
		initImgView();
		// �Ƿ���ʾ��Ϣ��ʾ���̡߳�
		dialogMsg();
	}
    
	

	// ���ʾ��
	private void showmessageDialog() {
		ShellRWSharesPreferences shellcheck = new ShellRWSharesPreferences(BuyLotteryMainList.this, "UserMessage");
		View view = layoutinflater.from(BuyLotteryMainList.this).inflate(R.layout.tanchuxinxi, null);
		TextView msg = (TextView) view.findViewById(R.id.tanchuxinxi_msg);
		msg.setText(messagedetail);
		dialog = new AlertDialog.Builder(BuyLotteryMainList.this).setView(view)
				.setTitle(messagetitle).setNeutralButton("ȷ��", null).show();
		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

	}

	@Override
	protected void onStart() {
		
//		//����һ���߳�,�ȴ���������
//		Thread t = new Thread(new Runnable() {
//			public void run() {
//				
//				if(Constants.NEWS != null){
//					try {
//						Log.e("", "hread.sleep(3000);");
//						Thread.sleep(3000);
//						Log.e("", "hread.sleep(over);");
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					newsHandler.post(updateNews);
//				}
//				
//			}
//		});
//		t.start();
		
		super.onStart();
	}

	final Handler newsHandler = new Handler();
	/**
	 * �����û����
	 */
//    final Runnable updateNews = new Runnable() {
//        public void run() {
//        	initRollingText(); // ��ʼ����������
//        }
//    };
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		Constants.RUYIHELPERSHOWLISTTYPE=0;
	}

	

	/**
	 * ��ʼ�����,��������
	 */
//	public void initRollingText() {
//
//		ViewFlipper mFlipper = ((ViewFlipper) this.findViewById(R.id.notice_other_flipper));
//
//		String str[] = splitStr(Constants.NEWS, 23);
//
//		for (int i = 0; i < str.length; i++) {
//			mFlipper.addView(addTextByText(str[i]));
//		}
//		mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));
//		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_out));
//		mFlipper.startFlipping();
//	}

	/**
	 * ��ֹ�����Ϣ�ַ��� ��ʽΪ���Ÿ���
	 */
	public String[] splitStr(String str, int with) {
		String strss[] = str.split(",");
		if (strss.length == 0) {
			int indexs = str.length() / with + 1;
			String strs[] = new String[indexs];
			for (int i = 0; i < indexs; i++) {
				if (i == indexs - 1) {
					strs[i] = str.substring(i * with, str.length());
				} else {
					strs[i] = str.substring(i * with, with * (i + 1));
				}
			}
			return strs;
		}
		return strss;
	}

	public View addTextByText(String text) {
		TextView tv = new TextView(this);
		tv.setText(text);
		tv.setGravity(1);
		tv.setTextSize(15);
		tv.setTextColor(Color.BLACK);
		// tv.setTextColor(com.palmdream.RuyicaiAndroid.R.color.black);
		return tv;
	}

	@Override
	public void onCancelClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOkClick(int[] aNums) {
		// TODO Auto-generated method stub
		this.finish();

	}

	/**
	 * �������ӿ�
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
	 * �˳����
	 * @param keyCode ���ذ����ĺ���
	 * @param event   �¼�
	 * @return
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		
		case 0x12345678: {
			Dialog dialog = new AlertDialog.Builder(this).setTitle("���Ҫ�뿪��")
			.setMessage("��ȷ��Ҫ�뿪")// ��������
			.setCancelable(false).setPositiveButton(this.getString(R.string.ok),// ����ȷ����ť
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,	int which) {
							RuyicaiActivityManager.getInstance().exit();
							PublicConst.islogin=false;
						    onOkClick(null);
						}

					}).setNegativeButton(this.getString(R.string.cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							dialog.cancel();
						}
					}).create();// ����
			// ��ʾ�Ի���
			dialog.show();
			break;
		}
		}
		return false;
	}

	/**
	 * �ѵ�¼�Ĺ㲥
	 * 
	 * @author Administrator
	 */
	public class SuccessReceiver extends BroadcastReceiver {
		Context context;

		@Override
		public void onReceive(Context context, Intent intent) {
			this.context = context;
			showSuccessReceiver();
		}

		public void showSuccessReceiver() {
		}
	}

	/**
	 * δ��¼�Ĺ㲥
	 * 
	 * @author Administrator
	 * 
	 */
	public class FailReceiver extends BroadcastReceiver {
		Context context;

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			this.context = context;
			showSuccessReceiver();
		}

		public void showSuccessReceiver() {

		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

	}

	// ��ʼ��ImageView��ť
	public void initImgView() {

		final Class[] cla = { SsqActivity.class, Fc3d.class, SscZhiXuan.class,
				DLTzhixuan.class, QLC.class, PL3.class, ShengFC.class,
				RenX9.class, JinQC.class, LiuCB.class, ChooseLuckyNum.class,
				 };

		int[] imgViews = { R.id.mainpage_ssq_sign, R.id.mainpage_fc3d_sign,
				R.id.mainpage_ssc_sign, R.id.mainpage_dlt_sign,
				R.id.mainpage_qlc_sign, R.id.mainpage_pl3_sign,
				R.id.mainpage_sfc_sign, R.id.mainpage_rxq_sign,
				R.id.mainpage_jqc_sign, R.id.mainpage_lcb_sign,
				R.id.mainpage_luck_sign

		};
		for (int i = 0; i < imgViews.length; i++) {
			final int n = i;

			ImageView imgView = (ImageView) findViewById(imgViews[i]);
			imgView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// haojie �����淨ͳ����Ϣ
					SharedPreferences sharedPreferences = getSharedPreferences(
							Constants.APPNAME, MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					int counts = sharedPreferences.getInt(Constants.GAME_CLASS
							+ n, 0); // ��Ϸ����+n , Ĭ��ֵΪ0
					int countsSum = sharedPreferences.getInt(
							Constants.GAME_CLICK_SUM, 0); // ��Ϸ����+n , Ĭ��ֵΪ0
					counts = counts + 1;
					countsSum = countsSum + 1;
					editor.putInt(Constants.GAME_CLASS + n, counts);
					editor.putInt(Constants.GAME_CLICK_SUM, countsSum);
					editor.commit();
					if (n == 10) {
						Intent intent = new Intent(BuyLotteryMainList.this,RuyicaiAndroid.class);
						Bundle bundle = new Bundle();
						bundle.putInt("index", 3);
						bundle.putBoolean("ischooselucy", true);
						intent.putExtras(bundle);
						Constants.RUYIHELPERSHOWLISTTYPE=1;
						startActivity(intent);
						
						//Constants.FACE_TYPE=n;
					} else {
						Intent intent = new Intent(BuyLotteryMainList.this,cla[n]);
						startActivity(intent);
						//Constants.FACE_TYPE=n;
					}
				}
			});

		}
	}

	private void dialogMsg() {

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(BuyLotteryMainList.this, "UserMessage");
		messageflage = shellRW.getPreferencesValue("tanchumessage");
		messageidflag = shellRW.getPreferencesValue("id");
		if (!PublicConst.MESSAGE.equals("")) {
			try {
				obj = new JSONObject(PublicConst.MESSAGE);
				String id = obj.getString("id");
				messagetitle = obj.getString("title");
				messagedetail = obj.getString("message");
				if (messageidflag == null) {
					shellRW.putPreferencesValue("id", id);
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				}else if (!messageidflag.equals(id)) {
					shellRW.putPreferencesValue("id", id);
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				}

			} catch (JSONException e) {

			}
		}
	}
	
	

	
}