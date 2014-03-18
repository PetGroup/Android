package com.ruyicai.activity.buy.eleven;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.HighFrequencyNoticeHistroyActivity;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.code.Gdeleven.GdelevenCode;
import com.ruyicai.code.Gdeleven.GdelevenDanTuoCode;
import com.ruyicai.code.eleven.ElevenCode;
import com.ruyicai.code.eleven.ElevenDanTuoCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.ElevenBalls;
import com.ruyicai.jixuan.GdelevenRxBalls;
import com.ruyicai.json.miss.DlcMissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SscZMissJson;
import com.umeng.analytics.MobclickAgent;

/**
 * 11运夺金
 * 
 * @author win
 * 
 */
public class Eleven extends Dlc {
	
	private String[] danTuoPlayMessage={ "任选二","任选三","任选四","任选五","任选六","任选七","前二组选","前三组选"};
	protected String dt_types[] = { "DT_R2", "DT_R3", "DT_R4", "DT_R5", "DT_R6", "DT_R7",
			"DT_ZU2", "DT_ZU3" };// 胆拖类型
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setLotnoX(Constants.LOTNO_eleven);
		highttype = "DLC";
		elevenSelectFiveTopView.setDanTuoPlayMessage(danTuoPlayMessage);
		setLotno();
		MobclickAgent.onEvent(this, "11yunduojin"); // BY贺思明 点击首页的“11运夺金”图标
		MobclickAgent.onEvent(this, "gaopingoucaijiemian ");// BY贺思明 高频购彩页面

	}
	
	public void changeState(int playMethodTag,int position){
		if(playMethodTag==1){
			state = dt_types[position];
		}else{
			state = dt_types[position];
		}
	}

	/**
	 * 设置遗漏值类别
	 */
	public void setSellWay() {
		if (state.equals("PT_QZ2") || state.equals("PT_QZ1")) {
			if (!sellWay.equals(MissConstant.ELV_MV_Q3)) {
				sellWay = MissConstant.ELV_MV_Q3;
			}
		} else if (state.equals("PT_ZU2")|| state.equals("DT_ZU2")) {
			if (!sellWay.equals(MissConstant.ELV_MV_Q2Z)) {
				sellWay = MissConstant.ELV_MV_Q2Z;
			}
		} else if (state.equals("PT_ZU3")|| state.equals("DT_ZU3")) {
			if (!sellWay.equals(MissConstant.ELV_MV_Q3Z)) {
				sellWay = MissConstant.ELV_MV_Q3Z;
			}
		} else if (state.equals("R5")) {
			isMissNet(new SscZMissJson(), MissConstant.ELV_MV_ZH_R5, true);// 获取遗漏值
			sellWay = MissConstant.ELV_MV_RX;
		} else if (state.equals("R7")) {
			isMissNet(new SscZMissJson(), MissConstant.ELV_MV_ZH_R7, true);// 获取遗漏值
			sellWay = MissConstant.ELV_MV_RX;
		} else if (state.equals("R8")) {
			isMissNet(new SscZMissJson(), MissConstant.ELV_ZH_R8, true);// 获取遗漏值
			sellWay = MissConstant.ELV_MV_RX;
		} else if (state.equals("PT_QZ3")) {
			sellWay = MissConstant.ELV_MV_Q3;
			isMissNet(new SscZMissJson(), MissConstant.ELV_MV_Q3_ZH, true);// 获取遗漏值
		} else {
			sellWay = MissConstant.ELV_MV_RX;
		}
		isMissNet(new DlcMissJson(), sellWay, false);// 获取遗漏值
	}

	/**
	 * 设置彩种编号
	 * 
	 * @param lotno
	 */
	public void setLotno() {
		this.lotno = Constants.LOTNO_eleven;
		this.noticeLotNo=NoticeActivityGroup.ID_SUB_YDJ_LISTVIEW;
		lotnoStr = lotno;
		sscCode=new  ElevenCode();
	}
	
	/**
	 * 投注注码
	 * 
	 * @return
	 */
	public String getZhuma() {
		String zhuma = "";
		if (is11_5DanTuo) {
			zhuma = ElevenDanTuoCode.zhuma(areaNums, state);
		} else {
			zhuma = ElevenCode.zhuma(areaNums, state);
		}
		return zhuma;
	}

	/**
	 * 机选投注注码
	 */
	public String getZhuma(Balls ball) {
		String zhuma = "";
		zhuma = ElevenBalls.getZhuma(ball, state);
		return zhuma;
	}


}
