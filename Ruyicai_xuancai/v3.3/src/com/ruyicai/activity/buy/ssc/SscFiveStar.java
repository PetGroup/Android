package com.ruyicai.activity.buy.ssc;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.code.ssc.FiveStarCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.SscBalls;
import com.ruyicai.json.miss.SscMissJson;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;

public class SscFiveStar extends ZixuanAndJiXuan  {
	public boolean isjixuan=false;
	public int FIVESTARTYPE=0;
    public static int SSC_TYPE=4;//����
    public static SscFiveStar self;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lotnoStr = Constants.LOTNO_SSC;
		childtype = new String []{"ֱѡ","ֱѡ��ѡ","����ͨѡ"};
		setContentView(R.layout.sscbuyview);
		sscCode = new FiveStarCode();
		self =this;
		highttype = "SSC";
		
	}
	public void theMethodYouWantToCall(){
        // do what ever you want here
		init();
    }
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		onCheckAction(checkedId);
		isMissNet(new SscMissJson(),sellWay,false);//��ȡ��©ֵ
   }   
	public void onCheckAction(int checkedId){
		switch(checkedId){		
		case 0:
			radioId = 0;
			isjixuan=false;
			FIVESTARTYPE=Constants.SSC_FIVESTAR_ZHIXUAN;
			BallTable qiantable;
			BallTable baitable;
			BallTable shitable;
			BallTable getable;
			iProgressBeishu = 1;iProgressQishu = 1;
			String wantitle = "��λ����";
			String qiantitle = "ǧλ����";
			String baititle = "��λ����";
			String shititle = "ʮλ����" ;
			String getitle = "��λ����";
			AreaNum[] areaNums= new AreaNum[5];
			areaNums[0] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, wantitle);
			areaNums[1] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, qiantitle);
			areaNums[2] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, baititle);
			areaNums[3] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, shititle);
			areaNums[4] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, getitle);
			createView(areaNums, sscCode,FIVE,true,checkedId);
			BallTable = areaNums[0].table;
			qiantable = areaNums[1].table;
			baitable = areaNums[2].table;
			shitable = areaNums[3].table;
		    getable = areaNums[4].table;
		break;
		case 1:
			radioId = 1;
			isjixuan=true;
			iProgressBeishu = 1;iProgressQishu = 1;
			 SscBalls sscb = new SscBalls(5);
			 createviewmechine(sscb,checkedId);
		break;	
		case 2:
			radioId = 2;
			isjixuan=false;
			FIVESTARTYPE=Constants.SSC_FIVESTAR_TONGXUAN;
			BallTable qiantablet;
			BallTable baitablet;
			BallTable shitablet;
			BallTable getablet;
			iProgressBeishu = 1;iProgressQishu = 1;
			String wantitlet = "��λ��";
			String qiantitlet = "ǧλ��";
			String baititlet = "��λ��";
			String shititlet = "ʮλ��" ;
			String getitlet = "��λ��";
			areaNums= new AreaNum[5];
			areaNums[0] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, wantitlet);
			areaNums[1] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, qiantitlet);
			areaNums[2] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, baititlet);
			areaNums[3] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, shititlet);
			areaNums[4] = new AreaNum(10,10, 9, BallResId, 0, 0,Color.RED, getitlet);
			createView(areaNums, sscCode,FIVE_TONGXUAN,true,checkedId);
			BallTable = areaNums[0].table;
			qiantable = areaNums[1].table;
			baitable = areaNums[2].table;
			shitable = areaNums[3].table;
		    getable = areaNums[4].table;
	}
	}
	 protected void onResume() {
		    // TODO Auto-generated method stub
		    super.onResume();
			lotnoStr = Constants.LOTNO_SSC;
            
	}

	 public String getZxAlertZhuma() {
		int iZhuShu = getZhuShu();
		int[] wan = areaNums[0].table.getHighlightBallNOs();
		int[] qian =areaNums[1].table.getHighlightBallNOs();
		int[] bai = areaNums[2].table.getHighlightBallNOs();
		int[] shi = areaNums[3].table.getHighlightBallNOs();
		int[] ge =  areaNums[4].table.getHighlightBallNOs();

		return  "ע�룺" + "\n" + 
				 "��λ��"
				+ getStrZhuMa(wan) + "\n" + "ǧλ��" + getStrZhuMa(qian) + "\n"
				+ "��λ��" + getStrZhuMa(bai) + "\n" + "ʮλ��" + getStrZhuMa(shi)
				+ "\n" + "��λ��" + getStrZhuMa(ge);

	}
	  public String getZhuma(){
	 	   String zhuma="";
	 	   zhuma =   sscCode.zhuma(areaNums, iProgressBeishu, FIVESTARTYPE);
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
			int beishu = iProgressBeishu;
			iReturnValue=balls.size()*beishu;
		}else{
		int wan = areaNums[0].table.getHighlightBallNums();
	    int qian = areaNums[1].table.getHighlightBallNums();
		int bai = areaNums[2].table.getHighlightBallNums();
		int shi = areaNums[3].table.getHighlightBallNums();
		int ge = areaNums[4].table.getHighlightBallNums();
		int beishu = iProgressBeishu;
		iReturnValue = wan * qian * bai * shi * ge * beishu;}
		return iReturnValue;
	}

	@Override
	public String isTouzhu() {
		String isTouzhu = "";
		if(isjixuan){
			setZhuShu(balls.size());
			alertJX(); 
		}else{
			int iZhuShu = getZhuShu();
			int wan = areaNums[0].table.getHighlightBallNums();
			int qian = areaNums[1].table.getHighlightBallNums();
		    int bai = areaNums[2].table.getHighlightBallNums();
			int shi = areaNums[3].table.getHighlightBallNums();
		    int ge = areaNums[4].table.getHighlightBallNums();
			if (wan == 0 | qian == 0 | bai == 0 | shi == 0 | ge == 0) {
				isTouzhu = "������ѡ��һע��";
			} else if (iZhuShu * 2 > MAX_ZHU) {
				isTouzhu = "false";
			} else{
				isTouzhu = "true";
			}
		}
		return isTouzhu;
	}
	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
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
