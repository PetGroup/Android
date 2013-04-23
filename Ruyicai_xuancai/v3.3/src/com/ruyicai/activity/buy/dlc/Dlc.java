/**
 * 
 */
package com.ruyicai.activity.buy.dlc;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.NoticeHistroy;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.buy.ssc.Ssc;
import com.ruyicai.code.dlc.DlcCode;
import com.ruyicai.code.dlc.DlcDanTuoCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.custom.jc.button.MyButton;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.DlcQxBalls;
import com.ruyicai.jixuan.DlcRxBalls;
import com.ruyicai.json.miss.DlcMissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SscZMissJson;
import com.ruyicai.net.newtransaction.GetLotNohighFrequency;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

/**
 * 
 * ���ֲʣ�11ѡ5��
 * @author Administrator
 *
 */
public class Dlc extends ZixuanAndJiXuan {
	private String type[] = {"R2","R3","R4","R5","R6","R7","R8","R1","Q2","Q3","Z2","Z3"};//����
	private int nums[] = {2,3,4,5,6,7,8,1,2,3,2,3};//��ʽ��ѡ����
	private int numsdantuo[]={};
	private int maxs[] = {3,4,7,10,8,9,8,6,11,11,8,9};//ѡ�����С����
	private String titles[] = {"��ѡ��","��ѡ��","��ѡ��","��ѡ��","��ѡ��","��ѡ��","��ѡ��","ѡǰһ","ѡǰ��","ѡǰ��","ѡǰ����ѡ","ѡǰ����ѡ"};//������
	public static String state = "";//��ǰ����
    public int num = 1;//��ǰ��ʽ��ѡ����
    private int max = 6;//ѡ�����С����
    private Spinner typeSpinner;
    private BallTable  oneBallTable;
    private BallTable  twoBallTable;
    private BallTable  thirdBallTable;
    public boolean isJiXuan = false;
    protected boolean is11_5DanTuo = false;
    protected TextView titleOne;//�����
	protected TextView issue;//�ں�
	protected TextView time;//��ֹʱ��
	protected Button imgRetrun;//���ع��ʴ�����ť
	public  static String batchCode;//�ں�
	private int    lesstime;//ʣ��ʱ��
	private Handler handler = new Handler();
	public String lotno;
	private boolean isRun = true;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    layoutMain = inflater.inflate(R.layout.buy_dlc_main, null);
		setContentView(layoutMain);
		highttype = "DLC";
		setLotno();
		initTop();
		initSpinner();
		setIssue(lotno);
		
	}
	/**
	 * ���ò��ֱ��
	 * @param lotno
	 */
    public void setLotno(){
    	this.lotno = Constants.LOTNO_11_5;
    	lotnoStr = lotno;
    	
    }
	/**
	 * ��ʼ�����
	 */
	private void initTop(){
		titleOne = (TextView) findViewById(R.id.layout_main_text_title_one);
		issue = (TextView) findViewById(R.id.layout_main_text_issue);
		time = (TextView) findViewById(R.id.layout_main_text_time);
		imgRetrun = (Button) findViewById(R.id.layout_main_img_return);
		imgRetrun.setBackgroundResource(R.drawable.returnselecter);
		imgRetrun.setText("��ʷ����");
		imgRetrun.setVisibility(View.VISIBLE);
        titleOne.setText(getString(R.string.dlc));
	    //ImageView�ķ����¼�
		imgRetrun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Dlc.this,NoticeHistroy.class);
				intent.putExtra("lotno", Constants.LOTNO_11_5);
				startActivity(intent);			
				}
		});		
	}
	/**
	 * ���ô����
	 * @param title
	 */
	public void setTitleOne(String title){
		titleOne.setText(title);
	}
	/**
	 * ��ʼ��spinner���
	 */
	public void initSpinner(){
		typeSpinner = (Spinner) findViewById(R.id.buy_dlc_spinner);
		childtypes = (LinearLayout)findViewById(R.id.buy_dlc_top);
		typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				int position = typeSpinner.getSelectedItemPosition();
			    action(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
	}
	/**
	 * ��ʼ��group
	 */
	public void initGroup(){
		if(state.equals("R1")){
			childtype= new String[]{"ֱѡ","��ѡ"};  	
		}else  if(state.equals("Q2")||state.equals("Q3")){
			childtype= new String[]{"ֱѡ","��ѡ"};
		}else  if(state.equals("Z2")||state.equals("Z3")){
			childtype= new String[]{"��ѡ","��ѡ","����"};
		}else{
			childtype= new String[]{"ֱѡ","��ѡ","����"};
		}
		init();
	    group.setOnCheckedChangeListener(this);
	    group.check(0);   
	}
	/**
	 * RadioGroup�Ƿ�����
	 * @param lotno
	 */
	public void setGroupVisable(boolean isVisable){
		if(isVisable){
			group.setVisibility(RadioGroup.VISIBLE);
		}else{
			group.setVisibility(RadioGroup.GONE);
		}
	}
	

	/**
	 * ��ֵ����ǰ��
	 * @param type���ֱ��
	 */
	public void setIssue(final String  lotno){
		final Handler sscHandler = new Handler();
		issue.setText("�ںŻ�ȡ��....");
		time.setText("��ȡ��...");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				String re = "";
				String message="";
					re = GetLotNohighFrequency.getInstance().getInfo(lotno);
					if (!re.equalsIgnoreCase("")) {
						try {
							JSONObject obj = new JSONObject(re);
							message = obj.getString("message");
					        error_code = obj.getString("error_code");
					        lesstime = Integer.valueOf(obj.getString("time_remaining"));
							batchCode = obj.getString("batchcode");	
							while(isRun){
								if(isEnd(lesstime)){
									sscHandler.post(new Runnable(){
										public void run() {
										issue.setText("��" + batchCode + "��");
										time.setText("ʣ��ʱ��:" + PublicMethod.isTen(lesstime/60)+":"+PublicMethod.isTen(lesstime%60));
										}});
									Thread.sleep(1000);
									lesstime--;
								}else{
									sscHandler.post(new Runnable() {
										public void run() {
											issue.setText("��" + batchCode + "��");
											time.setText("ʣ��ʱ��:00:00");	
											nextIssue();
								      }});
									break;
								}
							}
						}catch (Exception e) {
							sscHandler.post(new Runnable() {
								public void run() {
									issue.setText("��ȡ�ں�ʧ��");
									time.setText("��ȡʧ��");
						}});
						}
					} else {
						
					}
			}
		});
		thread.start();
	}
	private boolean isEnd(int time) {
		if(time>0){
			return true;
		}else{
			return false;
		}
	}
	private void nextIssue() {
		new AlertDialog.Builder(Dlc.this).setTitle("��ʾ").setMessage(titleOne.getText().toString()+"��"+batchCode+"���Ѿ�����,�Ƿ�ת����һ��")
		        .setNegativeButton("ת����һ��", new Dialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setIssue(lotno);
			}
		
		}).setNeutralButton("������ҳ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			 Dlc.this.finish();
			}
		 }).create().show();
	}
	public void updatePage() {
		Intent intent = new Intent(Dlc.this, Dlc.class);
		startActivity(intent);
		finish();
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	/**
	 * ��ѡ���л�ֱѡ����ѡ
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		onCheckAction(checkedId);
	}
	public void onCheckAction(int checkedId){
		radioId = checkedId;
		switch(checkedId){		
		case 0:
			is11_5DanTuo=false;
			isJiXuan = false;
			createViewZx(checkedId);
		break;
		case 1:	
			is11_5DanTuo=false;
			isJiXuan = true;
			createViewJx(checkedId);
		break;	
		case 2:
			is11_5DanTuo=true;
			isJiXuan = false;
			createViewDT(checkedId);
	    break;
		}
	}
	/**
	 * ��ʼ����ѡѡ��
	 */
	public void createViewZx(int id){
		iProgressBeishu = 1;iProgressQishu = 1;
		sscCode = new DlcCode();
		initArea();
		if(state.equals("R5")){
			lineNum = 2;
			textSize = 2;
			createViewNew(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id);
		}else if(state.equals("R7")){
			lineNum = 2;
			textSize = 2;
			createViewNew(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id);
		}else if(state.equals("R8")){
			lineNum = 2;
			textSize = 2;
			createViewNew(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id);
		}else if(state.equals("Q3")){
			lineNum = 2;
			textSize = 2;
			createViewNew(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id);
		}else{
			createView(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id);
		}
	}
	/**
	 * ��ʼ����ѡѡ��
	 */
	public void createViewJx(int id){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if(state.equals("Q2")||state.equals("Q3")){
			DlcQxBalls dlcb= new DlcQxBalls(num);
			createviewmechine(dlcb,id);
		}else{
			DlcRxBalls dlcb = new DlcRxBalls(num);
			createviewmechine(dlcb,id);
		}
	
	}
	
	/**
	 * ��ʼ������ѡ��
	 */
	public void createViewDT(int id){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		initDTArea();
		sscCode = new DlcDanTuoCode();
		createViewDanTuo(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id);
	
	}

	
	
	/**
	 * ��ʼ��ѡ��
	 */
	public AreaNum[] initArea() {
		String wantitle = getString(R.string.qxc_first);
		String qiantitle = getString(R.string.qxc_second);
		String baititle = getString(R.string.qxc_third);
		if(state.equals("Q2")){
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(11,10, max, BallResId, 0, 1,Color.RED, wantitle);
			areaNums[1] = new AreaNum(11,10, max, BallResId, 0, 1,Color.RED, qiantitle);
		}else if(state.equals("Q3")){
			areaNums = new AreaNum[3];
			areaNums[0] = new AreaNum(11,10, max, BallResId, 0, 1,Color.RED, wantitle);
			areaNums[1] = new AreaNum(11,10, max, BallResId, 0, 1,Color.RED, qiantitle);
			areaNums[2] = new AreaNum(11,10, max, BallResId, 0, 1,Color.RED, baititle);
		}else{
			areaNums = new AreaNum[1];
	        String title = "��ѡ��Ͷע����" ;
			areaNums[0] = new AreaNum(11,10, max, BallResId, 0, 1,Color.RED, title);
		}
       return areaNums;
	}
	
	/**
	 * ��ʼ������ѡ��
	 */
	public void initDTArea() {
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(11,10, num-1, BallResId, 0, 1,Color.RED, "����");
			areaNums[1] = new AreaNum(11,10, 10, BallResId, 0, 1,Color.RED, "����");
	}
	/**
	 * spinner�����¼�
	 */
	public void action(int position){
	    state = type[position];
        num = nums[position];
        max = maxs[position];
        missView.clear();
		initGroup();
		setSellWay();
	}
	public void setSellWay(){
		if(state.equals("Q2")||state.equals("R1")){
			if(!sellWay.equals(MissConstant.DLC_MV_Q3)){
				sellWay = MissConstant.DLC_MV_Q3;
			}
		}else if(state.equals("Z2")){
			if(!sellWay.equals(MissConstant.DLC_MV_Q2Z)){
				sellWay = MissConstant.DLC_MV_Q2Z;
			}
		}else if(state.equals("Z3")){
			if(!sellWay.equals(MissConstant.DLC_MV_Q3Z)){
				sellWay = MissConstant.DLC_MV_Q3Z;
			}
		}else if(state.equals("R5")){
			isMissNet(new SscZMissJson(),MissConstant.DLC_MV_ZH_R5,true);//��ȡ��©ֵ
		}else if(state.equals("R7")){
			isMissNet(new SscZMissJson(),MissConstant.DLC_MV_ZH_R7,true);//��ȡ��©ֵ
		}else if(state.equals("R8")){
			isMissNet(new SscZMissJson(),MissConstant.DLC_ZH_R8,true);//��ȡ��©ֵ
		}else if(state.equals("Q3")){
			sellWay = MissConstant.ELV_MV_Q3;
			isMissNet(new SscZMissJson(),MissConstant.DLC_MV_Q3_ZH,true);//��ȡ��©ֵ
		}else{
			if(!sellWay.equals(MissConstant.DLC_MV_RX)){
				sellWay = MissConstant.DLC_MV_RX;
			}
		}
		isMissNet(new DlcMissJson(),sellWay,false);//��ȡ��©ֵ
	}
	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		int nBallId = 0; 
		for(int i=0;i<areaNums.length;i++){
			nBallId = iBallId;
			iBallId = iBallId - areaNums[i].areaNum;
		
		 if(iBallId<0){
				if(is11_5DanTuo){
					if(i==0){
						int isHighLight = areaNums[0].table.changeBallState(areaNums[0].chosenBallSum, nBallId);
						if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& areaNums[1].table.getOneBallStatue(nBallId) !=0) {
							areaNums[1].table.clearOnBallHighlight(nBallId);
							toast.setText(getResources().getString(R.string.ssq_toast_danma_title));
							toast.show();
						}
		
					}else if(i==1){
						int isHighLight = areaNums[1].table.changeBallState(areaNums[1].chosenBallSum, nBallId);
						if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& areaNums[0].table.getOneBallStatue(nBallId) !=0) {
							areaNums[0].table.clearOnBallHighlight(nBallId);
							toast.setText(getResources().getString(R.string.ssq_toast_tuoma_title));
							toast.show();
						}
					}
			}else  if(state.equals("Q2")){
						if(i==0){
							 int isHighLight = areaNums[0].table.changeBallState(areaNums[0].chosenBallSum, nBallId);
							 if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
								areaNums[1].table.clearOnBallHighlight(nBallId);
							 }
						}else{
							int isHighLight = areaNums[1].table.changeBallState(areaNums[1].chosenBallSum, nBallId);
							if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
								areaNums[0].table.clearOnBallHighlight(nBallId);
							}
						}			  
				  }else if(state.equals("Q3")){
						if(i==0){
							 int isHighLight = areaNums[0].table.changeBallState(areaNums[0].chosenBallSum, nBallId);
							 if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
								areaNums[1].table.clearOnBallHighlight(nBallId);
								areaNums[2].table.clearOnBallHighlight(nBallId);
							 }
						}else if(i==1){
							int isHighLight = areaNums[1].table.changeBallState(areaNums[1].chosenBallSum, nBallId);
							if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
								areaNums[0].table.clearOnBallHighlight(nBallId);
								areaNums[2].table.clearOnBallHighlight(nBallId);
							}
						}else{
							int isHighLight = areaNums[2].table.changeBallState(areaNums[2].chosenBallSum, nBallId);
							if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
								areaNums[0].table.clearOnBallHighlight(nBallId);
								areaNums[1].table.clearOnBallHighlight(nBallId);
							}
						}	
				  }else{
					  areaNums[i].table.changeBallState(areaNums[i].chosenBallSum, nBallId);
				  }
				  break;
			}

	     }

	}
	
	 /**
	    * ���С����ʾ���
	    * @param areaNum
	    * @param iProgressBeishu
	    * @return
	    */
	   public String textSumMoney(AreaNum areaNum[],int iProgressBeishu){
		   String textSum = "";
		   int iZhuShu = getZhuShu();
		   if(is11_5DanTuo){
			   int dan = areaNum[0].table.getHighlightBallNums();
	    	   int tuo = areaNum[1].table.getHighlightBallNums();
	    	   if (dan + tuo < num+1 ) {
	   			int num2 =num+1-dan-tuo;
	            	  if(dan==0){
	            		  textSum= "����ѡ��1������";  
	             	  }else{
	             		 textSum= "���ٻ���Ҫ"+num2+"������";
	             	  } 
	   		} else if(tuo==0){
	   			textSum= "����ѡ��1������";  
	   		}else {
	   			textSum = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
	   	
	   		}
		   }else if(state.equals("Q2")){//������
	    	   int oneNum = areaNum[0].table.getHighlightBallNums();
	    	   int twoNum = areaNum[1].table.getHighlightBallNums();
	    	   if(oneNum==0){
	    		   textSum = "��λ����Ҫ1��С��";
	    	   }else if(twoNum==0){
	    		   textSum = "ǧλ����Ҫ1��С��";
	    	   }else{
				   textSum = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
	    	   }
		   }else if(state.equals("Q3")){
				if(isMove&&itemViewArray.get(newPosition).isZHmiss){
					int onClickNum = getClickNum();
					if(onClickNum == 0){
						textSum = getResources().getString(R.string.please_choose_number);
					}else{
						textSum = "��" + onClickNum + "ע����" + (onClickNum * 2) + "Ԫ";
					}
				}else{
				   int oneNum = areaNum[0].table.getHighlightBallNums();
				   int twoNum = areaNum[1].table.getHighlightBallNums();
				   int thirdNum = areaNum[2].table.getHighlightBallNums();
				   if(oneNum==0){
		    		   textSum = "��λ����Ҫ1��С��";
		    	   }else if(twoNum==0){
		    		   textSum = "ǧλ����Ҫ1��С��";
		    	   }else if(thirdNum==0){
		    		   textSum = "��λ����Ҫ1��С��";
		    	   }else{
					   textSum = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
		    	   }
				}
		   }else if(state.equals("R5")||state.equals("R7")||state.equals("R8")){//�����
				if(isMove&&itemViewArray.get(newPosition).isZHmiss){
					int onClickNum = getClickNum();
					if(onClickNum == 0){
						textSum = getResources().getString(R.string.please_choose_number);
					}else{
						textSum = "��" + onClickNum + "ע����" + (onClickNum * 2) + "Ԫ";
					}
				}else{
				   int ballNums = areaNum[0].table.getHighlightBallNums();
				   int oneNum = num - ballNums;
				   if(oneNum>0){
					   textSum = "����Ҫ"+oneNum+"��С��";
				   }else{
					   textSum = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
				   }
				}
			   
		   }else{
			   int ballNums = areaNum[0].table.getHighlightBallNums();
			   int oneNum = num - ballNums;
			   if(oneNum>0){
				   textSum = "����Ҫ"+oneNum+"��С��";
			   }else{
				   textSum = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
			   }
		   }
		return textSum ;
		   
	   };
	   /**
	    * �ж��Ƿ�����Ͷע����
	    */
	   public String  isTouzhu(){
		  String isTouzhu = "";
			  int iZhuShu = getZhuShu();
			  if(is11_5DanTuo){
				  
				  int dan = areaNums[0].table.getHighlightBallNums();
					int tuo =areaNums[1].table.getHighlightBallNums();
					if ( dan + tuo < num+1 || dan < 1
							|| dan > num-1 
							|| tuo < 2 || tuo > 10) {
						if(state.equals("R2")||state.equals("Z2")){
							isTouzhu = "��ѡ��:\"1�����룻\n" + " 2~10�����룻\n" + " �������������֮�Ͳ�С��"+(num+1)+"��";
						}else{
						isTouzhu = "��ѡ��:\n1~"+(num-1)+"�����룻\n" + " 2~10�����룻\n" + " �������������֮�Ͳ�С��"+(num+1)+"��";
						}
					} else if (iZhuShu > MAX_ZHU) {
						isTouzhu = "false";
					} else {
						isTouzhu = "true";
					}
				  
			  }else if(state.equals("Q2")){

		    	   if(iZhuShu==0){
		    		    isTouzhu = "������λ��ǧλ����ѡ��һ�����ٽ���Ͷע��";
		    	   }else if(iZhuShu  > MAX_ZHU) {
						isTouzhu = "false";
				   }else{
					   isTouzhu = "true";
				   }
		      }else if(state.equals("Q3")){
		  		if(isMove&&itemViewArray.get(newPosition).isZHmiss){
					int onClickNum = getClickNum();
					if(onClickNum==0){
						isTouzhu = "������ѡ��һע��";
					}else{
						isTouzhu = "true";
					}
				}else{
		    	   if(iZhuShu==0){
		    		   isTouzhu = "������λ��ǧλ�Ͱ�λ����ѡ��һ�����ٽ���Ͷע��";
		    	   }else if(iZhuShu  > MAX_ZHU) {
		    		   isTouzhu = "false";
		    	   }else{
					   isTouzhu = "true";
				   }
				}
		      }else if(state.equals("R5")||state.equals("R7")||state.equals("R8")){  
		  		if(isMove&&itemViewArray.get(newPosition).isZHmiss){
					int onClickNum = getClickNum();
					if(onClickNum==0){
						isTouzhu = "������ѡ��һע��";
					}else{
						isTouzhu = "true";
					}
				}else{
		   	       int ballNums = areaNums[0].table.getHighlightBallNums();
				   int oneNum = num - ballNums;
				   if(oneNum>0){
					   isTouzhu = "����ѡ��"+oneNum+"���ٽ���Ͷע��";
				   }else if(iZhuShu  > MAX_ZHU) {
						isTouzhu = "false";
				   }else{
						isTouzhu = "true";
				   }
				}
		      }else{
		   	       int ballNums = areaNums[0].table.getHighlightBallNums();
				   int oneNum = num - ballNums;
				   if(oneNum>0){
					   isTouzhu = "����ѡ��"+oneNum+"���ٽ���Ͷע��";
				   }else if(iZhuShu  > MAX_ZHU) {
						isTouzhu = "false";
				   }else{
						isTouzhu = "true";
				   }
		      }
		      return isTouzhu;
	   }
	   /**
	    * Ͷעע��
	    * @return
	    */
	   public String getZhuma(){
		   String zhuma="";
		   if(is11_5DanTuo){
		   zhuma = DlcDanTuoCode.zhuma(areaNums, state);
		   }else{
		   zhuma = DlcCode.zhuma(areaNums, state);
		   }
		   return zhuma;
	   }
		@Override
		public String getZhuma(Balls ball) {
			   String zhuma="";
			   zhuma = DlcRxBalls.getZhuma(ball, state); 
			   return zhuma;
		}
		
	   
	   /**
	    * ��ѡ���ѿ�ע��
	    * @return
	    */
	   public String getZxAlertZhuma(){
		int iZhuShu = getZhuShu();
		String zhuma = "";
		if(is11_5DanTuo){
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			zhuma = "���룺"+ PublicMethod.getStrZhuMa(dan)
					+"\n���룺"+ PublicMethod.getStrZhuMa(tuo);
		}else if(state.equals("Q2")){
			int[] one = areaNums[0].table.getHighlightBallNOs();
			int[] two = areaNums[1].table.getHighlightBallNOs();
			zhuma = "��λ��"+ PublicMethod.getStrZhuMa(one)
			+"\nǧλ��"+ PublicMethod.getStrZhuMa(two);
		}else if(state.equals("Q3")){
			int[] one = areaNums[0].table.getHighlightBallNOs();
			int[] two = areaNums[1].table.getHighlightBallNOs();
			int[] third = areaNums[2].table.getHighlightBallNOs();
			zhuma = "��λ��"+ PublicMethod.getStrZhuMa(one)
			+"\nǧλ��"+ PublicMethod.getStrZhuMa(two)
			+"\n��λ��"+ PublicMethod.getStrZhuMa(third);
		}else{
			int[] one = areaNums[0].table.getHighlightBallNOs();
			zhuma = PublicMethod.getStrZhuMa(one);
		}
		codeStr = "ע�룺\n"+zhuma;
		return codeStr;   
	   }


	   /**
	    * �����ע��
	    * @return
	    */
	   public int getZhuShu(){
		   int zhushu = 0;
		   if(isJiXuan){
			   zhushu = balls.size()*iProgressBeishu;
		   }else if(is11_5DanTuo){
			   int dan = areaNums[0].table.getHighlightBallNums();
	    	   int tuo = areaNums[1].table.getHighlightBallNums();
	    	   zhushu = (int) getDTZhuShu(dan, tuo,iProgressBeishu);
		   }else {
			if(state.equals("Q2")){
		     	   int oneNum = areaNums[0].table.getHighlightBallNums();
		    	   int twoNum = areaNums[1].table.getHighlightBallNums();
		    	   zhushu = oneNum*twoNum*iProgressBeishu;
			}else if(state.equals("Q3")){
				   int oneNum = areaNums[0].table.getHighlightBallNums();
				   int twoNum = areaNums[1].table.getHighlightBallNums();
				   int thirdNum = areaNums[2].table.getHighlightBallNums();
		    	   zhushu = oneNum*twoNum*thirdNum*iProgressBeishu;
			}else{
				   int ballNums = areaNums[0].table.getHighlightBallNums();
				   zhushu = (int) PublicMethod.zuhe(num,ballNums)*iProgressBeishu;
			}
		   }
		return zhushu;
	   }
	   
		/**
		 * ��ʽ�淨ע�����㷽��
		 * @param int aRedBalls �������
		 * 
		 * @return long ע��
		 */
		private long getDTZhuShu(int dan, int tuo,int iProgressBeishu) {
			long ssqZhuShu = 0L;
			if (dan > 0 && tuo > 0 ) {
				ssqZhuShu += (PublicMethod.zuhe(num - dan, tuo) * iProgressBeishu);
			}
			return ssqZhuShu;
		}
		/**
		 * ��ӵ�������
		 */
		public void getCodeInfo(AddView addView){
			int zhuShu = getZhuShu();
			CodeInfo codeInfo = addView.initCodeInfo(getAmt(zhuShu), zhuShu);
			code.setZHmiss(false);
			codeInfo.setTouZhuCode(getZhuma());
			for(AreaNum areaNum:areaNums){
				int[] codes = areaNum.table.getHighlightBallNOs();
				hightballs=codes.length;
				String codeStr = "";
				for (int i = 0; i < codes.length; i++) {
					codeStr += PublicMethod.isTen(codes[i]);
					if (i != codes.length - 1) {
						codeStr += ",";
					}
				}
				codeInfo.addAreaCode(codeStr,areaNum.textColor);
			}   
			addView.addCodeInfo(codeInfo);
		}
		/**
		 * �����©��ӵ�ѡ����
		 * @param addView
		 */
		public void getZCodeInfo(AddView addView){
			List<MyButton> missBtnList = itemViewArray.get(newPosition).missBtnList;
			for(int i=0;i<missBtnList.size();i++){
				MyButton myBtn = missBtnList.get(i);
				if(myBtn.isOnClick()){
					int zhuShu = 1;
					CodeInfo codeInfo = addView.initCodeInfo(getAmt(zhuShu), zhuShu);
					String codeStr = myBtn.getBtnText();
					code.setZHmiss(true);
					code.setIsZHcode(codeStr);
					codeInfo.setTouZhuCode(getZhuma());
					codeInfo.addAreaCode(codeStr,Color.RED);
					addView.addCodeInfo(codeInfo);
				}
			}
		}
	   /**
	    * Ͷע����
	    */
	   public void touzhuNet(){
			int zhuShu=getZhuShu();
			if(isJiXuan){
			betAndGift.setSellway("1")	;
			}else{
			betAndGift.setSellway("0");}//1�����ѡ 0������ѡ
			betAndGift.setLotno(lotno);
			betAndGift.setBet_code(getZhuma());
			betAndGift.setAmount(""+zhuShu*200);
		   
	   }

		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();

		}

		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			setLotno();
	       
		}

		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
		}   
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			isRun = false;
		}

}
