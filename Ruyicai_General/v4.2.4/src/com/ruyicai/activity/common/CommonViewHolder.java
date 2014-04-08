package com.ruyicai.activity.common;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommonViewHolder {

	public static class GroupViewHolder {
		public TextView titleTV;
	}
	
	public static class ChildViewHolder {
		public TextView gameName;
		public TextView gameNum;
		public TextView gameDate;
		public TextView gameTime;
		public TextView homeTeam;
		public TextView homeOdds;
		public TextView textVS;
		public TextView textOdds;
		public TextView guestTeam;
		public TextView guestOdds;
		public TextView analysis;
		public Button btnDan;
		public Button btnShowDetail;
		public LinearLayout homeLayout;
		public LinearLayout vsLayout;
		public LinearLayout guestLayout;
		public LinearLayout detailLayout;
		public View divider;
	}
}
