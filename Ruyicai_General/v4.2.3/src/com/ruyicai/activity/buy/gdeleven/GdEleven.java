package com.ruyicai.activity.buy.gdeleven;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.HighFrequencyNoticeHistroyActivity;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.activity.buy.eleven.Eleven;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.code.Gdeleven.GdelevenCode;
import com.ruyicai.code.Gdeleven.GdelevenDanTuoCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.GdelevenQxBalls;
import com.ruyicai.jixuan.GdelevenRxBalls;
import com.ruyicai.json.miss.DlcMissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SscZMissJson;
import com.ruyicai.pojo.AreaNum;
import com.umeng.analytics.MobclickAgent;

public class GdEleven extends Dlc {
	
	private String[] danTuoPlayMessage={ "任选二","任选三","任选四","任选五","任选六","任选七","前二组选","前三组选"};
	protected String dt_types[] = { "DT_R2", "DT_R3", "DT_R4", "DT_R5", "DT_R6", "DT_R7",
			"DT_ZU2", "DT_ZU3" };// 胆拖类型
	protected int[] dtNum={1,2,3,4,5,6,1,2};// 胆拖选区最大小球数
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setLotnoX(Constants.LOTNO_GD115);
		highttype = "DLC";
		elevenSelectFiveTopView.setDanTuoPlayMessage(danTuoPlayMessage);
		setLotno();
		MobclickAgent.onEvent(this, "guangdong11xuan5"); // BY贺思明
															// 点击首页的“广东11选5”图标
		MobclickAgent.onEvent(this, "gaopingoucaijiemian ");// BY贺思明 高频购彩页面
	}

	public void changeState(int playMethodTag,int position){
		if(playMethodTag==1){
			state = pt_types[position];
		}else{
			state = dt_types[position];
		}
	}
	
	/**
	 * 初始化胆拖选区
	 */
	public void createViewDT(int id) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		areaNums = new AreaNum[2];
		areaNums[0] = new AreaNum(cqArea, 1, dtNum[itemId], BallResId, 0, 1,
				Color.RED, "胆码", dtDPrompt(itemId), false, false, true);
		areaNums[1] = new AreaNum(cqArea, 10, 10, BallResId, 0, 1, Color.RED,
				"拖码", dtTPrompt, false, false, true);
		baseSensor.stopAction();
		if (state.equals("DT_ZU2")) {
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QE, id, true,clickBallText);
		} else if (state.equals("DT_ZU3")) {
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QS, id, true,clickBallText);
		} else {
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.NULL, id, true,clickBallText);
		}
	}
	
	/**
	 * 设置遗漏值类别
	 */
	public void setSellWay() {
		boolean isShowZhMissBtn=true;
		if (state.equals("PT_QZ2") || state.equals("PT_QZ1")) {
			isShowZhMissBtn=false;
			if (!sellWay.equals(MissConstant.gdELV_MV_Q3)) {
				sellWay = MissConstant.gdELV_MV_Q3;
			}
		} else if (state.equals("PT_ZU2")|| state.equals("DT_ZU2")) {
			isShowZhMissBtn=false;
			if (!sellWay.equals(MissConstant.gdELV_MV_Q2Z)) {
				sellWay = MissConstant.gdELV_MV_Q2Z;
			}
		} else if (state.equals("PT_ZU3")|| state.equals("DT_ZU3")) {
			isShowZhMissBtn=false;
			if (!sellWay.equals(MissConstant.gdELV_MV_Q3Z)) {
				sellWay = MissConstant.gdELV_MV_Q3Z;
			}
		} else if (state.equals("PT_R5")) {
			isMissNet(new SscZMissJson(), MissConstant.gdELV_MV_ZH_R5, true);// 获取遗漏值
			sellWay = MissConstant.gdELV_MV_RX;
		} else if (state.equals("PT_R7")) {
			isMissNet(new SscZMissJson(), MissConstant.gdELV_MV_ZH_R7, true);// 获取遗漏值
			sellWay = MissConstant.gdELV_MV_RX;
		} else if (state.equals("PT_R8")) {
			isMissNet(new SscZMissJson(), MissConstant.gdELV_ZH_R8, true);// 获取遗漏值
			sellWay = MissConstant.gdELV_MV_RX;
		} else if (state.equals("PT_QZ3")) {
			sellWay = MissConstant.gdELV_MV_Q3;
			isMissNet(new SscZMissJson(), MissConstant.gdELV_MV_Q3_ZH, true);// 获取遗漏值
		} else {
			isShowZhMissBtn=false;
			sellWay = MissConstant.gdELV_MV_RX;
		}
		showZhMissBtn(isShowZhMissBtn);
		isMissNet(new DlcMissJson(), sellWay, false);// 获取遗漏值
	}

	/**
	 * 设置彩种编号
	 * 
	 * @param lotno
	 */
	public void setLotno() {
		this.lotno = Constants.LOTNO_GD_11_5;
		this.noticeLotNo=NoticeActivityGroup.ID_SUB_GD115_LISTVIEW;
		lotnoStr = lotno;
		lotNoName="广东11选5";
	}
	
	/**
	 * 投注注码
	 * 
	 * @return
	 */
	public String getZhuma() {
		String zhuma = "";
		if (is11_5DanTuo) {
			zhuma = GdelevenDanTuoCode.zhuma(areaNums, state);
		} else {
			zhuma = GdelevenCode.zhuma(areaNums, state);
		}
		return zhuma;
	}

	/**
	 * 机选投注注码
	 */
	public String getZhuma(Balls ball) {
		String zhuma = "";
		zhuma = GdelevenRxBalls.getZhuma(ball, state);
		return zhuma;
	}

}
