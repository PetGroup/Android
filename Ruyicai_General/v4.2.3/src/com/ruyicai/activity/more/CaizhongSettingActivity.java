package com.ruyicai.activity.more;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.home.HomeActivity;
import com.ruyicai.activity.notice.NoticeMainActivity;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.util.RWSharedPreferences;

public class CaizhongSettingActivity extends Activity {
	private ListView caizhongSettListView;
	private List<Map<String, String>> caizhongList = new ArrayList<Map<String, String>>();
	private Map<String, String> map;

	private RWSharedPreferences shellRW;
	private String[] titles = { "合买大厅", "如意竞猜", "双色球", "大乐透", "福彩3D", "江西11选5", "时时彩",
			"猜冠军", "竞足彩", "新快三", "快三", "11运夺金", "专家荐号", "广东11选5", "排列三", "七乐彩", "22选5",
			"排列五", "七星彩", "足彩", "竞篮彩", "广东快乐十分", "北京单场", "重庆11选5"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caizhong_setting);
		caizhongSettListView = (ListView) findViewById(R.id.caizhong_setting_ListView);
		if(Constants.shellRWList==null){
			Constants.shellRWList = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			for (int i = 0; i < Constants.lotnoNameList.length; i++) {
				map = new HashMap<String, String>();
				map.put("shellKey", Constants.lotnoNameList[i][1]);
				map.put("shellName", titles[i].toString());
				Constants.shellRWList.add(map);
			}
		}
		CaizhongSettingAdapter caizhongSettingAdapter = new CaizhongSettingAdapter(
				this, Constants.shellRWList);
		caizhongSettListView.setAdapter(caizhongSettingAdapter);
		shellRW = new RWSharedPreferences(CaizhongSettingActivity.this,
				ShellRWConstants.CAIZHONGSETTING);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean isNull = false;
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
			for (int i = 0; i < Constants.shellRWList.size(); i++) {
				if (shellRW.getStringValue(
						Constants.shellRWList.get(i).get("shellKey")
								.toString()).equals(Constants.CAIZHONG_OPEN)) {
					isNull = true;
				}
			}
		}
		if (isNull) {
			return super.onKeyDown(keyCode, event);
		} else {
			Toast.makeText(CaizhongSettingActivity.this, "请至少选择一项",
					Toast.LENGTH_SHORT).show();
			return true;
		}
	}
}