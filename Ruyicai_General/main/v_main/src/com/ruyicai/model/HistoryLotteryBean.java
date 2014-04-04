package com.ruyicai.model;

public class HistoryLotteryBean {

	private String batchCode;
	private String winCode;
	private String tryCode;
	private String openTime;
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getWinCode() {
		return winCode;
	}
	public void setWinCode(String winCode) {
		this.winCode = winCode;
	}
	public String getTryCode() {
		return tryCode;
	}
	public void setTryCode(String tryCode) {
		this.tryCode = tryCode;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	@Override
	public String toString() {
		return "HistoryLotteryBean [batchCode=" + batchCode + ", winCode="
				+ winCode + ", tryCode=" + tryCode + ", openTime=" + openTime
				+ "]";
	}
	
}
