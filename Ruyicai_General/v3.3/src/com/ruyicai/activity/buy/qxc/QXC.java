package com.ruyicai.activity.buy.qxc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.activity.buy.NoticeHistroy;
import com.ruyicai.activity.buy.pl5.PL5;
import com.ruyicai.constant.Constants;

public class QXC extends BuyActivityGroup{
	private String[] titles ={"��ѡ","��ѡ"};
	private String[] topTitles ={"���ǲ�","���ǲ�"};
	private Class[] allId ={QXCZhiXuan.class,QXCJX.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_QXC);
	}
	
	private void initView(){
		relativeLayout =(RelativeLayout) findViewById(R.id.main_buy_relat_issue);
		title = (TextView) findViewById(R.id.layout_main_text_title);
		issue = (TextView) findViewById(R.id.layout_main_text_issue);
		time = (TextView) findViewById(R.id.layout_main_text_time);
		imgRetrun = (Button) findViewById(R.id.layout_main_img_return);
		imgRetrun.setText("��ʷ����");
		imgRetrun.setVisibility(View.VISIBLE);
		imgRetrun.setBackgroundResource(R.drawable.returnselecter);
	    //ImageView�ķ����¼�
		imgRetrun.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(QXC.this,NoticeHistroy.class);
				intent.putExtra("lotno", Constants.LOTNO_QXC);
				startActivity(intent);
			}
		});		
	}
}
