package com.ruyicai.activity.buy.eleven;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.palmdream.RuyicaiAndroid168.R;
import com.ruyicai.activity.buy.HighFrequencyNoticeHistroyActivity;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.code.eleven.ElevenCode;
import com.ruyicai.code.eleven.ElevenDanTuoCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.ElevenBalls;
import com.ruyicai.json.miss.DlcMissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SscZMissJson;
/**
 * 11运夺金
 * @author win
 *
 */
public class Eleven extends Dlc{
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleOne(getString(R.string.eleven));
		highttype = "DLC";
		setLotno();
		initSpinner();
		initGroup();
		setTitleOne(getString(R.string.eleven));
		
		
	}
	public void updatePage() {
		Intent intent = new Intent(Eleven.this, Eleven.class);
		startActivity(intent);
		finish();
	}
	public void turnHosity() {
		Intent intent = new Intent(Eleven.this,HighFrequencyNoticeHistroyActivity.class);
		intent.putExtra("lotno", Constants.LOTNO_eleven);
		startActivity(intent);	
	}
	/**
	 * 初始化group
	 */
	public void initGroup(){
		if(state.equals("R1")||state.equals("R8")||state.equals("Q2")||state.equals("Q3")){
			childtype= new String[]{"自选"};
			init();
			childtypes.setVisibility(View.GONE);
		}else  if(state.equals("Z2")||state.equals("Z3")){
			childtype= new String[]{"组选","胆拖"};
			init();
		}else{
			childtype= new String[]{"自选","胆拖"};
			init();
		}
	    group.setOnCheckedChangeListener(this);
	    group.check(0);   
	}
	/**
	 * 设置遗漏值类别
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
			isMissNet(new SscZMissJson(),MissConstant.ELV_MV_ZH_R5,true);//获取遗漏值
			sellWay = MissConstant.ELV_MV_RX;
		}else if(state.equals("R7")){
			isMissNet(new SscZMissJson(),MissConstant.ELV_MV_ZH_R7,true);//获取遗漏值
			sellWay = MissConstant.ELV_MV_RX;
		}else if(state.equals("R8")){
			isMissNet(new SscZMissJson(),MissConstant.ELV_ZH_R8,true);//获取遗漏值
			sellWay = MissConstant.ELV_MV_RX;
		}else if(state.equals("Q3")){
			sellWay = MissConstant.ELV_MV_Q3;
			isMissNet(new SscZMissJson(),MissConstant.ELV_MV_Q3_ZH,true);//获取遗漏值
		}else{
				sellWay = MissConstant.ELV_MV_RX;
		}
		isMissNet(new DlcMissJson(),sellWay,false);//获取遗漏值
	}
	/**
	 * 设置彩种编号
	 * @param lotno
	 */
    public void setLotno(){
    	this.lotno = Constants.LOTNO_eleven;
    	lotnoStr = lotno;
    }
	/**
	 * 初始化自选选区
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
			createView(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id,true);
		}
	}
	/**
	 * 初始化机选选区
	 */	
	public void createViewJx(int id){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		ElevenBalls dlcb = new ElevenBalls(num);
		createviewmechine(dlcb,id);
	}
	
	/**
	 * 初始化胆拖选区
	 */
	public void createViewDT(int id){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		initDTArea();
		sscCode = new ElevenDanTuoCode();
		createViewDanTuo(areaNums, sscCode,ZixuanAndJiXuan.NULL,true,id,true);
	
	}
	   /**
	    * 投注注码
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
		 * 机选投注注码
		 */
		public String getZhuma(Balls ball) {
			   String zhuma="";
				zhuma = ElevenBalls.getZhuma(ball, state);
			   return zhuma;
		}
	   

}
