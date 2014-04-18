package com.ruyicai.model;

import java.util.ArrayList;
import java.util.List;

public class PrizeInfoList {
	/**
	 * 返回结果
	 */
	private ReturnBean returnBean;
	/**
	 * 返回开奖结果
	 */
	private ArrayList<PrizeInfoBean> prizeInfoList;
	public ReturnBean getReturnBean() {
		return returnBean;
	}
	public void setReturnBean(ReturnBean returnBean) {
		this.returnBean = returnBean;
	}
	public ArrayList<PrizeInfoBean> getPrizeInfoList() {
		return prizeInfoList;
	}
	public void setPrizeInfoList(ArrayList<PrizeInfoBean> prizeInfoList) {
		this.prizeInfoList = prizeInfoList;
	}

}
