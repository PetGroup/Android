package com.ruyicai.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ChampionshipBean implements Parcelable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9092850677409889694L;
	private String teamId = "";			//球队序号
	private String team = "";  			//球队名称
	private String probability = "";	//概率
	private String award = "";			//奖金
	private String eventId = "";		//赛事编号
	private String state = "";			//销售状态
	private String winState = "";		//中奖状态
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getProbability() {
		return probability;
	}
	public void setProbability(String probability) {
		this.probability = probability;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWinState() {
		return winState;
	}
	public void setWinState(String winState) {
		this.winState = winState;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(teamId);
		dest.writeString(team);
		dest.writeString(probability);
		dest.writeString(award);
		dest.writeString(eventId);
		dest.writeString(state);
		dest.writeString(winState);
	}
	
	public static final Parcelable.Creator<ChampionshipBean> CREATOR = new Parcelable.Creator<ChampionshipBean>() {

		@Override
		public ChampionshipBean createFromParcel(Parcel source) {
			ChampionshipBean bean = new ChampionshipBean();
			bean.teamId = source.readString();
			bean.team = source.readString();
			bean.probability = source.readString();
			bean.award = source.readString();
			bean.eventId = source.readString();
			bean.state = source.readString();
			bean.winState = source.readString();
			return bean;
		}

		@Override
		public ChampionshipBean[] newArray(int size) {
			return new ChampionshipBean[size];
		}
		
	};
	
	
}
