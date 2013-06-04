/**
 * 
 */
package com.ruyicai.activity.more;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.interfaces.ReturnPage;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

/**
 * �������
 * @author Administrator
 *
 */
public class MoreActivity extends Activity implements ReturnPage,HandlerMsg{
	private List<Map<String, Object>> list;/* �б�������������Դ */
	private TextView text;
	private static final String IICON = "IICON";
	private final static String TITLE = "TITLE"; /* ���� */
	private LuckChoose luckChoose ;//����ѡ��
	private HelpCenter helpCenter;//��������
	private SystemSet  systemSet;//ϵͳ����
	private FeedBack  feedBack ;//�û�����
//	private CompanyInfo  companyInfo = new CompanyInfo(this);//��˾���
	private Context context;
	private ProgressDialog progressdialog;
	private RelativeLayout relativeLayout;
	public static int iQuitFlag = 0; // �����˳�
	MyHandler handler = new MyHandler(this);//�Զ���handler
	String textStr;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		initView();
	}
	/**
	 * ��ʼ�����б����
	 */
	private void initView(){
//		luckChoose = new LuckChoose(this);//����ѡ��
//		helpCenter = new HelpCenter(this);//��������
		systemSet = new SystemSet(this);//ϵͳ����
//		feedBack = new FeedBack(this);//�û�����
	}
	
	/**
	 *  �����ࡱѡ���б�
	 */
	public  void showMoreListView() {
		iQuitFlag = 0;//��ǰ������
		setContentView(R.layout.ruyihelper_listview);
		relativeLayout = (RelativeLayout) findViewById(R.id.ruyihelper_listview_relative);
		relativeLayout.setVisibility(RelativeLayout.GONE);
		// ����Դ
		list = getListForMoreAdapter();

		ListView listview = (ListView) findViewById(R.id.ruyihelper_listview_ruyihelper_id);

		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,R.layout.ruyihelper_listview_icon_item, new String[] {
						TITLE, IICON }, new int[] { R.id.ruyihelper_icon_text,R.id.ruyihelper_iicon });

		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);

		/* �б�ĵ����ı��� */
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				textStr = text.getText().toString();
				relativeLayout.setVisibility(RelativeLayout.VISIBLE);
				iQuitFlag = 10;//��ǰ��������һ��
				onClickListener(textStr);
			}

		};
		listview.setOnItemClickListener(clickListener);

	}
	/**
	 * �б���ʵ�ַ���
	 * @param str
	 */
	public void onClickListener(String str){
		/* ����ѡ�� */
		if (getString(R.string.xingyunxuanhao).equals(str)) {
			switchView(luckChoose.showView());
		}
		/* �������� */
		if (getString(R.string.bangzhuzhongxin).equals(str)) {
			switchView(helpCenter.showView());
		}
		/* ϵͳ���� */
		if (getString(R.string.xitongshezhi).equals(str)) {
			switchView(systemSet.showView());
		}
		/* �û����� */
		if (getString(R.string.yonghufankui).equals(str)) {
	        switchView(feedBack.showView());
		}
		/* ��˾���� */
		if (getString(R.string.gongsijianjie).equals(str)) {
//            switchView(companyInfo.showView());
		}
	}

	/**
	 *  ��á����ࡱ�б�������������Դ
	 * @return
	 */
	protected List<Map<String, Object>> getListForMoreAdapter() {

		String[] titles = {
				getString(R.string.xingyunxuanhao),
				getString(R.string.yonghuzhongxin),
				getString(R.string.bangzhuzhongxin),
				getString(R.string.xitongshezhi),
				getString(R.string.yonghufankui),
				getString(R.string.gongsijianjie) };
		int it = R.drawable.xiangyou;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles[i]);
			map.put(IICON, it);

			list.add(map);

		}

		return list;
	}
    /**
     * �л�view
     * 
     */
	public void switchView(View view){
		setContentView(view);
	}

	/**
	 *  �������ӿ�
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			return progressdialog;
		}
		}
		return null;
	}
	/**
	 * ��õ�ǰ����context
	 */
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
    /**
     * ���ص���ǰ����
     */
	public void returnMain() {
		// TODO Auto-generated method stub
		showMoreListView();
	}
    /**
     *  ȡ���������ӿ�
     */
	public void dismissDialog() {
		// TODO Auto-generated method stub
		progressdialog.dismiss();
	}
	 /**
     *  ��ʾ�������ӿ�
     */
	public void showDialog() {
		// TODO Auto-generated method stub
		showDialog(0);
	}
    /**
     * �����봦����
     */
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		
	}

	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("Constants.MEMUTYPE", Constants.MEMUTYPE+"");
		if(Constants.MEMUTYPE==0){
			showMoreListView();
		}else if(Constants.MEMUTYPE==1){
//			switchView(userCenter.showView());	
		}else if(Constants.MEMUTYPE==2){
		    switchView(feedBack.showView());
		}else if(Constants.MEMUTYPE==3){
			switchView(helpCenter.showView());
		}else if(Constants.MEMUTYPE==4){
//		switchView(companyInfo.showView());
		}else if(Constants.MEMUTYPE==5){
		switchView(luckChoose.showView());
		}
		
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#errorCode_000000()
	 */
	@Override
	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ����һ��activity���ص�ǰactivityִ�еķ���
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	Log.e("MoreActivity===", "resultCode=="+resultCode);
	Log.e("MoreActivity===", "RESULT_OK=="+RESULT_OK);
       switch(resultCode){
       case RESULT_OK:
//           userCenter.isUserCenterDetail();
    	   break;
       }
	}
    /**
     * ��д�Żؽ�
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e("MoreActivity===", "keyCode=="+keyCode);
		// TODO Auto-generated method stub

		switch (keyCode) {
		   case 4:
	    	   switch(iQuitFlag){
	    	   case 0://��ǰ����������
	    		   ExitDialogFactory.createExitDialog(this);
	    		   break;
	     	   case 10://��ǰ������������һ��
	     		  showMoreListView();//����������
	     		   break;
	     	   case 20://��ǰ����������һ��
	     		  switchView(helpCenter.showView());
	     		  break;
	     	   case 30://��ǰ�û�������һ��
//	     		  switchView(userCenter.showView());
	    		   break;
	    	   
	    	   }
			  break;
		}
		return false;
	}
}
