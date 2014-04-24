package com.ruyicai.activity.buy.jc.zq.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.JcMainActivity;
import com.ruyicai.data.db.GyjMap;
import com.ruyicai.model.ChampionshipBean;
import com.umeng.analytics.MobclickAgent;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChampionshipAdapter extends BaseAdapter {
	private List<ChampionshipBean> list= null;
	private LayoutInflater inflater = null;
	private Context context = null;
	private Map<Integer, Boolean> selectTeamMap = new HashMap<Integer, Boolean>();
	private boolean isWorldCup = true;
//	private final String worldCupEventId = "01";
//	private final String europeEventId = "02";
	private int white = 0 ;
	private int black = 0;
	private int red = 0;
	private int eliminateColor = 0;
	private int gray = 0;
	
	
	public ChampionshipAdapter(List<ChampionshipBean> list, Context context, boolean isWorldCup) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.isWorldCup = isWorldCup;
		initTextColor(context);
	}
	
	private void initTextColor(Context context) {
		Resources resources = context.getResources();
		white = resources.getColor(R.color.white);
		gray = resources.getColor(R.color.jc_odds_text_color);
		black = resources.getColor(R.color.black);
		red = resources.getColor(R.color.red);
		eliminateColor = resources.getColor(R.color.jc_gyj_eliminate_text_color);
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ChampionshipBean info = list.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.buy_jc_gyj_listview_item, null);
			holder = new ViewHolder();
			holder.teamId = (TextView) convertView
					.findViewById(R.id.buy_jc_gyj_team_id);
			holder.teamName = (TextView) convertView
					.findViewById(R.id.buy_jc_gyj_team_name);
			holder.teamProbability = (TextView) convertView
					.findViewById(R.id.buy_jc_gyj_team_probability);
			holder.teamAward = (TextView) convertView
					.findViewById(R.id.buy_jc_gyj_team_award);
			holder.teamIcon = (ImageView) convertView
					.findViewById(R.id.buy_jc_gyj_team_icon);
			holder.itemLayout = (RelativeLayout) convertView
					.findViewById(R.id.buy_jc_gyj_item_layout);
			holder.layout = (LinearLayout) convertView
					.findViewById(R.id.buy_jc_gyj_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.teamName.setText(info.getTeam());
		holder.teamId.setText(info.getTeamId());
		if (position == 0) {
			holder.teamProbability.setText("概率"+info.getProbability());
		} else {
			holder.teamProbability.setText(info.getProbability());
		}
		
		if (isWorldCup) {
			if (GyjMap.getWorldCupMap() != null && GyjMap.getWorldCupMap().containsKey(info.getTeam())) {
				holder.teamIcon.setImageResource(GyjMap.getWorldCupMap().get(info.getTeam()));
			} else {
				holder.teamIcon.setImageDrawable(null);
			}
		} else {
			if (GyjMap.getEuropeLeagueMap() != null && GyjMap.getEuropeLeagueMap().containsKey(info.getTeam())) {
				holder.teamIcon.setImageResource(GyjMap.getEuropeLeagueMap().get(info.getTeam()));
			} else {
				holder.teamIcon.setImageDrawable(null);
			}
		}
		
		setBgShowState(holder, selectTeamMap.containsKey(position) && selectTeamMap.get(position));
		if ("1".equals(info.getWinState())) {
			setViewState(holder);
			if (isWorldCup) {
				holder.teamAward.setBackgroundResource(R.drawable.buy_gyj_champion);
			} else {
				holder.teamAward.setBackgroundResource(R.drawable.buy_gyj_europe);
			}
			holder.teamAward.setText("");
			holder.teamName.setTextColor(red);
			TextPaint teamNameTp = holder.teamName.getPaint();
			teamNameTp.setFakeBoldText(true);
		} else {
			holder.teamAward.setBackgroundDrawable(null);
			if ("0".equals(info.getState())) {
				holder.teamAward.setText(info.getAward());
				setViewOnClick(holder, position);
			} else {
				setViewState(holder);
				holder.teamName.setTextColor(eliminateColor);
				holder.teamAward.setTextColor(eliminateColor);
				holder.teamAward.setText("已淘汰");
			}
		}
		
		return convertView;
	}
	
	private void setViewState(ViewHolder holder) {
		holder.layout.setClickable(false);
		holder.teamId.setTextColor(eliminateColor);
		holder.teamProbability.setTextColor(eliminateColor);
	}
	
	private void setViewOnClick(final ViewHolder holder, final int position) {
		holder.layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectTeamMap.containsKey(position)) {
					if (selectTeamMap.get(position)) {
						selectTeamMap.remove(position);
						setBgShowState(holder, false);
					} else {
						selectTeamMap.put(position, true);
						setBgShowState(holder, true);
					}
				} else {
					selectTeamMap.put(position, true);
					setBgShowState(holder, true);
				}
				if (context instanceof JcMainActivity) {
					JcMainActivity activity = (JcMainActivity)context;
					activity.setTeamNum(selectTeamMap.size());
				}
				MobclickAgent.onEvent(context, "jcgyjtouzhu_qiuduixuanze");
			}
		});
	}
	
	private void setBgShowState(ViewHolder holder, boolean flag) {
		TextPaint teamIdTp = holder.teamId.getPaint(); 
		TextPaint teamNameTp = holder.teamName.getPaint();
		TextPaint teamAwardTp = holder.teamAward.getPaint();
		holder.teamProbability.setTextColor(red);
		if (flag) {
			holder.teamId.setBackgroundResource(R.drawable.buy_jczq_gyj_item_id_click);
			holder.itemLayout.setBackgroundResource(R.drawable.buy_jczq_gyj_item_name_click);
			holder.teamId.setTextColor(white);
			holder.teamName.setTextColor(white);
			holder.teamAward.setTextColor(white);
			teamIdTp.setFakeBoldText(true);
			teamNameTp.setFakeBoldText(true);
			teamAwardTp.setFakeBoldText(true);
		} else {
			holder.teamId.setBackgroundResource(R.drawable.buy_jczq_gyj_item_id_normal);
			holder.itemLayout.setBackgroundResource(R.drawable.buy_jczq_gyj_item_name_normal);
			holder.teamId.setTextColor(gray);
			holder.teamName.setTextColor(black);
			holder.teamAward.setTextColor(black);
			teamIdTp.setFakeBoldText(false);
			teamNameTp.setFakeBoldText(false);
			teamAwardTp.setFakeBoldText(false);
		}
	}
	
	class ViewHolder {
		TextView teamId; //球队序号
		TextView teamName; //球队名字
		TextView teamProbability; //球队概率
		TextView teamAward; //奖金
		ImageView teamIcon; //球队图标
		RelativeLayout itemLayout;
		LinearLayout layout;
	}
	
	public Map<Integer, Boolean> getSelectTeamMap(){
		return selectTeamMap;
	}
	
	/**
	 * 获取注码
	 * @return
	 */
	public String getCode() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("500@");
		buffer.append(list.get(0).getEventId());
