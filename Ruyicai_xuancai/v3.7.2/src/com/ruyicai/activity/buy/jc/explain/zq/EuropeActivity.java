package com.ruyicai.activity.buy.jc.explain.zq;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.activity.buy.jc.explain.zq.AsiaActivity.JcInfoAdapter.ViewHolder;
import com.ruyicai.activity.buy.jc.explain.zq.BaseListActivity.ExplainInfo;
import com.ruyicai.activity.buy.jc.zq.view.SPfView.JcInfoAdapter;
import com.ruyicai.activity.join.JoinDetailActivity;
import com.ruyicai.activity.join.JoinHallActivity;
import com.ruyicai.activity.join.JoinInfoActivity;
import com.ruyicai.activity.more.CaiPSY;
import com.ruyicai.activity.more.ChangJWT;
import com.ruyicai.activity.more.GongNZY;
import com.ruyicai.activity.more.HelpTitles;
import com.ruyicai.activity.more.LotteryGame;
import com.ruyicai.activity.more.MoreActivity;
import com.ruyicai.activity.more.TeSGN;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 欧指的界面
 * 
 * @author Administrator
 * 
 */
public class EuropeActivity extends BaseListActivity {
	public boolean isLq = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLQ();
		setVisable();
		initList();
	}

	public void setLQ() {
		this.isLq = false;
	}

	private void setVisable() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.jc_europe_top_layout);
		layout.setVisibility(View.VISIBLE);
		if (isLq) {
			TextView standoff = (TextView) findViewById(R.id.jc_europe_list_item_text_left_leve);
			TextView standoffLu = (TextView) findViewById(R.id.jc_europe_list_item_text1_center_leve);
			TextView k_s = (TextView) findViewById(R.id.jc_europe_list_item_text1_right_leve);
			standoff.setVisibility(View.GONE);
			standoffLu.setVisibility(View.GONE);
			k_s.setVisibility(View.GONE);
		}
	}

	public JSONArray getJsonArray() {
		JSONArray json = null;
		try {
			json = JcExplainActivity.jsonObject.getJSONObject("result")
					.getJSONArray("standards");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public void initList() {
		JSONArray json = getJsonArray();
		if (json != null) {
			listMain = (ListView) findViewById(R.id.buy_jc_main_listview);
			adapter = new JcInfoAdapter(context, getScoreInfo(json));
			listMain.setAdapter(adapter);
		}
	}

	protected List getScoreInfo(JSONArray jsonArray) {
		listInfo = new ArrayList<ExplainInfo>();
		for (int i = 0; i < jsonArray.length(); i++) {
			ExplainInfo info = new ExplainInfo();
			try {
				JSONObject json = jsonArray.getJSONObject(i);
				info.setCompanyName(json.getString("companyName"));
				info.setHomeWin(json.getString("homeWin"));
				info.setStandoff(json.getString("standoff"));
				info.setGuestWin(json.getString("guestWin"));
				info.setHomeWinLu(json.getString("homeWinLu"));
				info.setStandoffLu(json.getString("standoffLu"));
				info.setGuestWinLu(json.getString("guestWinLu"));
				info.setK_h(json.getString("k_h"));
				info.setK_s(json.getString("k_s"));
				info.setK_g(json.getString("k_g"));
				info.setFanHuanLu(json.getString("fanHuanLu"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listInfo.add(info);
		}
		return listInfo;
	}

	/**
	 * 竞彩的适配器
	 */
	public class JcInfoAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // 扩充主列表布局
		private List<ExplainInfo> mList;

		public JcInfoAdapter(Context context, List<ExplainInfo> list) {
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

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ExplainInfo info = mList.get(position);
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.jc_europe_list_item,
						null);
				holder = new ViewHolder();
				holder.companyName = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_company);
				holder.homeWin = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_left_win);
				holder.standoff = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_left_leve);
				holder.guestWin = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_left_fail);
				holder.standoffLu = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_center_leve);
				holder.homeWinLu = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_center_win);
				holder.guestWinLu = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_center_fail);
				holder.k_h = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_right_win);
				holder.k_s = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_right_leve);
				holder.k_g = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_right_fail);
				holder.fanHuanLu = (TextView) convertView
						.findViewById(R.id.jc_europe_list_item_text_give);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (isLq) {
				holder.standoffLu.setVisibility(View.GONE);
				holder.standoff.setVisibility(View.GONE);
				holder.k_s.setVisibility(View.GONE);
			} else {
				holder.standoffLu.setText(info.getStandoffLu());
				holder.standoff.setText(info.getStandoff());
				holder.k_s.setText(info.getK_s());
			}
			holder.companyName.setText(info.getCompanyName());
			holder.homeWin.setText(info.getHomeWin());
			holder.guestWin.setText(info.getGuestWin());
			holder.homeWinLu.setText(info.getHomeWinLu());
			holder.guestWinLu.setText(info.getGuestWinLu());
			holder.k_h.setText(info.getK_h());
			holder.k_g.setText(info.getK_g());
			holder.fanHuanLu.setText(info.getFanHuanLu());
			return convertView;
		}

		class ViewHolder {

			TextView companyName;// 公司名
			TextView fanHuanLu;// 返还率
			TextView homeWinLu;// 主胜率
			TextView standoffLu;// 平局率
			TextView guestWinLu;// 客胜率
			TextView k_h;// 主凯指
			TextView k_s;// 平凯指
			TextView k_g;// 客凯指
			TextView homeWin;// 即时主胜赔率
			TextView standoff;// 即时平局赔率
			TextView guestWin;// 即时客胜赔率
		}
	}

}
