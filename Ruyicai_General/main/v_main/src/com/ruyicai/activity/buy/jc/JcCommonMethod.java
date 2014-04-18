package com.ruyicai.activity.buy.jc;


import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.activity.common.CommonViewHolder;
import com.ruyicai.util.PublicMethod;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JcCommonMethod {
	public static CommonViewHolder.ChildViewHolder initChildViewHolder(
			View convertView) {
		CommonViewHolder.ChildViewHolder holder = new CommonViewHolder.ChildViewHolder();
		holder.divider = (View) convertView
				.findViewById(R.id.jc_main_divider_up);
		holder.gameName = (TextView) convertView.findViewById(R.id.game_name);
		holder.gameNum = (TextView) convertView.findViewById(R.id.game_num);
		holder.gameDate = (TextView) convertView.findViewById(R.id.game_date);
		holder.gameTime = (TextView) convertView.findViewById(R.id.game_time);
		holder.homeLayout = (LinearLayout) convertView
				.findViewById(R.id.home_layout);
		holder.homeTeam = (TextView) convertView
				.findViewById(R.id.home_team_name);
		holder.homeOdds = (TextView) convertView
				.findViewById(R.id.home_team_odds);
		holder.vsLayout = (LinearLayout) convertView
				.findViewById(R.id.vs_layout);
		holder.textVS = (TextView) convertView.findViewById(R.id.game_vs);
		holder.textOdds = (TextView) convertView
				.findViewById(R.id.game_vs_odds);
		holder.guestLayout = (LinearLayout) convertView
				.findViewById(R.id.guest_layout);
		holder.guestTeam = (TextView) convertView
				.findViewById(R.id.guest_team_name);
		holder.guestOdds = (TextView) convertView
				.findViewById(R.id.guest_team_odds);
		holder.analysis = (TextView) convertView
				.findViewById(R.id.game_analysis);
		holder.btnDan = (Button) convertView.findViewById(R.id.game_dan);
		holder.btnShowDetail = (Button) convertView
				.findViewById(R.id.jc_main_list_item_button);
		holder.detailLayout = (LinearLayout) convertView
				.findViewById(R.id.jc_play_detail_layout);
		return holder;
	}

	public static void setDividerShowState(int childPosition,
			CommonViewHolder.ChildViewHolder holder) {
		if (childPosition == 0) {
			holder.divider.setVisibility(View.VISIBLE);
		} else {
			holder.divider.setVisibility(View.GONE);
		}
	}

	public static void setTeamTime(Info info,
			CommonViewHolder.ChildViewHolder holder) {
		String num = info.getTeamId();
		String date = PublicMethod.getTime(info.getTimeEnd());
		String time = PublicMethod.getEndTime(info.getTimeEnd()) + " " + "(截)";
		holder.gameNum.setText(num);
		holder.gameDate.setText(date);
		holder.gameTime.setText(time);
		holder.gameName.setText(info.getTeam());
	}

	public static void setJcZqTeamName(Info info,
			CommonViewHolder.ChildViewHolder holder) {
		holder.homeTeam.setText(info.getHome());
		holder.guestTeam.setText(info.getAway());
	}

	public static void setJcLqTeamName(Info info,
			CommonViewHolder.ChildViewHolder holder) {
		holder.homeTeam.setText(info.getAway() + "(客)");
		holder.guestTeam.setText(info.getHome() + "(主)");
	}

	public static void setBtnText(Info info,
			CommonViewHolder.ChildViewHolder holder) {
		if (info.getBtnStr() == null) {
			holder.btnShowDetail.setText("");
		} else {
			holder.btnShowDetail.setText(info.getBtnStr());
		}
	}
	
}
