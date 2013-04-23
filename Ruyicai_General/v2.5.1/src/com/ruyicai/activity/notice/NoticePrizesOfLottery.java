package com.ruyicai.activity.notice;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.dialog.MyDialogListener;
import com.ruyicai.net.newtransaction.NoticeInterface;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

/**
 * ��������
 * @author haojie
 *
 */
public class NoticePrizesOfLottery extends Activity implements MyDialogListener {

	public static final String TAG = "NoticePrizesOfLottery";
	
	public final static String LOTTERYTYPE = "LOTTERYTYPE";
	public final static String WINNINGNUM = "WINNINGNUM";
	public final static String DATE = "DATA";
	public final static String ISSUE = "ISSUE";
	public final static String FINALDATE = "FINALDATE";
	public final static String MONEYSUM = "MONEYSUM";
	public final static int a = R.drawable.kaijiang_lotterytype;
//	public static final int[] drawables = new int[] { a, 0, a , 0, a, 0, a, 0, a, 0 };

	public final static int ID_MAINLISTVIEW = 1;
    public static float  SCALE  = 1;

	// ������ӣ���������͸

	int redBallViewNum = 7;
	int redBallViewWidth = 22;

	public static  int BALL_WIDTH = 46;

	TextView text_lotteryName; // ��Ʊ�����TextView
	List<Map<String, Object>> list, list_ssq, list_fc3d, list_qlc, list_pl3,list_cjdlt; // zlm 8.9 �������������������͸
	static BallTable kaiJiangGongGaoBallTable = null;
	static LinearLayout iV;
	static String strChuanZhi;
	private static int[] aRedColorResId = { R.drawable.red };
	private static int[] aBlueColorResId = { R.drawable.blue };
	// ������ 7.5 �����޸ģ���Ӵ���
	private boolean iQuitFlag = true;
	// ������
	private ProgressDialog progressdialog;
	private static final int DIALOG1_KEY = 0;
	private static final Integer[] mIcon = { R.drawable.join_ssq,R.drawable.join_fc3d, R.drawable.join_qlc, R.drawable.join_pl3,
		                                     R.drawable.join_dlt ,R.drawable.join_ssc,R.drawable.join_11x5,R.drawable.join_sfc
		                                     ,R.drawable.join_rx9,R.drawable.join_6cb,R.drawable.join_jqc}; // zlm 8.9 �������������������͸ͼ��
	private static final String[] titles = {"˫ɫ��","����3D","���ֲ�","������","����͸","ʱʱ��","11ѡ5","ʤ����","��ѡ��","������","�����"};

