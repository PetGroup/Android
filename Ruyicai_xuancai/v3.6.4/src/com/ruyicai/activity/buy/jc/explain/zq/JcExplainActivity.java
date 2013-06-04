package com.ruyicai.activity.buy.jc.explain.zq;

import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.activity.buy.jc.score.zq.JcScoreListActivity;
import com.ruyicai.activity.buy.ssq.SsqJiXuan;
import com.ruyicai.activity.buy.ssq.SsqZiDanTuo;
import com.ruyicai.activity.buy.ssq.SsqZiZhiXuan;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.constant.Constants;
import com.ruyicai.net.newtransaction.ExplainInterface;
import com.ruyicai.net.newtransaction.ScoreInfoInterface;
import com.ruyicai.util.PublicMethod;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class JcExplainActivity extends BuyActivityGroup{
    protected String titleStr = "球队数据分析";
	protected String[] titles ={"分析","欧指","亚盘"};
	protected String[] topTitles ={titleStr,titleStr,titleStr,titleStr};
	protected Class[] allId ={ExplainListActivity.class,EuropeActivity.class,AsiaActivity.class};
	protected Handler handler = new Handler();
	protected String type = "dataAnalysis";
	public  static JSONObject jsonObject;
	protected ProgressDialog progressdialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isIssue(false);
		setTitleText();
		getExplainNet(getIntentInfo(),type);
	}
	public void setTitleText(){
		title.setText(titleStr);
	}
	/**
	 * 得到当前页面的下标
	 */
	public String getIntentInfo(){
		Intent intent = getIntent();
		return intent.getStringExtra("event");
	}
	/**
	 * 获取玩法介绍联网
	 */
	public void getExplainNet(final String event,final String type){
		progressdialog = UserCenterDialog.onCreateDialog(context);
		progressdialog.show();
		// 加入是否改变切入点判断 陈晨 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				str = ExplainInterface.getExplain(event,type);
				try {
					jsonObject = new JSONObject(str);		
					final String msg = jsonObject.getString("message");
					String error = jsonObject.getString("error_code");
					if(error.equals("0000")){
						handler.post(new Runnable() {
							public void run() {
								init(titles, topTitles, allId);
								progressdialog.dismiss();
							}
						});
					}else{
						handler.post(new Runnable() {
							public void run() {
								// TODO Auto-generated method stub
								progressdialog.dismiss();
								Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
							}
						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
					progressdialog.dismiss();
				}
			}

		});
		t.start();
	}
	/**
	 * 初始化组件
	 */
	public void initView(){
		relativeLayout =(RelativeLayout) findViewById(R.id.main_buy_relat_issue);
		title = (TextView) findViewById(R.id.layout_main_text_title);
		imgIcon = (Button) findViewById(R.id.layout_main_img_return);
		imgIcon.setBackgroundResource(R.drawable.returnselecter);
		imgIcon.setText("返回");
		imgIcon.setWidth(PublicMethod.getPxInt(70, context));
		imgIcon.setVisibility(View.VISIBLE);
		// ImageView的返回事件
		imgIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	public boolean getIsLuck(){
		return true;
	}
}
