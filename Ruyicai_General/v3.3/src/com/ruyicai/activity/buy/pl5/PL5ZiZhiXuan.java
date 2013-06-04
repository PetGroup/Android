package com.ruyicai.activity.buy.pl5;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.zixuan.BuyViewItem;
import com.ruyicai.activity.buy.zixuan.ZixuanActivity;
import com.ruyicai.code.pl5.PL5ZiZhiXuanCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.PublicMethod;

public class PL5ZiZhiXuan  extends ZixuanActivity {
	
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
		setCode(pl5Code);
		setIsTen(true);
		initViewItem();
		wanBallTable = itemViewArray.get(0).areaNums[0].table;
		qianBallTable = itemViewArray.get(0).areaNums[1].table;
		baiBallTable = itemViewArray.get(0).areaNums[2].table;
		shiBallTable = itemViewArray.get(0).areaNums[3].table;
		geBallTable = itemViewArray.get(0).areaNums[4].table;
	}
	/**
	 * ��ʼ��ѡ������
	 */
	public void initViewItem() {
		// TODO Auto-generated method stub
		   BuyViewItem buyView = new BuyViewItem(this,initArea());
		   itemViewArray.add(buyView);
		   layoutView.addView(buyView.createView());
	}

	/**
	 * ��ʼ��ѡ��
	 */
	public AreaNum[] initArea() {
		AreaNum areaNums[] = new AreaNum[5];
		String wanTitle = getResources().getString(R.string.ssc_table_wan).toString();
        String qianTitle = getResources().getString(R.string.ssc_table_qian).toString();
        String baiTitle = getResources().getString(R.string.ssc_table_bai).toString();
        String shiTitle = getResources().getString(R.string.ssc_table_shi).toString();
        String geTitle = getResources().getString(R.string.ssc_table_ge).toString();

        areaNums[0] = new AreaNum(10, 10, 10, ballResId, 0, 0,Color.RED,wanTitle);
        areaNums[1] = new AreaNum(10, 10, 10, ballResId, 0, 0,Color.RED,qianTitle);
        areaNums[2] = new AreaNum(10, 10, 10, ballResId, 0, 0,Color.RED,baiTitle);
        areaNums[3] = new AreaNum(10, 10, 10, ballResId, 0, 0,Color.RED,shiTitle);
        areaNums[4] = new AreaNum(10, 10, 10, ballResId, 0, 0,Color.RED,geTitle);
        return areaNums;
	}
	
	@Override
	public String isTouzhu() {
        String isTouzhu = "";
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
	       isTouzhu = "ÿλ����Ҫѡ��һ��С�򣬼��һ�°�";
        } else {
	    int iZhuShu = getZhuShu() ;
	    	 isTouzhu = "true"; 
         }
		return isTouzhu;
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
	@Override
	public String getZhuma() {
		// TODO Auto-generated method stub
		return null;
	}
}
