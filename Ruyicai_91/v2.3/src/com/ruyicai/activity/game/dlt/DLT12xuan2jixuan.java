package com.ruyicai.activity.game.dlt;

import java.util.Vector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.home.RuyicaiAndroid;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.SensorActivity;
import com.ruyicai.util.ShellRWSharesPreferences;

public class DLT12xuan2jixuan extends DLT {
	private Vector<TwoBalls> tballs;
	private TwoIn12jixuanSensor sensor = new TwoIn12jixuanSensor(this);
	private boolean isfreash = true;
	int beishu ;
	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		lottery_title.setText(getString(R.string.dlt_12xuan2_jixuan));
		dlt12xuan2_button.setBackgroundResource(R.drawable.dlt_downbutton_turn12xuan2);
		Toast.makeText(DLT12xuan2jixuan.this, "ҡ���ֻ�������ѡ", Toast.LENGTH_SHORT).show();
		goucaidating_button.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				sensor.stopAction();
				Intent goucai = new Intent(DLT12xuan2jixuan.this,RuyicaiAndroid.class);
				startActivity(goucai);
				DLT12xuan2jixuan.this.finish();
			}});
		dltzhixuan_button.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				sensor.stopAction();
				Intent zhixuan = new Intent(DLT12xuan2jixuan.this,DLTzhixuan.class);
				startActivity(zhixuan);
				DLT12xuan2jixuan.this.finish();
			}});
		dltdantuo_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sensor.stopAction();
				Intent dantuo = new Intent(DLT12xuan2jixuan.this,DLTdantuo.class);
				startActivity(dantuo);	
				DLT12xuan2jixuan.this.finish();
			}
		});
		dlt12xuan2_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sensor.stopAction();
				Intent twoxuan2 = new Intent(DLT12xuan2jixuan.this,DLT12xuan2.class);
				startActivity(twoxuan2);				
				DLT12xuan2jixuan.this.finish();
			}
		});

		init();
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("onPause","pause");
		sensor.stopAction();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("onResume","resume");
		sensor.startAction();
	}
	
	
	private void init() {
		iCurrentButton = PublicConst.BUY_DLT_TWOIN12_JX;
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(R.layout.layout_dlt_12xuan2_jixuan, null);
		dltjixuanlinear = (LinearLayout)iV.findViewById(R.id.dlt_12xuan2_jixuan_linear_zhuma);

		dltjixuanzhu = (Spinner)iV.findViewById(R.id.layout_dlt_12xuan_jixuan_spinner);
		mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.dlt_12xuan2_jixuan_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mSeekBarQishu = (SeekBar) iV.findViewById(R.id.dlt_12xuan2_jixuan_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(this);
		mSeekBarQishu.setProgress(iProgressQishu);
		mTextBeishu = (TextView) iV.findViewById(R.id.dlt_12xuan2_jixuan_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu = (TextView) iV.findViewById(R.id.dlt_12xuan2_jixuan_text_qishu);
		mTextQishu.setText("" + iProgressQishu);
		setSeekWhenAddOrSub(R.id.dlt_12xuan2_jixuan_seekbar_subtract_beishu, iV, -1,mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.dlt_12xuan2_jixuan_seekbar_add_beishu, iV, 1,mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.dlt_12xuan2_jixuan_seekbar_subtract_qihao, iV, -1,	mSeekBarQishu, false);
		setSeekWhenAddOrSub(R.id.dlt_12xuan2_jixuan_seekbar_add_qishu, iV, 1,mSeekBarQishu, false);
		dltjixuanzhu.setSelection(4);
		dlt_b_touzhu_fushi = (Button) iV.findViewById(R.id.dlt_12xuan2_jixuan_b_touzhu);
		dlt_b_touzhu_fushi.setOnClickListener(dlt12xuan2jixuanlistener);
		dltjixuanzhu.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(isfreash){
					dltjixuanlinear.removeAllViews();
					tballs = new Vector<TwoBalls>();
					for (int i = 0; i < dltjixuanzhu.getSelectedItemPosition() + 1; i++) {
						TwoBalls ball = new TwoBalls();
						tballs.add(ball);
					}
					creatTable12xuan2jixuan(dltjixuanlinear);
				}else{
					isfreash = true;
				}
			}
			public void onNothingSelected(AdapterView<?> arg0) {				
			}
		});
		Button chongxuan = (Button) iV.findViewById(R.id.dlt_12xuan2_jixuan_chongxuan);
		chongxuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dltjixuanlinear.removeAllViews();
				tballs = new Vector<TwoBalls>();
				for (int i = 0; i < dltjixuanzhu.getSelectedItemPosition() + 1; i++) {
					TwoBalls ball = new TwoBalls();
					tballs.add(ball);
				}
				creatTable12xuan2jixuan(dltjixuanlinear);
			}
		});
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView.getLayoutParams().width, buyView.getLayoutParams().height));
		sensor.onVibrator();// ��
	 }
	OnClickListener dlt12xuan2jixuanlistener=new OnClickListener(){
		public void onClick(View v) {
			ShellRWSharesPreferences pre = new ShellRWSharesPreferences(DLT12xuan2jixuan.this,"addInfo");
	        String sessionIdStr = pre.getUserLoginInfo("sessionid");
			if (sessionIdStr.equals("")||sessionIdStr == null) {
				Intent intentSession = new Intent(DLT12xuan2jixuan.this, UserLogin.class);
				startActivity(intentSession);
			}else{ 	
				String zhuma_twoin12jixuan = zhuma_twoin12jixuan();
				Log.e("zhuma=======",zhuma_twoin12jixuan);
				begin12xuan2jixuanTouZhu();
			}
		}};
	void begin12xuan2jixuanTouZhu(){		
			String sTouzhuAlert =  getTouZhuAlert();
			alert12xuan2(sTouzhuAlert);
	}
	/** ��ʾ�� ����ȷ���Ƿ�Ͷע*/
	 void alert12xuan2(String string) {	
		sensor.stopAction();
		Dialog dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
								Thread t = new Thread(new Runnable() {
									 int iZhuShu = tballs.size();
									 int iQiShu = getQishu();
									String[] strCode = null;
									public void run() {								
										String zhuma_twoin12jixuan = zhuma_twoin12jixuan();
											// ֧��������										
										strCode = payNew(zhuma_twoin12jixuan,"" + beishu, iZhuShu*2*beishu+"",iQiShu + "");							
									

										if (strCode[0].equals("0000")&& strCode[1].equals("000000")) {
											Message msg = new Message();
											msg.what = 6;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("070002")) {
											Message msg = new Message();
											msg.what = 7;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("0000")&& strCode[1].equals("000001")) {
											Message msg = new Message();
											msg.what = 4;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("1007")) {
											Message msg = new Message();
											msg.what = 2;
											handler.sendMessage(msg);
										} else if (strCode[1].equals("002002")) {
											Message msg = new Message();
											msg.what = 3;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("040006")|| strCode[0].equals("201015")) {
											Message msg = new Message();
											msg.what = 1;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("00")&& strCode[1].equals("00")) {
											Message msg = new Message();
											msg.what = 8;
											handler.sendMessage(msg);
										} else {
											Message msg = new Message();
											msg.what = 9;
											handler.sendMessage(msg);
										}
									}
								});
								t.start();
							}
						}).setNegativeButton("ȡ��",	new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {}
						}).create();
		dialog.show();
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface arg0) {
				// TODO Auto-generated method stub
				sensor.startAction();
			}
		});
	}
	class TwoBalls{
		int[] tNums= new int[2];;
	    public TwoBalls(){
		    tNums = PublicMethod.getRandomsWithoutCollision(2, 0, 11);
		    tNums = PublicMethod.orderby(tNums, "abc");
			}
	    public int[] getTNum(){
			return tNums;	    	
	    }
	    public String getTNumZhu() {
			String str = "";
			for (int i = 0; i < tNums.length; i++) {
				if (i != tNums.length - 1){
					str += PublicMethod.getZhuMa(tNums[i] + 1) + " ";
				}else{
					str += PublicMethod.getZhuMa(tNums[i] + 1);
				}
			}
			return str;
		}
	}
	void creatTable12xuan2jixuan(LinearLayout layout){
		for (int i = 0; i < tballs.size(); i++) {
			final int index = i;
			LinearLayout lines = new LinearLayout(layout.getContext());
			blueBallTable = new BallTable(lines,BLUE_BALL_START);
			PublicMethod.makeBallTableJiXuan(blueBallTable, iScreenWidth, redBallResId, tballs.get(i).getTNum(),this);
			final ImageButton delete = new ImageButton(lines.getContext());
			
			delete.setBackgroundResource(R.drawable.shanchu);
			delete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(tballs.size() > 1){
						dltjixuanlinear.removeAllViews();
						tballs.remove(index);						
						isfreash = false;
						dltjixuanzhu.setSelection(tballs.size() - 1);
						creatTable12xuan2jixuan(dltjixuanlinear);
					}else if(tballs.size() == 1){	
						Toast.makeText(DLT12xuan2jixuan.this, R.string.zhixuan_jixuan_toast, Toast.LENGTH_SHORT).show();						
					}
					
				}
			});
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			param.setMargins(15, 10, 0, 0);
			lines.addView(delete, param);
			lines.setGravity(Gravity.CENTER_HORIZONTAL);
			layout.addView(lines);
		}
	}
	/**
	 * �õ�����͸12ѡ2��ע��
	 * @return
	 */
	String getDLT12xuan2ZhuMa(){
		String dlt_12xuan2_jixuan_zhuma = "";
		
		for(int i=0;i<tballs.size();i++){
			int[] a = tballs.get(i).getTNum();
			dlt_12xuan2_jixuan_zhuma += (a[0]+1)+" "+(a[1]+1)+ ",";
		}
		return dlt_12xuan2_jixuan_zhuma;
	}
	 private String getTouZhuAlert(){
		 String zhuma_twoin12jixuan = zhuma_twoin12jixuan();
		 Log.e("zhuma_twoin12jixuan==", ""+zhuma_twoin12jixuan);
		 
		 String alert_string = "";	 
		 beishu = mSeekBarBeishu.getProgress();
		 int iZhuShu = tballs.size() * beishu;
		 alert_string="ע����"+ tballs.size()+ "ע"+ "\n"
					+ "������"/*ע���������ϱ��� �³� 20100713*/+ beishu + "��" + "\n" 
					+ "׷�ţ�"+ mSeekBarQishu.getProgress() + "��" + "\n" 
					+ "��"+ (iZhuShu * 2) + "Ԫ" + "\n" 
					+ "�����"+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
					+ "ע�룺" + "\n" + getDLT12xuan2ZhuMa() + "\n"
					+ "ȷ��֧����";	
		 return alert_string;
	 }
	 /**
	  * ��ȡ��ѡ12ѡ2��ע��
	  * @return ע�� 
	  */
	 public String zhuma_twoin12jixuan() {
		    int zhushu=tballs.size();	 
			String t_str = "";
			for (int i = 0; i < zhushu; i++) {
					t_str += tballs.get(i).getTNumZhu();
	           if(i!=zhushu-1){
	        	    t_str += ";";
	           }
			}
			return t_str;
	}

	 class TwoIn12jixuanSensor extends SensorActivity {
			public TwoIn12jixuanSensor(Context context) {
				getContext(context);
			}
			public void action() {
				dltjixuanlinear.removeAllViews();
				tballs = new Vector<TwoBalls>();
				for (int i = 0; i < dltjixuanzhu.getSelectedItemPosition() + 1; i++) {
					TwoBalls ball = new TwoBalls();
					tballs.add(ball);
				}
				creatTable12xuan2jixuan(dltjixuanlinear);
			}
		}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				sensor.stopAction();
				break;
			}
			return super.onKeyDown(keyCode, event);
		}
		protected void onDestroy() {
	 		super.onDestroy();
			PublicMethod.recycleBallTable(blueBallTable);
			}
}