//		if (isWorldCup) {
//			buffer.append(worldCupEventId);
//		} else {
//			buffer.append(europeEventId);
//		}
		buffer.append("|");		
		for (Entry<Integer, Boolean> entry : selectTeamMap.entrySet()) {
			ChampionshipBean info = list.get(entry.getKey());
			buffer.append(info.getTeamId());
		}
		return buffer.toString();
	}
	
	/**
	 * 获取方案内容
	 * @return
	 */
	public String getAlertCode() {
		StringBuffer buffer = new StringBuffer();
		for (Entry<Integer, Boolean> entry : selectTeamMap.entrySet()) {
			ChampionshipBean info = list.get(entry.getKey());
			buffer.append(info.getTeamId()+ " " + info.getTeam() + " " +info.getAward());
//			buffer.append(PublicMethod.stringToHtml(info.getTeamId()+ " " + info.getTeam() + " " +info.getAward(),
//					Constants.JC_TOUZHU_TITLE_TEXT_COLOR));
			buffer.append("<br>");
		}
		return buffer.toString();
	}
	
	/**
	 * 获取预计奖金
	 * @return
	 */
	public float getGyjPrize() {
		try {
			List<Float> prizeList = new ArrayList<Float>();
			for (Entry<Integer, Boolean> entry : selectTeamMap.entrySet()) {
				ChampionshipBean info = list.get(entry.getKey());
				prizeList.add(Float.valueOf(info.getAward().trim()));
			}
			Collections.sort(prizeList);
			return prizeList.get(prizeList.size() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0f;
	}
}
