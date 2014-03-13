package com.ruyicai.model;

import java.io.Serializable;

public class ChampionshipBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9092850677409889694L;
	private String teamId = "";			//球队序号
	private String team = "";  			//球队名称
	private String probability = "";	//概率
	private String award = "";			//奖金
	private String eventId = "";		//赛事编号
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
}
