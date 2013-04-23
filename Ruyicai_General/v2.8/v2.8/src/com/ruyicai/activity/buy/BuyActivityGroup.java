/**
 * 
 */
package com.ruyicai.activity.buy;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.notice.NoticeBlueBallActivity;
import com.ruyicai.activity.notice.NoticeInfoActivity;
import com.ruyicai.activity.notice.NoticeRedBallActivity;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 */
public class BuyActivityGroup extends ActivityGroup {
	protected TabHost mTabHost = null;
	protected LayoutInflater mInflater = null;
	private String[] titles ;
	private String[] topTitles ;
	private Class[] allId ;
	protected TabHost.TabSpec firstSpec = null;
	public TextView title;//����
	protected TextView issue;//�ں�
	protected TextView time;//��ֹʱ��
	private Button imgRetrun;//���ع��ʴ�����ť
	private RelativeLayout relativeLayout;
    
    TabWidget tabWidget = null;
	  
	Field mBottomLeftStrip;  
	Field mBottomRightStrip;  

	

    @Override     
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.buy_main_group);
		mTabHost = (TabHost) findViewById(R.id.tab_host);
		tabWidget = mTabHost.getTabWidget();  
		mTabHost.setup(getLocalActivityManager());
		mInflater = LayoutInflater.from(this);
		initView();
		//����tab�л��¼�
		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			public void onTabChanged(String tabId) {	
				for(int i=0;i<titles.length;i++){
					if(tabId.equals(titles[i])){
						title.setText(topTitles[i]);
					}
				}
			}
		});
	 }    
    /**
     * ��ʼ������
     * 
     */
    public void initInfo(String[] titles,String[] topTitles,Class[] allId ){
    	this.titles = titles;
    	this.topTitles = topTitles;
    	this.allId = allId;
    }
	/**
	 * ��ʼ�����
	 */
	private void initView(){
		relativeLayout =(RelativeLayout) findViewById(R.id.main_buy_relat_issue);
		title = (TextView) findViewById(R.id.layout_main_text_title);
		issue = (TextView) findViewById(R.id.layout_main_text_issue);
		time = (TextView) findViewById(R.id.layout_main_text_time);
		imgRetrun = (Button) findViewById(R.id.layout_main_img_return);
		imgRetrun.setBackgroundResource(R.drawable.returnselecter);
	    //ImageView�ķ����¼�
		imgRetrun.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});		
	}
	/**
	 * �Ƿ������ں�
	 */
	public void isIssue(boolean isVisble){
		if(isVisble){
			relativeLayout.setVisibility(RelativeLayout.INVISIBLE);
		}else{
			relativeLayout.setVisibility(RelativeLayout.GONE);
		}
	}
	/**
	 * ��ֵ����ǰ��
	 * @param type���ֱ��
	 */
	public void setIssue(String type){
		// ��ȡ�ںźͽ�ֹʱ��
		JSONObject ssqLotnoInfo = PublicMethod.getCurrentLotnoBatchCode(type);
		if (ssqLotnoInfo != null) {
			// �ɹ���ȡ�����ں���Ϣ
			try {
				String issueStr = ssqLotnoInfo.getString("batchCode");
				String timeStr = ssqLotnoInfo.getString("endTime");
				issue.setText("��" + issueStr + "��");
				time.setText("��ֹʱ�䣺" + timeStr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			// û�л�ȡ���ں���Ϣ,����������ȡ�ں�
		    PublicMethod.getIssue(type,issue,time,new Handler());
		}
	}
	/**
	 * ��ӱ�ǩ
	 * @param index 
	 * @param id
	 */
	public void addTab(int index){
		View indicatorTab = mInflater.inflate(R.layout.layout_nav_item, null);
		ImageView img = (ImageView) indicatorTab.findViewById(R.id.layout_nav_item);
		TextView title = (TextView) indicatorTab.findViewById(R.id.layout_nav_icon_title);
		img.setBackgroundResource(R.drawable.tab_buy_selector);
		title.setText(titles[index]);
		firstSpec = mTabHost.newTabSpec(titles[index]).setIndicator(indicatorTab).setContent(new Intent(BuyActivityGroup.this, allId[index]));
		mTabHost.addTab(firstSpec);
	}
	/**
	 * ���õ�ǰҳ
	 * @param index
	 */
	public void setTab(int index){
		mTabHost.setCurrentTab(index);
		title.setText(topTitles[index]);
	}
	/**
	 * ��ʼ�������б�
	 * @param titles   �����б���   e.g. {"����͸��������","����͸��������","����͸��������"};
	 * @param topTitles  �������б��� e.g. {"��������","��������","��������"};
	 * @param allId   ��ID  e.g.{NoticeRedBallActivity.class,NoticeBlueBallActivity.class,NoticeInfoActivity.class}
	 */
    public void init(String titles[],String topTitles[],Class allId[]){
    	this.titles = titles;
    	this.topTitles = topTitles;
    	this.allId = allId;
    	for(int i=0;i<titles.length;i++){
    		addTab(i);
    	}
    }  
    public void removeTabs(){
    	mTabHost.clearAllTabs();
    }
}

