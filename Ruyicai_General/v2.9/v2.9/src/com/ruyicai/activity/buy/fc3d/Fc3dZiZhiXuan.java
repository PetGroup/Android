/**
 * 
 */
package com.ruyicai.activity.buy.fc3d;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.fc3d.Fc3dZiZhiXuanCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 *
 */
public class Fc3dZiZhiXuan extends ZixuanActivity implements BuyImplement{
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[] = new AreaInfo[3];// 3��ѡ��
	private Fc3dZiZhiXuanCode fc3dCode = new Fc3dZiZhiXuanCode();
	BallTable baiBallTable ;
	BallTable shiBallTable ;
	BallTable geBallTable ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initArea();
		createView(areaInfos, fc3dCode,this,false);
		LinearLayout thirdLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_third);
		thirdLinear.setVisibility(LinearLayout.VISIBLE);
		baiBallTable = areaNums[0].table;
		shiBallTable = areaNums[1].table;
		geBallTable = areaNums[2].table;
	}
	/**
	 * ��ʼ��ѡ��
	 */
	public void initArea() {
        String baiTitle = getResources().getString(R.string.fc3d_text_bai_title).toString();
        String shiTitle = getResources().getString(R.string.fc3d_text_shi_title).toString();
        String geTitle = getResources().getString(R.string.fc3d_text_ge_title).toString();

		areaInfos[0] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,baiTitle);
		areaInfos[1] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,shiTitle);
		areaInfos[2] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,geTitle);
	}
	/**
	 * �ж��Ƿ�����Ͷע����
	 */
	public void isTouzhu() {
		// TODO Auto-generated method stub
		int baiweiNums = baiBallTable.getHighlightBallNums();
        int shiweiNums = shiBallTable.getHighlightBallNums();
        int geweiNums = geBallTable.getHighlightBallNums();
        int[] baiweis = baiBallTable.getHighlightBallNOs();
        int[] shiweis = shiBallTable.getHighlightBallNOs();
        int[] geweis = geBallTable.getHighlightBallNOs();
        String baiweistr = "";
        String shiweistr = "";
        String geweistr = "";
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
       if (baiweiNums < 1 || shiweiNums < 1 || geweiNums < 1) {
	       alertInfo("���ڰ�λ��ʮλ����λ������ѡ��һ��С�����Ͷע");
        } else {
	    int iZhuShu = getZhuShu();
       if (iZhuShu / iProgressBeishu > 600) {
		      dialogZhixuan();
	    } else if (iZhuShu * 2 > 100000) {
		      dialogExcessive();
	    } else {
	    	 setZhuShu(iZhuShu);
		     alert("ע�룺" + "\n" + "��λ��" + baiweistr + "\n"
				+ "ʮλ��" + shiweistr + "\n" + "��λ��"
				+ geweistr);
	          }
         }
		
	}

	 /**
	    * ���С����ʾ���
	    * @param areaNum
	    * @param iProgressBeishu
	    * @return
	  */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		String mTextSumMoney="";
		// TODO Auto-generated method stub
		if (baiBallTable.getHighlightBallNums() >0 && shiBallTable.getHighlightBallNums() >0&& geBallTable.getHighlightBallNums() >0)  {
			int iZhuShu = getZhuShu() ;
			mTextSumMoney = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
		} else if(baiBallTable.getHighlightBallNums()==0){
			mTextSumMoney = "���ٻ���Ҫ1����λС��";
		} else if(shiBallTable.getHighlightBallNums()==0){
			mTextSumMoney = "���ٻ���Ҫ1��ʮλС��";
		} else if(geBallTable.getHighlightBallNums()==0){
			mTextSumMoney = "���ٻ���Ҫ1����λС��";
		}
		return mTextSumMoney;
	}
	/**
	 * ��ȡע��
	 */
	public int getZhuShu(){
		int iReturnValue = 0;
			iReturnValue = baiBallTable.getHighlightBallNums()* shiBallTable.getHighlightBallNums()* geBallTable.getHighlightBallNums();
		return iReturnValue * iProgressBeishu;
		
	}

	 /**
	  * Ͷע����
	  */
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_FC3D);
	}
}
