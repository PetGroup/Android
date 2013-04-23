package com.ruyicai.activity.notice;

import java.util.List;
import java.util.Map;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.net.newtransaction.NoticeWinInterface;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.PublicMethod;

/**
 * ��������
 * @author haojie
 *
 */
public class NoticeMainActivity extends Activity  {

	public static final String TAG = "NoticePrizesOfLottery";
	public final static String LOTTERYTYPE = "LOTTERYTYPE";
	public final static String WINNINGNUM = "WINNINGNUM";
	public final static String DATE = "DATA";
	public final static String ISSUE = "ISSUE";
	public final static String FINALDATE = "FINALDATE";
	public final static String MONEYSUM = "MONEYSUM";
	public final static String WINCODE = "winCode";
	public final static String OPENTIME = "openTime";
	public final static String BATCHCODE = "batchCode";
	public final static int a = R.drawable.kaijiang_lotterytype;

	public final static int ID_MAINLISTVIEW = 1;
    public static float  SCALE  = 1;
	// ������ӣ���������͸
	int redBallViewNum = 7;
	int redBallViewWidth = 22;
	public static  int BALL_WIDTH = 46;
	TextView text_lotteryName; // ��Ʊ�����TextView
	List<Map<String, Object>> list; // zlm 8.9 �������������������͸
	static BallTable kaiJiangGongGaoBallTable = null;
	static LinearLayout iV;
	static String strChuanZhi;
	private static int[] aRedColorResId = { R.drawable.red };
	private static int[] aBlueColorResId = { R.drawable.blue };
	// ������ 7.5 �����޸ģ���Ӵ���
	private boolean iQuitFlag = true;
	// ������
	private ProgressDialog progressdialog;
	private static final int DIALOG1_KEY = 0;//
	private static final Integer[] mIcon = { R.drawable.join_ssq,R.drawable.join_fc3d, R.drawable.join_qlc,
											 R.drawable.join_dlt , R.drawable.join_pl3,R.drawable.join_pl5,
											 R.drawable.join_qxc,R.drawable.twenty,R.drawable.join_ssc,R.drawable.join_11x5,
											 R.drawable.join_11ydj,R.drawable.join_sfc,R.drawable.join_rx9,
											 R.drawable.join_6cb,R.drawable.join_jqc,R.drawable.join_jcz,R.drawable.join_jcl}; // zlm 8.9 �������������������͸ͼ��
	private static final String[] titles = {"˫ɫ��","����3D","���ֲ�","����͸","������","������","���ǲ�","22ѡ5","ʱʱ��","11ѡ5","11�˶��","ʤ����","��ѡ��","������","�����","�����","������"};
    public static boolean isFirstNotice = true; 
	/**
	 * ��Ϣ������
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showListView(ID_MAINLISTVIEW);
				progressdialog.dismiss();
				break;
			}

		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_prizes_main);
		setScale();
	}
    /**
     * �����ֻ���Ļ������Ĵ�С��������ű���
     */
	private void setScale() {
		int screenWith=PublicMethod.getDisplayWidth(this);
		if(screenWith<=240){
			BALL_WIDTH=46*120/240;
			SCALE = (float)140/240;
		}else if(screenWith>240&&screenWith<=320){
			BALL_WIDTH=46*160/240;
			SCALE = (float)180/240;
		}else if(screenWith==480){
			BALL_WIDTH=46;
			SCALE = 1;
		}else if(screenWith>480){
			BALL_WIDTH=screenWith/480*BALL_WIDTH;
			SCALE = (float)1.5;
		}
	}

