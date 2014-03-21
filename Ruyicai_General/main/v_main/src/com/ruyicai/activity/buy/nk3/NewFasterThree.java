package com.ruyicai.activity.buy.nk3;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveHistoryLottery;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.pojo.AreaNum;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

/**
 * 吉林新快三
 *
 */
public class NewFasterThree extends ZixuanAndJiXuan {
	
	private ElevenSelectFiveTopView newNmk3TopView;
	private String[] ptPlayMethod={"和值","三同号","二同号单选","三不同","二不同","二同号复选"};
	private String[] dtPlayMethod={"三不同","二不同"};
	private String[] ptPlayMethodDescribe={"奖金9-240元","奖金9-240元","奖金80元","奖金10-40元","奖金8元","奖金15元"};
	private String[] dtPlayMethodDescribe={"奖金10-40元","奖金8元"};
	private int[] itemClickPicture={R.drawable.new_nmk3_playmethod_normal,R.drawable.new_nmk3_playmethod_click};
	private int noticeLotNo=NoticeActivityGroup.ID_SUB_DLC_LISTVIEW;
	private int checkedId;
	/**玩法标识:1普通，2胆拖*/
	private int playMethodTag=1;
	public AddView addView = new AddView(this);
	int[] cqArea={5,6};
	protected int BallResId[] = { R.drawable.cq_11_5_ball_normal, R.drawable.cq_11_5_ball_select };
	protected int nums[] = { 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 2, 3 };// 单式机选个数
	private int itemId=3;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		lotno = Constants.LOTNO_NEW_FASTER_THREE;
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutMain = inflater.inflate(R.layout.activity_new_faster_three_main, null);
		setContentView(layoutMain);
		
		initView();
		action();
	}
	
	private void action(){
		missView.clear();
		childtype = new String[] { "自选" };
		init();
		childtypes.setVisibility(View.GONE);
		group.setOnCheckedChangeListener(this);
		group.check(0);
	}
	
	private void initView(){
		newNmk3TopView=(ElevenSelectFiveTopView) findViewById(R.id.newNmk3TopView);
		newNmk3TopView.isNewNmkThree(true);
		newNmk3TopView.setTopViewBackGround(R.drawable.new_nmk3_top);
		newNmk3TopView.setTopViewTitleBackGround(R.drawable.new_nmk3_top_title_click);
		newNmk3TopView.setPuTongPlayMessage(ptPlayMethod);
		newNmk3TopView.setDanTuoPlayMessage(dtPlayMethod);
		newNmk3TopView.removeTopViewTitleBackGround();
		newNmk3TopView.setPoupWindowItemClickPicture(itemClickPicture);
		newNmk3TopView.setPtPlayMethodDescribeList(ptPlayMethodDescribe);
		newNmk3TopView.setDtPlayMethodDescribeList(dtPlayMethodDescribe);
		newNmk3TopView.setTextColor(this.getResources().getColor(R.color.white));
		newNmk3TopView.setQueryMessage(lotno, noticeLotNo);
		newNmk3TopView.setZhMissBtnBackGround(R.drawable.new_nmk3_yao);
		newNmk3TopView.setZouShiBtnBackGround(R.drawable.new_nmk3_zoushi);
		newNmk3TopView.setLotteryMessageTextColor(this.getResources().getColor(R.color.white));
		newNmk3TopView.setPtPlayMessage(getResources().getStringArray(R.array.new_nmk3_choose_pt_type));
		newNmk3TopView.setDtPlayMessage(getResources().getStringArray(R.array.new_nmk3_choose_dt_type));
		
		newNmk3TopView.addElevenSelectFiveTopViewClickListener(new ElevenSelectFiveTopViewClickListener() {
			
			@Override
			public void TouchPTPlayMethod(int position) {
				
			}
			
			@Override
			public void TouchDTPlayMethod(int position) {
				
			}
			
			@Override
			public void ElevenSelectFiveOmission() {
				
			}
			
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		this.checkedId=checkedId;
		onCheckAction(checkedId);
	}

	@Override
	public String isTouzhu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getZhuShu() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getZhuma() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getZhuma(Balls ball) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckAction(int checkedId) {
		switch (checkedId) {
		case 0:
			if(playMethodTag==1){
				createViewPT(checkedId);
			}else if (playMethodTag==2) {
				createViewDT(checkedId);
			}
			break;
		default:
			break;
		}
	}
	
	private void createViewPT(int checkedId) {
		areaNums = new AreaNum[1];
		areaNums[0] = new AreaNum(cqArea, 1, 11, BallResId, 0, 1,Color.RED, "","", false, true, false);
		createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_HEZHI, true,checkedId, true);
	}

	private void createViewDT(int checkedId) {

	}

}
