package com.ruyicai.activity.buy.guess.bean;

import java.util.List;

public class ItemDetailInfoBean {
	private String id = "";
	private String question = "";
	private String answerId = "";
	private String time_remaining = "";
	private String prizePoolScore = "";
	private String prizeScore = "";
	private String praise = "";
	private String tread = "";
	private String praiseOrTread = "";
	private List<ItemOptionBean> options = null;
	private String answer = "";
	private boolean isSelected = false;
	
	public String getPrizeScore() {
		return prizeScore;
	}
	public void setPrizeScore(String prizeScore) {
		this.prizeScore = prizeScore;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getPrizePoolScore() {
		return prizePoolScore;
	}
	public void setPrizePoolScore(String prizePoolScore) {
		this.prizePoolScore = prizePoolScore;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getPraise() {
		return praise;
	}
	public void setPraise(String praise) {
		this.praise = praise;
	}
	public String getTread() {
		return tread;
	}
	public void setTread(String tread) {
		this.tread = tread;
	}
	public String getPraiseOrTread() {
		return praiseOrTread;
	}
	public void setPraiseOrTread(String praiseOrTread) {
		this.praiseOrTread = praiseOrTread;
	}
	public String getTime_remaining() {
		return time_remaining;
	}
	public void setTime_remaining(String time_remaining) {
		this.time_remaining = time_remaining;
	}
	public List<ItemOptionBean> getOptions() {
		return options;
	}
	public void setOptions(List<ItemOptionBean> options) {
		this.options = options;
	}
}