	/**
	 * ����ȡ���Ŀ�����Ϣ�ŵ���������
	 */
	public void analysisLotteryNoticeJsonObject(JSONObject jobject){
		
		
		//˫ɫ����Ϣ��ȡ
		try {
				Constants.ssqJson = jobject.getJSONObject("ssq");
		}catch(Exception e){
			//��ȡ˫ɫ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.ssqJson == null||!jobject.has("ssq")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "00000000000000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.ssqJson = tempObj;
			}
		}
		try {
				Constants.fc3dJson = jobject.getJSONObject("ddd");
		}catch(Exception e){
			//��ȡ����3D���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.fc3dJson == null||!jobject.has("ddd")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "00000000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.fc3dJson = tempObj;
			}
		}
		
		try {
				Constants.qlcJson = jobject.getJSONObject("qlc");
		}catch(Exception e){
			//��ȡ���ֲ����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.qlcJson == null||!jobject.has("qlc")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE,"0000000000000000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.qlcJson = tempObj;
			}
		}
		
		try {
			Constants.pl3Json = jobject.getJSONObject("pl3");
		}catch(Exception e){
			//��ȡ���������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.pl3Json == null||!jobject.has("pl3")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "00000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.pl3Json = tempObj;
			}
		}
		
		try {
				Constants.dltJson = jobject.getJSONObject("dlt");
		}catch(Exception e){
			//��ȡ����͸���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.dltJson == null||!jobject.has("dlt")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "00 00 00 00 00+00 0000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.dltJson = tempObj;
			}
		}
		
		try {
				Constants.sscJson = jobject.getJSONObject("ssc");
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.sscJson == null||!jobject.has("ssc")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "0000000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.sscJson = tempObj;
			}
		}
		
		try {
				Constants.dlcJson = jobject.getJSONObject("11-5");
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.dlcJson==null||!jobject.has("11-5")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "0000000000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.dlcJson = tempObj;
			}
		}
		

		try {
				Constants.ydjJson = jobject.getJSONObject("11-ydj");
		}catch(Exception e){
			//��ȡʵʱ�����ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.ydjJson == null||!jobject.has("11-ydj")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "0000000000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.ydjJson = tempObj;
			}
		}
		
	try {
			Constants.twentyJson = jobject.getJSONObject("22-5");
	}catch(Exception e){
		//��ȡʵʱ�����ݳ����쳣
		e.printStackTrace();
	}finally{
		//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
		if(Constants.twentyJson == null||!jobject.has("22-5")){
			//û����,��ʼ��������
			JSONObject tempObj = new JSONObject();
			for (int i = 0; i < 5; i++) {
				try {
					tempObj.put(BATCHCODE, "");
					tempObj.put(WINCODE, "0000000000");
					tempObj.put(OPENTIME, "");
				} catch (JSONException e) {
					
				}
			}
			Constants.twentyJson = tempObj;
		}
	}
		try {
				Constants.sfcJson = jobject.getJSONObject("sfc");
		}catch(Exception e){
			//��ȡʤ�������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.sfcJson == null||!jobject.has("sfc")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.sfcJson = tempObj;
			}
		}
		
		try {
				Constants.rx9Json = jobject.getJSONObject("rx9");
		}catch(Exception e){
			//��ȡ��ѡ9���ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.rx9Json == null||!jobject.has("rx9")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.rx9Json = tempObj;
			}
		}
			
		try {
				Constants.half6Json = jobject.getJSONObject("6cb");
		}catch(Exception e){
			//��ȡ6�������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.half6Json==null||!jobject.has("6cb")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.half6Json = tempObj;
			}
		}
		
	 try {
				Constants.jqcJson = jobject.getJSONObject("jqc");
		}catch(Exception e){
			//��ȡ��������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.jqcJson == null||!jobject.has("jqc")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.jqcJson = tempObj;
			}
		}
		//������
		try {
				Constants.pl5Json = jobject.getJSONObject("pl5");
		}catch(Exception e){
			//��ȡ��������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.pl5Json == null||!jobject.has("pl5")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "00000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.pl5Json = tempObj;
			}
		}
		//���ǲ�
		try {
				Constants.qxcJson = jobject.getJSONObject("qxc");
		}catch(Exception e){
			//��ȡ��������ݳ����쳣
			e.printStackTrace();
		}finally{
			//�ж��Ƿ��Ѿ��������ϻ�ȡ��������
			if(Constants.qxcJson==null||!jobject.has("qxc")){
				//û����,��ʼ��������
				JSONObject tempObj = new JSONObject();
				for (int i = 0; i < 5; i++) {
					try {
						tempObj.put(BATCHCODE, "");
						tempObj.put(WINCODE, "0000000");
						tempObj.put(OPENTIME, "");
					} catch (JSONException e) {
						
					}
				}
				Constants.qxcJson = tempObj;
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		noticeNet();
	}
	private void noticeNet() {
		showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
		new Thread(new Runnable() {
			@Override
			public void run() {
				//�жϻ���ʱ��,�����������ʱ��,������������
				if(Constants.lastNoticeTime == 0||
				  (System.currentTimeMillis()-Constants.lastNoticeTime)/1000 > Constants.NOTICE_CACHE_TIME_SECOND){
					//���״�����,���߻��泬ʱ,������ȡ����
					Constants.lastNoticeTime = System.currentTimeMillis();
					JSONObject lotteryInfos = NoticeWinInterface.getInstance().getLotteryAllNotice();//������Ϣjson����
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
				String iIssue = (String) list.get(position).get(ISSUE);
				NoticeActivityGroup.ISSUE = iIssue;
				// �������3D��ת������3D���б���
				if (titles[position].equals("����3D")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_FUCAI3D_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ������ֲ���ת�����ֲ����б���
				if (titles[position].equals("���ֲ�")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_QILECAI_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���˫ɫ����ת��˫ɫ�����б���
				if (titles[position].equals("˫ɫ��")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_SHUANGSEQIU_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// �����������ת�����������б���
				if (titles[position].equals("������")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_PAILIESAN_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// �����������͸��ת����������͸���б���
				if (titles[position].equals("����͸")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_DLT_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת��ʱʱ�����б���
				if (titles[position].equals("ʱʱ��")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_SHISHICAI_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���11-5��ת��11-5���б���
				if (titles[position].equals("11ѡ5")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_DLC_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���11-5��ת��11�˶�����б���
				if (titles[position].equals("11�˶��")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_YDJ_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���22ѡ5���б���
				if (titles[position].equals("22ѡ5")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_TWENTY_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת��ʤ�������б���
				if (titles[position].equals("ʤ����")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_SFC_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת����ѡ�Ų����б���
				if (titles[position].equals("��ѡ��")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_RXJ_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת�����������б���
				if (titles[position].equals("������")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_LCB_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת����������б���
				if (titles[position].equals("�����")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_JQC_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת����������б���
				if (titles[position].equals("������")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_PL5_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
				// ���ʱʱ����ת����������б���
				if (titles[position].equals("���ǲ�")) {
					NoticeActivityGroup.LOTNO = NoticeActivityGroup.ID_SUB_QXC_LISTVIEW;
					Intent intent = new Intent(NoticeMainActivity.this, NoticeActivityGroup.class); 
					startActivity(intent);
				}
			}

		};

		listview.setOnItemClickListener(clickListener);
	}



	

	// ���б�������
	public  class MainEfficientAdapter extends BaseAdapter {

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
			final String iIssue = (String) mList.get(position).get(mIndex[3]);
			String iIssueNo = "��"+iIssue+"��";
			ViewHolder holder = null;

			convertView = mInflater.inflate(R.layout.notice_prizes_main_layout, null);

			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.name = (TextView) convertView.findViewById(R.id.notice_prizes_main_title);
			holder.numbers = (LinearLayout) convertView.findViewById(R.id.ball_linearlayout);
			holder.date = (TextView) convertView.findViewById(R.id.notice_prizes_dateAndTimeId);
			holder.issue = (TextView) convertView.findViewById(R.id.notice_prizes_issueId);
			holder.lookBtn = (Button) convertView.findViewById(R.id.notice_prizes_main_btn_jc);
			holder.rLayout = (RelativeLayout) convertView.findViewById(R.id.notice_prizes_relative);
			holder.numbers.removeAllViews();
			convertView.setTag(holder);
		


			
			
			if (iGameType.equals("ssq")) {
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
			} else if (iGameType.equals("fc3d")) {
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
			} else if (iGameType.equals("qlc")) {
				int deletW = 4;
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
					tempBallView.initBall(BALL_WIDTH-deletW, BALL_WIDTH-deletW, ssqStr[i1],
							aRedColorResId);
					holder.numbers.addView(tempBallView);
				}
				// zlm 8.3 �����޸� ��������ֲ�����
				iShowNumber = iNumbers.substring(14, 16);
				tempBallView = new OneBallView(convertView.getContext(),1);
				tempBallView.initBall(BALL_WIDTH-deletW, BALL_WIDTH-deletW, iShowNumber,
						aBlueColorResId);

				holder.numbers.addView(tempBallView);
			} else if (iGameType.equals("cjdlt")) {
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
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, cjdltStr[i1],aRedColorResId);
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
			} else if (iGameType.equals("pl3")) {
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
			}else if (iGameType.equals("pl5")) {
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
					iShowNumber = Integer.valueOf(iNumbers.substring(i1, i1 + 1));
					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""+ iShowNumber, aRedColorResId);
					holder.numbers.addView(tempBallView);
				}
			} else if (iGameType.equals("qxc")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
				holder.date.setText(iDate);
				holder.date.setVisibility(TextView.VISIBLE);
				holder.issue.setText(iIssueNo);
				holder.issue.setVisibility(TextView.VISIBLE);
				int i1;
				int iShowNumber;
				OneBallView tempBallView;
				for (i1 = 0; i1 < 7; i1++) {
					iShowNumber = Integer.valueOf(iNumbers.substring(i1, i1 + 1));
					tempBallView = new OneBallView(convertView.getContext(),1);
					tempBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""+ iShowNumber, aRedColorResId);
					holder.numbers.addView(tempBallView);
				}
			}else if (iGameType.equals("ssc")) {
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
			} else if (iGameType.equals("11-5")) {
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
			}else if (iGameType.equals("11-ydj")) {
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
			}else if (iGameType.equals("22-5")) {
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
			}else if (iGameType.equals("sfc")) {
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
			} else if (iGameType.equals("rxj")) {
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
	    	} else if (iGameType.equals("lcb")) {
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
			} else if (iGameType.equals("jqc")) {
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
			}else if (iGameType.equals("jcz")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
			    holder.rLayout.setVisibility(RelativeLayout.GONE);
				holder.lookBtn.setVisibility(Button.VISIBLE);
				holder.lookBtn.setBackgroundResource(R.drawable.join_info_btn_selecter);
				holder.lookBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context, NoticeJcActivity.class); 
						
						context.startActivity(intent);
					}
				});
			}else if (iGameType.equals("jcl")) {
				holder.name.setText(titles[position]);
				holder.icon.setImageResource(mIcon[position]);
			    holder.rLayout.setVisibility(RelativeLayout.GONE);
				holder.lookBtn.setVisibility(Button.VISIBLE);
				holder.lookBtn.setBackgroundResource(R.drawable.join_info_btn_selecter);
				holder.lookBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context, NoticeJclActivity.class); 
						context.startActivity(intent);
					}
				});
			}
//			convertView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					NoticeActivityGroup.ISSUE = iIssue;
//				}
//			});
			
			return convertView;
		}

		 class ViewHolder {
			ImageView icon;
			TextView name;
			LinearLayout numbers;
			TextView date;
			TextView issue;
			Button lookBtn;
			RelativeLayout rLayout;
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
