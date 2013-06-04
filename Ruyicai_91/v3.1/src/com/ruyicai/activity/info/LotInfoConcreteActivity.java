package com.ruyicai.activity.info;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;

public class LotInfoConcreteActivity extends Activity implements OnSeekBarChangeListener ,HandlerMsg{
	private TextView news;
	private TextView titleTextView;
	private TextView informationtitle;
	private TextView informationtime;
	private int qishu=1;
	private int beishu=1;
	private TextView mtextqishu;
	private TextView mtextbeishu;
	private String sessionId;
	private String phonenum;
	private String userno;
	private MyHandler handler = new MyHandler(this);
	private ProgressDialog progressdialog;
	private TextView info;
	private String type;
	private Dialog dialog;
    private String betno;
    private String bet_code;
    private String lotno;
    private int zhushu = 1;
    private String titletype [] ={"����Ȥ��","ר���Ƽ�","������","����"};
 @Override
  protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.informationconcrete);
	init();
	initTextView();
}
    
	/**
	 * ��ʼ�����
	 */
	public void init() {
		news = (TextView)findViewById(R.id.webview);
		news.setTextColor(Color.BLACK);
		titleTextView = (TextView) findViewById(R.id.join_hall_text_title);
		informationtitle = (TextView) findViewById(R.id.informationtitle);
		informationtime =(TextView)findViewById(R.id.informationtime);
		Button imgRetrun = (Button) findViewById(R.id.join_hall_img_return);
		imgRetrun.setBackgroundResource(R.drawable.returnselecter);
		// ImageView�ķ����¼�
		imgRetrun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    
	}
	
	
	/**
	 *  ��ʾ����
	 */
	public void initTextView(){
		Bundle bundle = getIntent().getExtras();
		String newsContent = bundle.getString("content");
		String title = bundle.getString("title");
		String time = bundle.getString("time");
		informationtime.setHint(time);
		int    typetitle = 0;
		typetitle =bundle.getInt("type");
		if(typetitle!=0){
		switch (typetitle) {
		case 1:
			titleTextView.setText(titletype[0]);
			break;
		case 2:
			titleTextView.setText(titletype[1]);
			break;
		case 3:
			titleTextView.setText(titletype[2]);
			break;
		case 4:
			titleTextView.setText(titletype[3]);
			break;
		default:
			break;
		                 }
	     }
		
		informationtitle.setText(title);
		ContentList contentList1 = new ContentList();
		contentList1.setContent(newsContent);
		List<String> contentList=null;
		contentList = contentList1.getContentList();
		news.setMovementMethod(LinkMovementMethod.getInstance());   
		SpannableStringBuilder sb = new SpannableStringBuilder();
		//�����ַ�������"{}"�ļӵ���¼�
		for (Iterator<String> iterator = contentList.iterator(); iterator.hasNext();) {
			String str = iterator.next();
			final String view_code;
			if(str.startsWith("{")){
			try {
			//	Log.v("strconten", str);
				JSONObject obj =new JSONObject(str);
				 lotno = obj.getString("lotno");
				 if(lotno.equals("T01006")||lotno.equals("T01005")||lotno.equals("T01004")||lotno.equals("T01003")){
			     zhushu  = Integer.valueOf(obj.getString("footzhushu"));
				 }
				 type =  obj.getString("lottype");
				 bet_code = obj.getString("bet_code");
				 view_code = obj.getString("view_code");	
				 JSONObject LotnoInfo = PublicMethod.getCurrentLotnoBatchCode(lotno);
				 betno =    LotnoInfo.getString("batchCode");
				 SpannableStringBuilder strsp =new SpannableStringBuilder(view_code);
				 ThrowintoSpan span = new ThrowintoSpan(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					//	Log.v("do", "onclick");	
						LayoutInflater inflater = (LayoutInflater)LotInfoConcreteActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
						View view = inflater.inflate(R.layout.informationthrowintodialog, null);
					    info = (TextView)view.findViewById(R.id.alert_dialog_touzhu_text_one);
						TextView zhuma = (TextView)view.findViewById(R.id.alert_dialog_touzhu_text_two);
						info.setText("��Ʊ����:"+type+"\n"+"�ں�:"+"��"+betno+"��"+"\n"+"ע��:"+zhushu*beishu+"\n"+"����:"+beishu+""+"\n"+"׷��:"+(qishu-1)+"��"+"\n"+"���:"+2*zhushu*beishu+"Ԫ"+"\n"+"������:"+2*zhushu*beishu*(qishu-1)+"Ԫ");
						zhuma.setText("ע��:"+view_code);
						mtextqishu = (TextView)view.findViewById(R.id.buy_zixuan_text_qishu);
						mtextbeishu=(TextView)view.findViewById(R.id.buy_zixuan_text_beishu);
						SeekBar qishubar =(SeekBar)view.findViewById(R.id.buy_zixuan_seek_qishu);
						SeekBar beishubar=(SeekBar)view.findViewById(R.id.buy_zixuan_seek_beishu);
						beishubar.setOnSeekBarChangeListener(LotInfoConcreteActivity.this);
						beishubar.setProgress(beishu);
						qishubar.setOnSeekBarChangeListener(LotInfoConcreteActivity.this);
						qishubar.setProgress(qishu);
						mtextbeishu.setText("" + beishu);
						mtextqishu.setText("" + qishu);
						LotInfoConcreteActivity.this.setSeekWhenAddOrSub(view,R.id.buy_zixuan_img_subtract_beishu, -1,beishubar, true);
						LotInfoConcreteActivity.this.setSeekWhenAddOrSub(view,R.id.buy_zixuan_img_add_beishu, 1, beishubar,true);
						LotInfoConcreteActivity.this.setSeekWhenAddOrSub(view,R.id.buy_zixuan_img_subtract_qihao, -1,qishubar, false);
						LotInfoConcreteActivity.this.setSeekWhenAddOrSub(view,R.id.buy_zixuan_img_add_qishu, 1, qishubar,false);
				
					    dialog = new Dialog(LotInfoConcreteActivity.this,R.style.dialog);
						dialog.show();
						dialog.setContentView(view);
						
						Button ok = (Button)view.findViewById(R.id.alert_dialog_touzhu_button_ok);
						Button cannel =(Button)view.findViewById(R.id.alert_dialog_touzhu_button_cancel);
						final BetAndGiftPojo pojo=new BetAndGiftPojo();
						ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								RWSharedPreferences pre = new RWSharedPreferences(LotInfoConcreteActivity.this, "addInfo");
								sessionId = pre.getStringValue("sessionid");
								phonenum = pre.getStringValue("phonenum");
								userno = pre.getStringValue("userno");
								if (sessionId.equals("")) {
									//toLogin = true;
									Intent intentSession = new Intent(LotInfoConcreteActivity.this, UserLogin.class);
									startActivityForResult(intentSession, 0);
								} else {
									pojo.setSessionid(sessionId);
									pojo.setPhonenum(phonenum);
									pojo.setUserno(userno);
									pojo.setBettype("bet");
									pojo.setLotmulti(""+beishu);//lotmulti    ����   Ͷע�ı���
									pojo.setBatchnum(""+qishu);
									pojo.setSellway("0");
									pojo.setLotno(lotno);
									String bet_codetouzhu="";
									bet_codetouzhu =PublicMethod.getzhumainfo(lotno, beishu, bet_code);
									pojo.setBet_code(bet_codetouzhu);
									pojo.setAmount(""+200*zhushu*beishu);
									pojo.setInfoway("1");//ͨ����ѯͶע��ʶ
									LotInfoConcreteActivity.this.betting(pojo);
							}
							}});
						cannel.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
					}
				});
			
				strsp.setSpan(span, 0, view_code.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				sb.append(strsp);
				       
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			}else{
			sb.append(str);
			}
		}	
		//spstr.setSpan(span,s1.length(),s1.length()+s4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
		news.setText(sb);  

	}
	/**
	 * 
	 * @author Administrator
	 * �ض����ֵ���¼�
	 */
	public class ThrowintoSpan extends ClickableSpan {   
		  
		private final OnClickListener listener;   
		  
		public ThrowintoSpan(View.OnClickListener listener) {   
		this.listener = listener;   
		}   
		  
		@Override   
		public void onClick(View view) {  
		listener.onClick(view);   
		}   
	}
    
	/**
	 * Ͷע����
	 */
	
	public void betting(final BetAndGiftPojo pojo){
			showDialog(0); // ��ʾ������ʾ�� 2010/7/4
			// �����Ƿ�ı�������ж� �³� 8.11
			Thread t = new Thread(new Runnable() {
				String str = "00";
				@Override
				public void run() {
						str = BetAndGiftInterface.getInstance().betOrGift(pojo);
						try {
							JSONObject obj = new JSONObject(str);		
							String msg = obj.getString("message");
							String error = obj.getString("error_code");
							handler.handleMsg(error,msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						progressdialog.dismiss();
				}

			});
			t.start();
		
	}
	
	/**
	 * fqc edit ���һ������ isBeiShu ���жϵ�ǰ�Ǳ����������� ��
	 * 
	 * @param idFind
	 * @param iV
	 * @param isAdd
	 * @param mSeekBar
	 * @param isBeiShu
	 */
	protected void setSeekWhenAddOrSub(View view,int idFind, final int isAdd,final SeekBar mSeekBar, final boolean isBeiShu) {
		ImageButton subtractBeishuBtn = (ImageButton)view. findViewById(idFind);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isBeiShu) {
					if (isAdd == 1) {
						mSeekBar.setProgress(++beishu);
					} else {
						mSeekBar.setProgress(--beishu);
					}
					beishu = mSeekBar.getProgress();
				} else {
					if (isAdd == 1) {
						mSeekBar.setProgress(++qishu);
					} else {
						mSeekBar.setProgress(--qishu);
					}
					qishu = mSeekBar.getProgress();
				}
			}
		});
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		case R.id.buy_zixuan_seek_beishu:
			beishu = iProgress;
			mtextbeishu.setText("" + beishu);
			info.setText("��Ʊ����:"+type+"\n"+"�ں�:"+"��"+betno+"��"+"\n"+"ע��:"+zhushu*beishu+"\n"+"����:"+beishu+""+"\n"+"׷��:"+(qishu-1)+"��"+"\n"+"���:"+2*zhushu*beishu+"Ԫ"+"\n"+"������:"+2*zhushu*beishu*(qishu-1)+"Ԫ");

			//changeTextSumMoney();
			break;
		case R.id.buy_zixuan_seek_qishu:
			 if(lotno.equals("T01006")||lotno.equals("T01005")||lotno.equals("T01004")||lotno.equals("T01003")){
			     Toast.makeText(LotInfoConcreteActivity.this, "���û��׷��", Toast.LENGTH_SHORT);
				  }else
				 {
		          qishu = iProgress;
			      mtextqishu.setText("" + qishu);
		          info.setText("��Ʊ����:"+type+"\n"+"�ں�:"+"��"+betno+"��"+"\n"+"ע��:"+zhushu*beishu+"\n"+"����:"+beishu+""+"\n"+"׷��:"+(qishu-1)+"��"+"\n"+"���:"+2*zhushu*beishu+"Ԫ"+"\n"+"������:"+2*zhushu*beishu*(qishu-1)+"Ԫ");
				 }
			break;
		default:
			break;
		}

		
	}
    
	/**
	 * ����������ʾ��
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			// progressdialog.setTitle("Indeterminate");
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
		}
			progressdialog.setCancelable(true);
			return progressdialog;
		}
		return null;
	} 
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		PublicMethod.showDialog(LotInfoConcreteActivity.this);
	}

	@Override
	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}   
	
	private String formatTitle(String titleStr){
		String formattedStr = "";
		if(titleStr.length()>8){
			formattedStr = titleStr.substring(0, 8).toString()+"����";
		}else{
			formattedStr = titleStr;
		}
		return formattedStr;
	}
}
