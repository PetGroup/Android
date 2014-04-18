package com.ruyicai.activity.more;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.RuyiGuessActivity;
import com.ruyicai.activity.buy.guess.util.RuyiGuessConstant;
import com.ruyicai.activity.more.lotnoalarm.LotnoAlarmSetActivity;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.json.JsonUtils;

public class ToastSettingActivity extends RoboActivity {

	private RWSharedPreferences mSharedPreferences = null;
	private String mUserNo;
	private ProgressDialog progressdialog;
	private Map<String, String> infoMap = null;
	private Context context;
	// open
	@InjectView(R.id.chbOpenPush)
	private CheckBox openPush;
	@InjectView(R.id.chbOpenSms)
	private CheckBox openSms;

	// GetPrize
	@InjectView(R.id.chbGetPrizePush)
	private CheckBox getPrizePush;
	@InjectView(R.id.chbGetPrizeSms)
	private CheckBox getPrizeSms;

	// ZH
	@InjectView(R.id.chbZhuihaoPush)
	private CheckBox zhuihaoPush;
	@InjectView(R.id.chbZhuihaoSms)
	private CheckBox ZhuihaoSms;

	// Buy
	@InjectView(R.id.chbBuyPush)
	private CheckBox buyPush;

	//
	@InjectView(R.id.programme_settings)
	private RelativeLayout relativeLayout;

	@InjectView(R.id.toastSettingsSave)
	private Button toastSettingsSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// remove title
		setContentView(R.layout.toast_notifier);

		context = ToastSettingActivity.this;

		mSharedPreferences = new RWSharedPreferences(context, "addInfo");

		String sessionId = mSharedPreferences
				.getStringValue(ShellRWConstants.SESSIONID);
		if (!"".equals(sessionId)) {
			mUserNo = mSharedPreferences
					.getStringValue(ShellRWConstants.USERNO);
		}
		SetValues();
		Navigation();
		Save();
	}

	// 读取信息赋值 ----
	private void SetValues() {
		GetToastSettingsAsyncTask get = new GetToastSettingsAsyncTask();
		get.execute(openPush, openSms, getPrizePush, getPrizeSms, zhuihaoPush,
				ZhuihaoSms);

	}

	/***
	 * 一部读取
	 * 
	 * @author anfangshuo
	 * 
	 */
	private class GetToastSettingsAsyncTask extends
			AsyncTask<CheckBox, String, String> {

		CheckBox[] chbParams;

		@Override
		protected void onPreExecute() {
			progressdialog = PublicMethod
					.creageProgressDialog(ToastSettingActivity.this);
		}

		@Override
		protected String doInBackground(CheckBox... params) {
			try {

				chbParams = params;
				JSONObject jsonProtocol = ProtocolManager.getInstance()
						.getDefaultJsonProtocol();

				jsonProtocol.put(ProtocolManager.COMMAND, "message");
				jsonProtocol.put(ProtocolManager.TYPE, "queryMessageSetting");
				jsonProtocol.put(ProtocolManager.USERNO, mUserNo);

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
				ToJson(result, chbParams);
			}
			PublicMethod.closeProgressDialog(progressdialog);
		}

	}

	private class SaveToastSettingsAsyncTask extends
			AsyncTask<CheckBox, String, String> {

		CheckBox[] chbParams;

		@Override
		protected void onPreExecute() {
			progressdialog = PublicMethod
					.creageProgressDialog(ToastSettingActivity.this);
		}

		@Override
		protected String doInBackground(CheckBox... params) {
			try {
				chbParams = params;

				JSONObject jsonProtocol = ProtocolManager.getInstance()
						.getDefaultJsonProtocol();

				jsonProtocol.put(ProtocolManager.COMMAND, "message");
				jsonProtocol.put(ProtocolManager.TYPE, "messageSetting");
				jsonProtocol.put(ProtocolManager.USERNO, mUserNo);
				jsonProtocol.put(ProtocolManager.INFO,
								"winInfo:1:140!win:1:141_push:1:369!subscribe:1:142_push:1:372");

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
					ReturnBean resultBean = JsonUtils.resultData(result,ReturnBean.class);
					if (resultBean != null) {
						if ("0000".equals(resultBean.getError_code())) {
							Toast.makeText(context,"设置成功",
									Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(context, resultBean.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			PublicMethod.closeProgressDialog(progressdialog);
		}

	}

	// 界面跳转
	private void Navigation() {
		relativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentAlarmSet = new Intent(ToastSettingActivity.this,
						LotnoAlarmSetActivity.class);
				startActivity(intentAlarmSet);
			}
		});
	}

	// 保存结果返回
	private void Save() {
		toastSettingsSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				SaveToastSettingsAsyncTask get = new SaveToastSettingsAsyncTask();
				get.execute(openPush, openSms, getPrizePush, getPrizeSms, zhuihaoPush,ZhuihaoSms);

//				Toast.makeText(ToastSettingActivity.this, "设置成功",
//						Toast.LENGTH_SHORT).show();
				// finish();
			}
		});
	}

	private void ToJson(String result, CheckBox[] chbParams) {
		try {
			JSONObject jsonObj = new JSONObject(result);

			String message = jsonObj.getString("message");
			String error_code = jsonObj.getString("error_code");
			JSONObject winInfo = jsonObj.getJSONObject("winInfo");
			JSONObject win = jsonObj.getJSONObject("win");
			JSONObject subscribe = jsonObj.getJSONObject("subscribe");
			if ("0000".equals(error_code) && winInfo != null && win != null
					&& subscribe != null) {
				SetCheckbox(winInfo, chbParams[0], chbParams[1]);

				SetCheckbox(win, chbParams[2], chbParams[3]);

				SetCheckbox(subscribe, chbParams[4], chbParams[5]);
			} else {
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SetCheckbox(JSONObject jsonObj, CheckBox chbPush,
			CheckBox chbSms) throws JSONException {
		int mainId = jsonObj.getInt("id");
		JSONArray jArray = jsonObj.getJSONArray("sendChannels");
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject obj = jArray.getJSONObject(i);
			Boolean bSms = obj.has("sms");
			int id = obj.getInt("id");
			if (bSms) {
				Integer sms = obj.getInt("sms");
				chbSms.setChecked(sms == 1);
				chbSms.setTag(mainId + "#" + id + "#" + sms);
			}

			Boolean bPush = obj.has("push");
			if (bPush) {
				Integer push = obj.getInt("push");
				chbPush.setChecked(push == 1);
				chbSms.setTag(mainId + "#" + id + "#" + push);
			}

		}
	}
}