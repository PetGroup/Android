package com.ruyicai.activity.buy.ssc;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanAndJiXuan;
import com.ruyicai.code.ssc.TwoStarCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.SscBalls;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

public class SscTwoStar extends ZixuanAndJiXuan implements BuyImplement{
	    public static final int SSC_TYPE =2;
	    public static int TWOSTARTYPE=0;
	    public boolean isjixuan=false;
        public static SscTwoStar self;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		childtype = new String []{"ֱѡ","��ѡ","��ѡ","��ֵ"};
		setContentView(R.layout.sscbuyview);
        sscCode = new TwoStarCode();
        self = this;
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
			TWOSTARTYPE=Constants.SSC_TWOSTAR_ZHIXUAN;
			BallTable getable;
			iProgressBeishu = 1;iProgressQishu = 1;
			String shititle = "ʮλ��" ;
			String getitle = "��λ��";
			areaInfos= new AreaInfo[2];
			areaInfos[0] = new AreaInfo(10, 6, BallResId, 0, 0,Color.RED, shititle);
			areaInfos[1] = new AreaInfo(10, 6, BallResId, 0, 0,Color.RED, getitle);
			createView(areaInfos, sscCode,this);
			BallTable=areaNums[0].table;
		    getable = areaNums[1].table;
		break;
		case 1:
			isjixuan=true;
			iProgressBeishu = 1;iProgressQishu = 1;
			SscBalls  sscb = new SscBalls(2);
			createviewmechine(sscb,this);
		break;	
		case 2:
			isjixuan=false;
			TWOSTARTYPE=Constants.SSC_TWOSTAR_ZUXUAN;
			iProgressBeishu = 1;iProgressQishu = 1;
			areaInfos= new AreaInfo[1];
	        String  titlezu = "��ѡ��Ͷע����";
			areaInfos[0] = new AreaInfo(10, 7, BallResId, 0, 0,Color.RED, titlezu);
			createView(areaInfos, sscCode,this);
			BallTable=areaNums[0].table;
		break;
		case 3:
			isjixuan=false;
			TWOSTARTYPE=Constants.SSC_TWOSTAR_HEZHI;
			iProgressBeishu = 1;iProgressQishu = 1;
	        String  titlehe = "��ѡ��Ͷע����";
	        areaInfos= new AreaInfo[1];
			areaInfos[0] = new AreaInfo(19, 8, BallResId, 0, 0,Color.RED, titlehe);
			createView(areaInfos, sscCode,this);
			BallTable=areaNums[0].table;
        break;
	}

}      
	    protected void onResume() {
		    // TODO Auto-generated method stub
		    super.onResume();
		
		    }
	    private int getZhuShu() {
		   int iReturnValue = 0;
		   int beishu = mSeekBarBeishu.getProgress();
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
	 	   if(isjixuan){
	 	    zhuma = SscBalls.getzhuma(balls, SSC_TYPE);
	 	   }else{
	 	   zhuma =   sscCode.zhuma(areaNums, iProgressBeishu, TWOSTARTYPE);
	 	   }
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
	public void isTouzhu() {
		// TODO Auto-generated method stub
		if(isjixuan){
			String sTouzhuAlert = "";
			sTouzhuAlert = getTouzhuAlertJixuan();
			alertjx(sTouzhuAlert); 
		}else{
			int iZhuShu = getZhuShu();
			boolean isTouzhu = false;
	        switch (TWOSTARTYPE){
			case Constants.SSC_TWOSTAR_ZUXUAN:
				int one = areaNums[0].table.getHighlightBallNums();
				if (one < 2) {
					alertInfo("������ѡ��һע��");
				} else if (iZhuShu * 2 > 20000) {
					dialogExcessive();
				} else if (one > 7) {
					alertInfo("������ѡ��С��ĸ������Ϊ7����");
				} else {
					isTouzhu = true;
				}
				break;
			case Constants.SSC_TWOSTAR_ZHIXUAN:
				int shi = areaNums[0].table.getHighlightBallNums();
				int ge = areaNums[1].table.getHighlightBallNums();
				if (shi == 0 | ge == 0) {
					alertInfo("������ѡ��һע��");
				} else if (iZhuShu * 2 > 20000) {
					dialogExcessive();
				} else if (shi + ge > 12) {
					alertInfo("�����λ��С��ĸ������Ϊ12����");
				} else {
					isTouzhu = true;
				}
				   
				break;

			case Constants.SSC_TWOSTAR_HEZHI:
				int one2 = areaNums[0].table.getHighlightBallNums();
				if (one2 == 0) {
					alertInfo("������ѡ��һע��");
				} else if (iZhuShu * 2 > 20000) {
					dialogExcessive();
				} else if (one2 > 8) {
					alertInfo("�����ֵ��С��ĸ������Ϊ8����");
				} else {
					isTouzhu = true;
				}
				break;
	        }
		    	if (isTouzhu) {

				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alertzx(sTouzhuAlert);
			}
		}
	}
    
	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();
     	switch (TWOSTARTYPE) {
		
		case Constants.SSC_TWOSTAR_ZUXUAN:
			int[] one = areaNums[0].table.getHighlightBallNOs();
			return "��" +Ssc.batchCode+"��\n" 
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
					+"ע�룺" + "\n" 
					+ getStrZhuMa(one)
					+ "\n" + "ȷ��֧����";
		
  
		case Constants.SSC_TWOSTAR_ZHIXUAN:
			int[] shi = areaNums[0].table.getHighlightBallNOs();
			int[] ge = areaNums[1].table.getHighlightBallNOs();
			return 
			"��" +Ssc.batchCode+"��\n" 
			+ "ע����"
			+ iZhuShu / mSeekBarBeishu.getProgress()
			+ "ע"
			+ "\n"
			+ // ע���������ϱ��� �³� 20100713
			"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
			+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
			+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
			+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
			+"ע�룺" + "\n" +"ʮλ:"+getStrZhuMa(shi) + "\n" + "��λ:" + getStrZhuMa(ge)
			+ "\n" + "ȷ��֧����";

		case Constants.SSC_TWOSTAR_HEZHI:
			int[] one2 = areaNums[0].table.getHighlightBallNOs();
			return  "��" +Ssc.batchCode+"��\n" 
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
					+"ע�룺" + "\n" + getStrZhuMa(one2) 
					+ "\n" + "ȷ��֧����";

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