package com.ruyicai.data.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.ChampionshipBean;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;

public class GetGYJTeamInfoAsyncTask {
	public static final String WORLDCUP_ISSUE = "2014001";
	public static final String EUROPE_ISSUE = "2014002";
	private static GetGYJTeamInfoAsyncTask sInstance;
//	private String[] issueArray = null;
	private String championsType = "1";
	private String worldCupType = "2";
	private ProgressDialog progressdialog;
	private String command = "jingCaiZq";
	private String issue = "gyjIssue";
	private String against= "gyjDuiZhen";
	private Context context;
	private Handler handler;
//	private String[] endTime = null;
	private Map<String, String> infoMap = null;
	
	public GetGYJTeamInfoAsyncTask(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	public static GetGYJTeamInfoAsyncTask getInstance(Context context, Handler handler) {
		if (sInstance == null) {
			sInstance = new GetGYJTeamInfoAsyncTask(context, handler);
		} else {
			sInstance.context = context;
			sInstance.handler = handler;
		}
		return sInstance;
	}
	
	/**
	 * 获取欧冠杯数据
	 */
	public void getEuropeInfo() {
		new GetIssueAsyncTask().execute(1);
	}
	
	/**
	 * 获取世界杯数据
	 */
	public void getworldCupInfo() {
		if (infoMap == null) {
			new GetIssueAsyncTask().execute(0);
		} else {
			if (infoMap.containsKey(WORLDCUP_ISSUE)) {
				progressdialog = PublicMethod.creageProgressDialog(context);
				new GetTeamAgainstAsyncTask().execute(WORLDCUP_ISSUE, worldCupType);
			}
		}
//		if (issueArray == null) {
//			new GetIssueAsyncTask().execute(0);
//		} else {
//			progressdialog = PublicMethod.creageProgressDialog(context);
//			new GetTeamAgainstAsyncTask().execute(issueArray[0], worldCupType);
//		}
	}
	
//	public String[] getEndTime() {
//		return endTime;
//	}
	
//	public String getIssue(int index) {
//		if (issueArray != null && issueArray.length >index) {
//			if (index == 0) {
//				return issueArray[1];
//			} else {
//				return issueArray[0];
//			}
//		}
//		return "";
//	}
	
	public Map<String, String> getInfoMap() {
		return infoMap;
	}


	/**
	 * 获取期号异步任务类
	 */
	private class GetIssueAsyncTask extends AsyncTask<Integer, String, String> {
		private int index = 0;
		@Override
		protected void onPreExecute() {
			progressdialog = PublicMethod.creageProgressDialog(context);
		}
		
		@Override
		protected String doInBackground(Integer... params) {
			try {
				index  = params[0];
				JSONObject jsonProtocol = ProtocolManager.getInstance()
						.getDefaultJsonProtocol();
				jsonProtocol.put(ProtocolManager.COMMAND, command);
				jsonProtocol.put(ProtocolManager.REQUESTTYPE, issue);
				jsonProtocol.put(ProtocolManager.LOTNO, Constants.LOTNO_JCZQ_GJ);
				return InternetUtils.GetMethodOpenHttpConnectSecurity(
						Constants.LOT_SERVER, jsonProtocol.toString());
			} catch (JSONException e) {
				e.printStackTrace();
				PublicMethod.closeProgressDialog(progressdialog);
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			if (!TextUtils.isEmpty(result)) {
				try {
					ReturnBean resultBean = JsonUtils.resultData(result, ReturnBean.class);
					if (resultBean != null) {
						if ("0000".equals(resultBean.getError_code())) {
							JSONArray jsonArray = new JSONArray(resultBean.getResult());
//							issueArray = new String[jsonArray.length()];
//							endTime = new String[jsonArray.length()];
							infoMap = new HashMap<String, String>();
							for (int i = 0; i < jsonArray.length(); i++) {
								String jsonStr = jsonArray.getString(i);
								String issue = JsonUtils.readjsonString("batchCode", jsonStr);
								String endTiem = JsonUtils.readjsonString("endtime", jsonStr);
								infoMap.put(issue, endTiem);
//								issueArray[i] = JsonUtils.readjsonString("batchCode", jsonStr);
//								endTime[i] = JsonUtils.readjsonString("endtime", jsonStr);
							}
							if (index == 0) {
								if (infoMap.containsKey(WORLDCUP_ISSUE)) {
									new GetTeamAgainstAsyncTask().execute(WORLDCUP_ISSUE, worldCupType);
								}
							} else {
								if (infoMap.containsKey(EUROPE_ISSUE)) {
									new GetTeamAgainstAsyncTask().execute(EUROPE_ISSUE, championsType);
								}
							}
						} else {
							Toast.makeText(context, resultBean.getMessage(), Toast.LENGTH_SHORT).show();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			PublicMethod.closeProgressDialog(progressdialog);
		}
	}
	
	/**
	 * 获取对阵异步任务类
	 */
	private class GetTeamAgainstAsyncTask extends AsyncTask<String, String, String> {
		private String type = "";
		@Override
		protected String doInBackground(String... params) {
			try {
				type = params[1];
				JSONObject jsonProtocol = ProtocolManager.getInstance()
						.getDefaultJsonProtocol();
				jsonProtocol.put(ProtocolManager.COMMAND, command);
				jsonProtocol.put(ProtocolManager.REQUESTTYPE, against);
				jsonProtocol.put(ProtocolManager.LOTNO, Constants.LOTNO_JCZQ_GJ);
				jsonProtocol.put(ProtocolManager.BATCHCODE, params[0]);
				return InternetUtils.GetMethodOpenHttpConnectSecurity(
						Constants.LOT_SERVER, jsonProtocol.toString());
			} catch (JSONException e) {
				e.printStackTrace();
				PublicMethod.closeProgressDialog(progressdialog);
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			if (!TextUtils.isEmpty(result)) {
				try {
					ReturnBean resultBean = JsonUtils.resultData(result, ReturnBean.class);
					if (resultBean != null) {
						if ("0000".equals(resultBean.getError_code())) {
							List<ChampionshipBean> list = JsonUtils.getList(resultBean.getResult(), ChampionshipBean.class);
							Message msg = Message.obtain(handler);
							msg.obj = list;
							if (championsType.equals(type)) {
								msg.what = 0;
							} else {
								msg.what = 1;
							}
							handler.sendMessage(msg);
						} else {
							Toast.makeText(context, resultBean.getMessage(), Toast.LENGTH_SHORT).show();;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			PublicMethod.closeProgressDialog(progressdialog);
		}
	}
}
