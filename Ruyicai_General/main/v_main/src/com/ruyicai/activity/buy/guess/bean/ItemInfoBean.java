package com.ruyicai.activity.buy.guess.bean;

public class ItemInfoBean {
	
	private String id = ""; 			//竞猜Id
	private String title = ""; 			//竞猜题目
	private String detail = "";			//竞猜题目详情
	private String isParticipate = "";	//竞猜参与状态 0:未参与;1:已参与
	private String prizePoolScore = ""; //竞猜奖池积分
	private String isEnd = "";			//竞猜是否截止 0:未结束;1:已结束
	private String state = "";			//竞猜开奖状态 0:未开奖;1:开奖中;2:已开奖
	private String time_remaining = "";	//竞猜剩余时间
	private String isWin = "";			//是否中奖
	private String payScore = "";		//我的投入
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getIsParticipate() {
		return isParticipate;
	}
	public void setIsParticipate(String isParticipate) {
		this.isParticipate = isParticipate;
	}
	public String getPrizePoolScore() {
		return prizePoolScore;
	}
	public void setPrizePoolScore(String prizePoolScore) {
		this.prizePoolScore = prizePoolScore;
	}
	public String getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTime_remaining() {
		return time_remaining;
	}
	public void setTime_remaining(String time_remaining) {
		this.time_remaining = time_remaining;
	}
	public String getIsWin() {
		return isWin;
	}
	public void setIsWin(String isWin) {
		this.isWin = isWin;
	}
	public String getPayScore() {
		return payScore;
	}
	public void setPayScore(String payScore) {
		this.payScore = payScore;
	}

}