	/**
	 * ��Ϣ������
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				showListView(ID_MAINLISTVIEW);
				break;
			}

		}
	};

	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_prizes_main);
		int screenWith=PublicMethod.getDisplayWidth(this);
		if(screenWith<=240){
			BALL_WIDTH=46*120/240;
			SCALE = (float)140/240;
		}else if(screenWith>240&&screenWith<=320){
			BALL_WIDTH=46*160/240;
			SCALE = (float)180/240;
		}else if(screenWith>=480){
			BALL_WIDTH=46;
			SCALE = 1;
		}
	}

	/**
	 * ����ȡ���Ŀ�����Ϣ�ŵ���������
	 */
	public void analysisLotteryNoticeJsonObject(JSONObject jobject){
		
		
		//˫ɫ����Ϣ��ȡ
		try {
		    JSONArray ssqArray = jobject.getJSONArray("ssq");
		    if(ssqArray!=null&&ssqArray.length()>0){
				Constants.ssqNoticeList.clear();
				for (int i = 0; i < ssqArray.length(); i++) {
					JSONObject _ssq = (JSONObject) ssqArray.get(i);
					Constants.ssqNoticeList.add(_ssq);
				}
			}
		}catch(Exception e){
			//��ȡ˫ɫ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.ssqNoticeList==null||Constants.ssqNoticeList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00000000000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.ssqNoticeList.add(tempObj);
			}
		}
		try {
			JSONArray fc3dArray = jobject.getJSONArray("ddd");
			if(fc3dArray!=null&&fc3dArray.length()>0){
				Constants.fc3dList.clear();
				for (int i = 0; i < fc3dArray.length(); i++) {
					JSONObject _fc3d = (JSONObject) fc3dArray.get(i);
					Constants.fc3dList.add(_fc3d);
				}
			}
		}catch(Exception e){
			//��ȡ����3D���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.fc3dList==null||Constants.fc3dList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.fc3dList.add(tempObj);
			}
		}
		
		try {
			JSONArray qlcArray = jobject.getJSONArray("qlc");
			if(qlcArray!=null&&qlcArray.length()>0){
				Constants.qlcList.clear();
				for (int i = 0; i < qlcArray.length(); i++) {
					JSONObject _qlc = (JSONObject) qlcArray.get(i);
					Constants.qlcList.add(_qlc);
				}
			}
		}catch(Exception e){
			//��ȡ���ֲ����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.qlcList==null||Constants.qlcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "0000000000000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.qlcList.add(tempObj);
			}
		}
		
		try {
			JSONArray pl3Array = jobject.getJSONArray("pl3");
			if(pl3Array!=null&&pl3Array.length()>0){
			Constants.pl3List.clear();
				for (int i = 0; i < pl3Array.length(); i++) {
					JSONObject _pl3 = (JSONObject) pl3Array.get(i);
					Constants.pl3List.add(_pl3);
				}
			}
		}catch(Exception e){
			//��ȡ���������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.pl3List==null||Constants.pl3List.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.pl3List.add(tempObj);
			}
		}
		
		try {
			JSONArray dltArray = jobject.getJSONArray("dlt");
			if(dltArray!=null&&dltArray.length()>0){
				Constants.dltList.clear();
				for (int i = 0; i < dltArray.length(); i++) {
					JSONObject _dlt = (JSONObject) dltArray.get(i);
					Constants.dltList.add(_dlt);
				}
			}
		}catch(Exception e){
			//��ȡ����͸���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.dltList==null||Constants.dltList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00 00 00 00 00+00 0000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.dltList.add(tempObj);
			}
		}
		
		try {
			JSONArray sscArray = jobject.getJSONArray("ssc");
			if(sscArray!=null&&sscArray.length()>0){
				Constants.sscList.clear();
				for (int i = 0; i < sscArray.length(); i++) {
					JSONObject _ssc = (JSONObject) sscArray.get(i);
					Constants.sscList.add(_ssc);
				}
			}
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.sscList==null||Constants.sscList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "0000000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.sscList.add(tempObj);
			}
		}
		
		try {
			JSONArray dlcArray = jobject.getJSONArray("11-5");
			if(dlcArray!=null&&dlcArray.length()>0){
				Constants.dlcList.clear();
				for (int i = 0; i < dlcArray.length(); i++) {
					JSONObject _dlc = (JSONObject) dlcArray.get(i);
					Constants.dlcList.add(_dlc);
				}
			}
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.dlcList==null||Constants.dlcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "00000");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.dlcList.add(tempObj);
			}
		}
		
		try {
			JSONArray sfcArray = jobject.getJSONArray("sfc");
			if(sfcArray!=null&&sfcArray.length()>0){
				Constants.sfcList.clear();
				for (int i = 0; i < sfcArray.length(); i++) {
					JSONObject _sfc = (JSONObject) sfcArray.get(i);
					Constants.sfcList.add(_sfc);
				}
			}
		}catch(Exception e){
			//��ȡʤ�������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.sfcList==null||Constants.sfcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.sfcList.add(tempObj);
			}
		}
		
		try {
			JSONArray rx9Array = jobject.getJSONArray("rx9");
			if(rx9Array!=null&&rx9Array.length()>0){
				Constants.rx9List.clear();
				for (int i = 0; i < rx9Array.length(); i++) {
					JSONObject _rx9 = (JSONObject) rx9Array.get(i);
					Constants.rx9List.add(_rx9);
				}
			}
		}catch(Exception e){
			//��ȡ��ѡ9���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.rx9List==null||Constants.rx9List.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.rx9List.add(tempObj);
			}
		}
			
		try {
			JSONArray half6Array = jobject.getJSONArray("6cb");
			if(half6Array!=null&&half6Array.length()>0){
				Constants.half6List.clear();
				for (int i = 0; i < half6Array.length(); i++) {
					JSONObject _6cb = (JSONObject) half6Array.get(i);
					Constants.half6List.add(_6cb);
				}
			}
		}catch(Exception e){
			//��ȡ6�������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.half6List==null||Constants.half6List.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.half6List.add(tempObj);
			}
		}
		
		try {
			JSONArray jqcArray = jobject.getJSONArray("jqc");
			if(jqcArray!=null&&jqcArray.length()>0){
				Constants.jqcList.clear();
				for (int i = 0; i < jqcArray.length(); i++) {
					JSONObject _jqc = (JSONObject)jqcArray.get(i);
					Constants.jqcList.add(_jqc);
				}
			}
		}catch(Exception e){
			//��ȡ��������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.jqcList==null||Constants.jqcList.size()==0){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put("lotno", "");
						tempObj.put("winno", "");
						tempObj.put("date", "");
					} catch (JSONException e) {
						
					}
				}
				Constants.jqcList.add(tempObj);
			}
		}
		
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
	//	Constants.RUYIHELPERSHOWLISTTYPE=0;
		Constants.MEMUTYPE = 0;
		showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
		new Thread(new Runnable() {
			@Override
			public void run() {
				//�жϻ���ʱ��,�����������ʱ��,������������
				if(Constants.lastNoticeTime == 0||
				  (System.currentTimeMillis()-Constants.lastNoticeTime)/1000 > Constants.NOTICE_CACHE_TIME_SECOND){
					//���״�����,���߻��泬ʱ,������ȡ����
					Constants.lastNoticeTime = System.currentTimeMillis();
					JSONObject lotteryInfos = NoticeInterface.getInstance().getLotteryAllNotice();//������Ϣjson����
					//����ȡ���Ŀ�����Ϣ�ŵ���������
					analysisLotteryNoticeJsonObject(lotteryInfos);
				}else{
					//��������������
				}
				
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
			
		}).start();
	}

	/**
	 * �������������б������б�֮�����ת
	 * @param listViewID
	 *            �б�ID
	 */
	private void showListView(int listViewID) {
		iQuitFlag = false; 
		switch (listViewID) {
		case ID_MAINLISTVIEW:
			iQuitFlag = true; 
			showMainListView();
			break;

		}
	}

	/**
	 * �˳����
	 * @param keyCode      ���ذ����ĺ���
	 * @param event        �¼�
	 * @return
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case 4: {
				if (iQuitFlag == false) {
					setContentView(R.layout.notice_prizes_main);
					showListView(ID_MAINLISTVIEW);
				} else {
					ExitDialogFactory.createExitDialog(this);
				}
				break;
			}
		}
		return false;
	}

	// ������ 7.5 �����޸ģ�����˳����
	/**
	 * ȡ��
	 */
	public void onCancelClick() {
		// //iCallOnKeyDownFlag=false;
	}

	// ������ 7.5 �����޸ģ�����˳����
	/**
	 * ȷ��
	 */
	public void onOkClick(int[] nums) {
		// �˳�
		this.finish();
	}

	/**
	 * ���б�
	 */
	private void showMainListView() {
		setContentView(R.layout.notice_prizes_main);

		ListView listview = (ListView) findViewById(R.id.notice_prizes_listview);
		list = NoticeDataProvider.getListForMainListViewSimpleAdapter();//��ȡ������Ϣ����

		String[] str = new String[] { LOTTERYTYPE, WINNINGNUM, DATE, ISSUE };
		MainEfficientAdapter adapter = new MainEfficientAdapter(this, str, list);
		listview.setDividerHeight(0);
		listview.setAdapter(adapter);
		// ���õ������
		OnItemClickListener clickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// �������3D��ת������3D���б���
				if (titles[position].equals("����3D")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_FUCAI3D_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ������ֲ���ת�����ֲ����б���
				if (titles[position].equals("���ֲ�")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_QILECAI_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���˫ɫ����ת��˫ɫ�����б���
				if (titles[position].equals("˫ɫ��")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_SHUANGSEQIU_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// �����������ת�����������б���
				if (titles[position].equals("������")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_PAILIESAN_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// �����������͸��ת����������͸���б���
				if (titles[position].equals("����͸")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_DLT_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת��ʱʱ�����б���
				if (titles[position].equals("ʱʱ��")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_SHISHICAI_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���11-5��ת��11-5���б���
				if (titles[position].equals("11ѡ5")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_DLC_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת��ʤ�������б���
				if (titles[position].equals("ʤ����")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_SFC_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת����ѡ�Ų����б���
				if (titles[position].equals("��ѡ��")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_RXJ_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת�����������б���
				if (titles[position].equals("������")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_LCB_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת����������б���
				if (titles[position].equals("�����")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_JQC_LISTVIEW;
					Intent intent = new Intent(NoticePrizesOfLottery.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
			}

		};

		listview.setOnItemClickListener(clickListener);
	}



	

	// ���б�������
	public static class MainEfficientAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
		private List<Map<String, Object>> mList;
		private String[] mIndex;
		private Context context;

		public MainEfficientAdapter(Context context, String[] index,
				List<Map<String, Object>> list) {
			mInflater = LayoutInflater.from(context);
			mList = list;
			mIndex = index;
			this.context = context;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// �������б����е���ϸ����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String iGameType = (String) mList.get(position).get(mIndex[0]);
			String iNumbers = (String) mList.get(position).get(mIndex[1]);
			String iDate = (String) mList.get(position).get(mIndex[2]);
			String iIssueNo = (String) mList.get(position).get(mIndex[3]);
			ViewHolder holder = null;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.notice_prizes_main_layout, null);

				holder = new ViewHolder();
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);
				holder.name = (TextView) convertView.findViewById(R.id.notice_prizes_main_title);
				holder.numbers = (LinearLayout) convertView.findViewById(R.id.ball_linearlayout);
				holder.date = (TextView) convertView.findViewById(R.id.notice_prizes_dateAndTimeId);
				holder.issue = (TextView) convertView.findViewById(R.id.notice_prizes_issueId);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				holder.numbers.removeAllViews();

			}
			if (iGameType.equalsIgnoreCase("ssq")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);

				// zlm 7.28 �����޸ģ���Ӻ�������
				int i1, i2, i3;
				String iShowNumber;
				OneBallView tempBallView;
				int[] ssqInt01 = new int[6];
				int[] ssqInt02 = new int[6];
				String[] ssqStr = new String[6];

				for (i2 = 0; i2 < 6; i2++) {
					iShowNumber = iNumbers.substring(i2 * 2, i2 * 2 + 2);
					ssqInt01[i2] = Integer.valueOf(iShowNumber);
				}

				ssqInt02 = PublicMethod.sort(ssqInt01);

				for (i3 = 0; i3 < 6; i3++) {
					if (ssqInt02[i3] < 10) {
						ssqStr[i3] = "0" + ssqInt02[i3];
					} else {
						ssqStr[i3] = "" + ssqInt02[i3];
					}
				}
				for (i1 = 0; i1 < 6; i1++) {
					// iShowNumber = iNumbers.substring(i1 * 2, i1 * 2 + 2);
					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, ssqStr[i1],aRedColorResId);
					tempBallView.setScaleType(ScaleType.CENTER_INSIDE);
					holder.numbers.addView(tempBallView);
				}

				iShowNumber = iNumbers.substring(12, 14);
				tempBallView = new OneBallView(convertView.getContext(),1);
				tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, iShowNumber,aBlueColorResId);

				holder.numbers.addView(tempBallView);
			} else if (iGameType.equalsIgnoreCase("fc3d")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				int i1;
				// zlm 7.30 �����޸ģ��޸ĸ���3D����
				int iShowNumber;
				OneBallView tempBallView;
				for (i1 = 0; i1 < 3; i1++) {
					iShowNumber = Integer.valueOf(iNumbers.substring(i1 * 2,
							i1 * 2 + 2));
					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
							+ iShowNumber, aRedColorResId);
					holder.numbers.addView(tempBallView);
				}
			} else if (iGameType.equalsIgnoreCase("qlc")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
	        	// zlm 7.28 �����޸ģ���Ӻ�������
				int i1, i2, i3;
				String iShowNumber;
				OneBallView tempBallView;

				int[] ssqInt01 = new int[7];
				int[] ssqInt02 = new int[7];
				String[] ssqStr = new String[7];

				for (i2 = 0; i2 < 7; i2++) {
					iShowNumber = iNumbers.substring(i2 * 2, i2 * 2 + 2);
					ssqInt01[i2] = Integer.valueOf(iShowNumber);
				}

				ssqInt02 = PublicMethod.sort(ssqInt01);

				for (i3 = 0; i3 < 7; i3++) {
					if (ssqInt02[i3] < 10) {
						ssqStr[i3] = "0" + ssqInt02[i3];
					} else {
						ssqStr[i3] = "" + ssqInt02[i3];
					}
				}
				for (i1 = 0; i1 < 7; i1++) {

					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, ssqStr[i1],
							aRedColorResId);
					holder.numbers.addView(tempBallView);
				}
				// zlm 8.3 �����޸� ��������ֲ�����
				iShowNumber = iNumbers.substring(14, 16);
				tempBallView = new OneBallView(convertView.getContext(),1);
				tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, iShowNumber,
						aBlueColorResId);

				holder.numbers.addView(tempBallView);
			} else if (iGameType.equalsIgnoreCase("pl3")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				int i1;
				int iShowNumber;
				OneBallView tempBallView;
				for (i1 = 0; i1 < 3; i1++) {
					iShowNumber = Integer.valueOf(iNumbers.substring(i1, i1 + 1));
					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""+ iShowNumber, aRedColorResId);
					holder.numbers.addView(tempBallView);
				}
			} else if (iGameType.equalsIgnoreCase("cjdlt")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				// zlm 7.28 �����޸ģ���Ӻ�������
				int i1, i2, i3;
				String iShowNumber = "";
				OneBallView tempBallView;
				int[] cjdltInt01 = new int[5];
				int[] cjdltInt02 = new int[5];
				int[] cjdltInt03 = new int[2];
				int[] cjdltInt04 = new int[2];
				String[] cjdltStr = new String[5];
				String[] cjdltStr1 = new String[2];

				for (i2 = 0; i2 < 5; i2++) {
					iShowNumber = iNumbers.substring(i2 * 3, i2 * 3 + 2);
					cjdltInt01[i2] = Integer.valueOf(iShowNumber);
				}

				cjdltInt02 = PublicMethod.sort(cjdltInt01);

				for (i3 = 0; i3 < 5; i3++) {
					if (cjdltInt02[i3] < 10) {
						cjdltStr[i3] = "0" + cjdltInt02[i3];
					} else {
						cjdltStr[i3] = "" + cjdltInt02[i3];
					}
				}
				for (i1 = 0; i1 < 5; i1++) {

					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, cjdltStr[i1],
							aRedColorResId);
					holder.numbers.addView(tempBallView);
				}

				for (i2 = 0; i2 < 2; i2++) {
					try {
						iShowNumber = iNumbers.substring(i2 * 3 + 15, i2 * 3 + 17);
					} catch (Exception e) {
						e.printStackTrace();
					}
					cjdltInt03[i2] = Integer.valueOf(iShowNumber);
				}

				cjdltInt04 = PublicMethod.sort(cjdltInt03);

				for (i3 = 0; i3 < 2; i3++) {
					if (cjdltInt04[i3] < 10) {
						cjdltStr1[i3] = "0" + cjdltInt04[i3];
					} else {
						cjdltStr1[i3] = "" + cjdltInt04[i3];
					}
				}

				for (i1 = 0; i1 < 2; i1++) {
					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH,
							cjdltStr1[i1], aBlueColorResId);

					holder.numbers.addView(tempBallView);
				}
			} else if (iGameType.equalsIgnoreCase("ssc")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				int i1;
				int iShowNumber;
				OneBallView tempBallView;
				for (i1 = 0; i1 < 5; i1++) {
					iShowNumber = Integer.valueOf(iNumbers
							.substring(i1, i1 + 1));
					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
							+ iShowNumber, aRedColorResId);
					holder.numbers.addView(tempBallView);

				}
			} else if (iGameType.equalsIgnoreCase("11-5")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				int i1;
				int iShowNumber;
				OneBallView tempBallView;
				for (i1 = 0; i1 < 5; i1++) {
					iShowNumber = Integer.valueOf(iNumbers.substring(i1*2, i1*2 + 2));
					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""+ iShowNumber, aRedColorResId);
					holder.numbers.addView(tempBallView);

				}
			} else if (iGameType.equalsIgnoreCase("sfc")) {
				holder.name.setText(titles[position]);
//				holder.name.setGravity(0);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
			
				TextView tvFootball = new TextView(convertView.getContext());
				tvFootball.setTextColor( R.color.darkgreen);
				tvFootball.setTextSize(25);
				tvFootball.setGravity(Gravity.RIGHT);
				tvFootball.setText(iNumbers);
				holder.numbers.addView(tvFootball);
			} else if (iGameType.equalsIgnoreCase("rxj")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				TextView tvFootball = new TextView(convertView.getContext());
				tvFootball.setTextColor( R.color.darkgreen);
				tvFootball.setTextSize(25);
				tvFootball.setGravity(Gravity.RIGHT);
				tvFootball.setText(iNumbers);
				holder.numbers.addView(tvFootball);
	    	} else if (iGameType.equalsIgnoreCase("lcb")) {
	    		holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				TextView tvFootball = new TextView(convertView.getContext());
				tvFootball.setGravity(Gravity.RIGHT);
				tvFootball.setTextColor( R.color.darkgreen);
				tvFootball.setTextSize(25);
				tvFootball.setText(iNumbers);
				holder.numbers.addView(tvFootball);
			} else if (iGameType.equalsIgnoreCase("jqc")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				TextView tvFootball = new TextView(convertView.getContext());
				tvFootball.setTextColor( R.color.darkgreen);
				tvFootball.setTextSize(25);
				tvFootball.setGravity(Gravity.RIGHT);
				tvFootball.setText(iNumbers);
				holder.numbers.addView(tvFootball);
			}
			
			return convertView;
		}

		static class ViewHolder {
			ImageView icon;
			TextView name;
			LinearLayout numbers;
			TextView date;
			TextView issue;
		}
	}

	

	/**
	 * ����������ʾ��
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG1_KEY: {
				progressdialog = new ProgressDialog(this);
				
				progressdialog.setMessage("����������...");
				progressdialog.setIndeterminate(true);
				progressdialog.setCancelable(true);
				return progressdialog;
			}
		}
		return null;
	}
}
