package com.ruyicai.activity.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.notice.NoticeInfoActivity.SubEfficientAdapter;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.pojo.NoticeJcInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeJcActivity extends Activity implements HandlerMsg{
	private String msg;
	private JSONObject jsonObj;
	ProgressDialog progressdialog;
	MyHandler handler = new MyHandler(this);//�Զ���handler
	
	public void onCreate(Bundle savedInstanceState) {
		// RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notice_prizes_single_specific_main);
		initView();
		if(Constants.noticeJc.equals("")){
			notiecJcNet();
		}else{
			try {
				showJcListView(new JSONObject(Constants.noticeJc));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	
	}
	public void initView(){
		// ���ñ���
		RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.notice_prizes_single_specific_main_relative01);
		rLayout.setVisibility(RelativeLayout.VISIBLE);
		TextView noticePrizesTitle = (TextView) findViewById(R.id.notice_prizes_single_specific_title_id);
		noticePrizesTitle.setText(R.string.jingcai_kaijianggonggao);//˫ɫ�򿪽�����
		noticePrizesTitle.setTextSize(20);
		// �������б�
		Button reBtn;
		reBtn = (Button) findViewById(R.id.notice_prizes_single_specific_main_returnID);
		reBtn.setBackgroundResource(R.drawable.returnselecter);
		reBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                finish();
			}
		});
	}
	
	/**
	 * ������Ϣ����
	 */
	public void notiecJcNet(){
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				str = NoticeJcInfo.getInstance().getLotteryAllNotice();
				Log.e("str======",str);
				try {
					jsonObj = new JSONObject(str);		
					msg = jsonObj.getString("message");
					String error = jsonObj.getString("error_code");
					handler.handleMsg(error,msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				progressdialog.dismiss();
			}

		});
		t.start();
	}
	/**
	 * �������б�view
	 */
	private void showJcListView(JSONObject jsonObj) {
		List<JcInfo> list_jc = getSubInfoForListView(jsonObj);//��ȡ�����е����� 
		//��ʼ���б�
		ListView listview = (ListView) findViewById(R.id.notice_prizes_single_specific_listview);
		JcInfoAdapter adapter = new JcInfoAdapter(this, list_jc);
		listview.setDividerHeight(0);
		listview.setAdapter(adapter);

	}
	/**
	 * ����������ʾ��
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			// progressdialog.setTitle("Indeterminate");
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			progressdialog.setCancelable(true);
			return progressdialog;
		}
		}
		return null;
	}

	/**
	 * ���ʵ�������
	 */
	public class JcInfoAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
		private List<JcInfo> mList;

		public JcInfoAdapter(Context context, List<JcInfo> list) {
			mInflater = LayoutInflater.from(context);
			mList = list;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		int index;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			index = position;
			final JcInfo info = (JcInfo) mList.get(position);
			convertView = mInflater.inflate(R.layout.notice_jc_listview_item,null);
			final ViewHolder holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_time);
			holder.team = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_team);
			holder.home = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_team_name1);
			holder.away = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_team_name2);
			holder.letPoint = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_team_score);
			holder.timeEnd = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_time_end);
			holder.teamId = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_id);
			holder.result = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_jieguo);
			holder.score = (TextView) convertView.findViewById(R.id.jc_main_list_item_text_score);
			convertView.setTag(holder);
			holder.time.append(info.getTime());
			holder.team.append(info.getTeam());
			holder.home.append(info.getHome());
			holder.away.append(info.getAway());
			holder.letPoint.append(info.getLetPoint());
			holder.timeEnd.append(info.getTimeEnd());
			holder.teamId.append(info.getWeek()+info.getTeamId());
			holder.result.append(info.getResult());
			holder.score.append(info.getScore());
			return convertView;
		}
		class ViewHolder {
			TextView time;
			TextView team;
			TextView home;
			TextView away;
			TextView letPoint;
			TextView timeEnd;
			TextView teamId;
			TextView result;
			TextView score;
		}
	}
	/**
	 * ���б�����Ӧ������
	 */
	protected  List<JcInfo> getSubInfoForListView(JSONObject jsonObj) {
			ArrayList<JcInfo> list = new ArrayList<JcInfo>();
			try {
				JSONArray jsonArray = jsonObj.getJSONArray("result");
				for (int i = 0; i < jsonArray.length(); i++) {
					JcInfo itemInfo = new JcInfo();
					JSONObject jsonItem = jsonArray.getJSONObject(i);
					itemInfo.setTime(jsonItem.getString("dayForamt"));
					itemInfo.setWeek(jsonItem.getString("week"));
					itemInfo.setTeamId(jsonItem.getString("teamId"));
					itemInfo.setTeam(jsonItem.getString("league"));
					itemInfo.setResult(jsonItem.getString("result"));
					itemInfo.setScore(jsonItem.getString("score"));
					itemInfo.setTimeEnd(jsonItem.getString("time"));
					itemInfo.setLetPoint(jsonItem.getString("letPoint"));
					String teams[] = jsonItem.getString("team").split(":");
					itemInfo.setHome(teams[0]);
					itemInfo.setAway(teams[1]);
					list.add(itemInfo);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		return list;
     }
  	class JcInfo{
  		String time = "";
		String day = "";
		String team = "";
		String home = "";
		String away = "";
		String score = "";
		String timeEnd = "";
		String week = "";
		String teamId = "";
		String letPoint = "";
		String result = "";

		public JcInfo() {

		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getTeamId() {
			return teamId;
		}
		public void setTeamId(String teamId) {
			this.teamId = teamId;
		}
		public String getLetPoint() {
			return letPoint;
		}
		public void setLetPoint(String letPoint) {
			this.letPoint = letPoint;
		}
		public String getWeek() {
			return week;
		}
		public void setWeek(String week) {
			this.week = week;
		}
		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}


		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getTeam() {
			return team;
		}

		public void setTeam(String team) {
			this.team = team;
		}

		public String getHome() {
			return home;
		}

		public void setHome(String home) {
			this.home = home;
		}

		public String getAway() {
			return away;
		}

		public void setAway(String away) {
			this.away = away;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getTimeEnd() {
			return timeEnd;
		}

		public void setTimeEnd(String timeEnd) {
			this.timeEnd = timeEnd;
		}

	}
	@Override
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		Constants.noticeJc = jsonObj.toString();
		showJcListView(jsonObj);
	}
	@Override
	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
}
