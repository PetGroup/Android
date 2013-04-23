package com.ruyicai.activity.buy.eleven;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.NoticeHistroy;
import com.ruyicai.activity.buy.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.code.dlc.DlcCode;
import com.ruyicai.code.eleven.ElevenCode;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.DlcRxBalls;
import com.ruyicai.jixuan.ElevenBalls;
import com.ruyicai.util.Constants;

public class Eleven extends Dlc{
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_dlc_main);
		highttype = "DLC";
		setLotno();
		initTop();
		initSpinner();
		initGroup();
		setIssue(lotno);
		setTitleOne(getString(R.string.eleven));
		
	}
	/**
	 * ���ò��ֱ��
	 * @param lotno
	 */
    public void setLotno(){
    	this.lotno = Constants.LOTNO_eleven;
    	lotnoStr = lotno;
    }
	/**
	 * ��ʼ����ѡѡ��
	 */
	public void createViewZx(){
		iProgressBeishu = 1;iProgressQishu = 1;
		sscCode = new ElevenCode();
		initArea();
		createView(areaNums, sscCode,ZixuanAndJiXuan.NULL,false);
	}
	/**
	 * ��ʼ����ѡѡ��
	 */	
	public void createViewJx(){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		ElevenBalls dlcb = new ElevenBalls(num);
		createviewmechine(dlcb);
	}
	   /**
	    * Ͷעע��
	    * @return
	    */
	   public String getZhuma(){
		   String zhuma="";
		    zhuma = ElevenCode.zhuma(areaNums, state);
		   return zhuma;
	   }
		/**
		 * ��ѡͶעע��
		 */
		public String getZhuma(Balls ball) {
			   String zhuma="";
				zhuma = ElevenBalls.getZhuma(ball, state);
			   return zhuma;
		}
	   
		private void initTop(){
			titleOne = (TextView) findViewById(R.id.layout_main_text_title_one);
			issue = (TextView) findViewById(R.id.layout_main_text_issue);
			time = (TextView) findViewById(R.id.layout_main_text_time);
			imgRetrun = (Button) findViewById(R.id.layout_main_img_return);
			imgRetrun.setBackgroundResource(R.drawable.returnselecter);
			imgRetrun.setText("��ʷ����");
			imgRetrun.setVisibility(View.VISIBLE);
	        titleOne.setText(getString(R.string.dlc));
		    //ImageView�ķ����¼�
			imgRetrun.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Eleven.this,NoticeHistroy.class);
					intent.putExtra("lotno", Constants.LOTNO_eleven);
					startActivity(intent);			}
			});		
		}

}
