package com.ruyicai.activity.buy.ssc;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanAndJiXuan;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.SscBalls;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.Constants;

public class SscOneStar extends ZixuanAndJiXuan implements BuyImplement{
	    public static SscOneStar self;
	    private boolean isjixuan=false;
	    public static final int SSC_TYPE = 1;//һ��
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		childtype= new String[]{"ֱѡ","��ѡ"};
		setContentView(R.layout.sscbuyview);
		self = this;
		init();
		highttype = "SSC";
	}	
	
	public void theMethodYouWantToCall(){
        // do what ever you want here
		init();
    }

	@Override
	protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	}
	//��ѡ���л�ֱѡ����ѡ
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(checkedId){		
		case 0:
			isjixuan=false;
			iProgressBeishu = 1;iProgressQishu = 1;
			initArea();
			createView(areaInfos, sscCode,this);
			BallTable=areaNums[0].table;
		break;
		case 1:
			isjixuan=true;
			iProgressBeishu = 1;iProgressQishu = 1;
			SscBalls sscb  = new SscBalls(1);
			createviewmechine(sscb,this);
		break;	
		}
			
	}
    @Override
    protected void onResume() {
    // TODO Auto-generated method stub
    super.onResume();
    }

	@Override
	public void isTouzhu() {
		// TODO Auto-generated method stub
		if(isjixuan){
			String sTouzhuAlert = "";
			sTouzhuAlert = getTouzhuAlertJixuan();
			alert(sTouzhuAlert); 
		}else{
			int ge = BallTable.getHighlightBallNums();
			int iZhuShu = getZhuShu();
			if (ge == 0) {
				alertInfo("������ѡ��һע��");
			} else if (iZhuShu * 2 > 20000) {
				dialogExcessive();
			} else if (ge > 5) {
				alertInfo("һ��ֱѡ��С��ĸ������Ϊ5����");
			} else {
				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alert(sTouzhuAlert); 
			}
			
		}
		
		
	}
	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();
		String zhuma_string="";
		int[]  ZhuMa = BallTable.getHighlightBallNOs();
		for (int i = 0; i < BallTable.getHighlightBallNOs().length; i++) {
			    zhuma_string = zhuma_string + String.valueOf(ZhuMa[i]);
			if (i != BallTable.getHighlightBallNOs().length - 1) {
				zhuma_string = zhuma_string + ",";
			}
		}
		return      "��" +Ssc.batchCode + "��\n" 
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
					+"ע�룺" + "\n" + "��λ��" + zhuma_string
					+ "\n" + "ȷ��֧����";
		
	}
		

   private int getZhuShu() {
	int iReturnValue = 0;
	
	if(isjixuan){
	int beishu = mSeekBarBeishu.getProgress();	
	iReturnValue = balls.size()*beishu;
	}else{
	int ge = areaNums[0].table.getHighlightBallNums();
	int beishu = mSeekBarBeishu.getProgress();
    iReturnValue = ge * beishu;
    }
	
	return iReturnValue;
}   
   
   public String getZhuma(){
	   String zhuma="";
	   if(isjixuan){
	    zhuma = SscBalls.getzhuma(balls, SSC_TYPE);   
	   }else{
	   zhuma = sscCode.zhuma(areaNums, iProgressBeishu, 0);
	   }
	   return zhuma;
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
