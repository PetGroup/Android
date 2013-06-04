package com.ruyicai.activity.buy.ssc;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanAndJiXuan;
import com.ruyicai.code.ssc.ThreeStarCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.SscBalls;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;

public class SscThreeStar extends ZixuanAndJiXuan  {
	public boolean isjixuan=false;
	public static final int SSC_TYPE=3;
	public static SscThreeStar self;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lotnoStr = Constants.LOTNO_SSC;
		childtype = new String []{"ֱѡ","��ѡ"};
		setContentView(R.layout.sscbuyview);
		sscCode = new ThreeStarCode();
		self =this;
		init();
		highttype = "SSC";
	}
	public void theMethodYouWantToCall(){
        // do what ever you want here
		init();
    }
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		super.onCheckedChanged(group, checkedId);
		switch(checkedId){		
		case 0:
			isjixuan=false;
			BallTable shitable;
			BallTable getable;
			iProgressBeishu = 1;iProgressQishu = 1;
			String baititle = "��λ��";
			String shititle = "ʮλ��" ;
			String getitle = "��λ��";
			AreaNum[] areaNums = new AreaNum[3];
			areaNums[0] = new AreaNum(10,10, 8, BallResId, 0, 0,Color.RED, baititle);
			areaNums[1] = new AreaNum(10,10, 8, BallResId, 0, 0,Color.RED, shititle);
			areaNums[2] = new AreaNum(10,10, 8, BallResId, 0, 0,Color.RED, getitle);
			createView(areaNums, sscCode,ZixuanAndJiXuan.THREE,true);
			BallTable = areaNums[0].table;
			shitable = areaNums[1].table;
		    getable = areaNums[2].table;
		break;
		case 1:
			 isjixuan=true;
			 iProgressBeishu = 1;iProgressQishu = 1;
			 SscBalls sscb = new SscBalls(3);
			 createviewmechine(sscb);
		break;	
	}

}    
	  public String getZhuma(){
	 	   String zhuma="";
	 	   zhuma =   sscCode.zhuma(areaNums, iProgressBeishu, 0);
	 	   return zhuma;
	    }
		@Override
		public String getZhuma(Balls ball) {
			   String zhuma="";
			   zhuma = ball.getZhuma(null, SSC_TYPE);   
			   return zhuma;
		}
	  public int getZhuShu() {
		    int iReturnValue = 0;
		    if(isjixuan){
		    	int beishu = mSeekBarBeishu.getProgress();
		    	iReturnValue=balls.size()*beishu;
		    }else{
		    int bai =areaNums[0].table.getHighlightBallNums();
		    int shi =areaNums[1].table.getHighlightBallNums();
			int ge = areaNums[2].table.getHighlightBallNums();
			int beishu = iProgressBeishu;
		    iReturnValue =bai*shi* ge * beishu;
		    }
			return iReturnValue;
		}
	  
	  protected void onResume() {
		    // TODO Auto-generated method stub
		    super.onResume();
		   
		    }

	@Override
	public String isTouzhu() {
		String isTouzhu = "";
		  int bai =areaNums[0].table.getHighlightBallNums();
		  int shi =areaNums[1].table.getHighlightBallNums();
		  int ge = areaNums[2].table.getHighlightBallNums();
		int iZhuShu = getZhuShu();
		if (bai == 0 | shi == 0 | ge == 0) {
			isTouzhu = "������ѡ��һע��";
		} else if (iZhuShu * 2 > 20000) {
			isTouzhu = "false";
		} else if (bai + shi + ge > 24) {
			isTouzhu = "����ֱѡ��С��ĸ������Ϊ24����";
		} else {
			isTouzhu = "true";
		}
		   
		return isTouzhu;
	}
	
	public String getZxAlertZhuma() {
		int iZhuShu = getZhuShu();
		int[] bai = areaNums[0].table.getHighlightBallNOs();
		int[] shi = areaNums[1].table.getHighlightBallNOs();
		int[] ge = areaNums[2].table.getHighlightBallNOs();
	    return  "ע�룺" + "\n" 
					+ "��λ��"
					+ getStrZhuMa(bai) + "\n" + "ʮλ��" + getStrZhuMa(shi) + "\n"
					+ "��λ��" + getStrZhuMa(ge);

	}
	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		String iTempString;
		int iZhuShu = getZhuShu();
		if (iZhuShu != 0) {
		    iTempString = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
		} else {
			iTempString = getResources().getString(R.string.please_choose_number);
		}
	
		return iTempString;
	}
	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		int zhuShu=getZhuShu();
		if(isjixuan){
		betAndGift.setSellway("1")	;
		}else{
		betAndGift.setSellway("0");}//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_SSC);
		betAndGift.setBet_code(getZhuma());
		betAndGift.setAmount(""+zhuShu*200);
	}
}

