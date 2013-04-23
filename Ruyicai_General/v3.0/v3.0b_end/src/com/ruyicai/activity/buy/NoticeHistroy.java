package com.ruyicai.activity.buy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.notice.PrizeBallLinearLayout;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.net.newtransaction.PrizeInfoInterface;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

public class NoticeHistroy extends Activity {
	public final static String LOTTERYTYPE = "LOTTERYTYPE";
	public final static String WINNINGNUM = "WINNINGNUM";
	public final static String DATE = "DATA";
	public final static String ISSUE = "ISSUE";
	public final static String FINALDATE = "FINALDATE";
	public final static String MONEYSUM = "MONEYSUM";
	public ProgressDialog progress;
    public  float  SCALE  = 1;
    public   int BALL_WIDTH = 46;
	String LotLalel ="22-5";
	LayoutInflater mInflater;
    Button flush, ok;
	TextView noticePrizesTitle;
	ListView listview;
	String[] str = new String[] { LOTTERYTYPE, WINNINGNUM, DATE, ISSUE,FINALDATE, MONEYSUM };
    String lotno =Constants.LOTNO_22_5;		
 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.noticehistroy);
	setScale();
	flush = (Button)findViewById(R.id.alert_dialog_touzhu_button_cancel);
	ok = (Button)findViewById(R.id.alert_dialog_touzhu_button_ok);
	listview = (ListView)findViewById(R.id.noticehistroylist);
	netting();
	flush.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			netting();
		}
	});
	ok.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	
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
			BALL_WIDTH=80;
			SCALE = (float)1.5;
		}
	}
 private void netting(){
		final Handler tHandler = new Handler();
		final ProgressDialog dialog = UserCenterDialog.onCreateDialog(this);
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
		final JSONObject prizemore = PrizeInfoInterface.getInstance().getNoticePrizeInfo(lotno, "1", "10");
		try {
			final String msg = prizemore.getString("message");
			final String code = prizemore.getString("error_code");
			if(code.equals("0000")){
			 final List<Map<String, Object>> list = JsonToString(prizemore);
			 tHandler.post(new Runnable() {			
				@Override
				public void run() {
					// TODO Auto-generated method stub
					dialog.dismiss();
					SubEfficientAdapter adapter = new SubEfficientAdapter(NoticeHistroy.this, str, list);
					listview.setAdapter(adapter);
				}
			});
				
			}else{
				tHandler.post(new Runnable() {
					@Override
					public void run() {
						dialog.dismiss();
						Toast.makeText(NoticeHistroy.this, msg, Toast.LENGTH_SHORT).show();
					}
				 });
			}
		}catch (JSONException e) {
				// TODO: handle exception
		}
	   }

	
	 }).start();
		
	}
 /**
	 * ���б�������
	 */
	public  class SubEfficientAdapter extends BaseAdapter {
		int count = 0;
		private LayoutInflater mInflater; // �������б���
		private List<Map<String, Object>> mList;
		private String[] mIndex;
		private Activity context;
		public SubEfficientAdapter(Activity context, String[] index,
				List<Map<String, Object>> list) {
			this.context = context;
			this.mInflater = LayoutInflater.from(context);
			this.mList = list;
			this.mIndex = index;
			
		}
		@Override
		public int getCount() {
			count = mList.size();
			return count;
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public  final int[] colors = new int[] { 0x3000000, 0x300010ff };

		// �������б����е���ϸ����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			String iGameType = (String) mList.get(position).get(mIndex[0]);
			final String iNumbers = (String) mList.get(position).get(mIndex[1]);
			String iDate = (String) mList.get(position).get(mIndex[2]);
			final String iIssueNo = (String) mList.get(position).get(mIndex[3]);
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.notice_prizes_single_specific_layout, null);
				holder = new ViewHolder();
				holder.numbers = (LinearLayout) convertView.findViewById(R.id.notice_pirzes_single_specific_ball_linearlayout);
				holder.date = (TextView) convertView.findViewById(R.id.notice_prizes_single_specific_noticedDate_id);
				holder.issue = (TextView) convertView.findViewById(R.id.notice_prizes_single_specific_issue_id);
				holder.imgView = (ImageView) convertView.findViewById(R.id.notice_prizes_single_specific_img);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			PrizeBallLinearLayout linear = new PrizeBallLinearLayout(context,BALL_WIDTH);
			linear.Lotname = iGameType;
			holder.imgView.setVisibility(ImageView.INVISIBLE);
			linear.Batchcode = iNumbers;
			linear.linear = holder.numbers;
			linear.removeAllBalls();
			linear.initLinear();
			
			holder.date.setText(iDate);
			holder.issue.setText("��"+iIssueNo+"��");		
			return convertView;
		}

		 class ViewHolder {
			LinearLayout numbers;
			TextView name;
			TextView date;
			TextView issue;
			ImageView imgView;
		}
	}
	private List<Map<String, Object>> JsonToString(JSONObject prizemore)
			throws JSONException {
		List<Map<String,Object>> adpterlist = new ArrayList<Map<String,Object>>();
		JSONArray prizeArray = prizemore.getJSONArray("result");
		
		for (int i = 0; i < prizeArray.length(); i++) {
			JSONObject prizeJson = (JSONObject) prizeArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, LotLalel);
			map.put(WINNINGNUM, prizeJson.getString("winCode"));
			map.put(DATE, "��������:"+prizeJson.getString("openTime"));
			map.put(ISSUE,  prizeJson.getString("batchCode") );
			adpterlist.add(map);
		
		}
		return adpterlist;
	}
	
}
