package com.ruyicai.activity.buy.happypoker;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.pojo.AreaNum;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;

/**
 * 快乐扑克
 *
 */
public class HappyPoker extends ZixuanAndJiXuan{
	
	private ElevenSelectFiveTopView happyPokerTopView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		setContentView(R.layout.activity_buy_happy_poker_main);
		
		initView();
		MobclickAgent.onEvent(this, "happypoker"); 
		MobclickAgent.onEvent(this, "gaopingoucaijiemian ");
	}
	
	private void initView(){
		happyPokerTopView=(ElevenSelectFiveTopView) findViewById(R.id.happyPokerTopView);
		happyPokerTopView.removeZoushiBtnBackGround();
		happyPokerTopView.addElevenSelectFiveTopViewClickListener(new ElevenSelectFiveTopViewClickListener() {
			
			/* 
			 * 普通玩法选择
			 */
			@Override
			public void TouchPTPlayMethod(int position) {
				
			}
			
			/* 
			 * 胆拖玩法选择
			 */
			@Override
			public void TouchDTPlayMethod(int position) {
				
			}
			
			/* 
			 * 摇一摇机选
			 */
			@Override
			public void ElevenSelectFiveOmission() {
				
			}
			
			/* 
			 * 头部刷新按钮
			 */
			@Override
			public void ElevenSelectFiveFresh() {
				
			}
			
			@Override
			public void ChooseIsToShowMissMessage(boolean isChecked) {
				
			}
		});
	}

	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		return null;
	}

	@Override
	public String isTouzhu() {
		return null;
	}

	@Override
	public int getZhuShu() {
		return 0;
	}

	@Override
	public String getZhuma() {
		return null;
	}

	@Override
	public String getZhuma(Balls ball) {
		return null;
	}

	@Override
	public void touzhuNet() {
		
	}

	@Override
	public void onCheckAction(int checkedId) {
		
	}
}
