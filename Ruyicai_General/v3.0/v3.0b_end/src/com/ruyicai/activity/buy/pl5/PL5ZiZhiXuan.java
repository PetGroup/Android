package com.ruyicai.activity.buy.pl5;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.pl5.PL5ZiZhiXuanCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

public class PL5ZiZhiXuan  extends ZixuanActivity implements BuyImplement{
	
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[] = new AreaInfo[5];//����5����5��ѡ��
	private PL5ZiZhiXuanCode pl5Code = new PL5ZiZhiXuanCode();
	BallTable wanBallTable ;
	BallTable qianBallTable ;
	BallTable baiBallTable ;
	BallTable shiBallTable ;
	BallTable geBallTable ;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initArea();
		createView(areaInfos, pl5Code,this,true);
		LinearLayout fiveLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_five);
		fiveLinear.setVisibility(LinearLayout.VISIBLE);
		LinearLayout fourLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_four);
		fourLinear.setVisibility(LinearLayout.VISIBLE);
		LinearLayout thirdLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_third);
		thirdLinear.setVisibility(LinearLayout.VISIBLE);
		wanBallTable = areaNums[0].table;
		qianBallTable = areaNums[1].table;
		baiBallTable = areaNums[2].table;
		shiBallTable = areaNums[3].table;
		geBallTable = areaNums[4].table;
	}
	
	/**
	 * ��ʼ��ѡ��
	 */
	public void initArea() {
		String wanTitle = getResources().getString(R.string.ssc_table_wan).toString();
        String qianTitle = getResources().getString(R.string.ssc_table_qian).toString();
        String baiTitle = getResources().getString(R.string.ssc_table_bai).toString();
        String shiTitle = getResources().getString(R.string.ssc_table_shi).toString();
        String geTitle = getResources().getString(R.string.ssc_table_ge).toString();

    	areaInfos[0] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,wanTitle);
		areaInfos[1] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,qianTitle);
		areaInfos[2] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,baiTitle);
		areaInfos[3] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,shiTitle);
		areaInfos[4] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,geTitle);
	}
	
	@Override
	public void isTouzhu() {
		// TODO Auto-generated method stub
		int wanweiNums = wanBallTable.getHighlightBallNums();
		int qianweiNums = qianBallTable.getHighlightBallNums();
		int baiweiNums = baiBallTable.getHighlightBallNums();
        int shiweiNums = shiBallTable.getHighlightBallNums();
        int geweiNums = geBallTable.getHighlightBallNums();
        
        int[] wanweis = wanBallTable.getHighlightBallNOs();
        int[] qianweis = qianBallTable.getHighlightBallNOs();
        int[] baiweis = baiBallTable.getHighlightBallNOs();
        int[] shiweis = shiBallTable.getHighlightBallNOs();
        int[] geweis = geBallTable.getHighlightBallNOs();
        String wanweistr = "";
        String qianweistr = "";
        String baiweistr = "";
        String shiweistr = "";
        String geweistr = "";
        for (int i = 0; i < wanweiNums; i++) {
	        wanweistr += (wanweis[i]) + ",";
	        if (i == wanweiNums - 1) {
		     wanweistr = wanweistr.substring(0, wanweistr.length() - 1);
	        } 
         }
        for (int i = 0; i < qianweiNums; i++) {
	        qianweistr += (qianweis[i]) + ",";
	        if (i == qianweiNums - 1) {
		     qianweistr = qianweistr.substring(0, qianweistr.length() - 1);
	        } 
         }
        for (int i = 0; i < baiweiNums; i++) {
	        baiweistr += (baiweis[i]) + ",";
	        if (i == baiweiNums - 1) {
		     baiweistr = baiweistr.substring(0, baiweistr.length() - 1);
	        } 
         }
        for (int i = 0; i < shiweiNums; i++) {
	         shiweistr += (shiweis[i]) + ",";
	         if (i == shiweiNums - 1) {
		      shiweistr = shiweistr.substring(0, shiweistr.length() - 1);
	        }
         }
        for (int i = 0; i < geweiNums; i++) {
	         geweistr += (geweis[i]) + ",";
	        if (i == geweiNums - 1) {
		     geweistr = geweistr.substring(0,geweistr.length() - 1);
	         }
        }
       if (wanweiNums < 1||qianweiNums < 1||baiweiNums < 1 || shiweiNums < 1 || geweiNums < 1) {
	       alertInfo("ÿλ����Ҫѡ��һ��С�򣬼��һ�°�");
        } else {
	    int iZhuShu = getZhuShu() ;
       if (iZhuShu / iProgressBeishu > 600) {
		      dialogZhixuan();
	    } else if (iZhuShu * 2 > 100000) {
		      dialogExcessive();
	    } else {
	    	 setZhuShu(iZhuShu);
		     alert("ע�룺" + "\n" +"��λ��"+wanweistr+"\n"+"ǧλ��"+qianweistr+"\n"+ "��λ��" + baiweistr + "\n"
				+ "ʮλ��" + shiweistr + "\n" + "��λ��"+ geweistr );
	          }
         }
		
	}

	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		String mTextSumMoney="";
		if (wanBallTable.getHighlightBallNums() >0 && qianBallTable.getHighlightBallNums() >0&& baiBallTable.getHighlightBallNums() >0
				&&shiBallTable.getHighlightBallNums()>0&&geBallTable.getHighlightBallNums()>0)  {
			int iZhuShu = getZhuShu() ;
			mTextSumMoney = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
		}else if(wanBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "��λ��С��Ϊ����";
		}else if(qianBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "ǧλ��С��Ϊ����";
		}else if(baiBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "��λ��С��Ϊ����";
		}else if(shiBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "ʮλ��С��Ϊ����";
		}else if(geBallTable.getHighlightBallNums() == 0){
			mTextSumMoney = "��λ��С��Ϊ����";
		}
		
		return mTextSumMoney;
	}

	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_PL5);
	}
	/**
	 * ��ȡע��
	 */
	public int getZhuShu(){
		int iReturnValue = 0;
			iReturnValue = wanBallTable.getHighlightBallNums()*qianBallTable.getHighlightBallNums()*baiBallTable.getHighlightBallNums()
			* shiBallTable.getHighlightBallNums()* geBallTable.getHighlightBallNums();
		return iReturnValue * iProgressBeishu;
		
	}
}
