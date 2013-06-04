package com.ruyicai.activity.buy.eleven;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.NoticeHistroy;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.code.dlc.DlcCode;
import com.ruyicai.code.eleven.ElevenCode;
import com.ruyicai.code.eleven.ElevenDanTuoCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.ElevenBalls;
import com.ruyicai.json.miss.DlcMissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SscZMissJson;

public class Eleven extends Dlc{
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_dlc_main);
		highttype = "DLC";
		setLotno();
		initTop();
		initSpinner();
		initGroup();
		setTitleOne(getString(R.string.eleven));
		
		
	}
	public void updatePage() {
		Intent intent = new Intent(Eleven.this, Eleven.class);
		startActivity(intent);
		finish();
	}
	
	
	/**
	 * ��ʼ��group
	 */
	public void initGroup(){
		if(state.equals("R1")||state.equals("R8")){
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
	 * ������©ֵ���
	 */
	public void setSellWay(){
		if(state.equals("Q2")||state.equals("R1")){
			if(!sellWay.equals(MissConstant.ELV_MV_Q3)){
				sellWay = MissConstant.ELV_MV_Q3;
			}
		}else if(state.equals("Z2")){
			if(!sellWay.equals(MissConstant.ELV_MV_Q2Z)){
				sellWay = MissConstant.ELV_MV_Q2Z;
			}
		}else if(state.equals("Z3")){
			if(!sellWay.equals(MissConstant.ELV_MV_Q3Z)){
				sellWay = MissConstant.ELV_MV_Q3Z;
			}
		}else if(state.equals("R5")){
			isMissNet(new SscZMissJson(),MissConstant.ELV_MV_ZH_R5,true);//��ȡ��©ֵ
		}else if(state.equals("R7")){
			isMissNet(new SscZMissJson(),MissConstant.ELV_MV_ZH_R7,true);//��ȡ��©ֵ
		}else if(state.equals("R8")){
			isMissNet(new SscZMissJson(),MissConstant.ELV_ZH_R8,true);//��ȡ��©ֵ
		}else if(state.equals("Q3")){
			sellWay = MissConstant.ELV_MV_Q3;
			isMissNet(new SscZMissJson(),MissConstant.ELV_MV_Q3_ZH,true);//��ȡ��©ֵ
		}else{
			if(!sellWay.equals(MissConstant.ELV_MV_RX)){
				sellWay = MissConstant.ELV_MV_RX;
			}
		}
		isMissNet(new DlcMissJson(),sellWay,false);//��ȡ��©ֵ
	}
	/**
	 * ���ò��ֱ��
	 * @param lotno
	 */
    public void setLotno(){
    	this.lotno = Constants.LOTNO_eleven;
    	lotnoStr = lotno;
    }
	/**
	 * ��ʼ����ѡѡ��
	 */
	public void createViewZx(int id){
		iProgressBeishu = 1;iProgressQishu = 1;
		sscCode = new ElevenCode();
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
		ElevenBalls dlcb = new ElevenBalls(num);
		createviewmechine(dlcb,id);
	}
	
	/**
	 * ��ʼ������ѡ��
	 */
	public void createViewDT(int id){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		initDTArea();
		sscCode = new ElevenDanTuoCode();
		createViewDanTuo(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id);
	
	}
	   /**
	    * Ͷעע��
	    * @return
	    */
	   public String getZhuma(){
		   String zhuma="";
		    if(is11_5DanTuo){
		    zhuma = ElevenDanTuoCode.zhuma(areaNums, state);	
		    }else{
		    zhuma = ElevenCode.zhuma(areaNums, state);
		    }
		   return zhuma;
	   }
		/**
		 * ��ѡͶעע��
		 */
		public String getZhuma(Balls ball) {
			   String zhuma="";
				zhuma = ElevenBalls.getZhuma(ball, state);
			   return zhuma;
		}
	   
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
					Intent intent = new Intent(Eleven.this,NoticeHistroy.class);
					intent.putExtra("lotno", Constants.LOTNO_eleven);
					startActivity(intent);			}
			});		
		}

}
