package com.ruyicai.activity.buy.qxc;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.qxc.QXCZiZhiXuanCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;

public class QXCZhiXuan extends ZixuanActivity implements BuyImplement{
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[] = new AreaInfo[7];//���ǲʸ�ѡ��
	private QXCZiZhiXuanCode qxcCode = new QXCZiZhiXuanCode();
	BallTable firstlineBallTable ;
	BallTable secondlineBallTable ;
	BallTable thirdlineBallTable ;
	BallTable fourthlineBallTable ;
	BallTable fifthlineBallTable ;
	BallTable sixthlineBallTable ;
	BallTable seventhlineBallTable;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initArea();
		createView(areaInfos, qxcCode,this);
		LinearLayout sevenLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_seven);
		LinearLayout sixLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_six);
		sixLinear.setVisibility(LinearLayout.VISIBLE);
		sevenLinear.setVisibility(LinearLayout.VISIBLE);
		LinearLayout fiveLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_five);
		fiveLinear.setVisibility(LinearLayout.VISIBLE);
		LinearLayout fourLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_four);
		fourLinear.setVisibility(LinearLayout.VISIBLE);
		LinearLayout thirdLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_third);
		thirdLinear.setVisibility(LinearLayout.VISIBLE);
		firstlineBallTable = areaNums[0].table;
		secondlineBallTable = areaNums[1].table;
		thirdlineBallTable = areaNums[2].table;
		fourthlineBallTable = areaNums[3].table;
		fifthlineBallTable = areaNums[4].table;
		sixthlineBallTable = areaNums[5].table;
		seventhlineBallTable = areaNums[6].table;
	}
	/**
	 * ��ʼ��ѡ��
	 */
	public void initArea() {
		String firstTitle = getResources().getString(R.string.qxc_first).toString();
        String secondTitle = getResources().getString(R.string.qxc_second).toString();
        String thirdTitle = getResources().getString(R.string.qxc_third).toString();
        String fourthTitle = getResources().getString(R.string.qxc_fourth).toString();
        String fifthTitle = getResources().getString(R.string.qxc_fifth).toString();
        String sixthTitle = getResources().getString(R.string.qxc_sixth).toString();
        String seventhTitle = getResources().getString(R.string.qxc_seventh).toString();

    	areaInfos[0] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,firstTitle);
		areaInfos[1] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,secondTitle);
		areaInfos[2] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,thirdTitle);
		areaInfos[3] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,fourthTitle);
		areaInfos[4] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,fifthTitle);
		areaInfos[5] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,sixthTitle);
		areaInfos[6] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,seventhTitle);
	}
	
	@Override
	public void isTouzhu() {
		// TODO Auto-generated method stub
		int firstweiNums = firstlineBallTable.getHighlightBallNums();
		int secondweiNums = secondlineBallTable.getHighlightBallNums();
		int thirdweiNums = thirdlineBallTable.getHighlightBallNums();
        int fourthweiNums = fourthlineBallTable.getHighlightBallNums();
        int fifthweiNums = fifthlineBallTable.getHighlightBallNums();
        int sixthweiNums = sixthlineBallTable.getHighlightBallNums();
        int seventhweiNums = seventhlineBallTable.getHighlightBallNums();
        
        int[] firstweis = firstlineBallTable.getHighlightBallNOs();
        int[] secondweis = secondlineBallTable.getHighlightBallNOs();
        int[] thirdweis = thirdlineBallTable.getHighlightBallNOs();
        int[] fourthweis = fourthlineBallTable.getHighlightBallNOs();
        int[] fifthweis = fifthlineBallTable.getHighlightBallNOs();
        int[] sixthweis = sixthlineBallTable.getHighlightBallNOs();
        int[] seventhweis = seventhlineBallTable.getHighlightBallNOs();
        
        String firstweistr = "";
        String secondweistr = "";
        String thirdweistr = "";
        String fourthweistr = "";
        String fifthweistr = "";
        String sixthweistr = "";
        String seventhweistr = "";
        
        for (int i = 0; i < firstweiNums; i++) {
        	firstweistr += (firstweis[i]) + ",";
	        if (i == firstweiNums - 1) {
	        	firstweistr = firstweistr.substring(0, firstweistr.length() - 1);
	        } 
         }
        for (int i = 0; i < secondweiNums; i++) {
	        secondweistr += (secondweis[i]) + ",";
	        if (i == secondweiNums - 1) {
	        	secondweistr = secondweistr.substring(0,secondweistr.length() - 1);
	        } 
         }
        for (int i = 0; i < thirdweiNums; i++) {
        	thirdweistr += (thirdweis[i]) + ",";
	        if (i == thirdweiNums - 1) {
	        	thirdweistr = thirdweistr.substring(0, thirdweistr.length() - 1);
	        } 
         }
        for (int i = 0; i < fourthweiNums; i++) {
        	fourthweistr += (fourthweis[i]) + ",";
	         if (i == fourthweiNums - 1) {
	        	 fourthweistr = fourthweistr.substring(0, fourthweistr.length() - 1);
	        }
         }
        for (int i = 0; i < fifthweiNums; i++) {
        	fifthweistr += (fifthweis[i]) + ",";
	        if (i == fifthweiNums - 1) {
	        	fifthweistr = fifthweistr.substring(0,fifthweistr.length() - 1);
	         }
        }
        for (int i = 0; i < sixthweiNums; i++) {
        	sixthweistr += (sixthweis[i]) + ",";
	        if (i == sixthweiNums - 1) {
	        	sixthweistr = sixthweistr.substring(0,sixthweistr.length() - 1);
	         }
        }
        for (int i = 0; i < seventhweiNums; i++) {
        	seventhweistr += (seventhweis[i]) + ",";
	        if (i == seventhweiNums - 1) {
	        	seventhweistr = seventhweistr.substring(0,seventhweistr.length() - 1);
	         }
        }
       if (firstweiNums < 1||secondweiNums < 1||thirdweiNums < 1 || fourthweiNums < 1 || fifthweiNums < 1||sixthweiNums < 1||seventhweiNums <1) {
	       alertInfo("ÿλ����Ҫѡ��һ��С�򣬼��һ�°� ");
        } else {
	    int iZhuShu = getZhuShu() ;
       if (iZhuShu / iProgressBeishu > 600) {
		      dialogZhixuan();
	    } else if (iZhuShu * 2 > 100000) {
		      dialogExcessive();
	    } else {
		     alert(  "ע����"
				+ iZhuShu / iProgressBeishu + "ע" + "\n"
				+ "������" + iProgressBeishu + "��" + "\n"
				+ "׷�ţ�" + iProgressQishu + "��" + "\n"
				+ "��" + iZhuShu * 2 + "Ԫ" + "\n"
				+ "�����"
				+ (2 * (iProgressQishu - 1) * iZhuShu)
				+ "Ԫ" ,"ע�룺" + "\n" +"��һλ��"+firstweistr+"\n"+"�ڶ�λ��"+secondweistr+"\n"+ "����λ��" + thirdweistr + "\n"
				+ "����λ��" + fourthweistr + "\n" + "����λ��"+ fifthweistr + "\n"+ "����λ��"+ sixthweistr + "\n"+ "����λ��"+ seventhweistr + "\n" );
	          }
         }
		
	}
	/**
	 * ���С����ʾ���
	 */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		String mTextSumMoney="";
		if (firstlineBallTable.getHighlightBallNums() >0 && secondlineBallTable.getHighlightBallNums() >0&& thirdlineBallTable.getHighlightBallNums() >0
				&&fourthlineBallTable.getHighlightBallNums()>0&&fifthlineBallTable.getHighlightBallNums()>0&&sixthlineBallTable.getHighlightBallNums()>0
				&&seventhlineBallTable.getHighlightBallNums()>0)  {
			int iZhuShu = getZhuShu() ;
			mTextSumMoney = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
		}else if(firstlineBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "��һλ��С��Ϊ����";
		}else if(secondlineBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "�ڶ�λ��С��Ϊ����";
		}else if(thirdlineBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "����λ��С��Ϊ����";
		}else if(fourthlineBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "����λ��С��Ϊ����";
		}else if(fifthlineBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "����λ��С��Ϊ����";
		}else if(sixthlineBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "����λ��С��Ϊ����";
		}else if(seventhlineBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "����λ��С��Ϊ����";
		}
		
		return mTextSumMoney;
	}
	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		int zhuShu=getZhuShu();
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_QXC);
		betAndGift.setAmount(""+zhuShu*200);
	}
	/**
	 * ��ȡע��
	 */
	public int getZhuShu(){
		int iReturnValue = 0;
			iReturnValue = firstlineBallTable.getHighlightBallNums()*secondlineBallTable.getHighlightBallNums()*thirdlineBallTable.getHighlightBallNums()
			* fourthlineBallTable.getHighlightBallNums()* fifthlineBallTable.getHighlightBallNums()*sixthlineBallTable.getHighlightBallNums()
			*seventhlineBallTable.getHighlightBallNums();
		return iReturnValue * iProgressBeishu;
		
	}
}
