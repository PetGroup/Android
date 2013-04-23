package com.ruyicai.activity.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.service.PrizeNotificationService;
import com.ruyicai.util.RWSharedPreferences;

public class OrderPrizeDiaog {
	private  RWSharedPreferences shellRW;
	private Context context;
	
	public OrderPrizeDiaog(RWSharedPreferences shellRW,Context context) {
		// TODO Auto-generated constructor stub
		this.shellRW = shellRW;
		this.context = context;
	}
	
	boolean[] isOrderPrize = new boolean[8];
	/**
	 * ��ȡ��������
	 */
	public void getOrderPrize(){
		try{
		 for(int i=0;i<isOrderPrize.length;i++){
			 isOrderPrize[i] = shellRW.getBooleanValue(Constants.orderPrize[i]);
    	 }
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
	}
	public AlertDialog orderPrizeDialog(){
		getOrderPrize();
		return new AlertDialog.Builder(context)
         .setTitle("��������")
         .setMultiChoiceItems(R.array.lotttery_list,
        		 isOrderPrize,
                 new DialogInterface.OnMultiChoiceClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton,boolean isChecked) {
                         /* User clicked on a check box do some stuff */
                     }
                 })
         .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 /* User clicked Yes so do some stuff */
            	Toast.makeText(context, "�����ɹ�", Toast.LENGTH_SHORT).show();
            	 saveOrderPrize();
            	 startPrizeService();//���ȷ���Ϳ���Service
             }
         }).create();
	}
	/**
	 * ���涩������
	 */
	private void saveOrderPrize(){
		 for(int i=0;i<isOrderPrize.length;i++){
    		 shellRW.putBooleanValue(Constants.orderPrize[i], isOrderPrize[i]);
    	 }
	}

	private void startPrizeService(){
		   Intent prizeIntent = new Intent(context, PrizeNotificationService.class);
		   context.startService(prizeIntent);
	}
}
