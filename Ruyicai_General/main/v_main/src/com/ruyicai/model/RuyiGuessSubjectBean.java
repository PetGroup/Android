package com.ruyicai.model;
/**
 * 竞猜题目数据模型类
 * @author wangw
 *
 */
public class RuyiGuessSubjectBean {

	private int id;			//题目ID
	private String name;	//竞猜题目名称
	private String lasTime;	//剩余时间
	private String playInfo;//玩法
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLasTime() {
		return lasTime;
	}
	public void setLasTime(String lasTime) {
		this.lasTime = lasTime;
	}
	public String getPlayInfo() {
		return playInfo;
	}
	public void setPlayInfo(String playInfo) {
		this.playInfo = playInfo;
	}
	
	
}
