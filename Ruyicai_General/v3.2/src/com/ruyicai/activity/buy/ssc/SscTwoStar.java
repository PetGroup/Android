package com.ruyicai.activity.buy.ssc;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanAndJiXuan;
import com.ruyicai.code.ssc.TwoStarCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.MissConstant;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.SscBalls;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.PublicMethod;

public class SscTwoStar extends ZixuanAndJiXuan {
	    public static final int SSC_TYPE =2;
	    public static int TWOSTARTYPE=0;
	    public boolean isjixuan=false;
        public static SscTwoStar self;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lotnoStr = Constants.LOTNO_SSC;
		childtype = new String []{"ֱѡ","ֱѡ��ѡ","��ѡ","��ֵ"};
		setContentView(R.layout.sscbuyview);
        sscCode = new TwoStarCode();
        self = this;
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
		isMissNet();//��ȡ��©ֵ
		
    }      
	public void onCheckAction(int checkedId){
		switch(checkedId){		
		case 0:
			radioId = 0;
			sellWay = MissConstant.SSC_5X_ZX;
			isjixuan=false;
			TWOSTARTYPE=Constants.SSC_TWOSTAR_ZHIXUAN;
			BallTable getable;
			iProgressBeishu = 1;iProgressQishu = 1;
			String shititle = "ʮλ����" ;
			String getitle = "��λ����";
			AreaNum[] areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(10,10, 6, BallResId, 0, 0,Color.RED, shititle);
			areaNums[1] = new AreaNum(10,10, 6, BallResId, 0, 0,Color.RED, getitle);
			createView(areaNums, sscCode,TWO,true);
			BallTable=areaNums[0].table;
		    getable = areaNums[1].table;
		    missList.clear();
		   
		break;
		case 1:
			radioId = 1;
			isjixuan=true;
			iProgressBeishu = 1;iProgressQishu = 1;
			SscBalls  sscb = new SscBalls(2);
			createviewmechine(sscb);
		break;	
		case 2:
			radioId = 2;
			sellWay = MissConstant.SSC_MV_2ZX;
			isjixuan=false;
			TWOSTARTYPE=Constants.SSC_TWOSTAR_ZUXUAN;
			iProgressBeishu = 1;iProgressQishu = 1;
			areaNums= new AreaNum[1];
	        String  titlezu = "��ѡ��Ͷע����";
	        areaNums[0] = new AreaNum(10,10, 7, BallResId, 0, 0,Color.RED, titlezu);
			createView(areaNums, sscCode,TWO_ZUXUAN,true);
			BallTable=areaNums[0].table;
			missList.clear();
		break;
		case 3:
			radioId = 3;
			sellWay = MissConstant.SSC_MV_2ZXHZ;
			isjixuan=false;
			TWOSTARTYPE=Constants.SSC_TWOSTAR_HEZHI;
			iProgressBeishu = 1;iProgressQishu = 1;
	        String  titlehe = "��ѡ��Ͷע����";
	        areaNums= new AreaNum[1];
	        areaNums[0] = new AreaNum(19,10, 8, BallResId, 0, 0,Color.RED, titlehe);
			createView(areaNums, sscCode,TWO_HEZHI,true);
			BallTable=areaNums[0].table;
			missList.clear();
        break;
	  }
	}
	
	    public int getZhuShu() {
		   int iReturnValue = 0;
		   int beishu = iProgressBeishu;
		   if(isjixuan){
			  iReturnValue =balls.size()*beishu; 
		   }else{
		   switch(TWOSTARTYPE){
		   case Constants.SSC_TWOSTAR_ZHIXUAN:
			  int shi = areaNums[0].table.getHighlightBallNums();
			  int ge = areaNums[1].table.getHighlightBallNums();
		      iReturnValue = shi*ge * beishu;
			break;
		   case Constants.SSC_TWOSTAR_ZUXUAN:
			  int one = areaNums[0].table.getHighlightBallNums();
			  iReturnValue = (int) (PublicMethod.zuhe(2, one) * beishu);
			break;
		   case Constants.SSC_TWOSTAR_HEZHI:
			  iReturnValue = getSscHeZhiZhushu() * beishu; 
		   }
		   }
		   return iReturnValue;
		}
	    
	   public String getZhuma(){
	 	   String zhuma="";
	 	   zhuma =   sscCode.zhuma(areaNums, iProgressBeishu, TWOSTARTYPE);
	 	   return zhuma;
	    }
		@Override
		public String getZhuma(Balls ball) {
			   String zhuma="";
			   zhuma = ball.getZhuma(null, SSC_TYPE);   
			   return zhuma;
		}
	   private int getSscHeZhiZhushu() {
			int iZhuShu = 0;
			int[] BallNos = areaNums[0].table.getHighlightBallNOs();// ��ѡ��С��ĺ��루���1�����0������ʵ��ѡ��ļ�ȥ1
			int[] BallNoZhushus={1,1,2,2,3,3,4,4,5,5,5,4,4,3,3,2,2,1,1};
			

			for (int i = 0; i < BallNos.length; i++) {
				for (int j = 0; j < BallNoZhushus.length; j++) {
					if (j == BallNos[i]) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С������1��ʼ���ʼ�ȥ1
						// ɾ������ cc 20100713
						iZhuShu += BallNoZhushus[j];// *iProgressBeishu;
					}
				}
			}
			return iZhuShu;
		}
	@Override
	public String isTouzhu() {
		// TODO Auto-generated method stub
		String isTouzhuStr = "";
			int iZhuShu = getZhuShu();
	        switch (TWOSTARTYPE){
			case Constants.SSC_TWOSTAR_ZUXUAN:
				int one = areaNums[0].table.getHighlightBallNums();
				if (one < 2) {
					isTouzhuStr = "������ѡ��һע��";
				} else if (iZhuShu  > MAX_ZHU) {
					isTouzhuStr = "false";
				} else if (one > 7) {
					isTouzhuStr = "������ѡ��С��ĸ������Ϊ7����";
				} else {
					isTouzhuStr = "true";
				}
				break;
			case Constants.SSC_TWOSTAR_ZHIXUAN:
				int shi = areaNums[0].table.getHighlightBallNums();
				int ge = areaNums[1].table.getHighlightBallNums();
				if (shi == 0 | ge == 0) {
					isTouzhuStr =  "������ѡ��һע��";
				} else if (iZhuShu > MAX_ZHU) {
					isTouzhuStr = "false";
				} else if (shi + ge > 12) {
					isTouzhuStr =  "�����λ��С��ĸ������Ϊ12����";
				} else {
					isTouzhuStr = "true";
				}
				   
				break;

			case Constants.SSC_TWOSTAR_HEZHI:
				int one2 = areaNums[0].table.getHighlightBallNums();
				if (one2 == 0) {
					isTouzhuStr = "������ѡ��һע��";
				} else if (iZhuShu  > MAX_ZHU) {
					isTouzhuStr = "false";
				} else if (one2 > 8) {
					isTouzhuStr = "�����ֵ��С��ĸ������Ϊ8����";
				} else {
					isTouzhuStr = "true";
				}
				break;
	        }
		  return isTouzhuStr;
	}
    
	public String getZxAlertZhuma() {
		int iZhuShu = getZhuShu();
     	switch (TWOSTARTYPE) {
		
		case Constants.SSC_TWOSTAR_ZUXUAN:
			int[] one = areaNums[0].table.getHighlightBallNOs();
			return "ע�룺" + "\n" 
					+ getStrZhuMa(one);
		
  
		case Constants.SSC_TWOSTAR_ZHIXUAN:
			int[] shi = areaNums[0].table.getHighlightBallNOs();
			int[] ge = areaNums[1].table.getHighlightBallNOs();
			return "ע�룺" + "\n" +"ʮλ:"+getStrZhuMa(shi) + "\n" + "��λ:" + getStrZhuMa(ge);

		case Constants.SSC_TWOSTAR_HEZHI:
			int[] one2 = areaNums[0].table.getHighlightBallNOs();
			return "ע�룺" + "\n" + getStrZhuMa(one2);

		default:
			break;
		}
		return null;
	}
	public String getStrZhuMa(int balls[]) {
		String str = "";
		for (int i = 0; i < balls.length; i++) {
			str += (balls[i]);
			if (i != (balls.length - 1))
				str += ",";
		}
		return str;

	}
	
	@Override
	protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	lotnoStr = Constants.LOTNO_SSC;
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