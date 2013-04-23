/**
 * 
 */
package com.ruyicai.activity.buy.dlc;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.NoticeHistroy;
import com.ruyicai.activity.buy.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.ssc.Ssc;
import com.ruyicai.activity.buy.ssq.Ssq;
import com.ruyicai.code.dlc.DlcCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.DlcQxBalls;
import com.ruyicai.jixuan.DlcRxBalls;
import com.ruyicai.net.newtransaction.GetLotNohighFrequency;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

/**
 * 
 * ���ֲʣ�11ѡ5��
 * @author Administrator
 *
 */
public class Dlc extends ZixuanAndJiXuan {
	private Thread threed;
	private String type[] = {"R1","R2","R3","R4","R5","R6","R7","R8","Q2","Q3","Z2","Z3"};//����
	private int nums[] = {1,2,3,4,5,6,7,8,2,3,2,3};//��ʽ��ѡ����
	private int maxs[] = {6,3,4,7,10,8,9,8,11,11,8,9};//ѡ�����С����
	private String titles[] = {"��ѡһ","��ѡ��","��ѡ��","��ѡ��","��ѡ��","��ѡ��","��ѡ��","��ѡ��","ѡǰ��","ѡǰ��","ѡǰ����ѡ","ѡǰ����ѡ"};//������
	public static String state = "";//��ǰ����
    public int num = 1;//��ǰ��ʽ��ѡ����
    private int max = 6;//ѡ�����С����
    private Spinner typeSpinner;
    private BallTable  oneBallTable;
    private BallTable  twoBallTable;
    private BallTable  thirdBallTable;
    public boolean isJiXuan = false;
    protected TextView titleOne;//�����
	protected TextView issue;//�ں�
	protected TextView time;//��ֹʱ��
	protected Button imgRetrun;//���ع��ʴ�����ť
	public  static String batchCode;//�ں�
	private Timer  timer;//��ʱ��
	private int    lesstime;//ʣ��ʱ��
	private Handler handler = new Handler();
	public String lotno;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_dlc_main);
		sscCode = new DlcCode();
		highttype = "DLC";
		setLotno();
		initTop();
		initSpinner();
		initGroup();
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
				startActivity(intent);			}
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
		typeSpinner.setSelection(1);
	}
	/**
	 * ��ʼ��group
	 */
	public void initGroup(){
		if(state.equals("Q2")||state.equals("Q3")){
			childtype= new String[]{"ֱѡ","��ѡ"};
		}else if(state.equals("Z2")||state.equals("Z3")){
			childtype= new String[]{"��ѡ","��ѡ"};
		}else{
			childtype= new String[]{"ֱѡ","��ѡ"};
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
	
	public void setIssue(final String  lotno){
		issue.setText("�ںŻ�ȡ��....");
		time.setText("��ȡ��...");
		threed = new Thread(new Runnable() {
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
							handler.post(new Runnable(){
								public void run() {
								issue.setText("��" + batchCode + "��");
								}});
							if(lesstime<=0){
								handler.post(new Runnable(){
									public void run() {
										time.setText("ʣ��ʱ��:" + "00:00");
								}});
								return;
							}
							TimerTask task = new TimerTask(){
							public void run() {
							handler.post(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									//issue.setText("��" + "00000000" + "��");
									
									time.setText("ʣ��ʱ��:" + PublicMethod.isTen(lesstime/60)+":"+PublicMethod.isTen(lesstime%60));
									lesstime--;
									if(lesstime==0){
										timer.cancel();timer=null;
									new AlertDialog.Builder(Dlc.this).setTitle("��ʾ").setMessage(titleOne.getText().toString()+"��"+batchCode+"���Ѿ�����,�Ƿ�ת����һ��")
									.setNegativeButton("ת����һ��", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											setIssue(lotno);
										}
									}).setNeutralButton("������ҳ��", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
										 Dlc.this.finish();
										}
									}).create().show();
									}
								}
							});							
			             	}};
							timer = new Timer(true);
						    timer.schedule(task, 0, 1000);
						}catch (Exception e) {
							handler.post(new Runnable() {
								public void run() {
									issue.setText("��ȡ�ں�ʧ��");
						} });
						}
					} else {
						
					}
			}
		});
		threed.start();
	}

	/**
	 * ��ѡ���л�ֱѡ����ѡ
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(checkedId){		
		case 0:
			isJiXuan = false;
			createViewZx();
		break;
		case 1:	
			isJiXuan = true;
			createViewJx();
		break;	
		}
			
	}
	/**
	 * ��ʼ����ѡѡ��
	 */
	public void createViewZx(){
		iProgressBeishu = 1;iProgressQishu = 1;
		initArea();
		createView(areaNums, sscCode,ZixuanAndJiXuan.NULL,true);
	}
	/**
	 * ��ʼ����ѡѡ��
	 */
	public void createViewJx(){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if(state.equals("Q2")||state.equals("Q3")){
			DlcQxBalls dlcb= new DlcQxBalls(num);
			createviewmechine(dlcb);
		}else{
			DlcRxBalls dlcb = new DlcRxBalls(num);
			createviewmechine(dlcb);
		}
	
	}
	/**
	 * ��ʼ��ѡ��
	 */
	public void initArea() {
		String wantitle = "��λ��";
		String qiantitle = "ǧλ��";
		String baititle = "��λ��";
		if(state.equals("Q2")){
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(11,8, max, BallResId, 0, 1,Color.RED, wantitle);
			areaNums[1] = new AreaNum(11,8, max, BallResId, 0, 1,Color.RED, qiantitle);
		}else if(state.equals("Q3")){
			areaNums = new AreaNum[3];
			areaNums[0] = new AreaNum(11,8, max, BallResId, 0, 1,Color.RED, wantitle);
			areaNums[1] = new AreaNum(11,8, max, BallResId, 0, 1,Color.RED, qiantitle);
			areaNums[2] = new AreaNum(11,8, max, BallResId, 0, 1,Color.RED, baititle);
		}else{
			areaNums = new AreaNum[1];
	        String title = "��ѡ��Ͷע����" ;
			areaNums[0] = new AreaNum(11,8, max, BallResId, 0, 1,Color.RED, title);
		}

	}
	/**
	 * spinner�����¼�
	 */
	public void action(int position){
	    state = type[position];
        num = nums[position];
        max = maxs[position];
		initGroup();
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
				  if(state.equals("Q2")){
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
	       if(state.equals("Q2")){//������
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
		   }else{//�����	
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
		      if(state.equals("Q2")){

		    	   if(iZhuShu==0){
		    		    isTouzhu = "������λ��ǧλ����ѡ��һ�����ٽ���Ͷע��";
		    	   }else if(iZhuShu * 2 > 20000) {
						isTouzhu = "false";
				   }else{
					   isTouzhu = "true";
				   }
		      }else if(state.equals("Q3")){
		    	   if(iZhuShu==0){
		    		   isTouzhu = "������λ��ǧλ�Ͱ�λ����ѡ��һ�����ٽ���Ͷע��";
		    	   }else if(iZhuShu * 2 > 20000) {
		    		   isTouzhu = "false";
		    	   }else{
					   isTouzhu = "true";
				   }
		      }else{  
		   	       int ballNums = areaNums[0].table.getHighlightBallNums();
				   int oneNum = num - ballNums;
				   if(oneNum>0){
					   isTouzhu = "����ѡ��"+oneNum+"���ٽ���Ͷע��";
				   }else if(iZhuShu * 2 > 20000) {
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
		   zhuma = DlcCode.zhuma(areaNums, state);
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
		if(state.equals("Q2")){
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
	       
		}

		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
		
		}

		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
		}   
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			 if(timer!=null){
				 timer.cancel();
			 }
		}
}
